package netty.ch4;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerV2Handler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf readMessage = (ByteBuf) msg;
		System.out.println("channelRead : " + readMessage.toString(Charset.defaultCharset()));
		ctx.write(msg);
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelReadComplete 발생");
		// flush 메소드는 네티의 채널 버퍼에 저장된 데이터를 상대방으로 즉시 전송한다.
		ctx.flush();
	};
}
