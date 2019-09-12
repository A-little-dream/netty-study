package wyp.netty.secondex;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @author : miles wang
 * @date : 2019/9/6  5:01 PM
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("server remote address is :"+ctx.channel().remoteAddress());
        System.out.println("msg is :"+msg);
        //向服务器返回数据
        ctx.writeAndFlush("msg is from client time is :"+ LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("now bulid connection exec channelActive()");
        ctx.writeAndFlush("this is response when receive msg from server");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.channel().close();
    }
}
