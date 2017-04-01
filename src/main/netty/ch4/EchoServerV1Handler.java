package netty.ch4;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerV1Handler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// 수신된 데이터는 네티의 ByteBuf 객체에 저장되어 있으며 이벤트 메서드의 두번째 인자인 msg를 통해서 접근할 수 있다.
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
