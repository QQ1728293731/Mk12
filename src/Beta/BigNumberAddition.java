package Beta;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

///按照艾心仪的一道算法题的严格要求写出的大数加法
public class BigNumberAddition {
    public static void main(String[] args) {
        String num;
        BigInteger bi = new BigInteger("0");
        ArrayList<String> resultAL = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        do {
            do {
                num = input.next();
                if (num.length() > 2) {
                    bi = bi.add(new BigInteger(num));
                } else break;
            } while (true);
            resultAL.add(bi.toString());
            bi = bi.subtract(bi);
        } while (!num.equals("0"));
        for (int i = 1; i < resultAL.size(); i++) {
            System.out.println(resultAL.get(i));
        }
    }
}
