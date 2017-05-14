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
		String writeData = "안녕하세요";
		ByteBuf request = Unpooled.wrappedBuffer(writeData.getBytes());
		
		Base64Encoder encoder = new Base64Encoder();
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(encoder);
		
		// writeOutbound 메소드로 EmbeddedChannel의 아웃바운드에 데이터를 기록한다.
		embeddedChannel.writeOutbound(request);
		// readOutbound 메소드로 Base64Encoder의 인코딩 결과를 조회한다.
		ByteBuf response = (ByteBuf)embeddedChannel.readOutbound();
		
		String expect = "vsiz58fPvLy/5A==";
		assertEquals(expect, response.toString(Charset.defaultCharset()));
		
		embeddedChannel.finish();
	}
}
