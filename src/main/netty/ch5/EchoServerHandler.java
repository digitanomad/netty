package netty.ch5;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// ���ŵ� �����͸� Ŭ���̾�Ʈ ���� ���ۿ� ����ϰ� ������ �����͸� ä�η� �����ϴ� �񵿱� �޼����� writeAndFlush�� ȣ���ϰ� ChannelFuture ��ü�� �����޴´�.
		ChannelFuture channelFuture = ctx.writeAndFlush(msg);
		// ChannelFuture ��ü�� ä���� �����ϴ� �����ʸ� ����Ѵ�.
		// ChannelFutureListener.CLOSE �����ʴ� ��Ƽ�� �����ϴ� �⺻ �����ʷμ� CloseFuture ��ü�� �Ϸ� �̺�Ʈ�� ������ �� ����ȴ�.
		channelFuture.addListener(ChannelFutureListener.CLOSE);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
