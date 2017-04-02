package netty.ch5;

import io.netty.util.concurrent.Future;

public class SpecialCake {

	public static void main(String[] args) throws InterruptedException {
		Bakery bakery = new Bakery();
		
		// ����ũ�� �ֹ��ϰ� �ֹ����� �޴´�.
		Future future = bakery.orderCake();
		
		// �ٸ� ���� �Ѵ�.
		doSomething();
		
		// ����ũ�� �ϼ��Ǿ����� Ȯ���Ѵ�.
		if (future.isDone()) {
			// Cake cake = future.getCake();
		} else {
			// ����ũ�� �ϼ��Ǿ����� Ȯ���Ѵ�.
			while (!future.isDone()) {
				// �ٸ� ���� �Ѵ�.
				doSomething();
			}
			
			// while ������ �����������Ƿ� �ϼ��� ����ũ�� �����´�.
			// Cake cake = future.getCake();
		}
		
	}
	
	private static void doSomething() throws InterruptedException {
		Thread.sleep(100);
	}
}

class Bakery {
	public Future orderCake() {
		return null;
	}
}

class Cake {
	
}

