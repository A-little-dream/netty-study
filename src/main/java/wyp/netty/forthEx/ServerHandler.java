package wyp.netty.forthEx;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author : miles wang
 * @date : 2019/9/12  4:24 PM
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent=(IdleStateEvent) evt;
            String eventType=null;
            switch (idleStateEvent.state()){
                case READER_IDLE:
                    eventType="读超时";
                    break;
                case ALL_IDLE:
                    eventType="读写超时";
                    break;
                case WRITER_IDLE:
                    eventType="写超时";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress()+":"+eventType);
            ctx.channel().close();
        }
    }
}
