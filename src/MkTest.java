//此类用于各种疑难理论测试，成功后可使用branch功能进行实际测试
//现在正在测试：清屏

import java.io.IOException;
import java.util.Scanner;

public class MkTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("666");
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}
