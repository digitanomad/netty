package netty.ch8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public final class TelnetServer {
	private static final int PORT = 8023;
	
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			 .channel(NioServerSocketChannel.class)
			 .handler(new LoggingHandler(LogLevel.INFO))
			 .childHandler(new TelnetServerInitializer());
			// 부트스트랩 객체의 bind 메소드의 처리가 완료될 때까지 대기한다. 처리가 완료되면 생성된 서버채널에 대한 ChannelFuture 객체를 돌려준다.
			ChannelFuture future = b.bind(PORT).sync();
			// ChannelFuture 객체가 참조하는 서버 채널에 close 이벤트가 발생할 때까지 대기한다.
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
