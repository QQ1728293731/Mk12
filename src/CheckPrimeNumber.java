import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

//质数判断功能，并提供非质数的因子
public class CheckPrimeNumber extends Initializer implements FocusListener {

    JMenu propertiesJM = new JMenu("选项");
    JMenuItem exitJMI = new JMenuItem("退出质数判断器");
    JTextField inputJTF = new JTextField();
    JButton submitJB = new JButton("确定");
    String input;
    JLabel invalidInputWarningJL = new JLabel("输入有误，请重新输入");
    JLabel resultJL = new JLabel();
    String stateOne = "1既不是质数也不是合数";
    JLabel hintJL = new JLabel("判断质数，并给出合数的所有因子");

    CheckPrimeNumber(String username) {
        this.username = username;
        setResizable(false);
        initJFrame();
        initMenuBar();
        initContent();
        setVisible(true);
    }

    ///判断质数
    void checkPrimeNumber() {
        int number;
        if (input.equals("") || notInteger(input)) {
            invalidInputWarningJL.setVisible(true);
            return;
        }
        number = Integer.parseInt(input);
        if (number <= 0) {
            invalidInputWarningJL.setVisible(true);
            return;
        }
        if (number == 1) {
            resultJL.setText(stateOne);
            invalidInputWarningJL.setVisible(false);
            resultJL.setVisible(true);
            return;
        }
        StringBuilder result = new StringBuilder();
        result.append("<html>");
        ArrayList<Integer> arr = new ArrayList<>();
        int count = 0;
        boolean boolNumber = true;
        for (int i = number - 1; i > 1; i--) {
            if (number % i == 0) {
                boolNumber = false;
                int subNumber = number / i;
                arr.add(subNumber);
                count++;
            }
        }
        if (boolNumber) {
            result.append(String.format("%d是质数", number));
        } else {
            result.append(input).append("不是质数，而且它有").append(count).append("个因子（除去1与").append(input).append("），分别如下：<br />");
            for (int index = 0; index < arr.size(); index++) {
                if (arr.get(index) <= arr.get(count - index - 1))
                    result.append(String.format("%d    *    %d<br />", arr.get(index), arr.get(count - index - 1)));
                else break;
            }
        }
        resultJL.setText(result.toString());
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
        setTitle("质数判断器");
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
        inputJTF.setBounds(100, 100, 200, 30);
        submitJB.setBounds(100, 200, 70, 30);
        invalidInputWarningJL.setBounds(100, 130, 200, 50);
        resultJL.setBounds(100, 200, 640, 300);
        submitJB.addMouseListener(this);
        inputJTF.addFocusListener(this);
        invalidInputWarningJL.setVisible(false);
        inputJTF.setText("请输入一个正整数");
        con.add(inputJTF);
        con.add(submitJB);
        con.add(invalidInputWarningJL);
        con.add(resultJL);

        con.add(backJB);
        inputJTF.addKeyListener(this);
        submitJB.addKeyListener(this);
        invalidInputWarningJL.setForeground(Color.RED);
        hintJL.setBounds(100, 70, 200, 30);
        con.add(hintJL);
        aboutJM.addKeyListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object thing = e.getSource();
        if (thing == submitJB) {
            input = inputJTF.getText();
            checkPrimeNumber();
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
        String temp = inputJTF.getText();
        if (temp.equals("请输入一个正整数")) {
            inputJTF.setText("");
            inputJTF.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        String temp = inputJTF.getText();
        if (temp.equals("")) {
            inputJTF.setForeground(Color.GRAY);
            inputJTF.setText("请输入一个正整数");
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
            input = inputJTF.getText();
            checkPrimeNumber();
        } else if (code == 27) {
            dispose();
            new ToolsMenu(username);
        } else if (code == 71) showAbout();
    }
}
