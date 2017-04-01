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
		// ��Ƽ�� �����ϴ� HTTP ���� �ڵ��̴�. 
		// �� �ڵ��� �ιٿ��� �ƿ��ٿ�� �ڵ鷯�� ��� �����Ѵ�. HttpServerCodec�� �����ڿ��� HttpRequestDecoder�� HttpResponseEncoder�� ��� �����Ѵ�.
		// HttpServerCodec�� ������ �� ������ �����ϴ� �� ���Ǵ� �ڵ����μ�, ���ŵ� ByteBuf ��ü�� HttpRequest�� HttpContent ��ü�� ��ȯ�ϰ� HttpResponse ��ü�� ByteBuf�� ���ڵ��Ͽ� �۽��Ѵ�.
		p.addLast(new HttpServerCodec());
		// HttpServerCodec�� ������ �̺�Ʈ�� �����͸� ó���Ͽ� HTTP ��ü�� ��ȯ�� ���� channelRead �̺�Ʈ�� HttpHelloWorldServerHandler Ŭ������ �����Ѵ�.
		p.addLast(new HttpHelloWorldServerHandler());
	}

}
