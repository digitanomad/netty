package netty.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServerV4 {
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			 .channel(NioServerSocketChannel.class)
			 // childOption 메서드는 서버에 접속한 클라이언트 소켓 채널에 대한 옵션을 설정하는 데 사용한다.
			 // 소켓에 대하여 close 메서드를 호출한 이후 제어권은 애플리케이션에서 운영체제로 넘어가는데
			 // 이 때 커널 버퍼에 아직 전송되지 않은 데이터가 남아있으면 커널 버퍼의 데이터를 상대방으로 모두 전송하고, 상대방의 ACK 패킷을 기다린다.
			 .childOption(ChannelOption.SO_LINGER, 0)
			 .childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
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
