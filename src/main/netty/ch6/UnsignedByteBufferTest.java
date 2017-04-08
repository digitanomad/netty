package netty.ch6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.junit.Test;
import static org.junit.Assert.*;

public class UnsignedByteBufferTest {
	final String source = "Hello world";
	
	@Test
	public void unsignedBufferToJavaBuffer() {
		ByteBuf buf = Unpooled.buffer(11);
		buf.writeShort(-1);
		
		assertEquals(65535, buf.getUnsignedShort(0));
	}
}
