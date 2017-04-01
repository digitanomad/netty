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
			 // 클라이언트 소켓 채널이 사용할 채널 파이프라인을 설정
			 .childHandler(new ChannelInitializer<Channel>() {
				 // initChannel 메소드는 클라이언트 소켓 채널이 성성될 때 자동으로 호출되는데 이 때 채널 파이프라인의 설정을 수행한다.
				@Override
				protected void initChannel(Channel ch) throws Exception {
					// 네티 내부에서 클라이언트 소켓 채널을 생성할 때 빈 채널 파이프라인 객체를 생성하여 할당한다.
					ChannelPipeline p = ch.pipeline();
					// 이벤트 핸들러를 채널 파이프라인에 등록
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
