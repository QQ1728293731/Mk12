import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

//签到
public class SignIn extends Initializer {

    static int succession;
    static int point;
    static int sumPoint;
    JMenu propertiesJM = new JMenu("选项");
    JMenuItem backJMI = new JMenuItem("返回主菜单");
    JButton signInJB = new JButton("签到");
    JLabel successJL = new JLabel(String.format("签到成功，已连续签到%d天，获得%d积分，目前积分%d，等级%d", succession, point, sumPoint, getLevel(sumPoint)));

    public SignIn(String username) {
        this.username = username;
        initJFrame();
        initMenuBar();
        initContent();
        setResizable(false);
        setVisible(true);
    }

    ///计算用户等级
    private static int getLevel(int sumPoint) {
        return 1 + sumPoint / 10;
    }

    ///签到
    private boolean signIn() throws IOException {
        getData(username);
        LocalDateTime currentLDT = LocalDateTime.now();
        LocalDateTime todayMidnightLDT = LocalDateTime.of(currentLDT.getYear(), currentLDT.getMonth(), currentLDT.getDayOfMonth(), 0, 0, 0);
        //签到从每天的0点刷新，判断用户两次尝试签到的时间间隔和本次尝试签到与0点的时间间隔的大小
        long userSpan = ChronoUnit.MILLIS.between(lastLDT, currentLDT);
        long signInSpan = ChronoUnit.MILLIS.between(todayMidnightLDT, currentLDT);
        if (userSpan < signInSpan) {
            return false;
        } else if (userSpan > signInSpan && ChronoUnit.DAYS.between(lastLDT, currentLDT) == 1) {
            point += succession;
            succession++;
        } else {
            point = 1;
            succession = 1;
        }
        sumPoint += point;
        lastLDT = lastLDT.with(currentLDT);
        saveData(sumPoint + "", 3);
        saveData(getLevel(sumPoint) + "", 4);
        saveData(succession + "", 5);
        saveData(lastLDT + "", 6);
        return true;
    }

    ///获取用户数据：积分，连续签到天数，上次签到时间，也就是索引3，5，6。等级无需获取，由积分计算产生。
    void getData(String username) throws IOException {
        File file = new File(dir + "User\\" + username);
        decrypt(file, username);
        File temp = new File(dir + "Temp\\" + username);
        BufferedReader br = new BufferedReader(new FileReader(temp));
        br.readLine();
        br.readLine();
        br.readLine();
        point = Integer.parseInt(br.readLine());
        br.readLine();
        succession = Integer.parseInt(br.readLine());
        lastLDT = LocalDateTime.parse(br.readLine());
        br.close();
        if (!temp.delete()) {
            System.out.println(username + "数据删除失败，程序紧急中止！");
            System.exit(-1);
        }
    }

    @Override
    void collectData() {

    }

    @Override
    void initJFrame() {
        setLayout(null);
        setSize(356, 390);
        setTitle(version);
        setIcon();
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    void initMenuBar() {
        JMenuBar jmb = new JMenuBar();
        jmb.add(propertiesJM);
        propertiesJM.add(backJMI);
        jmb.add(aboutJM);
        aboutJM.addMouseListener(this);
        propertiesJM.addMouseListener(this);
        backJMI.addMouseListener(this);
        setJMenuBar(jmb);
    }

    @Override
    void initContent() {
        signInJB.setBounds(95, 180, 150, 50);
        signInJB.addMouseListener(this);
        con.add(signInJB);

        successJL.setBounds(0, 130, 356, 30);
        con.add(successJL);
        successJL.setVisible(false);

        con.add(backJB);
        aboutJM.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();
        if (code == 27) {
            dispose();
            new MainMenu(username);
        } else if (code == 71) showAbout();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Object thing = mouseEvent.getSource();
        if (thing == aboutJM) showAbout();
        else if (thing == backJMI || thing == backJB) {
            dispose();
            new MainMenu(username);
        } else if (thing == signInJB) {
            try {
                if (signIn()) {
                    successJL.setText(String.format("签到成功，已连续签到%d天，获得%d积分，目前积分%d，等级%d", succession, point, sumPoint, getLevel(sumPoint)));
                    successJL.setVisible(true);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            signInJB.setText("今日已签到");
        }
        if (thing == backJB || thing == signInJB) {
            try {
                playButtonClickRelease();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        Object thing = mouseEvent.getSource();
        if (thing == signInJB || thing == backJB) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (thing == backJB) {
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回 - 按下.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        }
        if (thing == backJB || thing == signInJB) {
            try {
                playButtonRollover();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        Object thing = mouseEvent.getSource();
        if (thing == signInJB || thing == backJB) setCursor(Cursor.getDefaultCursor());
        if (thing == backJB) {
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        }
    }
}
