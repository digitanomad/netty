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
		
		String sourceData = "hello world";
		buf.writeBytes(sourceData.getBytes());
		assertEquals(11, buf.readableBytes());
		assertEquals(0, buf.writableBytes());
		
		assertEquals(sourceData, buf.toString(Charset.defaultCharset()));
		
		// 바이트 버퍼의 크기를 6바이트로 줄인다. 네티의 바이트 버퍼는 capacity 메소드를 사용하여 바이트 버퍼의 크기를 동적으로 조절할 수 있다.
		// 단, 저장된 데이터보다 작은 크기로 조절하면 나머지 데이터는 잘려진다.
		buf.capacity(6);
		assertEquals("hello ", buf.toString(Charset.defaultCharset()));
		assertEquals(6, buf.capacity());
		
		// 바이트 버퍼의 크기를 13으로 늘린다. 바이트 버퍼의 크기를 늘리면 이전의 저장된 데이터는 보존된다.
		buf.capacity(13);
		assertEquals("hello ", buf.toString(Charset.defaultCharset()));
		
		buf.writeBytes("world".getBytes());
		assertEquals(sourceData, buf.toString(Charset.defaultCharset()));
		
		assertEquals(13, buf.capacity());
		assertEquals(2, buf.writableBytes());
	}
}
