package netty.ch6;

import java.nio.ByteBuffer;
import static org.junit.Assert.*;
import org.junit.Test;

public class RightByteBufferTest3 {
	@Test
	public void test() {
		ByteBuffer firstBuffer = ByteBuffer.allocate(11);
		System.out.println("초기 상태 : " + firstBuffer);
		
		firstBuffer.put((byte)1);
		firstBuffer.put((byte)2);
		assertEquals(2, firstBuffer.position());
		
		// position 속성을 0으로 변경하게 되어 바이트 버퍼에 저장된 첫 번째 값을 조회할 수 있게 되고, position 속성이 1 증가한다.
		firstBuffer.rewind();
		assertEquals(0, firstBuffer.position());
		
		assertEquals(1, firstBuffer.get());
		assertEquals(1, firstBuffer.position());
		
		System.out.println(firstBuffer);
	}
}
