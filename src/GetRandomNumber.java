import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

//随机数生成器
public class GetRandomNumber extends Initializer implements FocusListener {

    JMenu propertiesJM = new JMenu("选项");
    JMenuItem exitJMI = new JMenuItem("退出随机数生成器");
    JTextField inputMinimumJTF = new JTextField();
    JTextField inputMaximumJTF = new JTextField();
    JButton submitJB = new JButton("确定");
    String inputMin;
    String inputMax;
    String warning;
    JLabel warningJL = new JLabel(warning);
    String warningDefault = "输入有误，请重新输入";
    String warningInputGreater = "最大值必须大于最小值，请重新输入";
    String warningInputPositive = "请输入正数";
    JLabel resultJL = new JLabel();
    JLabel hintJL = new JLabel("在指定范围内生成随机整数");

    GetRandomNumber(String username) {
        this.username = username;
        setResizable(false);
        initJFrame();
        initMenuBar();
        initContent();
        setVisible(true);
    }

    ///获取随机数
    void getRandomNumber() {
        Random r = new Random();
        if (notInteger(inputMax) || notInteger(inputMin) || inputMin.equals("") || inputMax.equals("")) {
            warning = warningDefault;
            warningJL.setText(warning);
            warningJL.setVisible(true);
            return;
        }
        if (Integer.parseInt(inputMin) < 0) {
            warning = warningInputPositive;
            warningJL.setText(warning);
            warningJL.setVisible(true);
            return;
        }
        if (Integer.parseInt(inputMin) >= Integer.parseInt(inputMax)) {
            warning = warningInputGreater;
            warningJL.setText(warning);
            warningJL.setVisible(true);
            return;
        }
        int randomNumber;
        warningJL.setVisible(false);
        randomNumber = r.nextInt(Integer.parseInt(inputMax) - Integer.parseInt(inputMin) + 1) + Integer.parseInt(inputMin);
        resultJL.setText("本次结果为 " + randomNumber);
        resultJL.setVisible(true);
    }

    @Override
    void collectData() {

    }

    ///窗口初始化
    @Override
    void initJFrame() {
        setLayout(null);
        setSize(640, 648);
        setIcon();
        setTitle("随机数生成器");
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
    @Override
    void initContent() {
        inputMinimumJTF.setBounds(100, 100, 100, 30);
        inputMaximumJTF.setBounds(250, 100, 100, 30);
        submitJB.setBounds(100, 200, 80, 35);
        warningJL.setBounds(100, 130, 300, 50);
        resultJL.setBounds(100, 300, 200, 30);
        submitJB.addMouseListener(this);
        warningJL.setVisible(false);
        resultJL.setVisible(false);
        inputMinimumJTF.setText("范围最小值");
        inputMaximumJTF.setText("范围最大值");
        con.add(inputMinimumJTF);
        con.add(inputMaximumJTF);
        con.add(submitJB);
        con.add(warningJL);
        con.add(resultJL);

        con.add(backJB);
        inputMinimumJTF.addFocusListener(this);
        inputMaximumJTF.addFocusListener(this);
        inputMinimumJTF.addKeyListener(this);
        inputMaximumJTF.addKeyListener(this);
        hintJL.setBounds(100, 70, 200, 30);
        con.add(hintJL);
        submitJB.addKeyListener(this);
        warningJL.setForeground(Color.RED);
        aboutJM.addKeyListener(this);
        resultJL.setFont(new Font(null, Font.BOLD, 24));
        submitJB.setFont(new Font(null, Font.BOLD, 20));
        warningJL.setFont(new Font(null, Font.BOLD, 18));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == submitJB) {
            inputMin = inputMinimumJTF.getText();
            inputMax = inputMaximumJTF.getText();
            getRandomNumber();
        } else if (thing == aboutJM) showAbout();
        else if (thing == exitJMI || thing == backJB) {
            dispose();
            new ToolsMenu(username);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == backJB || thing == submitJB) setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        if (thing == backJB) {
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回 - 按下.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == backJB || thing == submitJB) setCursor(Cursor.getDefaultCursor());
        if (thing == backJB) {
            backJB.setIcon(new ImageIcon(new ImageIcon("image\\new\\返回.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (inputMinimumJTF.getText().equals("范围最小值") || inputMaximumJTF.getText().equals("范围最大值")) {
            inputMinimumJTF.setText("");
            inputMaximumJTF.setText("");
            inputMinimumJTF.setForeground(Color.BLACK);
            inputMaximumJTF.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (inputMinimumJTF.getText().equals("") && inputMaximumJTF.getText().equals("")) {
            inputMinimumJTF.setForeground(Color.GRAY);
            inputMaximumJTF.setForeground(Color.GRAY);
            inputMinimumJTF.setText("范围最小值");
            inputMaximumJTF.setText("范围最大值");
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
            inputMin = inputMinimumJTF.getText();
            inputMax = inputMaximumJTF.getText();
            getRandomNumber();
        } else if (code == 27) {
            dispose();
            new ToolsMenu(username);
        } else if (code == 71) showAbout();
    }
}
