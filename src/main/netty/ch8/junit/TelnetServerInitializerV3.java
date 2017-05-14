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
		
		// ������ �����ڸ� �������� �����͸� �����Ͽ� ó���Ѵ�. ���ŵ� �������� �ִ� ũ��� 8192����Ʈ�̰� �ش� �������� �������� �ٹٲ� ���ڷ� ���еȴٴ� ���̴�.
		// lineDelimiter �޼ҵ�� �ٹٲ� ���ڰ� ���Ե� ����Ʈ ���� ��ü �迭�� �����ش�.
		// ����Ʈ ���� ��ü �迭���� �������� �ٹٲ� ������ CRLF�� ������ �ٹٲ� ������ LF�� ���ԵǾ� �ִ�.
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		pipeline.addLast(DECODER);
		pipeline.addLast(ENCODER);
		pipeline.addLast(SERVER_HANDLER);
	}

}
