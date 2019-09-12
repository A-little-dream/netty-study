package wyp.netty.thirdexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author : miles wang
 * @date : 2019/9/10  10:04 AM
 * 当客户端和服务端建立好连接以后，如果server想要向client建立通信，则需要保存好这些channel。这些
 * channel保存在channelGroup中
 */
@ChannelHandler.Sharable
public class ChatServerHandler extends SimpleChannelInboundHandler<String> {


     private static ChannelGroup channelGroup= new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * <strong>Please keep in mind that this method will be renamed to
     * {@code messageReceived(ChannelHandlerContext, I)} in 5.0.</strong>
     * <p>
     * Is called for each message of type {@link I}.
     *
     * @param ctx the {@link ChannelHandlerContext} which this {@link SimpleChannelInboundHandler}
     *            belongs to
     * @param msg the message to handle
     * @throws Exception is thrown if an error occurred
     *
     * 服务器端收到客户端发来的任意一个消息， 此方法会被调用
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //获取当前channel，指的是收到消息的客户端和服务器建立的channel
        Channel channel = ctx.channel();

        channelGroup.forEach(ch->{
            if (channel != ch){
                //如果 遍历到的channle不是当前的channle，即表明不是此channel不是收到消息的channel
                ch.writeAndFlush(channel.remoteAddress()+"发送的消息："+msg+"\n");
            }else {
                //表明此时遍历到的channel是 正在发送消息的client和server建立的
                ch.writeAndFlush("自己"+msg+"\n");
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

    }

    /**
     * 当服务端和客服端连接建立好以后，会回调这个方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("服务器:-->"+channel.remoteAddress()+"加入\n");
        channelGroup.add(channel);
    }


    /**
     * 当连接断掉，会执行这个回调方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("服务器:-->"+channel.remoteAddress()+"断开\n");
        //netty 会自动的Remove掉断开的连接，所以我们不需要手动从channelGroup中remove
    }

    /**
     * 连接处于不活动状态会调用此方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        System.out.println(channel.remoteAddress()+"下线\n");
    }

    /**
     * 连接处于活动状态
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress()+"上线\n");
    }
}
