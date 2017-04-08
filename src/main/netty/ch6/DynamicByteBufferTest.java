package netty.ch6;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

import org.junit.Test;

public class DynamicByteBufferTest {
	@Test
	public void createUnpooledHeapBufferTest() {
		// ����Ʈ ���� Ǯ�� ������� �ʴ� 11 ����Ʈ ũ���� �� ���۸� ����
		ByteBuf buf = Unpooled.buffer(11);
		
		testBuffer(buf, false);
	}
	
	@Test
	public void createUnpooledDirectBufferTest() {
		// ����Ʈ ���� Ǯ�� ������� �ʴ� 11 ����Ʈ ũ���� ���̷�Ʈ ���۸� ����
		ByteBuf buf = Unpooled.directBuffer(11);
		
		testBuffer(buf, true);
	}
	
	@Test
	public void createPooledHeapBufferTest() {
		// Ǯ���� 11����Ʈ ũ���� �� ���۸� �����Ѵ�.
		// ��Ƽ ����Ʈ ���۴� �ڹ� ����Ʈ ���ۿ� �޸� �����ӿ�ũ ������ ����Ʈ ���� Ǯ�� �����Ѵ�. �̸� ���� ������ ����Ʈ ���۸� �����Ѵ�.
		ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(11);
		
		testBuffer(buf, false);
	}
	
	@Test
	public void createPooledDirectBufferTest() {
		// Ǯ���� 11����Ʈ ũ���� ���̷�Ʈ ���۸� �����Ѵ�.
		ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(11);
		
		testBuffer(buf, true);
	}
	
	
	private void testBuffer(ByteBuf buf, boolean isDirect) {
		assertEquals(11, buf.capacity());
		assertEquals(isDirect, buf.isDirect());
		
		String sourceData = "hello world";
		buf.writeBytes(sourceData.getBytes());
		assertEquals(11, buf.readableBytes());
		assertEquals(0, buf.writableBytes());
		
		assertEquals(sourceData, buf.toString(Charset.defaultCharset()));
		
		// ����Ʈ ������ ũ�⸦ 6����Ʈ�� ���δ�. ��Ƽ�� ����Ʈ ���۴� capacity �޼ҵ带 ����Ͽ� ����Ʈ ������ ũ�⸦ �������� ������ �� �ִ�.
		// ��, ����� �����ͺ��� ���� ũ��� �����ϸ� ������ �����ʹ� �߷�����.
		buf.capacity(6);
		assertEquals("hello ", buf.toString(Charset.defaultCharset()));
		assertEquals(6, buf.capacity());
		
		// ����Ʈ ������ ũ�⸦ 13���� �ø���. ����Ʈ ������ ũ�⸦ �ø��� ������ ����� �����ʹ� �����ȴ�.
		buf.capacity(13);
		assertEquals("hello ", buf.toString(Charset.defaultCharset()));
		
		buf.writeBytes("world".getBytes());
		assertEquals(sourceData, buf.toString(Charset.defaultCharset()));
		
		assertEquals(13, buf.capacity());
		assertEquals(2, buf.writableBytes());
	}
}
