package netty.ch6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreateByteBufferByNettyTest {
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
		
		assertEquals(0, buf.readableBytes());
		assertEquals(11, buf.writableBytes());
	}
}
