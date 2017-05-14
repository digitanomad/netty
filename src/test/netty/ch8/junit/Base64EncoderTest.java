package netty.ch8.junit;

import static org.junit.Assert.*;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.base64.Base64Encoder;

import org.junit.Test;

public class Base64EncoderTest {
	@Test
	public void testEncoder() {
		String writeData = "�ȳ��ϼ���";
		ByteBuf request = Unpooled.wrappedBuffer(writeData.getBytes());
		
		Base64Encoder encoder = new Base64Encoder();
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(encoder);
		
		// writeOutbound �޼ҵ�� EmbeddedChannel�� �ƿ��ٿ�忡 �����͸� ����Ѵ�.
		embeddedChannel.writeOutbound(request);
		// readOutbound �޼ҵ�� Base64Encoder�� ���ڵ� ����� ��ȸ�Ѵ�.
		ByteBuf response = (ByteBuf)embeddedChannel.readOutbound();
		
		String expect = "vsiz58fPvLy/5A==";
		assertEquals(expect, response.toString(Charset.defaultCharset()));
		
		embeddedChannel.finish();
	}
}
