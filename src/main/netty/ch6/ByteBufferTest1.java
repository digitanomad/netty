package netty.ch6;

import java.nio.ByteBuffer;

public class ByteBufferTest1 {
	public static void main(String[] args) {
		ByteBuffer firstBuffer = ByteBuffer.allocate(11);
		System.out.println("바이트 버퍼 초기값 : " + firstBuffer);
		
		// 바이트 버퍼 객체는 데이터 저장을 위한 put 메서드에 자바의 기본형 데이터만을 입력할 수 있다.
		// 바이트 버퍼에 저장할 "Hello world" 문자열 데이터를 바이트 배열로 변환한다.
		byte[] source = "Hello world".getBytes();
		firstBuffer.put(source);
		System.out.println("11바이트 기록 후 : " + firstBuffer);
	}
}
