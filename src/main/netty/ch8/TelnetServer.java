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
			// ��Ʈ��Ʈ�� ��ü�� bind �޼ҵ��� ó���� �Ϸ�� ������ ����Ѵ�. ó���� �Ϸ�Ǹ� ������ ����ä�ο� ���� ChannelFuture ��ü�� �����ش�.
			ChannelFuture future = b.bind(PORT).sync();
			// ChannelFuture ��ü�� �����ϴ� ���� ä�ο� close �̺�Ʈ�� �߻��� ������ ����Ѵ�.
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
