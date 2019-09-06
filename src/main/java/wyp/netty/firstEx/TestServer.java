package wyp.netty.firstEx;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author : miles wang
 * @date : 2019/9/6  10:07 AM
 * 基本逻辑： 定义好workerGroup然后在childHandler中构造好我们自己的Initializer然后在我们自己的
 *          Initializer中重写initChannel
 */
public class TestServer {

    public static void main(String[] args) throws  Exception {
        // 两个连接池，构成了异步处理   1 接收连接， 2 处理接连的逻辑
        // 两个事件循环组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 管道，通过反射创建
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());     //子处理器

            ChannelFuture sync = serverBootstrap.bind(8899).sync();
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
