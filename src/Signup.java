import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

//注册
public class Signup extends Initializer {

    JMenu propertiesJM = new JMenu("选项");
    JMenuItem exitJMI = new JMenuItem("中止注册");
    JTextField usernameJTF = new JTextField();
    JPasswordField passwordAgainJPF = new JPasswordField();
    JTextField phoneNumberJTF = new JTextField();
    JButton submitJB = new JButton("确定");
    JTextField codeJTF = new JTextField();
    JButton codeJB = new JButton();
    String passwordAgain;
    String phoneNumber;
    String code;
    String codeTemp;
    JLabel invalidCodeJL = new JLabel("验证码输入有误，请重新输入，或点击验证码重新生成");
    JLabel invalidUsernameJL = new JLabel("用户名无效，用户名长度在5-15之间，只能使用字母、数字及下划线，且不能为纯数字");
    JLabel occupiedUsernameJL = new JLabel("用户名已被占用");
    JLabel validUsernameJL = new JLabel("用户名可用");
    JLabel invalidPasswordJL = new JLabel("密码无效，密码长度在8-20之间，必须包括大小写字母和数字");
    JLabel differentPasswordJL = new JLabel("两次输入的密码不一致");
    JLabel validPasswordJL = new JLabel("密码可用");
    JLabel inputPasswordJL = new JLabel("输入密码");
    JLabel inputPasswordAgainJL = new JLabel("再次输入密码");
    JLabel invalidPhoneNumberJL = new JLabel("手机号码无效");
    JLabel occupiedPhoneNumberJL = new JLabel("手机号已被占用");
    JLabel inputUsernameJL = new JLabel("输入用户名");
    JLabel inputPhoneNumberJL = new JLabel("输入大陆手机号码");

    public Signup() {
        initJFrame();
        initMenuBar();
        initContent();
        setResizable(false);
        setVisible(true);
    }

    ///判断注册时用户名是否被占用
    public static boolean checkUsernameUsed(String username) {
        File[] files = new File("User").listFiles();
        assert files != null;
        for (File file : files) {
            if (username.equals(file.getName())) return true;
        }
        return false;
    }

    ///判断用户名是否合法
    public static boolean checkUsername(String username) {
        return username.matches("(?!\\d+$)\\w{5,15}");
    }

    ///窗口初始化
    @Override
    void initJFrame() {
        setLayout(null);
        setSize(560, 648);
        setTitle("注册");
        setIcon();
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    ///菜单初始化
    @Override
    void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        propertiesJM.add(exitJMI);
        menuBar.add(propertiesJM);
        menuBar.add(aboutJM);
        aboutJM.addMouseListener(this);
        exitJMI.addMouseListener(this);
        setJMenuBar(menuBar);
    }

    ///内容初始化
    void initContent() {
        aboutJM.setText("关于");
        usernameJTF.setBounds(100, 50, 200, 30);
        passwordJPF.setBounds(100, 130, 200, 30);
        passwordAgainJPF.setBounds(100, 210, 200, 30);
        phoneNumberJTF.setBounds(100, 290, 200, 30);
        codeJTF.setBounds(100, 370, 100, 30);
        codeJB.setBounds(200, 377, 80, 16);
        invalidCodeJL.setBounds(100, 330, 400, 30);
        invalidUsernameJL.setBounds(100, 10, 500, 30);
        occupiedUsernameJL.setBounds(100, 10, 400, 30);
        validUsernameJL.setBounds(100, 10, 400, 30);
        invalidPasswordJL.setBounds(100, 90, 400, 30);
        validPasswordJL.setBounds(100, 90, 400, 30);
        differentPasswordJL.setBounds(100, 170, 400, 30);
        inputPasswordJL.setBounds(310, 130, 200, 30);
        inputPasswordAgainJL.setBounds(310, 210, 200, 30);
        invalidPhoneNumberJL.setBounds(100, 250, 200, 30);
        occupiedPhoneNumberJL.setBounds(100, 250, 200, 30);
        codeTemp = getVerificationCode();
        codeJB.setText(codeTemp);
        codeJB.setContentAreaFilled(false);
        codeJB.setBorderPainted(false);
        submitJB.setBounds(200, 460, 70, 30);
        submitJB.addMouseListener(this);
        codeJB.addMouseListener(this);
        con.add(usernameJTF);
        con.add(passwordJPF);
        con.add(passwordAgainJPF);
        con.add(phoneNumberJTF);
        con.add(codeJTF);
        con.add(codeJB);
        con.add(submitJB);
        con.add(invalidCodeJL);
        con.add(invalidUsernameJL);
        con.add(occupiedUsernameJL);
        con.add(validUsernameJL);
        con.add(invalidPasswordJL);
        con.add(validPasswordJL);
        con.add(differentPasswordJL);
        con.add(inputPasswordJL);
        con.add(inputPasswordAgainJL);
        con.add(invalidPhoneNumberJL);
        con.add(occupiedPhoneNumberJL);
        invalidCodeJL.setVisible(false);
        invalidUsernameJL.setVisible(false);
        occupiedUsernameJL.setVisible(false);
        validUsernameJL.setVisible(false);
        invalidPasswordJL.setVisible(false);
        validPasswordJL.setVisible(false);
        differentPasswordJL.setVisible(false);
        invalidPhoneNumberJL.setVisible(false);
        occupiedPhoneNumberJL.setVisible(false);

        con.add(backJB);
        inputUsernameJL.setBounds(310, 50, 200, 30);
        inputPhoneNumberJL.setBounds(310, 290, 200, 30);
        con.add(inputUsernameJL);
        con.add(inputPhoneNumberJL);
        usernameJTF.addKeyListener(this);
        passwordJPF.addKeyListener(this);
        passwordAgainJPF.addKeyListener(this);
        phoneNumberJTF.addKeyListener(this);
        codeJTF.addKeyListener(this);
        invalidCodeJL.setForeground(Color.RED);
        invalidUsernameJL.setForeground(Color.RED);
        occupiedUsernameJL.setForeground(Color.RED);
        invalidPasswordJL.setForeground(Color.RED);
        differentPasswordJL.setForeground(Color.RED);
        invalidPhoneNumberJL.setForeground(Color.RED);
        occupiedPhoneNumberJL.setForeground(Color.RED);
        submitJB.addKeyListener(this);
        revealPasswordJL.setBounds(70, 130, 30, 30);
        revealPasswordPressedJL.setBounds(70, 130, 30, 30);
        con.add(revealPasswordJL);
        con.add(revealPasswordPressedJL);
        revealPasswordPressedJL.setVisible(false);
        revealPasswordJL.addMouseListener(this);
        revealPasswordPressedJL.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == aboutJM) showAbout();
        else if (thing == exitJMI || thing == backJB) {
            dispose();
            new Login();
        } else if (thing == codeJB) {
            codeTemp = getVerificationCode();
            codeJB.setText(codeTemp);
        } else if (thing == submitJB) {
            collectData();
            try {
                signup();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (thing == revealPasswordJL) {
            assert revealPasswordJL != null;
            revealPasswordJL.setVisible(false);
            revealPasswordPressedJL.setVisible(true);
            showPassword();
        }
        if (thing == backJB || thing == revealPasswordJL || thing == codeJB || thing == submitJB || thing == aboutJM) {
            try {
                playButtonClickRelease();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    ///注册
    void signup() throws IOException {
        if (checkUsername(username) && !checkUsernameUsed(username) && checkPassword(password) && password.equals(passwordAgain) && checkPhoneNumber(phoneNumber) && !checkPhoneNumberUsed(phoneNumber, username) && code.equals(codeTemp)) {
            saveData();
            dispose();
            new Login();
        } else {
            invalidCodeJL.setVisible(!code.equals(codeTemp));
            codeTemp = getVerificationCode();
            codeJB.setText(codeTemp);
            invalidUsernameJL.setVisible(!checkUsername(username));
            occupiedUsernameJL.setVisible(checkUsernameUsed(username));
            validUsernameJL.setVisible(checkUsername(username) && !checkUsernameUsed(username));
            invalidPasswordJL.setVisible(!checkPassword(password));
            invalidPhoneNumberJL.setVisible(!checkPhoneNumber(phoneNumber));
            occupiedPhoneNumberJL.setVisible(checkPhoneNumberUsed(phoneNumber, username));
            if (!password.equals(passwordAgain)) {
                invalidPasswordJL.setVisible(false);
                differentPasswordJL.setVisible(true);
            } else differentPasswordJL.setVisible(false);
            if (!checkPhoneNumber(phoneNumber)) {
                invalidPhoneNumberJL.setVisible(true);
                occupiedPhoneNumberJL.setVisible(false);
            }
            if (checkPhoneNumberUsed(phoneNumber, username)) {
                invalidPhoneNumberJL.setVisible(false);
                occupiedPhoneNumberJL.setVisible(true);
            }
        }
    }

    ///收集用户输入的数据
    @Override
    void collectData() {
        passwordSB.delete(0, passwordSB.length());
        username = usernameJTF.getText();
        for (char c : passwordJPF.getPassword()) {
            passwordSB.append(c);
        }
        password = passwordSB.toString();
        passwordSB.delete(0, passwordSB.length());
        for (char c : passwordAgainJPF.getPassword()) {
            passwordSB.append(c);
        }
        passwordAgain = passwordSB.toString();
        phoneNumber = phoneNumberJTF.getText();
        code = codeJTF.getText();
    }

    ///保存用户数据并加密
    /*用户名
     * 密码
     * 手机号
     * 积分
     * 等级
     * 连续签到天数
     * 上次签到时间
     * 注册时间
     * 拼图小游戏最佳时间
     * 拼图小游戏最佳步数*/
    void saveData() throws IOException {
        File temp = new File(dir + "\\Temp\\" + username);
        LocalDateTime signupLDT = LocalDateTime.now();
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
        bw.write(username);
        bw.newLine();
        bw.write(password);
        bw.newLine();
        bw.write(phoneNumber);
        bw.newLine();
        bw.write("0");
        bw.newLine();
        bw.write("1");
        bw.newLine();
        bw.write("0");
        bw.newLine();
        bw.write(LocalDateTime.of(2020, 3, 24, 0, 0, 0) + "");
        bw.newLine();
        bw.write(signupLDT + "");
        bw.newLine();
        bw.write("0");
        bw.newLine();
        //这个杠是一个标注，在签到时，程序会使用最后一行比对数据，如果不加标注，这一行可能会和上面的一些内容重复
        bw.write("-0");
        bw.close();
        encrypt(new File("Temp\\" + username));
        if (!temp.delete()) {
            System.out.println(username + "数据删除失败，程序紧急中止！");
            System.exit(-1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == revealPasswordPressedJL) {
            revealPasswordJL.setVisible(true);
            revealPasswordPressedJL.setVisible(false);
            hidePassword();
        }


    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == backJB || thing == submitJB || thing == revealPasswordJL || thing == revealPasswordPressedJL || thing == aboutJM)
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else if (thing == codeJB) {
            codeJB.setText(String.format("<HTML><U>%s", codeTemp));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
        if (thing == backJB) {
            assert backJB != null;
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回 - 按下.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        }
        if (thing == backJB || thing == revealPasswordJL || thing == codeJB || thing == submitJB || thing == aboutJM) {
            try {
                playButtonRollover();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == backJB || thing == submitJB || thing == revealPasswordJL || thing == revealPasswordPressedJL || thing == aboutJM)
            setCursor(Cursor.getDefaultCursor());
        else if (thing == codeJB) {
            codeJB.setText(codeTemp);
            setCursor(Cursor.getDefaultCursor());
        }
        if (thing == backJB) {
            assert backJB != null;
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 10) {
            collectData();
            try {
                signup();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (code == 27) {
            dispose();
            new Login();
        }
    }
}
