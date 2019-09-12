package wyp.netty.secondex;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import wyp.netty.firstEx.TestServerInitializer;

/**
 * @author : miles wang
 * @date : 2019/9/6  4:36 PM
 * 这里是server的模板代码，具体解释在第一个server里面
 */
public class Server {

        public static void main(String[] args) throws  Exception {
            // 两个连接池，构成了异步处理   1 接收连接， 2 处理接连的逻辑
            // 两个事件循环组
            NioEventLoopGroup bossGroup = new NioEventLoopGroup();
            NioEventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap serverBootstrap = new ServerBootstrap();
                // 管道，通过反射创建
                serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                        .childHandler(new SecondInitializer());     //子处理器

                ChannelFuture sync = serverBootstrap.bind(8899).sync();
                sync.channel().closeFuture().sync();
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }


        }
}
