package wyp.netty.nettyWebSocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.net.Socket;

/**
 * @author : miles wang
 * @date : 2019/9/12  5:14 PM
 * netty对于请求是分段的，比如一个请求长度1000分段200分成5段，每一段都会经历一个完整的handler处理链
 *
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());

        pipeline.addLast(new ChunkedWriteHandler());
        //会把http请求聚合成一个完整的FullHttpRequest/FullHttpResponse
        pipeline.addLast(new HttpObjectAggregator(8192));


        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new WebSocketHandler());
    }
}
