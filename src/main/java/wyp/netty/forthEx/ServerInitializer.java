package wyp.netty.forthEx;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author : miles wang
 * @date : 2019/9/12  4:13 PM
 * netty提供了各种handler 就类似于我们所学的springboot的各种拦截器
 * 各个请求会通过各种handler处理，正向处理一遍，然后再反向处理一遍，最后返回
 * 设计模式中称之为： 责任链模式
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
            //空闲状态监测处理器
            //读空闲：指定时间内没有从客户端读数据
            //写空闲: 指定时间内没有写数据给客户端
            //读写空闲： 指定时间内 没有读或者写任意一个发生
        pipeline.addLast(new IdleStateHandler(5,5,3, TimeUnit.SECONDS));
        pipeline.addLast(new ServerHandler());
    }
}
