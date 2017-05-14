package netty.ch8.junit;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResponseGeneratorTest {
	@Test
	public void testZeroLengthString() {
		String request = "";
		
		ResponseGenerator generator = new ResponseGenerator(request);
		// ResponseGenerator 객체가 정상인지 테스트한다. 테스트가 실패한다면 객체 생성에 실패한 것이다.
		assertNotNull(generator);
		
		// response 메소드의 결과로 응답 문자열이 생성되는지 테스트한다.
		assertNotNull(generator.response());
		assertEquals("명령을 입력해 주세요.\r\n", generator.response());
		
		assertFalse(generator.isClose());
	}
	
	@Test
	public void testHi() {
		String request = "hi";
		
		ResponseGenerator generator = new ResponseGenerator(request);
		assertNotNull(generator);
		
		assertNotNull(generator.response());
		assertEquals("입력하신 명령이 '" + request + "' 입니까?\r\n", generator.response());
		
		assertFalse(generator.isClose());
	}
	
	@Test
	public void testBye() {
		String request = "bye";
		
		ResponseGenerator generator = new ResponseGenerator(request);
		assertNotNull(generator);
		
		assertNotNull(generator.response());
		assertEquals("좋은 하루 되세요!\r\n", generator.response());
		
		assertTrue(generator.isClose());
	}
}
