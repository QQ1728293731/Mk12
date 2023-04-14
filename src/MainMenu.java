import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

//水银主菜单
public class MainMenu extends Initializer implements FocusListener {

    JButton gamesJB = new JButton(new ImageIcon(new ImageIcon("image\\new\\游戏.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
    JButton toolsJB = new JButton(new ImageIcon(new ImageIcon("image\\new\\工具.png").getImage().getScaledInstance(92, 92, Image.SCALE_DEFAULT)));
    JLabel gamesJL = new JLabel("游戏");
    JLabel toolsJL = new JLabel("工具");
    JMenuItem resetPasswordJMI = new JMenuItem("修改密码");
    JMenuItem resetPhoneNumberJMI = new JMenuItem("修改手机号码");
    JMenuItem logoutJMI = new JMenuItem("退出登录");
    JMenuItem signInJMI = new JMenuItem("每日签到");
    JMenuItem profileJMI = new JMenuItem("账户信息");
    int focusSelect;
    JMenu meJM = new JMenu("我的");

    public MainMenu(String username) {
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

    ///窗口初始化
    @Override
    void initJFrame() {
        setLayout(null);
        setSize(356, 390);
        setIcon();
        setTitle(version);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    ///菜单初始化
    @Override
    void initMenuBar() {
        JMenuBar jmb = new JMenuBar();
        meJM.add(profileJMI);
        meJM.add(signInJMI);
        meJM.add(logoutJMI);
        meJM.add(resetPasswordJMI);
        meJM.add(resetPhoneNumberJMI);
        jmb.add(meJM);
        jmb.add(aboutJM);
        setJMenuBar(jmb);
    }

    ///内容初始化
    @Override
    void initContent() {
        toolsJB.setBounds(124, 34, 92, 92);
        gamesJB.setBounds(130, 180, 80, 80);
        con.add(gamesJB);
        con.add(toolsJB);
        toolsJB.addMouseListener(this);
        gamesJB.addMouseListener(this);
        aboutJM.addMouseListener(this);
        logoutJMI.addMouseListener(this);
        resetPasswordJMI.addMouseListener(this);
        resetPhoneNumberJMI.addMouseListener(this);
        toolsJB.addKeyListener(this);
        gamesJB.addKeyListener(this);
        toolsJB.addFocusListener(this);
        gamesJB.addFocusListener(this);
        signInJMI.addMouseListener(this);
        profileJMI.addMouseListener(this);
        aboutJM.addKeyListener(this);

        gamesJB.setOpaque(false);
        gamesJB.setBorderPainted(false);
        gamesJB.setBackground(new Color(0, 0, 0, 0));
        gamesJB.setFocusPainted(false);
        toolsJB.setOpaque(false);
        toolsJB.setBorderPainted(false);
        toolsJB.setBackground(new Color(0, 0, 0, 0));
        toolsJB.setFocusPainted(false);
        gamesJL.setBounds(130, 260, 80, 30);
        con.add(gamesJL);
        con.add(toolsJL);
        gamesJL.setHorizontalAlignment(JLabel.CENTER);
        gamesJL.setVerticalAlignment(JLabel.CENTER);
        toolsJL.setBounds(124, 126, 92, 30);
        toolsJL.setHorizontalAlignment(JLabel.CENTER);
        toolsJL.setVerticalAlignment(JLabel.CENTER);
        meJM.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == aboutJM) {
            showAbout();
        } else if (thing == gamesJB) {
            dispose();
            new GamesMenu(username);
        } else if (thing == toolsJB) {
            dispose();
            new ToolsMenu(username);
        } else if (thing == logoutJMI) {
            dispose();
            new Login();
        } else if (thing == resetPasswordJMI) {
            dispose();
            new ResetPassword(username);
        } else if (thing == resetPhoneNumberJMI) {
            dispose();
            new ResetPhoneNumber(username);
        } else if (thing == signInJMI) {
            dispose();
            new SignIn(username);
        } else if (thing == profileJMI) {
            dispose();
            try {
                new Profile(username);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (thing == toolsJB || thing == gamesJB || thing == signInJMI || thing == profileJMI || thing == meJM
                || thing == logoutJMI || thing == resetPasswordJMI || thing == resetPhoneNumberJMI || thing == aboutJM) {
            try {
                playButtonClickRelease();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == toolsJB || thing == gamesJB || thing == aboutJM || thing == signInJMI || thing == profileJMI || thing == meJM
                || thing == logoutJMI || thing == resetPasswordJMI || thing == resetPhoneNumberJMI)
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (thing == toolsJB) {
            assert toolsJB != null;
            toolsJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\工具 - 按下.png").getImage().getScaledInstance(92, 92, Image.SCALE_DEFAULT)));
            toolsJL.setForeground(Color.decode("#1296db"));
        }
        if (thing == gamesJB) {
            assert gamesJB != null;
            gamesJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\游戏 - 按下.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
            gamesJL.setForeground(Color.decode("#d4237a"));
        }
        if (thing == toolsJB || thing == gamesJB || thing == signInJMI || thing == profileJMI || thing == meJM
                || thing == logoutJMI || thing == resetPasswordJMI || thing == resetPhoneNumberJMI || thing == aboutJM) {
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
        if (thing == toolsJB || thing == gamesJB || thing == aboutJM || thing == signInJMI || thing == profileJMI || thing == meJM
                || thing == logoutJMI || thing == resetPasswordJMI || thing == resetPhoneNumberJMI)
            setCursor(Cursor.getDefaultCursor());
        if (thing == toolsJB) {
            toolsJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\工具.png").getImage().getScaledInstance(92, 92, Image.SCALE_DEFAULT)));
            toolsJL.setForeground(Color.decode("#000000"));
        }
        if (thing == gamesJB) {
            gamesJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\游戏.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
            gamesJL.setForeground(Color.decode("#000000"));
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
            switch (focusSelect) {
                case 1: {
                    dispose();
                    new ToolsMenu(username);
                    break;
                }
                case 2: {
                    dispose();
                    new GamesMenu(username);
                    break;
                }
            }
        } else if (code == 27) {
            dispose();
            new Login();
        } else if (code == 71) showAbout();
    }

    @Override
    public void focusGained(FocusEvent e) {
        Object thing = e.getSource();
        if (thing == toolsJB) {
            focusSelect = 1;
        } else if (thing == gamesJB) {
            focusSelect = 2;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}
