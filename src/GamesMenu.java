import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GamesMenu extends Initializer implements FocusListener {
    JButton puzzleGameJB = new JButton(new ImageIcon(new ImageIcon("image\\new\\拼图.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
    JButton beatLordGameJB = new JButton(new ImageIcon(new ImageIcon("image\\new\\斗地主.png").getImage().getScaledInstance(96, 96, Image.SCALE_DEFAULT)));
    JLabel puzzleGameJL = new JLabel("拼图");
    JLabel beatLordGameJL = new JLabel("斗地主");
    int focusSelect;

    public GamesMenu(String username) {
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
        puzzleGameJB.setBounds(130, 180, 80, 80);
        beatLordGameJB.setBounds(120, 32, 96, 96);
        puzzleGameJB.addMouseListener(this);
        beatLordGameJB.addMouseListener(this);
        con.add(puzzleGameJB);
        con.add(beatLordGameJB);
        aboutJM.addMouseListener(this);
        puzzleGameJB.addKeyListener(this);
        beatLordGameJB.addKeyListener(this);
        puzzleGameJB.addFocusListener(this);
        beatLordGameJB.addFocusListener(this);
        con.add(backJB);

        puzzleGameJB.setOpaque(false);
        puzzleGameJB.setBorderPainted(false);
        puzzleGameJB.setBackground(new Color(0, 0, 0, 0));
        puzzleGameJB.setFocusPainted(false);
        beatLordGameJB.setOpaque(false);
        beatLordGameJB.setBorderPainted(false);
        beatLordGameJB.setBackground(new Color(0, 0, 0, 0));
        beatLordGameJB.setFocusPainted(false);
        puzzleGameJL.setBounds(130, 260, 80, 30);
        puzzleGameJL.setHorizontalAlignment(JLabel.CENTER);
        puzzleGameJL.setVerticalAlignment(JLabel.CENTER);
        beatLordGameJL.setBounds(120, 128, 96, 30);
        beatLordGameJL.setHorizontalAlignment(JLabel.CENTER);
        beatLordGameJL.setVerticalAlignment(JLabel.CENTER);
        con.add(beatLordGameJL);
        con.add(puzzleGameJL);
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
                    try {
                        new PuzzleGame(username);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                case 2: {
                    dispose();
                    new BeatLordGame(username);
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
        else if (thing == puzzleGameJB) {
            dispose();
            try {
                new PuzzleGame(username);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (thing == beatLordGameJB) {
            dispose();
            new BeatLordGame(username);
        } else if (thing == backJB) {
            dispose();
            new MainMenu(username);
        }
        if (thing == backJB || thing == beatLordGameJB || thing == puzzleGameJB) {
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
        if (thing == puzzleGameJB || thing == beatLordGameJB || thing == backJB)
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (thing == backJB) {
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回 - 按下.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        }
        if (thing == beatLordGameJB) {
            beatLordGameJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\斗地主 - 按下.png").getImage().getScaledInstance(96, 96, Image.SCALE_DEFAULT)));
            beatLordGameJL.setForeground(Color.decode("#d4237a"));
        }
        if (thing == puzzleGameJB) {
            puzzleGameJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\拼图 - 按下.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
            puzzleGameJL.setForeground(Color.decode("#16de23"));
        }
        if (thing == backJB || thing == beatLordGameJB || thing == puzzleGameJB) {
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
        if (thing == puzzleGameJB || thing == beatLordGameJB || thing == backJB)
            setCursor(Cursor.getDefaultCursor());
        if (thing == backJB) {
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        }
        if (thing == beatLordGameJB) {
            beatLordGameJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\斗地主.png").getImage().getScaledInstance(96, 96, Image.SCALE_DEFAULT)));
            beatLordGameJL.setForeground(Color.decode("#000000"));
        }
        if (thing == puzzleGameJB) {
            puzzleGameJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\拼图.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT)));
            puzzleGameJL.setForeground(Color.decode("#000000"));
        }
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        Object thing = focusEvent.getSource();
        if (thing == puzzleGameJB) {
            focusSelect = 1;
        } else if (thing == beatLordGameJB) {
            focusSelect = 2;
        }
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

    }
}
