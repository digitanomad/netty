package netty.ch8.junit;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResponseGeneratorTest {
	@Test
	public void testZeroLengthString() {
		String request = "";
		
		ResponseGenerator generator = new ResponseGenerator(request);
		// ResponseGenerator ��ü�� �������� �׽�Ʈ�Ѵ�. �׽�Ʈ�� �����Ѵٸ� ��ü ������ ������ ���̴�.
		assertNotNull(generator);
		
		// response �޼ҵ��� ����� ���� ���ڿ��� �����Ǵ��� �׽�Ʈ�Ѵ�.
		assertNotNull(generator.response());
		assertEquals("����� �Է��� �ּ���.\r\n", generator.response());
		
		assertFalse(generator.isClose());
	}
	
	@Test
	public void testHi() {
		String request = "hi";
		
		ResponseGenerator generator = new ResponseGenerator(request);
		assertNotNull(generator);
		
		assertNotNull(generator.response());
		assertEquals("�Է��Ͻ� ����� '" + request + "' �Դϱ�?\r\n", generator.response());
		
		assertFalse(generator.isClose());
	}
	
	@Test
	public void testBye() {
		String request = "bye";
		
		ResponseGenerator generator = new ResponseGenerator(request);
		assertNotNull(generator);
		
		assertNotNull(generator.response());
		assertEquals("���� �Ϸ� �Ǽ���!\r\n", generator.response());
		
		assertTrue(generator.isClose());
	}
}
