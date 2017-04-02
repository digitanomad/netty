package netty.ch5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandlerWithFuture extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ChannelFuture channelFuture = ctx.writeAndFlush(msg);
		
		// ��Ƽ�� ������ msg ��ü�� ByteBuf ��ü��. ���� Ŭ���̾�Ʈ�κ��� ������ �����͸� Ŭ���̾�Ʈ�� �ǵ����ֹǷ� ������ �������� ũ��� msg ��ü�� ũ��� ����.
		// msg ��ü�� ����� �������� ũ�⸦ final ���� ������ �����Ѵ�.
		final int writeMessageSize = ((ByteBuf)msg).readableBytes();
		
		// ����� ���� ä�� �����ʸ� �����Ͽ� ChannelFuture ��ü�� �Ҵ��Ѵ�.
		channelFuture.addListener(new ChannelFutureListener() {
			// operationComplete �޼ҵ�� ChannelFuture ��ü���� �߻��ϴ� �۾� �Ϸ� �̺�Ʈ �޼ҵ�μ� ����� ���� ä�� ������ ������ ���ԵǾ�� �Ѵ�.
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				System.out.println("������ Bytes : " + writeMessageSize);
				// ChannelFuture ��ü�� ���Ե� ä���� �����ͼ� ä�� �ݱ� �̺�Ʈ�� �߻���Ų��.
				future.channel().close();
			}
		});
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
