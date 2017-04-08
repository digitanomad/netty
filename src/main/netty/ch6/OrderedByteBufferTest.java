package netty.ch6;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderedByteBufferTest {
	final String source = "Hello world";
	
	@Test
	public void pooledHeapBufferTest() {
		ByteBuf buf = Unpooled.buffer();
		// 옵션없이 생성한 네티 바이트 버퍼의 엔디안이 빅엔디안인지 확인한다.
		assertEquals(ByteOrder.BIG_ENDIAN, buf.order());

		// 바이트 버퍼의 기본 엔디안은 빅엔디안이므로 0x0001이 저장된다.
		buf.writeShort(1);
		
		// 현재 바이트 버퍼의 읽기 인덱스 위치를 표시한다. markReaderIndex 메소드로 표시한 읽기 인덱스 위치로 돌아가려면 resetReaderIndex를 사용한다.
		buf.markReaderIndex();
		// 저장된 데이터가 1인지 확인한다. 빅엔디안으로 저장된 데이터를 그대로 읽는다.
		assertEquals(1, buf.readShort());
		
		// 읽기 인덱스 위치를 markReaderIndex를 사용하여 표시한 위치로 이동시킨다.
		buf.resetReaderIndex();
		
		// 바이트 버퍼의 order 메소드로 리틀엔디안의 바이트 버퍼를 생성한다.
		// 여기서 생성한 바이트 버퍼는 바이트 버퍼 내부의 배열과 읽기 인덱스, 쓰기 인덱스를 공유한다.
		// 즉 내용은 동일하지만 리틀엔디안에 해당하는 읽기 쓰기 메소드를 제공하는 바이트 버퍼 객체를 얻을 수 있다.
		ByteBuf littleEndianBuf = buf.order(ByteOrder.LITTLE_ENDIAN);
		// 리틀 엔디안에 해당하는 2바이트 Short형 데이터를 읽고 값이 256인지 확인한다.
		// 빅엔디안 0x0001을 리틀엔디안으로 변환하면 0x0100이 되므로 십진수 256이 된다.
		assertEquals(256, littleEndianBuf.readShort());
	}
	
	@Test
	public void convertNettyBufferToJavaBuffer() {
		ByteBuf buf = Unpooled.buffer(11);
		
		buf.writeBytes(source.getBytes());
		assertEquals(source, buf.toString(Charset.defaultCharset()));
		
		// 네티 바이트 버퍼의 nioBuffer 메소드로 자바 바이트 버퍼 객체를 생성한다. 여기서 생성한 자바 바이트 버퍼와 네티 바이트 버퍼의 내부 배열은 서류 공유된다.
		ByteBuffer nioByteBuffer = buf.nioBuffer();
		assertNotNull(nioByteBuffer);
		assertEquals(source, new String(nioByteBuffer.array(), nioByteBuffer.arrayOffset(), nioByteBuffer.remaining()));
	}
	
	@Test
	public void convertJavaBufferToNettyBuffer() {
		ByteBuffer byteBuffer = ByteBuffer.wrap(source.getBytes());
		// 네티 Unpooled 클래스의 wrappedBuffer 메소드에 자바 바이트 버퍼를 입력하여 네티 바이트 버퍼를 생성한다.
		// 여기서 생성한 자바 바이트 버퍼와 네티 바이트 버퍼의 내부 배열은 서로 공유된다. 이 같이 내부 배열을 공유하는 바이트 버퍼를 뷰 버퍼라고 한다.
		ByteBuf nettyBuffer = Unpooled.wrappedBuffer(byteBuffer);
		
		assertEquals(source, nettyBuffer.toString(Charset.defaultCharset()));
	}
}
