package wyp.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author : miles wang
 * @date : 2019/9/18  11:01 AM
 * 创建一个输出流  然后把io转成nio然后把指定内容写到文件里面
 * buffer一定要关联到一个channel上
 */
public class NioTest2 {
    public static void main(String[] args) throws  Exception {
        FileOutputStream fileOutputStream = new FileOutputStream("./test2.txt");
        //获取nio的channel
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] bytes = "你好".getBytes();

        for (int i=0 ;i<bytes.length;i++){
            //向 byteBuffer中写入数据
            byteBuffer.put(bytes[i]);
        }
        //切换  由写切换为读
        byteBuffer.flip();
        //把buffer中的数据读取到channel中
        channel.write(byteBuffer);

        fileOutputStream.close();
    }
}
