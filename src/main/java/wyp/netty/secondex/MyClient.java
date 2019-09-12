package wyp.netty.secondex;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author : miles wang
 * @date : 2019/9/6  4:52 PM
 */
public class MyClient {

    public static void main(String[] args) throws  Exception{
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup).channel(NioSocketChannel.class).handler(new MyClientInitializer());
            ChannelFuture channelFuture=bootstrap.connect("localhost",8899).sync();
            channelFuture.channel().close().sync();
        } finally {
            clientGroup.shutdownGracefully();
        }
    }
}
