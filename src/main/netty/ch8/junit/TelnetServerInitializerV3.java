package netty.ch8.junit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class TelnetServerInitializerV3 extends ChannelInitializer<SocketChannel> {
	private static final StringDecoder DECODER = new StringDecoder();
	private static final StringEncoder ENCODER = new StringEncoder();
	
	private static final TelnetServerHandlerV3 SERVER_HANDLER = new TelnetServerHandlerV3();
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		// 지정한 구분자를 기준으로 데이터를 구분하여 처리한다. 수신된 데이터의 최대 크기는 8192바이트이고 해당 데이터의 마지막은 줄바꿈 문자로 구분된다는 뜻이다.
		// lineDelimiter 메소드는 줄바꿈 문자가 포함된 바이트 버퍼 객체 배열을 돌려준다.
		// 바이트 버퍼 객체 배열에는 윈도우의 줄바꿈 문자인 CRLF와 리눅스 줄바꿈 문자인 LF가 포함되어 있다.
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(DECODER);
		pipeline.addLast(ENCODER);
		pipeline.addLast(SERVER_HANDLER);
	}

}
