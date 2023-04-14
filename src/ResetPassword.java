import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

//重设密码
public class ResetPassword extends Initializer {
    JMenu propertiesJM = new JMenu("选项");
    JMenuItem exitJMI = new JMenuItem("取消修改");
    JTextField phoneNumberJTF = new JTextField();
    JPasswordField passwordAgainJPF = new JPasswordField();
    JTextField codeJTF = new JTextField();
    JLabel inputPhoneNumberJL = new JLabel("输入手机号码用于验证");
    JLabel inputNewPasswordJL = new JLabel("输入新的密码");
    JLabel inputNewPasswordAgainJL = new JLabel("再次输入密码");
    JButton submitJB = new JButton("确定");
    JButton codeJB = new JButton();
    String phoneNumber;
    String codeTemp;
    String passwordAgain;
    String code;
    JLabel invalidPhoneNumberJL = new JLabel("手机号码无效，请重新输入");
    JLabel invalidCodeJL = new JLabel("验证码输入有误，请重新输入，或点击验证码重新生成");
    JLabel invalidPasswordJL = new JLabel("密码无效，密码长度在8-20之间，必须包括大小写字母和数字");
    JLabel differentPasswordJL = new JLabel("两次输入的密码不一致");
    JLabel samePasswordJL = new JLabel("新密码不能与旧密码相同");

    public ResetPassword(String username) {
        super.username = username;
        setResizable(false);
        initJFrame();
        initMenuBar();
        initContent();
        setVisible(true);
    }

    @Override
    void initJFrame() {
        setLayout(null);
        setSize(640, 648);
        setTitle("重设密码");
        setIcon();
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

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

    @Override
    void initContent() {
        aboutJM.setText("关于");
        phoneNumberJTF.setBounds(100, 50, 200, 30);
        inputPhoneNumberJL.setBounds(100, 20, 200, 30);
        submitJB.setBounds(100, 500, 100, 30);
        invalidPhoneNumberJL.setBounds(100, 80, 200, 30);
        passwordJPF.setBounds(100, 150, 200, 30);
        passwordAgainJPF.setBounds(100, 250, 200, 30);
        inputNewPasswordJL.setBounds(100, 120, 200, 30);
        inputNewPasswordAgainJL.setBounds(100, 220, 200, 30);
        codeJTF.setBounds(100, 350, 100, 30);
        codeJB.setBounds(200, 357, 80, 16);
        invalidPasswordJL.setBounds(100, 180, 400, 30);
        differentPasswordJL.setBounds(100, 280, 200, 30);
        invalidCodeJL.setBounds(100, 380, 400, 30);
        samePasswordJL.setBounds(100, 180, 400, 30);

        submitJB.addMouseListener(this);
        codeJB.addMouseListener(this);

        con.add(phoneNumberJTF);
        con.add(inputPhoneNumberJL);
        con.add(submitJB);
        con.add(invalidPhoneNumberJL);
        con.add(passwordJPF);
        con.add(passwordAgainJPF);
        con.add(inputNewPasswordJL);
        con.add(inputNewPasswordAgainJL);
        con.add(codeJTF);
        con.add(codeJB);
        con.add(invalidPasswordJL);
        con.add(differentPasswordJL);
        con.add(invalidCodeJL);
        con.add(samePasswordJL);

        invalidPhoneNumberJL.setVisible(false);
        codeJB.setContentAreaFilled(false);
        codeJB.setBorderPainted(false);
        invalidPasswordJL.setVisible(false);
        differentPasswordJL.setVisible(false);
        invalidCodeJL.setVisible(false);
        samePasswordJL.setVisible(false);

        codeTemp = getVerificationCode();
        codeJB.setText(codeTemp);

        con.add(backJB);
        invalidPhoneNumberJL.setForeground(Color.RED);
        invalidCodeJL.setForeground(Color.RED);
        invalidPasswordJL.setForeground(Color.RED);
        differentPasswordJL.setForeground(Color.RED);
        samePasswordJL.setForeground(Color.RED);
        submitJB.addKeyListener(this);
        phoneNumberJTF.addKeyListener(this);
        passwordJPF.addKeyListener(this);
        passwordAgainJPF.addKeyListener(this);
        codeJTF.addKeyListener(this);
        revealPasswordJL.setBounds(70, 150, 30, 30);
        revealPasswordPressedJL.setBounds(70, 150, 30, 30);
        con.add(revealPasswordJL);
        con.add(revealPasswordPressedJL);
        revealPasswordJL.addMouseListener(this);
        revealPasswordPressedJL.addMouseListener(this);
        revealPasswordPressedJL.setVisible(false);
    }

    ///重设密码
    void resetPassword() throws IOException {
        if (code.equals(codeTemp) && checkSamePhoneNumber(username, phoneNumber) && checkPassword(password) && !checkSamePassword(username, password) && password.equals(passwordAgain)) {
            saveData(password, 1);
            dispose();
            new MainMenu(username);
        } else {
            invalidCodeJL.setVisible(!code.equals(codeTemp));
            codeTemp = getVerificationCode();
            codeJB.setText(codeTemp);
            invalidPhoneNumberJL.setVisible(!checkSamePhoneNumber(username, phoneNumber));
            invalidPasswordJL.setVisible(!checkPassword(password));
            if (!password.equals(passwordAgain)) {
                invalidPasswordJL.setVisible(false);
                samePasswordJL.setVisible(false);
                differentPasswordJL.setVisible(true);
            } else differentPasswordJL.setVisible(false);
            if (checkSamePassword(username, password)) {
                invalidPasswordJL.setVisible(false);
                samePasswordJL.setVisible(true);
            }
        }
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
            new MainMenu(username);
        } else if (thing == codeJB) {
            codeTemp = getVerificationCode();
            codeJB.setText(codeTemp);
        } else if (thing == submitJB) {
            collectData();
            try {
                resetPassword();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (thing == revealPasswordJL) {
            revealPasswordJL.setVisible(false);
            revealPasswordPressedJL.setVisible(true);
            showPassword();
        }
        if (thing == backJB || thing == revealPasswordJL || thing == codeJB || thing == submitJB || thing == aboutJM) {
            try {
                playButtonClickRelease();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                throw new RuntimeException(ex);
            }
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
        if (thing == backJB || thing == revealPasswordJL || thing == codeJB || thing == submitJB) {
            try {
                playButtonRollover();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
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
                resetPassword();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (code == 27) {
            dispose();
            new MainMenu(username);
        }
    }

    ///收集用户输入的数据
    void collectData() {
        passwordSB.delete(0, passwordSB.length());
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
}
