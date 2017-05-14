package netty.ch8.junit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class TelnetServerBySpringV2 {
	public static void main(String[] args) {
		AbstractApplicationContext springContext = null;
		try {
			// 어노테이션 설정을 가진 클래스를 지정한다. 여기에 입력되는 클래스는 스프링 컨텍스트를 생성하는 데 필요한 설정 정보가 포함되어 있다.
			springContext = new AnnotationConfigApplicationContext(TelnetServerConfigV2.class);
			springContext.registerShutdownHook();
			
			TelnetServerV3 server = springContext.getBean(TelnetServerV3.class);
			server.start();
		} finally {
			springContext.close();
		}
	}
}
