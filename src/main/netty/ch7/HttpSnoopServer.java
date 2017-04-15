package netty.ch7;

import java.io.File;

import javax.net.ssl.SSLException;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

public class HttpSnoopServer {
	// SSL/TLS 연결을 수락하기 위한 포트
	// SSL/TLS 연결을 위한 기본 포트는 443번이다.
	private static final int PORT = 8443;
	
	public static void main(String[] args) throws InterruptedException {
		SslContext sslCtx = null;
		
		try {
			File certChainFile = new File("netty.crt");
			File keyFile = new File("privatekey.pem");
			
			// 인증서와 개인키를 사용하여 SslContext 객체를 생성한다. 맨 마지막 인수는 개인키를 생성할 때 입력한 암호이다.
			sslCtx = SslContextBuilder.forServer(certChainFile, keyFile, "pass").build();
			
		} catch (SSLException e) {
			e.printStackTrace();
		}
		
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			 .channel(NioServerSocketChannel.class)
			 .handler(new LoggingHandler(LogLevel.INFO))
			 .childHandler(new HttpSnoopServerInitializer(sslCtx));
			
			Channel ch = b.bind(PORT).sync().channel();
			
			ch.closeFuture().sync();
			
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
}
