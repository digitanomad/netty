package netty.ch6;

import static org.junit.Assert.assertEquals;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

import org.junit.Test;

public class ReadWriteByteBufferByNettyTest {
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
		
		// ���ۿ� 4����Ʈ ũ���� ���� 65537�� ����Ѵ�.
		// �����͸� ����ϴ� write �޼ҵ�� ����� ������ ũ�⸸ŭ writeIndex �Ӽ����� ������Ų��.
		buf.writeInt(65537);
		assertEquals(4, buf.readableBytes());
		assertEquals(7, buf.writableBytes());
		
		// 2����Ʈ ũ���� ������ �а�, �о���� ���� 1�� ������ Ȯ���Ѵ�.
		// 65537�� 16������ ǥ���ϸ� 0x10001�̰� �� ���� 4����Ʈ �е��� �ϸ� 0x00010001�� �ȴ�. �׷��Ƿ� ���� 2����Ʈ�� �о��� ���� ���� 1�̴�.
		assertEquals(1, buf.readShort());
		assertEquals(2, buf.readableBytes());
		assertEquals(7, buf.writableBytes());
		
		// isReadable �޼ҵ�� ����Ʈ ���ۿ� ���� ���� �����Ͱ� ���Ҵ��� Ȯ���ϱ� ���ؼ� ���� �ε����� �б� �ε������� ū�� �˻��Ѵ�.
		assertEquals(true, buf.isReadable());
		
		// �־��� ����Ʈ ���۸� �ʱ�ȭ�ϴ� clear �޼ҵ�� ȣ���Ѵ�.
		// clear �޼ҵ�� �־��� ������ �б� �ε����� ���� �ε��� ���� 0���� �����Ѵ�.
		buf.clear();
		
		assertEquals(0, buf.readableBytes());
		assertEquals(11, buf.writableBytes());
	}
}
