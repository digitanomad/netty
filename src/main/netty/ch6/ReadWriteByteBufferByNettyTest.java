package netty.ch6;

import static org.junit.Assert.assertEquals;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

import org.junit.Test;

public class ReadWriteByteBufferByNettyTest {
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
		
		// 버퍼에 4바이트 크기의 정수 65537을 기록한다.
		// 데이터를 기록하는 write 메소드는 기록한 데이터 크기만큼 writeIndex 속성값을 증가시킨다.
		buf.writeInt(65537);
		assertEquals(4, buf.readableBytes());
		assertEquals(7, buf.writableBytes());
		
		// 2바이트 크기의 정수를 읽고, 읽어들인 값이 1과 같은지 확인한다.
		// 65537을 16진수로 표현하면 0x10001이고 이 값에 4바이트 패딩을 하면 0x00010001이 된다. 그러므로 앞쪽 2바이트를 읽었을 때의 값은 1이다.
		assertEquals(1, buf.readShort());
		assertEquals(2, buf.readableBytes());
		assertEquals(7, buf.writableBytes());
		
		// isReadable 메소드는 바이트 버퍼에 읽지 않은 데이터가 남았는지 확인하기 위해서 쓰기 인덱스가 읽기 인덱스보다 큰지 검사한다.
		assertEquals(true, buf.isReadable());
		
		// 주어진 바이트 버퍼를 초기화하는 clear 메소드는 호출한다.
		// clear 메소드는 주어진 버퍼의 읽기 인덱스와 쓰기 인덱스 값을 0으로 변경한다.
		buf.clear();
		
		assertEquals(0, buf.readableBytes());
		assertEquals(11, buf.writableBytes());
	}
}
