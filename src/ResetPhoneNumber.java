import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

//重设手机号码
public class ResetPhoneNumber extends Initializer {

    JMenu properties = new JMenu("选项");
    JMenuItem exitJMI = new JMenuItem("取消修改");
    JTextField phoneNumberJTF = new JTextField();
    JTextField codeJTF = new JTextField();
    JLabel inputPasswordJL = new JLabel("输入密码用于验证");
    JLabel inputNewPhoneNumberJL = new JLabel("输入新的手机号码");
    JButton submitJB = new JButton("确定");
    JButton codeJB = new JButton();
    String phoneNumber;
    String codeTemp;
    String code;
    JLabel invalidPhoneNumberJL = new JLabel("手机号码无效，请重新输入");
    JLabel invalidCodeJL = new JLabel("验证码输入有误，请重新输入，或点击验证码重新生成");
    JLabel invalidPasswordJL = new JLabel("密码无效，请重新输入");
    JLabel samePhoneNumberJL = new JLabel("新手机号码不能与旧手机号码相同");
    JLabel occupiedPhoneNumberJL = new JLabel("手机号码已被占用");

    public ResetPhoneNumber(String username) {
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
        setTitle("重设手机号码");
        setIcon();
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        properties.add(exitJMI);
        menuBar.add(properties);
        menuBar.add(aboutJM);
        aboutJM.addMouseListener(this);
        exitJMI.addMouseListener(this);
        setJMenuBar(menuBar);
    }

    @Override
    void initContent() {
        aboutJM.setText("关于");
        passwordJPF.setBounds(100, 50, 200, 30);
        inputPasswordJL.setBounds(100, 20, 200, 30);
        submitJB.setBounds(100, 500, 100, 30);
        invalidPasswordJL.setBounds(100, 80, 200, 30);
        phoneNumberJTF.setBounds(100, 150, 200, 30);
        inputNewPhoneNumberJL.setBounds(100, 120, 200, 30);
        codeJTF.setBounds(100, 250, 100, 30);
        codeJB.setBounds(200, 257, 80, 16);
        invalidPhoneNumberJL.setBounds(100, 180, 400, 30);
        invalidCodeJL.setBounds(100, 280, 400, 30);
        samePhoneNumberJL.setBounds(100, 180, 400, 30);
        occupiedPhoneNumberJL.setBounds(100, 180, 400, 30);

        submitJB.addMouseListener(this);
        codeJB.addMouseListener(this);

        con.add(phoneNumberJTF);
        con.add(inputPasswordJL);
        con.add(submitJB);
        con.add(invalidPhoneNumberJL);
        con.add(passwordJPF);
        con.add(inputNewPhoneNumberJL);
        con.add(codeJTF);
        con.add(codeJB);
        con.add(invalidPasswordJL);
        con.add(invalidCodeJL);
        con.add(samePhoneNumberJL);
        con.add(occupiedPhoneNumberJL);

        invalidPhoneNumberJL.setVisible(false);
        codeJB.setContentAreaFilled(false);
        codeJB.setBorderPainted(false);
        invalidPasswordJL.setVisible(false);
        invalidCodeJL.setVisible(false);
        samePhoneNumberJL.setVisible(false);

        codeTemp = getVerificationCode();
        codeJB.setText(codeTemp);

        con.add(backJB);
        invalidPhoneNumberJL.setForeground(Color.RED);
        invalidCodeJL.setForeground(Color.RED);
        invalidPasswordJL.setForeground(Color.RED);
        samePhoneNumberJL.setForeground(Color.RED);
        occupiedPhoneNumberJL.setForeground(Color.RED);
        submitJB.addKeyListener(this);
        passwordJPF.addKeyListener(this);
        phoneNumberJTF.addKeyListener(this);
        codeJTF.addKeyListener(this);
        revealPasswordJL.setBounds(70, 50, 30, 30);
        revealPasswordPressedJL.setBounds(70, 50, 30, 30);
        con.add(revealPasswordJL);
        con.add(revealPasswordPressedJL);
        revealPasswordPressedJL.setVisible(false);
        revealPasswordJL.addMouseListener(this);
        revealPasswordPressedJL.addMouseListener(this);
        occupiedPhoneNumberJL.setVisible(false);
    }

    ///重设手机号码
    private void resetPhoneNumber() throws IOException {
        if (checkSamePassword(username, password) && checkPhoneNumber(phoneNumber) && !checkSamePhoneNumber(username, phoneNumber) && !checkPhoneNumberUsed(phoneNumber, username) && code.equals(codeTemp)) {
            setVisible(false);
            saveData(phoneNumber, 2);
            new MainMenu(username);
        } else {
            invalidCodeJL.setVisible(!code.equals(codeTemp));
            codeTemp = getVerificationCode();
            codeJB.setText(codeTemp);
            invalidPasswordJL.setVisible(!checkPassword(password));
            if (!checkPhoneNumber(phoneNumber)) {
                occupiedPhoneNumberJL.setVisible(false);
                samePhoneNumberJL.setVisible(false);
                invalidPhoneNumberJL.setVisible(true);
            }
            if (checkPhoneNumberUsed(phoneNumber, username)) {
                invalidPhoneNumberJL.setVisible(false);
                samePhoneNumberJL.setVisible(false);
                occupiedPhoneNumberJL.setVisible(true);
            }
            if (checkSamePhoneNumber(username, phoneNumber)) {
                invalidPhoneNumberJL.setVisible(false);
                occupiedPhoneNumberJL.setVisible(false);
                samePhoneNumberJL.setVisible(true);
            }
            if (!code.equals(codeTemp) && !checkPhoneNumberUsed(phoneNumber, username) && !checkSamePhoneNumber(username, phoneNumber)) {
                invalidPhoneNumberJL.setVisible(false);
                occupiedPhoneNumberJL.setVisible(false);
                samePhoneNumberJL.setVisible(false);
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
                resetPhoneNumber();
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
        if (thing == backJB || thing == revealPasswordJL || thing == codeJB || thing == submitJB || thing == aboutJM) {
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
        if (thing == backJB || thing == submitJB || thing == revealPasswordJL || thing == revealPasswordPressedJL)
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
                resetPhoneNumber();
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
        phoneNumber = phoneNumberJTF.getText() + "";
        code = codeJTF.getText();
    }
}
