package wyp.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author : miles wang
 * @date : 2019/9/18  10:24 AM
 * java nio的第一个例子
 * Nio只能从buffer中读取数据，不可能从channel中读数据，同buffer既可以读，也可以写
 * 读变成写，或者写变成读之前一定要调用buffer.flip();
 */
public class NioTest1 {
    public static void main(String[] args) {
        //分配一个大小为10的缓冲区
        IntBuffer buffer=IntBuffer.allocate(10);

        for (int i=0;i<buffer.capacity();i++){
            int i1 = new SecureRandom().nextInt(100);
            buffer.put(i1);
        }

        buffer.flip();

        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
