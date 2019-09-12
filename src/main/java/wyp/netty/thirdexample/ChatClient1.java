package wyp.netty.thirdexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import wyp.netty.secondex.MyClientInitializer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author : miles wang
 * @date : 2019/9/10  10:36 AM
 */
public class ChatClient1  {

    public static void main(String[] args) throws  Exception{
        NioEventLoopGroup clientGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(clientGroup).channel(NioSocketChannel.class)
                    .handler(new ChatClientInitializer1());


            Channel channel=bootstrap.connect("localhost",8899).sync().channel();
            //标准输入：键盘输入
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            for (;;){
                channel.writeAndFlush(bufferedReader.readLine()+"\r\n");
            }

        } finally {
            clientGroup.shutdownGracefully();
        }
    }
}
