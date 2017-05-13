package netty.ch8.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class TelnetServerBySpring {
	public static void main(String[] args) {
		AbstractApplicationContext springContext = null;
		try {
			// 어노테이션 설정을 가진 클래스를 지정한다. 여기에 입력되는 클래스는 스프링 컨텍스트를 생성하는 데 필요한 설정 정보가 포함되어 있다.
			springContext = new AnnotationConfigApplicationContext(TelnetServerConfig.class);
			springContext.registerShutdownHook();
			
			// 스프링 컨텍스트에서 TelnetServerV2 클래스의 객체를 가져온다.
			TelnetServerV2 server = springContext.getBean(TelnetServerV2.class);
			server.start();
		} finally {
			springContext.close();
		}
	}
}
