package netty.ch8.junit;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class ResponseGenerator {
	String request;
	
	public ResponseGenerator(String request) {
		this.request = request;
	}
	
	public final String response() {
		String command = null;
		
		if (this.request.isEmpty()) {
			command = "����� �Է��� �ּ���.\r\n";
		} else if ("bye".equals(this.request.toLowerCase())) {
			command = "���� �Ϸ� �Ǽ���!\r\n";
		} else {
			command = "�Է��Ͻ� ����� '" + request + "' �Դϱ�?\r\n";
		}
		
		return command;
	}
	
	public boolean isClose() {
		return "bye".equals(this.request);
	}
	
	public static final String makeHello() throws UnknownHostException {
		StringBuilder builder = new StringBuilder();
		
		builder.append("ȯ���մϴ�. ")
			   .append(InetAddress.getLocalHost().getHostName())
			   .append("�� �����ϼ̽��ϴ�!\r\n")
			   .append("���� �ð��� ").append(new Date().toString())
			   .append(" �Դϴ�.\r\n");
		
		return builder.toString();
	}
}
