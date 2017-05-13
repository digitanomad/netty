package netty.ch8.spring;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

// Configuration 어노테이션은 지정된 클래스가 스프링의 설정 정보를 포함한 클래스임을 표시한다.
@Configuration
// ComponentScan 어노테이션은 스프링의 컨텍스트가 클래스를 동적으로 찾을 수 있도록 한다는 의미며 입력되는 패키지명을 포함한 하위 패키지를 대상으로 검색한다는 의미이다.
@ComponentScan("netty.ch8.spring")
// PropertySource 어노테이션은 설정 정보를 가진 파일의 위치에서 파일을 읽어서 Enviorment 객체로 자동 저장한다.
@PropertySource("classpath:telnet-server.properties")
public class TelnetServerConfig {
	// 설정 정보에서 boss.thread.count 키로 지정된 설정값을 찾고 해당 값을 변수 bossCount에 할당한다.
	@Value("${boss.thread.count}")
	private int bossCount;
	
	@Value("${worker.thread.count}")
	private int workerCount;
	
	@Value("${tcp.port}")
	private int tcpPort;

	public int getBossCount() {
		return bossCount;
	}

	public int getWorkerCount() {
		return workerCount;
	}

	public int getTcpPort() {
		return tcpPort;
	}
	
	// 설정 파일에서 읽어들인 tcp.port 정보로부터 InetSocketAddress 객체를 생성하고 객체 이름을 tcpSocketAddress로 지정한다.
	// 이 설정은 스프링 컨텍스트에 tcpSocketAddress라는 이름으로 추가되며 다른 Bean에서 사용할 수 있다.
	@Bean(name = "tcpSocketAddress")
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(tcpPort);
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
