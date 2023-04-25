import java.io.IOException;
import java.net.*;

//此类使用UDP协议进行数据发送
public class MkUDPSender {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        String content = "Hello, World!";
        byte[] arr = content.getBytes();
        InetAddress ia = InetAddress.getByName("10.8.29.218");
        DatagramPacket dp = new DatagramPacket(arr, arr.length, ia, 33333);
        ds.send(dp);
        ds.close();
    }
}
