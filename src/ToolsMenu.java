import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class ToolsMenu extends Initializer implements FocusListener {
    JButton changeBaseJB = new JButton("进制转换器");
    JButton checkPrimeNumberJB = new JButton("质数判断器");
    JButton getRandomNumberJB = new JButton("随机数生成器");
    int focusSelect;

    public ToolsMenu(String username) {
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
        setIcon();
        setTitle(version);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    void initMenuBar() {
        JMenuBar jmb = new JMenuBar();
        jmb.add(aboutJM);
        setJMenuBar(jmb);
    }

    @Override
    void initContent() {
        changeBaseJB.setBounds(95, 20, 150, 50);
        checkPrimeNumberJB.setBounds(95, 100, 150, 50);
        getRandomNumberJB.setBounds(95, 180, 150, 50);
        changeBaseJB.addMouseListener(this);
        checkPrimeNumberJB.addMouseListener(this);
        getRandomNumberJB.addMouseListener(this);
        con.add(changeBaseJB);
        con.add(checkPrimeNumberJB);
        con.add(getRandomNumberJB);
        aboutJM.addMouseListener(this);
        changeBaseJB.addKeyListener(this);
        checkPrimeNumberJB.addKeyListener(this);
        getRandomNumberJB.addKeyListener(this);
        changeBaseJB.addFocusListener(this);
        checkPrimeNumberJB.addFocusListener(this);
        getRandomNumberJB.addFocusListener(this);
        con.add(backJB);
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
        } else if (code == 10) {
            switch (focusSelect) {
                case 1: {
                    dispose();
                    new ChangeBase(username);
                    break;
                }
                case 2: {
                    dispose();
                    new CheckPrimeNumber(username);
                    break;
                }
                case 3: {
                    dispose();
                    new GetRandomNumber(username);
                    break;
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Object thing = mouseEvent.getSource();
        if (thing == aboutJM) showAbout();
        else if (thing == changeBaseJB) {
            dispose();
            new ChangeBase(username);
        } else if (thing == checkPrimeNumberJB) {
            dispose();
            new CheckPrimeNumber(username);
        } else if (thing == getRandomNumberJB) {
            dispose();
            new GetRandomNumber(username);
        } else if (thing == backJB) {
            dispose();
            new MainMenu(username);
        }
        if (thing == backJB || thing == changeBaseJB || thing == checkPrimeNumberJB || thing == getRandomNumberJB) {
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
        if (thing == changeBaseJB || thing == checkPrimeNumberJB || thing == getRandomNumberJB || thing == backJB)
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (thing == backJB) {
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回 - 按下.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        }
        if (thing == backJB || thing == changeBaseJB || thing == checkPrimeNumberJB || thing == getRandomNumberJB) {
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
        if (thing == changeBaseJB || thing == checkPrimeNumberJB || thing == getRandomNumberJB || thing == backJB)
            setCursor(Cursor.getDefaultCursor());
        if (thing == backJB) {
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        }
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        Object thing = focusEvent.getSource();
        if (thing == changeBaseJB) {
            focusSelect = 1;
        } else if (thing == checkPrimeNumberJB) {
            focusSelect = 2;
        } else if (thing == getRandomNumberJB) {
            focusSelect = 3;
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

    }
}
