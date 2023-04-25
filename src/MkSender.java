import java.io.IOException;
import java.net.*;

//此类使用UDP协议进行数据发送
public class MkSender {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        String content = "Hello, World!";
        byte[] arr = content.getBytes();
<<<<<<< Updated upstream
        InetAddress ia = InetAddress.getByName("10.8.29.218");
=======
        InetAddress ia = InetAddress.getByName("127.0.0.1");
>>>>>>> Stashed changes
        DatagramPacket dp = new DatagramPacket(arr, arr.length, ia, 33333);
        ds.send(dp);
        ds.close();
    }
}
