package netty.ch6;

import java.nio.ByteBuffer;

public class ByteBufferTest1 {
	public static void main(String[] args) {
		ByteBuffer firstBuffer = ByteBuffer.allocate(11);
		System.out.println("����Ʈ ���� �ʱⰪ : " + firstBuffer);
		
		// ����Ʈ ���� ��ü�� ������ ������ ���� put �޼��忡 �ڹ��� �⺻�� �����͸��� �Է��� �� �ִ�.
		// ����Ʈ ���ۿ� ������ "Hello world" ���ڿ� �����͸� ����Ʈ �迭�� ��ȯ�Ѵ�.
		byte[] source = "Hello world".getBytes();
		firstBuffer.put(source);
		System.out.println("11����Ʈ ��� �� : " + firstBuffer);
	}
}
