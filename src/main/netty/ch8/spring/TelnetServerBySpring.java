package netty.ch8.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class TelnetServerBySpring {
	public static void main(String[] args) {
		AbstractApplicationContext springContext = null;
		try {
			// ������̼� ������ ���� Ŭ������ �����Ѵ�. ���⿡ �ԷµǴ� Ŭ������ ������ ���ؽ�Ʈ�� �����ϴ� �� �ʿ��� ���� ������ ���ԵǾ� �ִ�.
			springContext = new AnnotationConfigApplicationContext(TelnetServerConfig.class);
			springContext.registerShutdownHook();
			
			// ������ ���ؽ�Ʈ���� TelnetServerV2 Ŭ������ ��ü�� �����´�.
			TelnetServerV2 server = springContext.getBean(TelnetServerV2.class);
			server.start();
		} finally {
			springContext.close();
		}
	}
}
