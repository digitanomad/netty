package netty.ch8.spring;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

// Configuration ������̼��� ������ Ŭ������ �������� ���� ������ ������ Ŭ�������� ǥ���Ѵ�.
@Configuration
// ComponentScan ������̼��� �������� ���ؽ�Ʈ�� Ŭ������ �������� ã�� �� �ֵ��� �Ѵٴ� �ǹ̸� �ԷµǴ� ��Ű������ ������ ���� ��Ű���� ������� �˻��Ѵٴ� �ǹ��̴�.
@ComponentScan("netty.ch8.spring")
// PropertySource ������̼��� ���� ������ ���� ������ ��ġ���� ������ �о Enviorment ��ü�� �ڵ� �����Ѵ�.
@PropertySource("classpath:telnet-server.properties")
public class TelnetServerConfig {
	// ���� �������� boss.thread.count Ű�� ������ �������� ã�� �ش� ���� ���� bossCount�� �Ҵ��Ѵ�.
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
	
	// ���� ���Ͽ��� �о���� tcp.port �����κ��� InetSocketAddress ��ü�� �����ϰ� ��ü �̸��� tcpSocketAddress�� �����Ѵ�.
	// �� ������ ������ ���ؽ�Ʈ�� tcpSocketAddress��� �̸����� �߰��Ǹ� �ٸ� Bean���� ����� �� �ִ�.
	@Bean(name = "tcpSocketAddress")
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(tcpPort);
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
