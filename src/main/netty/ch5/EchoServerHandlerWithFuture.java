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
		
		// 네티가 수신한 msg 객체는 ByteBuf 객체다. 또한 클라이언트로부터 수신한 데이터를 클라이언트로 되돌려주므로 전송한 데이터의 크기는 msg 객체의 크기와 같다.
		// msg 객체에 저장된 데이터의 크기를 final 지역 변수에 저장한다.
		final int writeMessageSize = ((ByteBuf)msg).readableBytes();
		
		// 사용자 정의 채널 리스너를 생성하여 ChannelFuture 객체에 할당한다.
		channelFuture.addListener(new ChannelFutureListener() {
			// operationComplete 메소드는 ChannelFuture 객체에서 발생하는 작업 완료 이벤트 메소드로서 사용자 정의 채널 리스너 구현에 포함되어야 한다.
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				System.out.println("전송한 Bytes : " + writeMessageSize);
				// ChannelFuture 객체에 포함된 채널을 가져와서 채널 닫기 이벤트를 발생시킨다.
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
