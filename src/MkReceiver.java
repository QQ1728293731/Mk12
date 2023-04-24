import java.io.IOException;
import java.net.*;

//此类使用UDP协议进行数据接收
public class MkReceiver {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(33334);
        byte[] content = new byte[1024];
        DatagramPacket dp = new DatagramPacket(content, content.length);
        ds.receive(dp);
        content = dp.getData();
        System.out.println(new String(content));
        ds.close();
    }
}
