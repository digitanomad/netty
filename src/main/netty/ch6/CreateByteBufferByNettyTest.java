package netty.ch6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreateByteBufferByNettyTest {
	@Test
	public void createUnpooledHeapBufferTest() {
		// 바이트 버퍼 풀을 사용하지 않는 11 바이트 크기의 힙 버퍼를 생성
		ByteBuf buf = Unpooled.buffer(11);
		
		testBuffer(buf, false);
	}
	
	@Test
	public void createUnpooledDirectBufferTest() {
		// 바이트 버퍼 풀을 사용하지 않는 11 바이트 크기의 다이렉트 버퍼를 생성
		ByteBuf buf = Unpooled.directBuffer(11);
		
		testBuffer(buf, true);
	}
	
	@Test
	public void createPooledHeapBufferTest() {
		// 풀링된 11바이트 크기의 힙 버퍼를 생성한다.
		// 네티 바이트 버퍼는 자바 바이트 버퍼와 달리 프레임워크 레벨의 바이트 버퍼 풀을 제공한다. 이를 통해 생성된 바이트 버퍼를 재사용한다.
		ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(11);
		
		testBuffer(buf, false);
	}
	
	@Test
	public void createPooledDirectBufferTest() {
		// 풀링된 11바이트 크기의 다이렉트 버퍼를 생성한다.
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
