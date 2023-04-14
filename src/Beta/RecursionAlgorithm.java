package Beta;

public class RecursionAlgorithm {

    ///阶乘运算
    public static int getFactorial(int number) {
        if (number == 1) return 1;
        return number * getFactorial(number - 1);
    }
}
