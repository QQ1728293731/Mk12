import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

////此类使用TCP协议进行数据接收
public class MkTCPServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(33333);
        Socket socket = ss.accept();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        int i;
        while ((i = br.read()) != -1) {
            System.out.print((char) i);
        }
        br.close();
        ss.close();
    }
}
