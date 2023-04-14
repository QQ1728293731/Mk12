import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

//此类用于各种疑难理论测试，成功后可使用branch功能进行实际测试
//现在正在测试：
public class MkTest extends JFrame {
    public MkTest() {
        JFrame jFrame = new JFrame();
        JLabel label = new JLabel();
        jFrame.setSize(500, 500);
        label.setText("This is a very long text that will not fit in the label.");
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        jFrame.getContentPane().add(scrollPane);
//        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {
        new MkTest();
        Initializer i = new Initializer() {
            @Override
            void collectData() {

            }

            @Override
            void initJFrame() {

            }

            @Override
            void initMenuBar() {

            }

            @Override
            void initContent() throws IOException {

            }

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
//        i.decrypt(new File("User\\QQ1728293731"),"1");
//        i.encrypt(new File("Temp\\QQ1728293731"));
    }
}
