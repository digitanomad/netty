package netty.ch7;

import java.net.InetAddress;
import java.util.Date;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

// Sharable 어노테이션은 네티가 제공하는 공유기능 상태표시 어노테이션이다.
// Sharable로 지정된 클래스를 채널 파이프라인에서 공유할 수 있다는 의미다. 즉, 다중 스레드에서 스레드 경합 없이 참조가 가능하다.
// Sharable 어노테이션이 지정된 대표적인 클래스로서 StringDecoder와 String Encoder가 있으며 대부분 codec 패키지에 속한다.
@Sharable
// 여기에 지정된 제네릭 타입은 데이터 수신 이벤트인 channelRead0 메소도의 두 번째 인수의 데이터형이 된다.
public class TelnetServerHandler extends SimpleChannelInboundHandler<String> {

	// channelActive 메소드는 채널이 생성된 다음 바로 호출되는 이벤트다.
	// 서버 프로그램을 예로 들면 클라이언트가 서버에 접속되면 네티의 채널이 생성되고 해당 채널이 활성화되는데 이 때 호출된다.
	// 통상적으로 채널이 연결된 직후에 수행할 작업을 처리할 때 사용하는 이벤트다.
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.write(InetAddress.getLocalHost().getHostName() + " 서버에 접속 하셨습니다!\r\n");
		ctx.write("현재 시간은 " + new Date() + " 입니다.\r\n");
		// 채널에 기록된 데이터를 즉시 클라이언트로 전송한다.
		ctx.flush();
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String request)
			throws Exception {
		String response;
		boolean close = false;
		
		if (request.isEmpty()) {
			response = "명령을 입력해주세요.\r\n";
		} else if ("bye".equals(request.toLowerCase())) {
			// 종료 문자열이 입력되었으면
			response = "안녕히 가세요!\r\n";
			close = true;
		} else {
			response = "입력하신 명령은 '" + request + "' 입니다.\r\n";
		}
		
		// 명령어 분기에 따라서 생성된 메시지를 채널에 기록한다.
		ChannelFuture future = ctx.write(response);
		
		// 종료 명령 플래그를 확인하여 연결된 클라이언트의 채널을 닫는다.
		// 이 때 ChannelFuture에 ChannelFutureListener.CLOSE를 등록하여 비동기로 채널을 닫는다.
		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// channelReadComplete 이벤트를 channelRead0 이벤트가 완료되면 호출되는 이벤트다.
		// 여기서 ChannelHandlerContext의 flush 메소드를 사용하여 채널에 기록된 데이터를 클라이언트로 즉시 전송한다.
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
