package netty.ch6;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf readMessage = (ByteBuf)msg;
		System.out.println("channelRead : " + readMessage.toString(Charset.defaultCharset()));
		
		// ChannelHandlerContext�� ���ؼ� ��Ƽ �����ӿ�ũ���� �ʱ�ȭ�� ByteBufAllocator�� ������ �� �ִ�.
		// ByteBufAllocator�� ����Ʈ ���� Ǯ�� �����ϴ� �������̽��� �÷����� ���� ���ο� ���� ���̷�Ʈ ���ۿ� �� ���� Ǯ�� �����Ѵ�.
		// �⺻������ ���̷�Ʈ ���� Ǯ�� �����ϸ� ���ø����̼� �������� �ʿ信 ���� �� ���� Ǯ�� ������ ���� �ִ�.
		ByteBufAllocator byteBufAllocator = ctx.alloc();
		// ByteBufAllocator�� buffer �޼ҵ带 ����Ͽ� ������ ����Ʈ ���۴� ByteBufAllocator�� Ǯ���� �����Ǹ�
		// ����Ʈ ���۸� ä�ο� ����ϰų� ��������� release �޼ҵ带 ȣ���ϸ� ����Ʈ ���� Ǯ�� ���ư���.
		ByteBuf newBuffer = byteBufAllocator.buffer();
		
		// newBuffer ���
		
		// write �޼ҵ��� �μ��� ����Ʈ ���۰� �ԷµǸ� �����͸� ä�ο� ����ϰ� �� �ڿ� ���� Ǯ�� ���ư���.
		ctx.write(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
