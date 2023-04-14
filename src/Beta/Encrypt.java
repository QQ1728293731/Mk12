package Beta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

//加密算法练习
public class Encrypt {

    ///使用异或运算进行加密
    public static void xor(File src) throws IOException {
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream("666.txt");
        int b;
        while ((b = fis.read()) != -1) {
            fos.write(b ^ 3);
        }
        fos.close();
        fis.close();
    }

    ///重载xor方法，允许用户直接输入文件地址
    public static void xor(String path) throws IOException {
        xor(new File(path));
    }
}
