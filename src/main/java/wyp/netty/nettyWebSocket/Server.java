package wyp.netty.nettyWebSocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import wyp.netty.forthEx.ServerInitializer;

import java.net.InetSocketAddress;

/**
 * @author : miles wang
 * @date : 2019/9/12  5:11 PM
 * netty对webSocket的支持
 */
public class Server {

    public static void main(String[] args) throws  Exception {
        // 两个连接池，构成了异步处理   1 接收连接， 2 处理接连的逻辑
        // 两个事件循环组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 管道，通过反射创建。
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))//这个处理器针对bossGroup
                    .childHandler(new WebSocketChannelInitializer());     //子处理器针对workerGroup

            ChannelFuture sync = serverBootstrap.bind(new InetSocketAddress(8899)).sync();

            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
