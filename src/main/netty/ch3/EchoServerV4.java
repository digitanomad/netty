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
			 // childOption �޼���� ������ ������ Ŭ���̾�Ʈ ���� ä�ο� ���� �ɼ��� �����ϴ� �� ����Ѵ�.
			 // ���Ͽ� ���Ͽ� close �޼��带 ȣ���� ���� ������� ���ø����̼ǿ��� �ü���� �Ѿ�µ�
			 // �� �� Ŀ�� ���ۿ� ���� ���۵��� ���� �����Ͱ� ���������� Ŀ�� ������ �����͸� �������� ��� �����ϰ�, ������ ACK ��Ŷ�� ��ٸ���.
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
