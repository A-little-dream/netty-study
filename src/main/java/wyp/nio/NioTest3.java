package wyp.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @author : miles wang
 * @date : 2019/9/19  6:22 PM
 */
public class NioTest3 {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(10);
        System.out.println("Capacity:"+intBuffer.capacity());   //10

        for (int i=0;i<5 ;i++ ){
            int random = new SecureRandom().nextInt(20);
            intBuffer.put(random);
        }

        System.out.println("before flip limit:"+intBuffer.limit()); //10

        intBuffer.flip();

        System.out.println("after flip limit:"+intBuffer.limit());  //5
        System.out.println("after flip position"+intBuffer.position()); //0
        System.out.println("-----------------------------------------");
        while (intBuffer.hasRemaining()){
            System.out.println("limit:"+intBuffer.limit());
            System.out.println("position:"+intBuffer.position());
            System.out.println("capacity:"+intBuffer.capacity());

            System.out.println(intBuffer.get());
        }

    }
}
