package netty.ch8.junit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

import netty.ch8.TelnetServerInitializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public final class TelnetServerV3 {
	@Autowired
	// ������ ���ؽ�Ʈ�� ������ ��ü �̸� �߿� tcpSocketAddress�� �ش��ϴ� ��ü�� �Ҵ��ϵ��� �����Ѵ�.
	@Qualifier("tcpSocketAddress")
	private InetSocketAddress port;
	
	public void start() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGorup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGorup)
			 .channel(NioServerSocketChannel.class)
			 .childHandler(new TelnetServerInitializer());
			
			ChannelFuture future = b.bind(port).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGorup.shutdownGracefully();
		}
	}
}
