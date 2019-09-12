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
 *
 *          基本步骤    ：1 构建bossGroup和workerGroup然后使用ServerBootstrap启动服务器然后通过  childHandler
 *          来指定我们自定义的Initializer，然后我们可以在Initializer的initChannel方法中，来关联诸多我们自定义的
 *          各种handler然后在我们自定义的handler中复写netty所提供的各种事件的回调方法，然后就完事了
 *          无论是简单还是复杂的netty程序，都是这个步骤
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
        //serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
        //                    .handler(new TestServerInitializer());
            //如果后面跟的事   handler 则处理的事 bossGroup中的事件
            ChannelFuture sync = serverBootstrap.bind(8899).sync();
            sync.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
