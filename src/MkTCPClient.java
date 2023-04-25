import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

//此类使用TCP协议进行数据发送
public class MkTCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("10.93.147.208", 33333);
        OutputStream os = socket.getOutputStream();
        os.write("My name is Apermesa".getBytes());
        os.close();
    }
}

