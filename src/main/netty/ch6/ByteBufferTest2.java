package netty.ch6;

import java.nio.ByteBuffer;

public class ByteBufferTest2 {
	public static void main(String[] args) {
		ByteBuffer firstBuffer = ByteBuffer.allocate(11);
		System.out.println("����Ʈ ���� �ʱⰪ : " + firstBuffer);
		
		byte[] source = "Hello world!".getBytes();
		
		for (byte item : source) {
			firstBuffer.put(item);
			System.out.println("���� ���� : " + firstBuffer);
		}
	}
}
