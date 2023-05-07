import java.util.Random;
import java.util.Scanner;

public class Game2048 { private final int UP = 1;
    private final int DOWN = 2;
    private final int LEFT = 3;
    private final int RIGHT = 4;
    //创建游戏主体：一个4*4二维数组
    int[][] board = new int[4][4];
    int score = 0;
    int step = 0;
    Random r = new Random();
    //判断用户的指令是否有效，即数组是否真的发生了变化
    private boolean changed;

    public Game2048() {
        changed = true;
        spawn();
        changed = true;
        spawn();
        initContent();
    }

    public static void main(String[] args) {
        new Game2048();
    }

    //打印游戏
    private void print() {
        System.out.println("得分：" + score + "，步数：" + step);
        for (int[] row : board) {
            for (int i : row) {
                if (i == 0)
                    System.out.print("-    ");
                else {
                    if (i < 9) System.out.print(i + "    ");
                    else if (i < 99) System.out.print(i + "   ");
                    else if (i < 999) System.out.print(i + "  ");
                    else System.out.print(i + " ");
                }
            }
            System.out.println();
        }
    }

    private void initContent() {
        print();
        Scanner sc = new Scanner(System.in);
        String order = "3";
        while (!order.equals("0")) {
            order = sc.nextLine();
            if (order.equalsIgnoreCase("w")) {
                act(UP);
            } else if (order.equalsIgnoreCase("s")) {
                act(DOWN);
            } else if (order.equalsIgnoreCase("a")) {
                act(LEFT);
            } else if (order.equalsIgnoreCase("d")) {
                act(RIGHT);
            } else if (order.equals("z")) System.exit(0);
            else {
                System.out.println("无效指令");
            }
        }
    }

    //将合并，移动和展示整合成一个动作。先移动，再合并，最后再移动一次。
    private void act(int direction) {
        move(direction);
        merge(direction);
        move(direction);
        if (changed) step++;
        spawn();
        print();
        if (over()) {
            System.out.println("游戏结束，最终得分" + score + "，所用步数" + step);
            System.exit(0);
        }
    }

    //生成新数字的方法：随机找一个元素，如果不是0则再循环一次；如果是0就在这里生成2或4（概率五五开）。如果用户执行了移动操作但是数组没有变化则不生成。
    private void spawn() {
        if (changed) {
            while (true) {
                int a = r.nextInt(4);
                int b = r.nextInt(4);
                if (board[a][b] == 0) {
                    board[a][b] = (r.nextInt(2) + 1) * 2;
                    changed = false;
                    return;
                }
            }
        }
    }

    //移动
    private void move(int direction) {
        if (direction == UP) {
            for (int k = 0; k < 3; k++) {
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 3; i++) {
                        if (board[i][j] == 0 && board[i + 1][j] != 0) {
                            changed = true;
                            board[i][j] = board[i + 1][j];
                            board[i + 1][j] = 0;
                        }
                    }
                }
            }
        } else if (direction == DOWN) {
            for (int k = 0; k < 3; k++) {
                for (int j = 0; j < 4; j++) {
                    for (int i = 3; i > 0; i--) {
                        if (board[i][j] == 0 && board[i - 1][j] != 0) {
                            changed = true;
                            board[i][j] = board[i - 1][j];
                            board[i - 1][j] = 0;
                        }
                    }
                }
            }
        } else if (direction == LEFT) {
            for (int k = 0; k < 3; k++) {
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == 0 && board[i][j + 1] != 0) {
                            changed = true;
                            board[i][j] = board[i][j + 1];
                            board[i][j + 1] = 0;
                        }
                    }
                }
            }
        } else if (direction == RIGHT) {
            for (int k = 0; k < 3; k++) {
                for (int i = 0; i < 4; i++) {
                    for (int j = 3; j > 0; j--) {
                        if (board[i][j] == 0 && board[i][j - 1] != 0) {
                            changed = true;
                            board[i][j] = board[i][j - 1];
                            board[i][j - 1] = 0;
                        }
                    }
                }
            }
        }
    }

    //合并
    private void merge(int direction) {
        if (direction == UP) {
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 3; i++) {
                    if (board[i][j] != 0 && board[i][j] == board[i + 1][j]) {
                        changed = true;
                        board[i][j] = board[i + 1][j] * 2;
                        score += board[i][j];
                        board[i + 1][j] = 0;
                    }
                }
            }
        } else if (direction == DOWN) {
            for (int j = 0; j < 4; j++) {
                for (int i = 3; i > 0; i--) {
                    if (board[i][j] != 0 && board[i][j] == board[i - 1][j]) {
                        changed = true;
                        board[i][j] = board[i - 1][j] * 2;
                        score += board[i][j];
                        board[i - 1][j] = 0;
                    }
                }
            }
        } else if (direction == LEFT) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] != 0 && board[i][j] == board[i][j + 1]) {
                        changed = true;
                        board[i][j] = board[i][j + 1] * 2;
                        score += board[i][j];
                        board[i][j + 1] = 0;
                    }
                }
            }
        } else if (direction == RIGHT) {
            for (int i = 0; i < 4; i++) {
                for (int j = 3; j > 0; j--) {
                    if (board[i][j] != 0 && board[i][j] == board[i][j - 1]) {
                        changed = true;
                        board[i][j] = board[i][j - 1] * 2;
                        score += board[i][j];
                        board[i][j - 1] = 0;
                    }
                }
            }
        }
    }

    //判断游戏是否结束，即是否已经无法移动
    private boolean over() {
        for (int[] row : board) {
            for (int i : row) {
                if (i == 0) return false;
            }
        }
        if (board[0][1] == board[1][1] || board[2][1] == board[1][1] || board[1][0] == board[1][1] || board[1][2] == board[1][1])
            return false;
        else if (board[0][2] == board[1][2] || board[2][2] == board[1][2] || board[1][3] == board[1][2])
            return false;
        else if (board[2][0] == board[2][1] || board[3][1] == board[2][1] || board[2][2] == board[2][1])
            return false;
        else if (board[3][2] == board[2][2] || board[2][3] == board[2][2])
            return false;
        else if (board[1][0] == board[0][0] || board[0][1] == board[0][0] || board[0][2] == board[0][3] || board[1][3] == board[0][3])
            return false;
        else if (board[0][1] == board[0][2] || board[1][0] == board[2][0] || board[3][1] == board[3][2] || board[1][3] == board[2][3])
            return false;
        else
            return board[2][0] != board[3][0] && board[3][1] != board[3][0] && board[2][3] != board[3][3] && board[3][2] != board[3][3];
    }
}
