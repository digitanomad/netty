package netty.ch4;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerV1Handler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// ���ŵ� �����ʹ� ��Ƽ�� ByteBuf ��ü�� ����Ǿ� ������ �̺�Ʈ �޼����� �ι�° ������ msg�� ���ؼ� ������ �� �ִ�.
		ByteBuf readMessage = (ByteBuf) msg;
		System.out.println("channelRead : " + readMessage.toString(Charset.defaultCharset()));
		ctx.writeAndFlush(msg);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}