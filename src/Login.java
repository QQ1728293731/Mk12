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

//用户登录界面
public class Login extends Initializer {

    JButton loginJB = new JButton(new ImageIcon("image\\login\\登录按钮.png"));
    JButton signupJB = new JButton(new ImageIcon("image\\login\\注册按钮.png"));
    JTextField usernameJTF = new JTextField();
    JLabel invalidInputJL = new JLabel(new ImageIcon("image\\login\\登录输入有误.png"));
    JLabel usernameJL = new JLabel("用户名");
    JLabel passwordJL = new JLabel("密码");
    boolean isReveal = false;

    public Login() {
        addMouseListener(this);
        initJFrame();
        initMenuBar();
        initContent();
        setResizable(false);
        setVisible(true);
    }

    ///判断输入新密码的用户是否存在
    public static boolean checkUserExist(String username) {
        File[] files = new File("User").listFiles();
        assert files != null;
        for (File file : files) {
            if (username.equals(file.getName())) return true;
        }
        return false;
    }

    ///登录检验
    public boolean check() throws IOException {
        if (checkUserExist(username)) {
            decrypt(new File(dir + "User\\" + username), username);
            File userTemp = new File(dir + "Temp\\" + username);
            BufferedReader br = new BufferedReader(new FileReader(userTemp));
            br.readLine();
            boolean result = password.equals(br.readLine());
            br.close();
            if (!userTemp.delete()) {
                System.out.println(username + "数据删除失败，程序紧急中止！");
                System.exit(-1);
            }
            return result;
        }
        return false;
    }

    ///显示输入框
    void showLogin() {
        revealPasswordJL.setVisible(true);
        usernameJTF.setVisible(true);
        passwordJPF.setVisible(true);
        usernameJL.setVisible(true);
        passwordJL.setVisible(true);
    }

    ///登录
    void login() throws IOException {
        invalidInputJL.setVisible(false);
        if (check()) {
            dispose();
            new MainMenu(username);
        } else if ((!username.equals("") || passwordSB.length() != 0)) {
            invalidInputJL.setVisible(true);
        }
    }

    ///采集登录信息
    void collectData() {
        username = usernameJTF.getText();
        passwordSB.delete(0, passwordSB.length());
        for (char c : passwordJPF.getPassword()) {
            passwordSB.append(c);
        }
        password = passwordSB.toString();
    }

    ///窗口初始化
    @Override
    void initJFrame() {
        setLayout(null);
        setSize(555, 269);
        setTitle(version);
        setIcon();
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    ///菜单初始化
    @Override
    void initMenuBar() {
        JMenuBar loginMenuBar = new JMenuBar();
        loginMenuBar.add(aboutJM);
        aboutJM.addMouseListener(this);
        setJMenuBar(loginMenuBar);
    }

    ///内容初始化
    @Override
    void initContent() {
        //创建User文件夹和Temp文件夹以及Save文件夹，用于以后的文件操作
        new File(dir + "User").mkdirs();
        new File(dir + "Temp").mkdirs();
        new File(dir + "Save").mkdirs();
        aboutJM.setText("关于");
        usernameJTF.setBounds(100, 70, 340, 30);
        passwordJPF.setBounds(100, 170, 340, 30);
        loginJB.setBounds(110, 70, 128, 47);
        loginJB.setContentAreaFilled(false);
        loginJB.setBorderPainted(false);
        loginJB.addMouseListener(this);
        signupJB.setBounds(302, 70, 128, 47);
        signupJB.setContentAreaFilled(false);
        signupJB.setBorderPainted(false);
        signupJB.addMouseListener(this);
        invalidInputJL.setBounds(100, 215, 221, 34);
        invalidInputJL.setVisible(false);
        con.add(invalidInputJL);
        con.add(loginJB);
        con.add(signupJB);
        con.add(usernameJTF);
        con.add(passwordJPF);
        usernameJL.setBounds(100, 30, 100, 50);
        passwordJL.setBounds(100, 130, 100, 50);
        con.add(usernameJL);
        con.add(passwordJL);
        usernameJTF.addKeyListener(this);
        passwordJPF.addKeyListener(this);
        usernameJTF.setVisible(false);
        passwordJPF.setVisible(false);
        usernameJL.setVisible(false);
        passwordJL.setVisible(false);
        revealPasswordJL.setBounds(70, 170, 30, 30);
        con.add(revealPasswordJL);
        revealPasswordJL.setVisible(false);
        loginJB.addKeyListener(this);
        revealPasswordJL.addMouseListener(this);
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
                login();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == aboutJM) {
            showAbout();
        } else if (thing == revealPasswordJL) {
            isReveal = !isReveal;
            if (isReveal) {
                revealPasswordJL.setIcon(new ImageIcon(new ImageIcon("image\\login\\密码显示.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            } else
                revealPasswordJL.setIcon(new ImageIcon(new ImageIcon("image\\login\\密码隐藏.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
            if (isReveal) {
                showPassword();
            } else {
                hidePassword();
            }
        }
        if (thing == loginJB || thing == signupJB || thing == revealPasswordJL || thing == aboutJM) {
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
        if (thing == loginJB) {
            setSize(555, 469);
            loginJB.setLocation(110, 270);
            signupJB.setLocation(302, 270);
            showLogin();
            collectData();
            try {
                login();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (thing == signupJB) {
            dispose();
            new Signup();
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == loginJB) loginJB.setIcon(new ImageIcon("image\\login\\登录按下.png"));
        else if (thing == signupJB) signupJB.setIcon(new ImageIcon("image\\login\\注册按下.png"));
        if (thing == loginJB || thing == signupJB || thing == revealPasswordJL || thing == aboutJM) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            try {
                playButtonRollover();
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == loginJB) loginJB.setIcon(new ImageIcon("image\\login\\登录按钮.png"));
        else if (thing == signupJB) signupJB.setIcon(new ImageIcon("image\\login\\注册按钮.png"));
        if (thing == loginJB || thing == signupJB || thing == revealPasswordJL || thing == aboutJM)
            setCursor(Cursor.getDefaultCursor());
    }

}
