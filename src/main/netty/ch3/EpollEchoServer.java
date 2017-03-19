package netty.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;

public class EpollEchoServer {
	public static void main(String[] args) throws InterruptedException {
		EventLoopGroup bossGroup = new EpollEventLoopGroup(1);
		EventLoopGroup workerGroup = new EpollEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			 .channel(EpollServerSocketChannel.class)
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
