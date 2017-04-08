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
		// �ɼǾ��� ������ ��Ƽ ����Ʈ ������ ������� �򿣵������ Ȯ���Ѵ�.
		assertEquals(ByteOrder.BIG_ENDIAN, buf.order());

		// ����Ʈ ������ �⺻ ������� �򿣵���̹Ƿ� 0x0001�� ����ȴ�.
		buf.writeShort(1);
		
		// ���� ����Ʈ ������ �б� �ε��� ��ġ�� ǥ���Ѵ�. markReaderIndex �޼ҵ�� ǥ���� �б� �ε��� ��ġ�� ���ư����� resetReaderIndex�� ����Ѵ�.
		buf.markReaderIndex();
		// ����� �����Ͱ� 1���� Ȯ���Ѵ�. �򿣵������ ����� �����͸� �״�� �д´�.
		assertEquals(1, buf.readShort());
		
		// �б� �ε��� ��ġ�� markReaderIndex�� ����Ͽ� ǥ���� ��ġ�� �̵���Ų��.
		buf.resetReaderIndex();
		
		// ����Ʈ ������ order �޼ҵ�� ��Ʋ������� ����Ʈ ���۸� �����Ѵ�.
		// ���⼭ ������ ����Ʈ ���۴� ����Ʈ ���� ������ �迭�� �б� �ε���, ���� �ε����� �����Ѵ�.
		// �� ������ ���������� ��Ʋ����ȿ� �ش��ϴ� �б� ���� �޼ҵ带 �����ϴ� ����Ʈ ���� ��ü�� ���� �� �ִ�.
		ByteBuf littleEndianBuf = buf.order(ByteOrder.LITTLE_ENDIAN);
		// ��Ʋ ����ȿ� �ش��ϴ� 2����Ʈ Short�� �����͸� �а� ���� 256���� Ȯ���Ѵ�.
		// �򿣵�� 0x0001�� ��Ʋ��������� ��ȯ�ϸ� 0x0100�� �ǹǷ� ������ 256�� �ȴ�.
		assertEquals(256, littleEndianBuf.readShort());
	}
	
	@Test
	public void convertNettyBufferToJavaBuffer() {
		ByteBuf buf = Unpooled.buffer(11);
		
		buf.writeBytes(source.getBytes());
		assertEquals(source, buf.toString(Charset.defaultCharset()));
		
		// ��Ƽ ����Ʈ ������ nioBuffer �޼ҵ�� �ڹ� ����Ʈ ���� ��ü�� �����Ѵ�. ���⼭ ������ �ڹ� ����Ʈ ���ۿ� ��Ƽ ����Ʈ ������ ���� �迭�� ���� �����ȴ�.
		ByteBuffer nioByteBuffer = buf.nioBuffer();
		assertNotNull(nioByteBuffer);
		assertEquals(source, new String(nioByteBuffer.array(), nioByteBuffer.arrayOffset(), nioByteBuffer.remaining()));
	}
	
	@Test
	public void convertJavaBufferToNettyBuffer() {
		ByteBuffer byteBuffer = ByteBuffer.wrap(source.getBytes());
		// ��Ƽ Unpooled Ŭ������ wrappedBuffer �޼ҵ忡 �ڹ� ����Ʈ ���۸� �Է��Ͽ� ��Ƽ ����Ʈ ���۸� �����Ѵ�.
		// ���⼭ ������ �ڹ� ����Ʈ ���ۿ� ��Ƽ ����Ʈ ������ ���� �迭�� ���� �����ȴ�. �� ���� ���� �迭�� �����ϴ� ����Ʈ ���۸� �� ���۶�� �Ѵ�.
		ByteBuf nettyBuffer = Unpooled.wrappedBuffer(byteBuffer);
		
		assertEquals(source, nettyBuffer.toString(Charset.defaultCharset()));
	}
}
