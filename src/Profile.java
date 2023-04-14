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
import java.time.format.DateTimeFormatter;

//展示用户资料
public class Profile extends Initializer {
    JMenu propertiesJM = new JMenu("选项");
    JMenuItem backJMI = new JMenuItem("返回主菜单");
    JLabel profileJL = new JLabel();
    JLabel changePhoneNumberJL = new JLabel("修改");

    public Profile(String username) throws IOException {
        this.username = username;
        initJFrame();
        initMenuBar();
        initContent();
        setResizable(false);
        setVisible(true);
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
    void initContent() throws IOException {
        con.add(backJB);
        profileJL.setBounds(50, 0, 356, 360);
        File file = new File(dir + "User\\" + username);
        decrypt(file, username);
        File temp = new File(dir + "Temp\\" + username);
        BufferedReader br = new BufferedReader(new FileReader(temp));
        br.readLine();
        br.readLine();
        String phoneNumber = br.readLine();
        String point = br.readLine();
        String level = br.readLine();
        String succession = br.readLine();
        String lastSignInTime = LocalDateTime.parse(br.readLine()).format(DateTimeFormatter.ofPattern("yyyy年MM月dd日hh点mm分ss秒"));
        String signupTime = LocalDateTime.parse(br.readLine()).format(DateTimeFormatter.ofPattern("yyyy年MM月dd日hh点mm分ss秒"));
        String puzzleGameTimeRecord = br.readLine() + "秒";
        String puzzleGameStepRecord = br.readLine().substring(1) + "步";
        if (lastSignInTime.equals("2020年03月24日12点00分00秒")) {
            lastSignInTime = "暂无，快去签到吧！";
        }
        if (puzzleGameStepRecord.equals("0步")) {
            puzzleGameStepRecord = "暂无，快去通关吧！";
        }
        if (puzzleGameTimeRecord.equals("0秒")) {
            puzzleGameTimeRecord = "暂无，快去通关吧！";
        }
        profileJL.setText("<html>用户名：" + username + "<br />" +
                "手机号码：" + phoneNumber + "<br />" +
                "积分：" + point + "<br />" +
                "等级：" + level + "<br />" +
                "连续签到天数：" + succession + "<br />" +
                "上次签到时间：" + lastSignInTime + "<br />" +
                "注册时间：" + signupTime + "<br />" +
                "拼图小游戏最佳时间：" + puzzleGameTimeRecord + "<br />" +
                "拼图小游戏最佳步数：" + puzzleGameStepRecord);
        changePhoneNumberJL.setBounds(190, 124, 26, 16);
        changePhoneNumberJL.addMouseListener(this);
        changePhoneNumberJL.setForeground(Color.BLUE);
        con.add(changePhoneNumberJL);
        con.add(profileJL);
        aboutJM.addKeyListener(this);
        br.close();
        if (!temp.delete()) {
            System.out.println(username + "数据删除失败，程序紧急中止！");
            System.exit(-1);
        }
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
        if (code == 71) showAbout();
        else if (code == 27) {
            dispose();
            new MainMenu(username);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Object thing = mouseEvent.getSource();
        if (thing == backJB || thing == backJMI) {
            dispose();
            new MainMenu(username);
        } else if (thing == aboutJM) showAbout();
        else if (thing == changePhoneNumberJL) {
            dispose();
            new ResetPhoneNumber(username);
        }
        if (thing == backJB || thing == changePhoneNumberJL) {
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
        if (thing == backJB) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回 - 按下.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        } else if (thing == changePhoneNumberJL) {
            changePhoneNumberJL.setText("<HTML><U>修改");
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (thing == backJB || thing == changePhoneNumberJL) {
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
        if (thing == backJB) {
            setCursor(Cursor.getDefaultCursor());
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        } else if (thing == changePhoneNumberJL) {
            changePhoneNumberJL.setText("修改");
            setCursor(Cursor.getDefaultCursor());
        }
    }
}
