package netty.ch6;

import java.nio.ByteBuffer;

public class ByteBufferTest3 {
	public static void main(String[] args) {
		ByteBuffer firstBuffer = ByteBuffer.allocate(11);
		System.out.println("�ʱ� ���� : " + firstBuffer);
		
		firstBuffer.put((byte)1);
		System.out.println(firstBuffer.get());
		System.out.println(firstBuffer);
	}
}
