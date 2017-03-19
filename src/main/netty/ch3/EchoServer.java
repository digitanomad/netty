package netty.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		// NioEventLoopGroup Ŭ������ �μ� ���� �����ڴ� ����� ������ ���� ���� ���ø����̼��� ���ؤ��ϴ� �ϵ���� �ھ� ���� �������� ����
		// ������� �ϵ��� ������ �ִ� CPU �ھ� ���� 2�踦 ����Ѵ�.
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			// ���� ���ø����̼��� ����� �� ������ �׷��� �����ߴ�.
			// ù��° ������ �׷��� Ŭ���̾�Ʈ�� ������ �����ϴ� �θ� ������ �׷�
			// �ι�° ������ �׷��� ����� Ŭ���̾�Ʈ�� �������κ��� ������ ����� �� �̺�Ʈ ó���� ����ϴ� �ڽ� ������ �׷�
			b.group(bossGroup, workerGroup)
			// ���� ����(�θ𽺷���)�� ����� ��Ʈ��ũ ����� ��带 �����Ѵ�.
			// NioServerSocketChannel Ŭ������ �����߱� ������ NIO ���� �����Ѵ�.
			 .channel(NioServerSocketChannel.class)
			// �ڽ� ä���� �ʱ�ȭ ����� �����Ѵ�. ���⼭�� �͸�Ŭ������ ä�� �ʱ�ȭ ����� �����ߴ�.
			 .childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					// ä�� ���������� ��ü�� �����ϰ�, EchoServerHandler Ŭ������ ����Ѵ�.
					// EchoServerHandler Ŭ������ ���Ŀ� Ŭ���̾�Ʈ�� ������ �����Ǿ��� �� ������ ó���� ����Ѵ�.
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
