package netty.ch5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServerWithFuture {
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			 .channel(NioServerSocketChannel.class)
			 .childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoServerHandlerWithFuture());
				}
			});
			
			// ���� ������ 8888�� ��Ʈ�� ����ϵ��� ���ε��ϴ� �񵿱� bind �޼ҵ带 ȣ���Ѵ�.
			// ��Ʈ��Ʈ�� Ŭ������ bind �޼ҵ��� ��Ʈ ���ε��� �Ϸ�Ǳ� ���� ChannelFuture ��ü�� �����ش�.
			ChannelFuture bindFuture = b.bind(8888);
			// ChannelFuture �������̽��� sync �޼ҵ�� �־��� ChannelFuture ��ü�� �۾��� �Ϸ�� ������ ���ŷ�ϴ� �޼ҵ��.
			// �׷��Ƿ� bind �޼ҵ��� ó���� �Ϸ�� �� sync �޼ҵ嵵 ���� �Ϸ�ȴ�.
			bindFuture.sync();
			
			// bindFuture ��ü�� ���� ä���� ���´�. ���⼭ ����� ä���� 8888�� ��Ʈ�� ���ε��� ���� ä���̴�.
			Channel serverChannel = bindFuture.channel();
			// ���ε尡 �Ϸ�� ���� ä���� CloseFuture ��ü�� �����ش�.
			// ��Ƽ ���ο����� ä���� ������ �� CloseFuture ��ü�� ���� �����ǹǷ� closeFuture �޼ҵ尡 �����ִ� CloseFuture ��ü�� �׻� ������ ��ü��.
			ChannelFuture closeFuture = serverChannel.closeFuture();
			// CloseFuture ��ü�� ä���� ������ ����� �� ���� ���� �̺�Ʈ�� �޴´�.
			// ä���� ������ �� ���� �����Ǵ� �⺻ CloseFuture ��ü���� �ƹ� ���۵� �����Ǿ� ���� �����Ƿ� �̺�Ʈ�� �޾��� �� �ƹ� ���۵� ���� �ʴ´�.
			closeFuture.sync();
			
		} finally {
			workerGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
