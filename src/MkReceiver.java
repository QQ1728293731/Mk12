import java.io.IOException;
import java.net.*;

//此类使用UDP协议进行数据接收
public class MkReceiver {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(33333);
        byte[] receiver = new byte[1024];
        DatagramPacket dp = new DatagramPacket(receiver, receiver.length);
        ds.receive(dp);
        byte[] content;
        content = dp.getData();
        System.out.println(new String(content));
        ds.close();
    }
}
