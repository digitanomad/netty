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
			
			// 에코 서버가 8888번 포트를 사용하도록 바인드하는 비동기 bind 메소드를 호출한다.
			// 부트스트랩 클래스의 bind 메소도는 포트 바인딩이 완료되기 전에 ChannelFuture 객체를 돌려준다.
			ChannelFuture bindFuture = b.bind(8888);
			// ChannelFuture 인터페이스의 sync 메소드는 주어진 ChannelFuture 객체의 작업이 완료될 때까지 블로킹하는 메소드다.
			// 그러므로 bind 메소드의 처리가 완료될 때 sync 메소드도 같이 완료된다.
			bindFuture.sync();
			
			// bindFuture 객체를 통해 채널을 얻어온다. 여기서 얻어진 채널은 8888번 포트에 바인딩된 서버 채널이다.
			Channel serverChannel = bindFuture.channel();
			// 바인드가 완료된 서버 채널의 CloseFuture 객체를 돌려준다.
			// 네티 내부에서는 채널이 생성될 때 CloseFuture 객체도 같이 생성되므로 closeFuture 메소드가 돌려주는 CloseFuture 객체는 항상 동일한 객체다.
			ChannelFuture closeFuture = serverChannel.closeFuture();
			// CloseFuture 객체는 채널의 연결이 종료될 때 연결 종료 이벤트를 받는다.
			// 채널이 생성될 때 같이 생성되는 기본 CloseFuture 객체에는 아무 동작도 설정되어 있지 않으므로 이벤트를 받았을 때 아무 동작도 하지 않는다.
			closeFuture.sync();
			
		} finally {
			workerGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
