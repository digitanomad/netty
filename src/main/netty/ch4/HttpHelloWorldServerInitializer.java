package netty.ch4;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;

public class HttpHelloWorldServerInitializer extends
		ChannelInitializer<SocketChannel> {
	
	private final SslContext sslCtx;
	
	public HttpHelloWorldServerInitializer(SslContext sslCtx) {
		this.sslCtx = sslCtx;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		if (sslCtx != null) {
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		// 네티가 제공하는 HTTP 서버 코덱이다. 
		// 이 코덱은 인바운드와 아웃바운드 핸들러를 모두 구현한다. HttpServerCodec의 생성자에서 HttpRequestDecoder와 HttpResponseEncoder를 모두 생성한다.
		// HttpServerCodec은 간단한 웹 서버를 생성하는 데 사용되는 코덱으로서, 수신된 ByteBuf 객체를 HttpRequest와 HttpContent 객체로 변환하고 HttpResponse 객체를 ByteBuf로 인코딩하여 송신한다.
		p.addLast(new HttpServerCodec());
		// HttpServerCodec이 수신한 이벤트와 데이터를 처리하여 HTTP 객체로 변환한 다음 channelRead 이벤트를 HttpHelloWorldServerHandler 클래스로 전달한다.
		p.addLast(new HttpHelloWorldServerHandler());
	}

}
