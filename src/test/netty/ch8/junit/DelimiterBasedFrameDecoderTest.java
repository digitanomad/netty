package netty.ch8.junit;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

import org.junit.Test;

import static org.junit.Assert.*;

public class DelimiterBasedFrameDecoderTest {
	@Test
	public void testDecoder() {
		String writeData = "안녕하세요\r\n반갑습니다\r\n";
		
		String firstResponse = "안녕하세요\r\n";
		String secondResponse = "반갑습니다\r\n";
		
		// 최대 8192바이트의 데이터를 줄바꿈 문자를 기준으로 잘라서 디코딩하는 DelimiterBasedFrameDecoder 객체를 생성한다.
		// 디코더의 두 번째 인수는 디코딩된 데이터에 구분자의 포함 여부를 설정한다.
		DelimiterBasedFrameDecoder decoder = new DelimiterBasedFrameDecoder(8192, false, Delimiters.lineDelimiter());
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(decoder);
		
		ByteBuf request = Unpooled.wrappedBuffer(writeData.getBytes());
		// 바이트 버퍼로 변환된 문자열을 EmbeddedChannel의 인바운드에 기록하낟. 즉 클라이언트로부터 데이터를 수신한 것과 같은 상태가 된다.
		boolean result = embeddedChannel.writeInbound(request);
		// writeInbound 메서드의 수행 결과는 EmbeddedChannel의 버퍼에 데이터가 정상적으로 기록되었음을 나타낸다.
		assertTrue(result);
		
		ByteBuf response = null;
		// EmbeddedChannel에서 인바운드 데이터를 읽는다. 즉 디코더가 수신하여 디코딩한 데이터를 조회한다.
		// DelimiterBasedFrameDecoder는 줄바꿈 문자를 기준으로 데이터를 분리하므로 writeData 문자열의 시작부터 첫 번째 줄바꿈 문자 앞 문자열인 '안녕하세요\r\n'을 돌려준다.
		response = (ByteBuf)embeddedChannel.readInbound();
		assertEquals(firstResponse, response.toString(Charset.defaultCharset()));
		
		response = (ByteBuf)embeddedChannel.readInbound();
		assertEquals(secondResponse, response.toString(Charset.defaultCharset()));
		
		embeddedChannel.finish();
	}
}
