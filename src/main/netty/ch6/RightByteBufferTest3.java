package netty.ch6;

import java.nio.ByteBuffer;
import static org.junit.Assert.*;
import org.junit.Test;

public class RightByteBufferTest3 {
	@Test
	public void test() {
		ByteBuffer firstBuffer = ByteBuffer.allocate(11);
		System.out.println("�ʱ� ���� : " + firstBuffer);
		
		firstBuffer.put((byte)1);
		firstBuffer.put((byte)2);
		assertEquals(2, firstBuffer.position());
		
		// position �Ӽ��� 0���� �����ϰ� �Ǿ� ����Ʈ ���ۿ� ����� ù ��° ���� ��ȸ�� �� �ְ� �ǰ�, position �Ӽ��� 1 �����Ѵ�.
		firstBuffer.rewind();
		assertEquals(0, firstBuffer.position());
		
		assertEquals(1, firstBuffer.get());
		assertEquals(1, firstBuffer.position());
		
		System.out.println(firstBuffer);
	}
}
