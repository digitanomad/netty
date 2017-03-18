package netty.ch2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockingServer {
	public static void main(String[] args) throws IOException {
		BlockingServer server = new BlockingServer();
		server.run();
	}
	
	private void run() throws IOException {
		ServerSocket server = new ServerSocket(8888);
		System.out.println("접속 대기중");
		
		while (true) {
			// 연결요청이 올 때까지 스레드가 블로킹됨.
			Socket sock = server.accept();
			System.out.println("클라이언트 연결됨");
			
			OutputStream out = sock.getOutputStream();
			InputStream in = sock.getInputStream();
			
			while (true) {
				try {
					// 클라이언트에서 보낸 데이터를 다 읽기 전까지 스레드가 블로킹됨.
					int request = in.read();
					out.write(request);
				} catch (IOException e) {
					break;
				}
			}
		}
	}
}
