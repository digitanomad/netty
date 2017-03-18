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
		System.out.println("���� �����");
		
		while (true) {
			// �����û�� �� ������ �����尡 ���ŷ��.
			Socket sock = server.accept();
			System.out.println("Ŭ���̾�Ʈ �����");
			
			OutputStream out = sock.getOutputStream();
			InputStream in = sock.getInputStream();
			
			while (true) {
				try {
					// Ŭ���̾�Ʈ���� ���� �����͸� �� �б� ������ �����尡 ���ŷ��.
					int request = in.read();
					out.write(request);
				} catch (IOException e) {
					break;
				}
			}
		}
	}
}
