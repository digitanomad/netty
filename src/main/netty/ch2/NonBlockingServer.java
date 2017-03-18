package netty.ch2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NonBlockingServer {
	private Map<SocketChannel, List<byte[]>> keepDataTrack = new HashMap<>();
	private ByteBuffer buffer = ByteBuffer.allocate(2 * 1024);
	
	private void startEchoServer() throws IOException {
		// �ڹ� 1.7���� ���� ������ ������� try ����� ���� �� �Ұ�ȣ �ȿ��� ����� �ڿ��� �ڵ����� �������ش�.
		try (
			// Selector�� �ڽſ��� ��ϵ� ä�ο� ���� ������ �߻��ߴ��� �˻��ϰ� ��������� �߻��� ä�ο� ���� ������ �����ϰ� ���ش�.
			Selector selector = Selector.open();
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		) {
			if ((serverSocketChannel.isOpen()) && (selector.isOpen())) {
				// ServerSocketChannel ��ü�� ����ŷ ���� ����
				serverSocketChannel.configureBlocking(false);
				serverSocketChannel.bind(new InetSocketAddress(8888));
				
				// ServerSocketChannel ��ü�� Selector ��ü�� ����Ѵ�. Selector�� ������ �̺�Ʈ�� ���� ��û�� �ش��ϴ� OP_ACCEPT��.
				serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
				System.out.println("���� �����");
				
				while (true) {
					// Selector�� ��ϵ� ä�ο��� ���� ������ �߻��ߴ��� �˻��Ѵ�.
					// Selector�� �ƹ��� I/O �̺�Ʈ�� �߻����� ������ ������� �� �κп��� ���ŷ�ȴ�.
					// I/O �̺�Ʈ�� �߻����� �ʾ��� �� ���ŷ�� ���ϰ� �ʹٸ� selectNow �޼��带 ����ϸ� �ȴ�.
					selector.select();
					// Selector�� ��ϵ� ä�� �߿��� I/O �̺�Ʈ�� �߻��� ä�ε��� ����� ��ȸ�Ѵ�.
					Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
					
					while (keys.hasNext()) {
						SelectionKey key = keys.next();
						// I/O �̺�Ʈ�� �߻��� ä�ο��� ������ �̺�Ʈ�� �����Ǵ� ���� �����ϱ� ���Ͽ� ��ȸ�� ��Ͽ��� �����Ѵ�.
						keys.remove();
						
						if (!key.isValid()) {
							continue;
						}
						
						if (key.isAcceptable()) {
							this.acceptOP(key, selector);
						} else if (key.isReadable()) {
							this.readOP(key);
						} else if (key.isWritable()) {
							this.writeOP(key);
						}
					}
				}
			} else {
				System.out.println("���� ������ �������� ���߽��ϴ�.");
			}
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	private void acceptOP(SelectionKey key, Selector selector) throws IOException {
		ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
		// ServerSocketChannel�� ����Ͽ� Ŭ���̾�Ʈ�� ������ �����ϰ� ����� ���� ä���� �����´�.
		SocketChannel socketChannel = serverChannel.accept();
		socketChannel.configureBlocking(false);
		
		System.out.println("Ŭ���̾�Ʈ ����� : " + socketChannel.getRemoteAddress());
		
		keepDataTrack.put(socketChannel, new ArrayList<byte[]>());
		// Ŭ���̾�Ʈ ���� ä���� Selector�� ����Ͽ� I/O �̺�Ʈ�� �����Ѵ�.
		socketChannel.register(selector, SelectionKey.OP_READ);
	}
	
	private void readOP(SelectionKey key) {
		try {
			SocketChannel socketChannel = (SocketChannel) key.channel();
			buffer.clear();
			int numRead = -1;
			try {
				numRead = socketChannel.read(buffer);
			} catch (IOException e) {
				System.err.println("������ �б� ����");
			}
			
			if (numRead == -1) {
				this.keepDataTrack.remove(socketChannel);
				System.out.println("Ŭ���̾�Ʈ ���� ���� : " + socketChannel.getRemoteAddress());
				socketChannel.close();
				key.cancel();
				return;
			}
			
			byte[] data = new byte[numRead];
			System.arraycopy(buffer.array(), 0, data, 0, numRead);
			System.out.println(new String(data, "UTF-8") + " from " + socketChannel.getRemoteAddress());
			
			doEchoJob(key, data);
			
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	private void writeOP(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		
		List<byte[]> channelData = keepDataTrack.get(socketChannel);
		Iterator<byte[]> its = channelData.iterator();
		
		while (its.hasNext()) {
			byte[] it = its.next();
			its.remove();
			socketChannel.write(ByteBuffer.wrap(it));
		}
		
		key.interestOps(SelectionKey.OP_READ);
	}
	
	private void doEchoJob(SelectionKey key, byte[] data) {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		List<byte[]> channelData = keepDataTrack.get(socketChannel);
		channelData.add(data);
		
		key.interestOps(SelectionKey.OP_WRITE);
	}
	
	public static void main(String[] args) throws IOException {
		NonBlockingServer main = new NonBlockingServer();
		main.startEchoServer();
	}
}
