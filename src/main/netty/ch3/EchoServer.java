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
		// NioEventLoopGroup 클래스의 인수 없는 생성자는 사용할 스레드 수를 서버 애플리케이션이 도앚ㄱ하는 하드웨어 코어 수를 기준으로 설정
		// 스레드는 하드웨어가 가지고 있는 CPU 코어 수의 2배를 사용한다.
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			// 서버 애플리케이션이 사용할 두 스레드 그룹을 설정했다.
			// 첫번째 스레드 그룹은 클라이언트의 연결을 수락하는 부모 스레드 그룹
			// 두번째 스레드 그룹은 연결된 클라이언트의 소켓으로부터 데이터 입출력 및 이벤트 처리를 담당하는 자식 스레드 그룹
			b.group(bossGroup, workerGroup)
			// 서버 소켓(부모스레드)가 사용할 네트워크 입출력 모드를 설정한다.
			// NioServerSocketChannel 클래스를 설정했기 때문에 NIO 모드로 동작한다.
			 .channel(NioServerSocketChannel.class)
			// 자식 채널의 초기화 방법을 설정한다. 여기서는 익명클래스로 채널 초기화 방법을 지정했다.
			 .childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					// 채널 파이프라인 객체를 생성하고, EchoServerHandler 클래스를 등록한다.
					// EchoServerHandler 클래스는 이후에 클라이언트의 연결이 생성되었을 때 데이터 처리를 담당한다.
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
