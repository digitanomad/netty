package netty.ch6;

import java.nio.ByteBuffer;
import static org.junit.Assert.*;
import org.junit.Test;

public class WriteByteBufferTest {
	@Test
	public void writeTest() {
		ByteBuffer firstBuffer = ByteBuffer.allocateDirect(11);
		assertEquals(0, firstBuffer.position());
		assertEquals(11, firstBuffer.limit());
		
		firstBuffer.put((byte)1);
		firstBuffer.put((byte)2);
		firstBuffer.put((byte)3);
		firstBuffer.put((byte)4);
		assertEquals(4, firstBuffer.position());
		assertEquals(11, firstBuffer.limit());
		
		// limit 속성값이 마지막에 기록한 데이터의 위치로 변경된다.
		firstBuffer.flip();
		assertEquals(0, firstBuffer.position());
		assertEquals(4, firstBuffer.limit());
	}
}
