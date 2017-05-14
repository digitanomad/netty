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
		String writeData = "�ȳ��ϼ���\r\n�ݰ����ϴ�\r\n";
		
		String firstResponse = "�ȳ��ϼ���\r\n";
		String secondResponse = "�ݰ����ϴ�\r\n";
		
		// �ִ� 8192����Ʈ�� �����͸� �ٹٲ� ���ڸ� �������� �߶� ���ڵ��ϴ� DelimiterBasedFrameDecoder ��ü�� �����Ѵ�.
		// ���ڴ��� �� ��° �μ��� ���ڵ��� �����Ϳ� �������� ���� ���θ� �����Ѵ�.
		DelimiterBasedFrameDecoder decoder = new DelimiterBasedFrameDecoder(8192, false, Delimiters.lineDelimiter());
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(decoder);
		
		ByteBuf request = Unpooled.wrappedBuffer(writeData.getBytes());
		// ����Ʈ ���۷� ��ȯ�� ���ڿ��� EmbeddedChannel�� �ιٿ�忡 ����ϳ�. �� Ŭ���̾�Ʈ�κ��� �����͸� ������ �Ͱ� ���� ���°� �ȴ�.
		boolean result = embeddedChannel.writeInbound(request);
		// writeInbound �޼����� ���� ����� EmbeddedChannel�� ���ۿ� �����Ͱ� ���������� ��ϵǾ����� ��Ÿ����.
		assertTrue(result);
		
		ByteBuf response = null;
		// EmbeddedChannel���� �ιٿ�� �����͸� �д´�. �� ���ڴ��� �����Ͽ� ���ڵ��� �����͸� ��ȸ�Ѵ�.
		// DelimiterBasedFrameDecoder�� �ٹٲ� ���ڸ� �������� �����͸� �и��ϹǷ� writeData ���ڿ��� ���ۺ��� ù ��° �ٹٲ� ���� �� ���ڿ��� '�ȳ��ϼ���\r\n'�� �����ش�.
		response = (ByteBuf)embeddedChannel.readInbound();
		assertEquals(firstResponse, response.toString(Charset.defaultCharset()));
		
		response = (ByteBuf)embeddedChannel.readInbound();
		assertEquals(secondResponse, response.toString(Charset.defaultCharset()));
		
		embeddedChannel.finish();
	}
}
