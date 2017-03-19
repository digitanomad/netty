package netty.ch3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			 .channel(NioSocketChannel.class)
			 // 클라이언트 소켓 채널의 이벤트 핸들러를 설정
			 .handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoClientHandler());
				}
			});
			
			ChannelFuture f = b.connect("localhost", 8888).sync();
			f.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
}
