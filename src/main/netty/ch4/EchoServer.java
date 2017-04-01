package netty.ch4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			 .channel(NioServerSocketChannel.class)
			 // Ŭ���̾�Ʈ ���� ä���� ����� ä�� ������������ ����
			 .childHandler(new ChannelInitializer<Channel>() {
				 // initChannel �޼ҵ�� Ŭ���̾�Ʈ ���� ä���� ������ �� �ڵ����� ȣ��Ǵµ� �� �� ä�� ������������ ������ �����Ѵ�.
				@Override
				protected void initChannel(Channel ch) throws Exception {
					// ��Ƽ ���ο��� Ŭ���̾�Ʈ ���� ä���� ������ �� �� ä�� ���������� ��ü�� �����Ͽ� �Ҵ��Ѵ�.
					ChannelPipeline p = ch.pipeline();
					// �̺�Ʈ �ڵ鷯�� ä�� ���������ο� ���
					p.addLast(new EchoServerHandler());
				}
			});
			
			ChannelFuture f = b.bind(8888).sync();
			
			f.channel().closeFuture().sync();
			
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
