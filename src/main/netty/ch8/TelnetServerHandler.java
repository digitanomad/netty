package netty.ch8;

import java.net.InetAddress;
import java.util.Date;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.write("ȯ���մϴ�. " + InetAddress.getLocalHost().getHostName() + "�� �����ϼ̽��ϴ�!\r\n");
		ctx.write("���� �ð��� " + new Date() + " �Դϴ�.\r\n");
		ctx.flush();
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String request)
			throws Exception {
		String response;
		boolean close = false;
		
		if (request.isEmpty()) {
			response = "����� �Է��� �ּ���.\r\n";
		} else if ("bye".equals(request.toLowerCase())) {
			response = "���� �Ϸ� �Ǽ���!\r\n";
			close = true;
		} else {
			response = "�Է��Ͻ� ����� '" + request + "' �Դϱ�?\r\n";
		}
		
		ChannelFuture future = ctx.write(response);
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

}
