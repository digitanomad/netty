package netty.ch8.junit;

import io.netty.channel.embedded.EmbeddedChannel;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.junit.Test;

import static org.junit.Assert.*;

public class TelnetServerHandlerV3Test {
	@Test
	public void testConnect() {
		StringBuilder builder = new StringBuilder();
		try {
			builder.append("환영합니다. ")
				.append(InetAddress.getLocalHost().getHostName())
				.append("에 접속하셨습니다!\r\n")
				.append("현재 시간은 ")
				.append(new Date().toString())
				.append(" 입니다.\r\n");
		} catch (UnknownHostException e) {
			fail();
			e.printStackTrace();
		}
		
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(new TelnetServerHandlerV3());
		
		// TelnetServerHandlerV3 클래스는 클라이언트가 접속할 때마다 환영 메시지를 전송한다.
		// 인바운드 이벤트 핸들러의 channelActive 이벤트 메소드는 이벤트 핸들러가 등록될 떄 호출된다.
		// 그러므로 다른 write 이벤트 메소드 호출 없이 readOutbound 메소드로 아웃바운드 데이터를 조회할 수 있다.
		String expected = (String)embeddedChannel.readOutbound();
		assertNotNull(expected);
		
		assertEquals(builder.toString(), expected);
		
		
		
		String request = "hello";
		expected = "입력하신 명령이 '" + request + "' 입니까?\r\n";
		
		// hello 메시지를 writeInbound 메소드로 EmbeddedChannel의 인바운드에 기록한다.
		embeddedChannel.writeInbound(request);
		
		String response = (String)embeddedChannel.readOutbound();
		assertEquals(expected, response);
		
		embeddedChannel.finish();
	}
}
