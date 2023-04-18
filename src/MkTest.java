//此类用于各种疑难理论测试，成功后可使用branch功能进行实际测试
//现在正在测试：2048的原理
//游戏整体是一个4*4的二维数组，空余的格子用0表示
//用户每一次执行某个方向的移动，就判断此方向上有哪些数字可以合并。
//移动的时候，所有元素都会一直移动，直到来到边缘或者被元素挡住
//当用户搞出2048，游戏就要提示玩家胜利。当然，用户可以继续玩。
//创建一个方法，用来判断从某一个元素为起点，某一个方向上的下一个元素是否与自己一样。参数是起始元素和方向。如果发现一样的，那么就执行合并。
//这个方法只判断一次，因此别的地方会有一个嵌套循环。某方向的边缘的元素无需判断，因此每一次只需要判断12次。
//合并用一个独立的方法，参数是要合并的两个元素。合并后，该方向的下一个元素变为两倍，起始元素消失，后面的元素依次按方向移动一格
//所有合并需要全部计算完之后才能展现给用户。
//合并操作结束后要执行产生新元素的方法，这是一个独立方法。在空格中随机选取一个格子产生新元素，2或4，几率各占一半。
//合并和产生操作结束后，判断游戏是否结束。
//需要注意的是出现一个方向上有连续多个同样数字的情况。这个时候，只有方向尾端的两个数字会合并，其他的只会移动过去。

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MkTest implements ActionListener {
    //创建游戏主体：一个4*4二维数组
    int[][] board = new int[4][4];
    Random r = new Random();

    public MkTest() {
        spawn();
        initContent();
    }

    public static void main(String[] args) {
        new MkTest();
    }

    private void initContent() {
        for (int[] ints : board) {
            for (int i : ints) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    //生成新数字的方法：随机找一个元素，如果不是0则再循环一次；如果是0就在这里生成2或4（概率五五开）
    private void spawn() {
        while (true) {
            int a = r.nextInt(4);
            int b = r.nextInt(4);
            if (board[a][b] == 0) {
                board[a][b] = (r.nextInt(2) + 1) * 2;
                return;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
