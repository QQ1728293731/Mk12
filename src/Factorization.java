import java.util.Scanner;

public class Factorization {
    public static void main(String[] args) {
        System.out.println("ax³+bx²+cx+d,请输入整数a,b,c,d的值, 得因式分解(若系数有小数可以乘10或100等等变成整数)");
        System.out.println("本计算器不能算含有根号解的因式分解 或 2个无限循环小数的解 (可以有一个)");
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt(), b = sc.nextInt(), c = sc.nextInt(), d = sc.nextInt();
        double tempa = (double) a;
        double tempb = (double) b;
        double tempc = (double) c;
        double tempd = (double) d;
        if (a != 0) {
            double sign = 2001.0;
            double sign2 = 2001.0;
            double sign3 = 2001.0;
            int multiple = 1;
            for (int i = 2; i < 2000; i++) {
                if (b == 0 && c == 0 && d == 0) {
                    System.out.println("So stupid !");
                    System.exit(0);
                }
                if (a % i == 0 && b % i == 0 && c % i == 0 && d % i == 0) {
                    a /= i;
                    b /= i;
                    c /= i;
                    d /= i;
                    multiple *= i;
                }
                if (a % i == 0 && b % i == 0 && c % i == 0 && d % i == 0)
                    i--;
            }
            if (a < 0 && b <= 0 && c <= 0 && d <= 0) {
                a = -a;
                b = -b;
                c = -c;
                d = -d;
                multiple = -multiple;
            }
            if (a < 0) {
                a = -a;
                b = -b;
                c = -c;
                d = -d;
                multiple = -multiple;
            }
            for (int i = -200000; i < 200000; ) {
                double n = i * 0.01;
                if (a * n * n * n + b * n * n + c * n + d == 0.0) {
                    sign = n;//!第一个解
                    System.out.println("其中第一个根是 X1 = " + n);
                    break;
                }
                i++;
            }
            if (sign != 2001.0) {
                String s = getString(sign);
                double a1 = a;
                double step1 = -sign * a;
                double b1 = b - step1;//!step2
                double step3 = b1 * (-sign);
                double c1 = c - step3;//!step4
                //! a1x²+b1x+c1
                for (int i = 2; i < 2000; i++) {
                    if (a1 % i == 0 && b1 % i == 0 && c1 % i == 0) {
                        a1 /= i;
                        b1 /= i;
                        c1 /= i;
                        multiple *= i;
                    }
                    if (a1 % i == 0 && b1 % i == 0 && c1 % i == 0)
                        i--;
                }
                if (a1 <= 0 && b1 <= 0 && c1 <= 0) {
                    b1 = -b1;
                    c1 = -c1;
                    a1 = -a1;
                    multiple = -multiple;
                }
                sign2 = getSignFromNegative(sign2, a1, b1, c1);
                if (sign2 != 2001) {
                    String s2 = getString(sign2);
                    for (int i = 200000; i > -200000; ) {
                        double n = i * 0.01;
                        if (a1 * n * n + b1 * n + c1 == 0.0) {
                            sign3 = n;//!另一个解
                            System.out.println("其中第三个根是 X3 = " + n + "(这个 根 可能是错的, 仅供参考)");
                            break;
                        }
                        i--;
                    }
                    String s3 = getString(sign3);
                    if (-multiple * sign * sign2 * sign3 == tempd && multiple * ((-sign - sign2) * (-sign3) + (-sign) * (-sign2)) == tempc && multiple * (-sign - sign2 - sign3) == tempb) {
                        System.out.println("本次因式分解成功");
                        String multiples = getnosignString(-multiple);
                        includingZeroSolution3case1(sign, sign2, sign3, multiple);
                        /*    System.out.println(multiples + "(x" + s + ")(x" + s2 + ")(x" + s3 + ")");*/
                    } else {
                        System.out.println("本次因式分解可能是失败的因为错发了纠错机制, 请验证");
                        if (sign != 0 && sign2 != 0 && sign3 != 0) {
                            multiple = (int) (tempd / (-sign * sign2 * sign3));
                        }
                        if (multiple * ((-sign - sign2) * (-sign3) + (-sign) * (-sign2)) == tempc && multiple * (-sign - sign2 - sign3) == tempb) {
                            System.out.println("但是通过二次验证,本次因式分解很可能是成功的");
                            String multiples = getnosignString(-multiple);
                            includingZeroSolution3case1(sign, sign2, sign3, multiple);
                            /*    System.out.println(multiples + "(x" + s + ")(x" + s2 + ")(x" + s3 + ")");*/
                        } else {
                            System.out.println("本次因式分解可能是失败的因为触发了二次纠错机制, 请验证");
                            if (sign != 0 && sign2 != 0)
                                sign3 = tempd / (-sign * sign2);
                            if (tempa % sign3 == 0) {
                                threeItemAnswer(tempa, sign3, s, s2);
                            } else {
                                if (sign3 > 0 && tempa < 0) {
                                    sign3 = -sign3;
                                    tempa = -tempa;
                                    multiple = -multiple;
                                }
                                s2 = getString(sign3);
                                String tempas = getnosignString(-tempa);
                                String multiples = getnosignString(-multiple);
                                if (sign == sign2) {
                                    System.out.println(multiples + "(x" + s + ")²(" + tempas + "x" + s3 + ")");
                                    System.exit(0);
                                }
                                System.out.println(multiples + "(x" + s + ")(x" + s2 + ")(" + tempas + "x" + s3 + ")");
                            }
                        }
                    }
                } else {
                    String b1s = getnullString(-b1);
                    String c1s = getString(-c1);
                    String a1s = getnosignString(-a1);
                    String multiples = getnosignString(-multiple);
                    if (sign == 0) {
                        if (b1 == 0) {
                           if (a1*c1 < 0){
                                System.out.println(multiples + "x(x" + getsqrtString(-c1) + ")(x" + getsqrtString(c1) + ")");
                                System.exit(0);
                            }
                            System.out.println(multiples + "x(" + a1s + "x²" + c1s + ")");
                            System.exit(0);
                        }
                        System.out.println(multiples + "x(" + a1s + "x²" + b1s + "x" + c1s + ")");
                    } else {
                        if (b1 == 0) {
                            if (a1*c1 < 0){//!!!!!!!!!!!!!!!!!!!!!!不知道后面展开的a1s有没有提到前面的系数去, 因为隔了时间, 思路断过
                                System.out.println(multiples + "(x" + s + ")(x" + getsqrtString(-c1) + ")(x" + getsqrtString(c1) + ")");
                                System.exit(0);
                            }//!!!!!!!!!!!!!!!!!!!!!!不知道后面展开的a1s有没有提到前面的系数去, 因为隔了时间, 思路断过
                            System.out.println(multiples + "(x" + s + ")(" + a1s + "x²" + c1s + ")");
                            System.exit(0);
                        }
                        System.out.println(multiples + "(x" + s + ")(" + a1s + "x²" + b1s + "x" + c1s + ")");
                    }
                }
            } else System.out.println("本式子应该无法因式分解, 或者解含根号 或 2个无限循环小数的解 (可以有一个)");
        } else { //!一元二次方程
            double sign1 = 2001;
            double sign2 = 2001;
            int multiple = 1;
            for (int i = 2; i < 2000; i++) {
                if (c == 0 && d == 0 || b == 0 && d == 0 || b == 0 && c == 0) {
                    System.out.println("So stupid !");
                    System.exit(0);
                }
                if (b % i == 0 && c % i == 0 && d % i == 0) {
                    b /= i;
                    c /= i;
                    d /= i;
                    multiple *= i;
                }
                if (b % i == 0 && c % i == 0 && d % i == 0)
                    i--;
            }
            if (b <= 0 && c <= 0 && d <= 0) {
                b = -b;
                c = -c;
                d = -d;
                multiple = -multiple;
            }
            if (b < 0) {
                b = -b;
                c = -c;
                d = -d;
                multiple = -multiple;
            }
            for (int i = 200000; i > -200000; ) {
                double n = i * 0.01;
                if (b * n * n + c * n + d == 0.0) {
                    sign1 = n;//!第一个解
                    System.out.println("其中第一个根是 X1 = " + n);
                    break;
                }
                i--;
            }
            if (sign1 != 2001.0) {
                String s1 = getString(sign1);
                sign2 = getSignFromNegative(sign2, b, c, d);
                String s2 = getString(sign2);
                if (multiple * sign1 * sign2 == tempd && multiple * (-sign1 - sign2) == tempc) {
                    System.out.println("本次因式分解成功");
                    includingZeroSolution2case1(sign1, sign2, multiple);
                } else {
                    System.out.println("本次因式分解可能是失败的因为错发了纠错机制, 请验证");
                    if (sign1 != 0 && sign2 != 0)
                        multiple = (int) (tempd / (sign1 * sign2));
                    if (multiple * (-sign1 - sign2) == tempc) {
                        System.out.println("但是通过二次验证,本次因式分解很可能是成功的");
                        includingZeroSolution2case1(sign1, sign2, multiple);
                    } else {
                        System.out.println("本次因式分解可能是失败的因为触发了二次纠错机制, 请验证");
                        if (sign1 != 0)
                            sign2 = tempd / sign1;
                        if (tempb % sign2 == 0) {
                            getMendedTwoItemAnswer(tempb, sign2, s1);
                        } else {
                            if (sign2 > 0 && tempa < 0) {
                                sign2 = -sign2;
                                tempb = -tempb;
                                multiple = -multiple;
                            }
                            s2 = getString(sign2);
                            String tempbs = getnosignString(-tempb);
                            String multiples = getnosignString(-multiple);
                            System.out.println(multiples + "(x" + s1 + ")(" + tempbs + "x" + s2 + ")");
                        }
                    }
                }
            } else { //!平方差公式搞出根号
                if (b * d < 0 && c == 0) {
                    if (b < 0) {
                        b = -b;
                        d = -d;
                        multiple = -multiple;
                    }
                    String multiples = getnosignString(-multiple);
                    String s1 = getsqrtString(d);
                    String s2 = getsqrtString(-d);
                    System.out.println(multiples + "(x" + s1 + ")(x" + s2 + ")");//!含根号
                    System.exit(0);
                }
                System.out.println("本式子无法因式分解, 或者含有根号的解 或 2个无限循环小数的解 (可以有一个)");
            }
        }
    }
    private static void getMendedTwoItemAnswer(double tempb, double sign2, String s1) {
        String s2;
        int multiple;
        tempb /= sign2;
        multiple = (int) sign2;
        sign2 = 1;
        if (tempb < 0) {//!sign>0恒成立
            multiple = -multiple;
            tempb = -tempb;
            sign2 = -sign2;
        }
        s2 = getString(sign2);
        String multiples = getnosignString(-multiple);
        String tempbs = getnosignString(-tempb);
        System.out.println(multiples + "(x" + s1 + ")(" + tempbs + "x" + s2 + ")");
    }

    private static void threeItemAnswer(double tempa, double sign3, String s, String s2) {
        int multiple;
        String s3;
        tempa /= sign3;
        multiple = (int) sign3;
        sign3 = 1;
        if (tempa < 0) {//!sign>0恒成立
            multiple = -multiple;
            tempa = -tempa;
            sign3 = -sign3;
        }
        s3 = getString(sign3);
        String multiples = getnosignString(-multiple);
        String tempas = getnosignString(-tempa);
        if (s.equals(s2)) {
            System.out.println(multiples + "(x" + s + ")²(" + tempas + "x" + s3 + ")");
            System.exit(0);
        }
        System.out.println(multiples + "(x" + s + ")(x" + s2 + ")(" + tempas + "x" + s3 + ")");
    }

    private static double getSignFromNegative(double sign2, double a1, double b1, double c1) {
        for (int i = -200000; i < 200000; ) {
            double n = i * 0.01;
            if (a1 * n * n + b1 * n + c1 == 0.0) {
                sign2 = n;//!另一个解
                System.out.println("其中第二个根是 X2 = " + n + "(这个 根 可能是错的, 仅供参考)");
                break;
            }
            i++;
        }
        return sign2;
    }

    private static String getString(double sign) {
        return judgeIntegerAndGetString(sign);
    }

    private static String judgeIntegerAndGetString(double sign) {
        String s;
        boolean flag = false;//!不是整数
        if (sign - (int) sign == 0)
            flag = true;//!是一个整数
        if (!flag) {
            if (sign > 0.0) {
                s = "-" + sign;
            } else s = "+" + (-sign);
            return s;
        } else {
            int x = (int) sign;
            if (x > 0) {
                s = "-" + x;
            } else s = "+" + (-x);
            return s;
        }
    }

    private static String getnosignString(double sign) {
        String s = "";
        if (sign == -1.0)
            return s;
        if (sign == 1.0)
            return s = "-";
        boolean flag = false;//!不是整数
        if (sign - (int) sign == 0)
            flag = true;//!是一个整数
        if (!flag) {
            if (sign > 0.0) {
                s = "-" + sign;
            } else s = s + (-sign);
            return s;
        } else {
            int x = (int) sign;
            if (x > 0) {
                s = "-" + x;
            } else s = s + (-x);
            return s;
        }
    }

    private static String getnullString(double sign) {
        String s;
        if (sign == -1.0)
            return s = "+";
        if (sign == 1.0)
            return s = "-";
        return judgeIntegerAndGetString(sign);
    }

    private static void includingZeroSolution3case1(double sign, double sign2, double sign3, int multiple) {
        String s1 = getString(sign);
        String s2 = getString(sign2);
        String s3 = getString(sign3);
        String multiples = getnosignString(-multiple);
        if (sign == 0 && sign2 != 0 && sign3 != 0) {
            if (sign2 == sign3) {
                System.out.println(multiples + "x(x" + s2 + ")²");
                System.exit(0);
            }
            System.out.println(multiples + "x(x" + s2 + ")(x" + s3 + ")");
            System.exit(0);
        }
        if (sign != 0 && sign2 == 0 && sign3 != 0) {
            if (sign == sign3) {
                System.out.println(multiples + "x(x" + s3 + ")²");
                System.exit(0);
            }
            System.out.println(multiples + "x(x" + s1 + ")(x" + s3 + ")");
            System.exit(0);
        }
        if (sign != 0 && sign2 != 0 && sign3 == 0) {
            if (sign2 == sign) {
                System.out.println(multiples + "x(x" + s2 + ")²");
                System.exit(0);
            }
            System.out.println(multiples + "x(x" + s1 + ")(x" + s2 + ")");
            System.exit(0);
        }
        if (sign == 0 && sign2 == 0 && sign3 != 0) {
            System.out.println(multiples + "x²(x" + s3 + ")");
            System.exit(0);
        }
        if (sign == 0 && sign2 != 0 && sign3 == 0) {
            System.out.println(multiples + "x²(x" + s2 + ")");
            System.exit(0);
        }
        if (sign != 0 && sign2 == 0 && sign3 == 0) {
            System.out.println(multiples + "x²(x" + s1 + ")");
            System.exit(0);
        }
        if (sign != 0 && sign2 != 0 && sign3 != 0) {
            if (sign2 == sign3 && sign == sign2) {
                System.out.println(multiples + "(x" + s1 + ")³");
                System.exit(0);
            }
            if (sign2 == sign3) {
                System.out.println(multiples + "(x" + s1 + ")(x" + s2 + ")²");
                System.exit(0);
            }
            if (sign == sign3) {
                System.out.println(multiples + "(x" + s1 + ")²(x" + s2 + ")");
                System.exit(0);
            }
            if (sign == sign2) {
                System.out.println(multiples + "(x" + s1 + ")²(x" + s3 + ")");
                System.exit(0);
            }
            System.out.println(multiples + "(x" + s1 + ")(x" + s2 + ")(x" + s3 + ")");
            System.exit(0);
        }

    }

    private static void includingZeroSolution2case1(double sign1, double sign2, int multiple) {
        String s1 = getString(sign1);
        String s2 = getString(sign2);
        String multiples = getnosignString(-multiple);
        if (sign1 != 0 && sign2 != 0) {
            if (sign1 != sign2) {
                System.out.println(multiples + "(x" + s1 + ")(x" + s2 + ")");
                System.exit(0);
            }
            if (sign1 == sign2) {
                System.out.println(multiples + "(x" + s1 + ")²");
                System.exit(0);
            }
        }
        if (sign1 == 0 && sign2 != 0) {
            System.out.println(multiples + "x(x" + s2 + ")");
            System.exit(0);
        }
        if (sign1 != 0 && sign2 == 0) {
            System.out.println(multiples + "x(x" + s1 + ")");
            System.exit(0);
        }
    }

    private static String getsqrtString(double n1) {
        String s;
        boolean flag = false;//!不是整数, 是无理数
        if (Math.sqrt(n1) - (int) Math.sqrt(n1) == 0)
            flag = true;//!是一个整数, 不是无理数
        if (!flag) {
            int []arr = getSimplifiedSqrtNumber((int) n1);
            if (n1 > 0.0) {
                String factors = getnullString(arr[0]);
                s = factors+"√" + (int) arr[1];
            } else {
                String factors = getnullString(-arr[0]);
                s = factors+"√" + (int) (-arr[1]);
            }
            return s;
        } else {
            int x = (int) n1;
            if (x > 0) {
                s = "-" + Math.sqrt(x);
            } else s = "+" + Math.sqrt(x);
            return s;
        }
    }
    private static int[] getSimplifiedSqrtNumber(int n){
        int factor = 1;
        for (int i = 2000; i >= 2; i--) {
            if (n%(i*i)==0) {
                n /= (i*i);
                factor *= i;
            }
            if (n % (i*i) == 0)
                i++;
        }
        int[] arr = {factor,n};
        return arr;
    }
}