import org.apache.commons.io.input.ReversedLinesFileReader;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

//拼图小游戏
public class PuzzleGame extends Initializer implements Border {

    //管理图片数据
    static int[][] data = new int[4][4];
    //空白方块在二维数组中的位置
    static int x = 0;
    static int y = 0;
    //用户之前的最佳通关时间
    int lastBestTime;
    //用户之前的最佳通关步数
    int lastBestStep;
    int mouseClickCount = 0;
    //记录用户操作的步数
    int step = 0;
    //图片路径
    ArrayList<String> animalLibrary = new ArrayList<>();
    ArrayList<String> girlLibrary = new ArrayList<>();
    ArrayList<String> sportLibrary = new ArrayList<>();
    ArrayList<String> miscLibrary = new ArrayList<>();
    String defaultPath = "image\\girl\\girl2\\";
    String path = defaultPath;
    JLabel fullImage = new JLabel(new ImageIcon(path + "all.jpg"));
    String chooseImage;
    JMenu changeImageJM = new JMenu("更换图片");
    JMenu changeMovePatternJM = new JMenu("更改移动模式");
    JMenuItem moveBlankJMI = new JMenuItem("移动空白方块");
    JMenuItem movePuzzleJMI = new JMenuItem("移动拼图（默认）");
    JMenuItem animalJMI = new JMenuItem("动物");
    JMenuItem girlJMI = new JMenuItem("美女");
    JMenuItem sportJMI = new JMenuItem("运动");
    JMenuItem miscJMI = new JMenuItem("其他");
    JMenuItem exitGameJMI = new JMenuItem("退出游戏");
    JMenuItem replayJMI = new JMenuItem("重玩游戏");
    JMenu helpJM = new JMenu("帮助(H)");
    JMenu propertiesJM = new JMenu("选项");
    JMenuBar puzzleGameJMB = new JMenuBar();
    JLabel backgroundJL = new JLabel(new ImageIcon("image\\background.png"));
    JButton successReplayJB = new JButton("重玩");
    JButton successExitJB = new JButton("退出");
    JLabel countStepJL = new JLabel();
    boolean isReplay = false;
    JLabel timerJL = new JLabel();
    int time = 0;
    Timer timer = new Timer(1000, e -> {
        time++;
        timerJL.setText(String.format("%02d: %02d", time / 60, time % 60));
    });
    JLabel startJL = new JLabel("按下空格键开始");
    boolean isStart = false;
    JDialog help = new JDialog();
    JLabel background = new JLabel(new ImageIcon("image\\background.png"));
    private int MOVE_UP_LEFT_OR_Y = 1;
    private int MOVE_DOWN_RIGHT_OR_Y = -1;
    private int BOUNDS_UP_LEFT = 3;
    private int BOUNDS_DOWN_RIGHT = 0;

    public PuzzleGame(String username) throws IOException {
        loadLibrary();
        this.username = username;
        initJFrame();
        initMenuBar();
        initData();
        initContent();
        setVisible(true);
    }

    ///计算拼图可解性
    private static boolean isPlayable() {
        int[] array = new int[16];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                array[index] = data[i][j];
            }
        }
        //记录逆序数
        int count = x + y + 2;
        int base = 1;
        for (int i : array) {
            for (int start2 = 1; start2 < array.length - base; start2++) {
                if (i > array[start2]) count++;
            }
            base++;
        }
        return count % 2 == 0;
    }

    ///加载图片素材
    void loadLibrary() {
        animalLibrary.add("image\\animal\\animal1\\");
        animalLibrary.add("image\\animal\\animal2\\");
        animalLibrary.add("image\\animal\\animal3\\");
        animalLibrary.add("image\\animal\\animal4\\");
        animalLibrary.add("image\\animal\\animal5\\");
        animalLibrary.add("image\\animal\\animal6\\");
        animalLibrary.add("image\\animal\\animal7\\");
        animalLibrary.add("image\\animal\\animal8\\");
        girlLibrary.add("image\\girl\\girl1\\");
        girlLibrary.add("image\\girl\\girl2\\");
        girlLibrary.add("image\\girl\\girl3\\");
        girlLibrary.add("image\\girl\\girl4\\");
        girlLibrary.add("image\\girl\\girl5\\");
        girlLibrary.add("image\\girl\\girl6\\");
        girlLibrary.add("image\\girl\\girl7\\");
        girlLibrary.add("image\\girl\\girl8\\");
        girlLibrary.add("image\\girl\\girl9\\");
        girlLibrary.add("image\\girl\\girl10\\");
        girlLibrary.add("image\\girl\\girl11\\");
        sportLibrary.add("image\\sport\\sport1\\");
        sportLibrary.add("image\\sport\\sport2\\");
        sportLibrary.add("image\\sport\\sport3\\");
        sportLibrary.add("image\\sport\\sport4\\");
        sportLibrary.add("image\\sport\\sport5\\");
        sportLibrary.add("image\\sport\\sport6\\");
        sportLibrary.add("image\\sport\\sport7\\");
        sportLibrary.add("image\\sport\\sport8\\");
        sportLibrary.add("image\\sport\\sport9\\");
        sportLibrary.add("image\\sport\\sport10\\");
        miscLibrary.add("image\\misc\\misc1\\");
        miscLibrary.add("image\\misc\\misc2\\");
    }

    ///显示帮助
    private void showHelp() {
        help.setResizable(false);
        JLabel helpText = new JLabel("使用方向键或WASD控制方向，按住ctrl查看原图，可在选项中更改移动方式");
        help.setAlwaysOnTop(true);
        help.setTitle("怎么玩？");
        help.setIconImage(new ImageIcon("image\\login\\MkLogInBackground.jpg").getImage());
        helpText.setBounds(0, 0, 420, 80);
        help.getContentPane().add(helpText);
        help.setSize(420, 80);
        help.setAlwaysOnTop(true);
        help.setLocationRelativeTo(null);
        help.setVisible(true);
    }

    ///数据初始化，如果Save文件夹存在存档，则直接读取存档
    private void initData() throws IOException {
        File file = new File(dir + "Save\\PuzzleGameSave.txt");
        if (!isReplay && file.exists()) {
            loadData();
            return;
        }
        isReplay = true;
        Random r = new Random();
        int[] arrayTemp = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        do {
            for (int i = 0; i < arrayTemp.length; i++) {
                int randomIndex = r.nextInt(16);
                int temp = arrayTemp[i];
                arrayTemp[i] = arrayTemp[randomIndex];
                arrayTemp[randomIndex] = temp;
            }
            for (int i = 0; i < arrayTemp.length; i++) {
                if (arrayTemp[i] == 0) {
                    x = i / 4;
                    y = i % 4;
                }
                data[i / 4][i % 4] = arrayTemp[i];
            }
        } while (!isPlayable());
    }

    ///获取用户数据。在这里指的是用户通关后获取之前通关的最佳时间以及最佳步数
    void getData(String username) throws IOException {
        File file = new File(dir + "User\\" + username);
        decrypt(file, username);
        File temp = new File(dir + "Temp\\" + username);
        ReversedLinesFileReader rlfr = new ReversedLinesFileReader(temp, StandardCharsets.UTF_8);
        lastBestStep = Integer.parseInt(rlfr.readLine().substring(1));
        lastBestTime = Integer.parseInt(rlfr.readLine());
        rlfr.close();
        if (lastBestStep == 0) lastBestStep = 99999;
        if (lastBestTime == 0) lastBestTime = 3599;
        if (!temp.delete()) {
            System.out.println(username + "数据删除失败，程序紧急中止！PuzzleGame-getData");
            System.exit(-1);
        }
    }

    @Override
    void collectData() {

    }

    ///窗口初始化
    @Override
    void initJFrame() {
        setSize(603, 680);
        setTitle("拼图小游戏");
        setIcon();
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        addKeyListener(this);
    }

    ///菜单初始化
    @Override
    void initMenuBar() {
        changeImageJM.add(animalJMI);
        changeImageJM.add(girlJMI);
        changeImageJM.add(sportJMI);
        changeImageJM.add(miscJMI);
        propertiesJM.add(changeImageJM);
        propertiesJM.add(changeMovePatternJM);
        changeMovePatternJM.add(movePuzzleJMI);
        changeMovePatternJM.add(moveBlankJMI);
        propertiesJM.add(replayJMI);
        propertiesJM.add(exitGameJMI);
        puzzleGameJMB.add(propertiesJM);
        puzzleGameJMB.add(aboutJM);
        puzzleGameJMB.add(helpJM);
        aboutJM.addMouseListener(this);
        helpJM.addMouseListener(this);
        exitGameJMI.addMouseListener(this);
        replayJMI.addMouseListener(this);
        animalJMI.addMouseListener(this);
        girlJMI.addMouseListener(this);
        sportJMI.addMouseListener(this);
        miscJMI.addMouseListener(this);
        setJMenuBar(puzzleGameJMB);
        backgroundJL.addMouseListener(this);
        changeMovePatternJM.addMouseListener(this);
        moveBlankJMI.addMouseListener(this);
        movePuzzleJMI.addMouseListener(this);
        backgroundJL.addKeyListener(this);
    }

    ///用户退出游戏时，若游戏未完成，则保存数据至Save文件夹下的PuzzleGameSave.txt中
    void saveData() throws IOException {
        timer.stop();
        BufferedWriter bw = new BufferedWriter(new FileWriter(dir + "Save\\PuzzleGameSave.txt"));
        int newLineCount = 0;
        bw.write(path);
        bw.newLine();
        bw.write(time + "");
        bw.newLine();
        bw.write(step + "");
        bw.newLine();
        bw.write(isReplay + "");
        bw.newLine();
        bw.write(x + "");
        bw.newLine();
        bw.write(y + "");
        bw.newLine();
        for (int[] datum : data) {
            for (int i : datum) {
                bw.write((i + ""));
                if (newLineCount < 15) {
                    bw.newLine();
                    newLineCount++;
                }
            }
        }
        bw.close();
    }

    ///用户进入游戏时，如果Save文件夹中存在存档，则自动读取存档
    void loadData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(dir + "Save\\PuzzleGameSave.txt"));
        path = br.readLine();
        time = Integer.parseInt(br.readLine());
        step = Integer.parseInt(br.readLine());
        isReplay = Boolean.parseBoolean(br.readLine());
        x = Integer.parseInt(br.readLine());
        y = Integer.parseInt(br.readLine());
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                data[i][j] = Integer.parseInt(br.readLine());
            }
        }
        br.close();
    }

    ///内容初始化
    @Override
    void initContent() throws IOException {
        timerJL.setText(String.format("%02d: %02d", time / 60, time % 60));
        con.removeAll();
        con.add(startJL);
        startJL.setBounds(182, 292, 219, 50);
        startJL.setOpaque(true);
        startJL.setBackground(Color.WHITE);
        if (victory()) {
            timer.stop();
            JLabel victoryJL = new JLabel(new ImageIcon("image\\win.png"));
            victoryJL.setBounds(203, 283, 197, 73);
            successReplayJB.setBounds(235, 380, 60, 30);
            successExitJB.setBounds(305, 380, 60, 30);
            con.add(successReplayJB);
            con.add(successExitJB);
            con.add(victoryJL);
            getData(username);
            //获取用户上次的记录，若这次破纪录了，则更新数据
            if (step != 99999 && step < lastBestStep) {
                saveData(step + "", 9);
            }
            if (time != 3599 && time < lastBestTime) {
                saveData(time + "", 8);
            }
            loadPuzzles();
            con.repaint();
            successReplayJB.setVisible(true);
            successExitJB.setVisible(true);
            successReplayJB.setFocusable(true);
            successReplayJB.addKeyListener(this);
            File file = new File(dir + "Save\\PuzzleGameSave.txt");
            //通关后，删除之前的游戏存档
            if (file.exists()) {
                file.delete();
            }
            return;
        }
        countStepJL.setBounds(50, 30, 100, 20);
        timerJL.setBounds(500, 30, 80, 20);
        con.add(timerJL);
        loadPuzzles();
        con.repaint();
        aboutJM.addKeyListener(this);
        successReplayJB.addMouseListener(this);
        requestFocus();
        successExitJB.addMouseListener(this);
        successExitJB.setVisible(false);
        successReplayJB.setVisible(false);
        startJL.setFont(new Font(null, Font.BOLD, 30));
    }

    ///加载拼图
    private void loadPuzzles() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int number = data[i][j];
                ImageIcon icon = new ImageIcon(new ImageIcon(path + number + ".jpg").getImage().getScaledInstance(105, 105, Image.SCALE_DEFAULT));
                JLabel mainImage = new JLabel(icon);
                mainImage.setBounds(105 * i + 83, 105 * j + 134, 105, 105);
                mainImage.setBorder(new BevelBorder(BevelBorder.RAISED));
                con.add(mainImage);
                backgroundJL.setBounds(40, 40, 508, 560);
                con.add(backgroundJL);
                countStepJL.setText("步数：" + step);
                con.add(countStepJL);
            }
        }
    }

    ///判断用户是否胜利
    boolean victory() {
        int index = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (data[j][i] != index) return false;
                index++;
                if (index == 16) return true;
            }
        }
        return true;
    }

    ///更换图片
    void changeImage() {
        switch (chooseImage) {
            case "animal":
                path = animalLibrary.get(new Random().nextInt(animalLibrary.size()));
                break;
            case "girl":
                path = girlLibrary.get(new Random().nextInt(girlLibrary.size()));
                break;
            case "sport":
                path = sportLibrary.get(new Random().nextInt(sportLibrary.size()));
                break;
            case "misc": {
                path = miscLibrary.get(new Random().nextInt(miscLibrary.size()));
            }
            break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    ///重玩
    void replay() throws IOException {
        timer.restart();
        time = 0;
        step = 0;
        isReplay = true;
        initData();
        initContent();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == aboutJM) showAbout();
        else if (thing == helpJM) showHelp();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == replayJMI || thing == successReplayJB) {
            try {
                replay();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (thing == exitGameJMI || thing == successExitJB) {
            if (!victory()) {
                try {
                    saveData();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            dispose();
            new GamesMenu(username);
        } else if (thing == animalJMI) {
            chooseImage = "animal";
            changeImage();
            try {
                replay();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (thing == girlJMI) {
            chooseImage = "girl";
            changeImage();
            try {
                replay();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (thing == sportJMI) {
            chooseImage = "sport";
            changeImage();
            try {
                replay();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (thing == miscJMI) {
            chooseImage = "misc";
            changeImage();
            try {
                replay();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (thing == backgroundJL) mouseClickCount++;
        else if (thing == moveBlankJMI) {
            MOVE_UP_LEFT_OR_Y = -1;
            MOVE_DOWN_RIGHT_OR_Y = 1;
            BOUNDS_UP_LEFT = 0;
            BOUNDS_DOWN_RIGHT = 3;
        } else if (thing == movePuzzleJMI) {
            MOVE_UP_LEFT_OR_Y = 1;
            MOVE_DOWN_RIGHT_OR_Y = -1;
            BOUNDS_UP_LEFT = 3;
            BOUNDS_DOWN_RIGHT = 0;
        }
        if (mouseClickCount == 3) showHelp();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == successExitJB || thing == successReplayJB)
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == successExitJB || thing == successReplayJB)
            setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    ///用户按住不松
    @Override
    public void keyPressed(KeyEvent e) {
        if (victory()) return;
        int code = e.getKeyCode();
        if (isStart && code == 17) {
            con.removeAll();
            fullImage = new JLabel(new ImageIcon(path + "all.jpg"));
            fullImage.setBounds(83, 134, 420, 420);
            background.setBounds(40, 40, 508, 560);
            con.add(fullImage);
            con.add(background);
            con.repaint();
        }
    }

    ///用户键盘操作
    @Override
    public void keyReleased(KeyEvent e) {
        //左 37, 上 38, 右 39, 下 40, W 87, A 65, S 83, D 68, Esc 27, Enter 10, 小- 109, ctrl 17, G 71, H 72, 空格 32
        if (victory()) return;
        int code = e.getKeyCode();
        if (code == 32) {
            isStart = true;
            startJL.setVisible(false);
            timer.start();
        }
        if (isStart) {
            if (code == 37 || code == 65) {
                if (x != BOUNDS_UP_LEFT) {
                    step++;
                    data[x][y] = data[x + MOVE_UP_LEFT_OR_Y][y];
                    data[x + MOVE_UP_LEFT_OR_Y][y] = 0;
                    x += MOVE_UP_LEFT_OR_Y;
                    try {
                        initContent();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else if (code == 38 || code == 87) {
                if (y != BOUNDS_UP_LEFT) {
                    step++;
                    data[x][y] = data[x][y + MOVE_UP_LEFT_OR_Y];
                    data[x][y + MOVE_UP_LEFT_OR_Y] = 0;
                    y += MOVE_UP_LEFT_OR_Y;
                    try {
                        initContent();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else if (code == 39 || code == 68) {
                if (x != BOUNDS_DOWN_RIGHT) {
                    step++;
                    data[x][y] = data[x + MOVE_DOWN_RIGHT_OR_Y][y];
                    data[x + MOVE_DOWN_RIGHT_OR_Y][y] = 0;
                    x += MOVE_DOWN_RIGHT_OR_Y;
                    try {
                        initContent();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else if (code == 40 || code == 83) {
                if (y != BOUNDS_DOWN_RIGHT) {
                    step++;
                    data[x][y] = data[x][y + MOVE_DOWN_RIGHT_OR_Y];
                    data[x][y + MOVE_DOWN_RIGHT_OR_Y] = 0;
                    y += MOVE_DOWN_RIGHT_OR_Y;
                    try {
                        initContent();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            } else if (code == 17) {
                try {
                    initContent();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            //官方开挂键，数字键盘“-”
            else if (code == 109) {
                step = 99999;
                time = 3599;
                data = new int[][]{
                        {1, 5, 9, 13},
                        {2, 6, 10, 14},
                        {3, 7, 11, 15},
                        {4, 8, 12, 0}
                };
                try {
                    initContent();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else if (code == 27) {
                if (!victory()) {
                    try {
                        saveData();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                dispose();
                new GamesMenu(username);
            } else if (code == 71) showAbout();
            else if (code == 72) showHelp();
        }
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

    }

    @Override
    public Insets getBorderInsets(Component c) {
        return null;
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }
}
