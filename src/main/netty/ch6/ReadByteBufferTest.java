package netty.ch6;

import java.nio.ByteBuffer;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReadByteBufferTest {
	@Test
	public void readTest() {
		byte[] tempArray = { 1, 2, 3, 4, 5, 0, 0, 0, 0, 0, 0 };
		ByteBuffer firstBuffer = ByteBuffer.wrap(tempArray);
		assertEquals(0, firstBuffer.position());
		assertEquals(11, firstBuffer.limit());
		
		assertEquals(1, firstBuffer.get());
		assertEquals(2, firstBuffer.get());
		assertEquals(3, firstBuffer.get());
		assertEquals(4, firstBuffer.get());
		assertEquals(4, firstBuffer.position());
		assertEquals(11, firstBuffer.limit());
		
		// flip 메소드는 쓰기 작업 완료 이후에 데이터의 처음부터 읽을 수 있도록 현재 포인터의 위치를 변경하여
		// 읽기에서 쓰기 또는 쓰기에서 읽기로 작업을 전환할 수 있게 된다.
		firstBuffer.flip();
		assertEquals(0, firstBuffer.position());
		assertEquals(4, firstBuffer.limit());
		
		firstBuffer.get(3);
		
		assertEquals(0, firstBuffer.position());
		
	}
}
