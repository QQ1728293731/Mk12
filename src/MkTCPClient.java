import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

//此类使用TCP协议进行数据发送
public class MkTCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("10.8.29.218", 33333);
        OutputStream os = socket.getOutputStream();
        os.write("苟利国家生死以，岂因祸福避趋之？Window正在更新，请不要关闭计算机。".getBytes());
        os.close();
    }
}

