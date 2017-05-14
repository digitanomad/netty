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
			builder.append("ȯ���մϴ�. ")
				.append(InetAddress.getLocalHost().getHostName())
				.append("�� �����ϼ̽��ϴ�!\r\n")
				.append("���� �ð��� ")
				.append(new Date().toString())
				.append(" �Դϴ�.\r\n");
		} catch (UnknownHostException e) {
			fail();
			e.printStackTrace();
		}
		
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(new TelnetServerHandlerV3());
		
		// TelnetServerHandlerV3 Ŭ������ Ŭ���̾�Ʈ�� ������ ������ ȯ�� �޽����� �����Ѵ�.
		// �ιٿ�� �̺�Ʈ �ڵ鷯�� channelActive �̺�Ʈ �޼ҵ�� �̺�Ʈ �ڵ鷯�� ��ϵ� �� ȣ��ȴ�.
		// �׷��Ƿ� �ٸ� write �̺�Ʈ �޼ҵ� ȣ�� ���� readOutbound �޼ҵ�� �ƿ��ٿ�� �����͸� ��ȸ�� �� �ִ�.
		String expected = (String)embeddedChannel.readOutbound();
		assertNotNull(expected);
		
		assertEquals(builder.toString(), expected);
		
		
		
		String request = "hello";
		expected = "�Է��Ͻ� ����� '" + request + "' �Դϱ�?\r\n";
		
		// hello �޽����� writeInbound �޼ҵ�� EmbeddedChannel�� �ιٿ�忡 ����Ѵ�.
		embeddedChannel.writeInbound(request);
		
		String response = (String)embeddedChannel.readOutbound();
		assertEquals(expected, response);
		
		embeddedChannel.finish();
	}
}
