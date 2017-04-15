package netty.ch7;

import java.net.InetAddress;
import java.util.Date;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

// Sharable ������̼��� ��Ƽ�� �����ϴ� ������� ����ǥ�� ������̼��̴�.
// Sharable�� ������ Ŭ������ ä�� ���������ο��� ������ �� �ִٴ� �ǹ̴�. ��, ���� �����忡�� ������ ���� ���� ������ �����ϴ�.
// Sharable ������̼��� ������ ��ǥ���� Ŭ�����μ� StringDecoder�� String Encoder�� ������ ��κ� codec ��Ű���� ���Ѵ�.
@Sharable
// ���⿡ ������ ���׸� Ÿ���� ������ ���� �̺�Ʈ�� channelRead0 �޼ҵ��� �� ��° �μ��� ���������� �ȴ�.
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {

	// channelActive �޼ҵ�� ä���� ������ ���� �ٷ� ȣ��Ǵ� �̺�Ʈ��.
	// ���� ���α׷��� ���� ��� Ŭ���̾�Ʈ�� ������ ���ӵǸ� ��Ƽ�� ä���� �����ǰ� �ش� ä���� Ȱ��ȭ�Ǵµ� �� �� ȣ��ȴ�.
	// ��������� ä���� ����� ���Ŀ� ������ �۾��� ó���� �� ����ϴ� �̺�Ʈ��.
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.write(InetAddress.getLocalHost().getHostName() + " ������ ���� �ϼ̽��ϴ�!\r\n");
		ctx.write("���� �ð��� " + new Date() + " �Դϴ�.\r\n");
		// ä�ο� ��ϵ� �����͸� ��� Ŭ���̾�Ʈ�� �����Ѵ�.
		ctx.flush();
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String request)
			throws Exception {
		String response;
		boolean close = false;
		
		if (request.isEmpty()) {
			response = "����� �Է����ּ���.\r\n";
		} else if ("bye".equals(request.toLowerCase())) {
			// ���� ���ڿ��� �ԷµǾ�����
			response = "�ȳ��� ������!\r\n";
			close = true;
		} else {
			response = "�Է��Ͻ� ����� '" + request + "' �Դϴ�.\r\n";
		}
		
		// ��ɾ� �б⿡ ���� ������ �޽����� ä�ο� ����Ѵ�.
		ChannelFuture future = ctx.write(response);
		
		// ���� ��� �÷��׸� Ȯ���Ͽ� ����� Ŭ���̾�Ʈ�� ä���� �ݴ´�.
		// �� �� ChannelFuture�� ChannelFutureListener.CLOSE�� ����Ͽ� �񵿱�� ä���� �ݴ´�.
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// channelReadComplete �̺�Ʈ�� channelRead0 �̺�Ʈ�� �Ϸ�Ǹ� ȣ��Ǵ� �̺�Ʈ��.
		// ���⼭ ChannelHandlerContext�� flush �޼ҵ带 ����Ͽ� ä�ο� ��ϵ� �����͸� Ŭ���̾�Ʈ�� ��� �����Ѵ�.
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
