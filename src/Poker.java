import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//斗地主扑克牌对象
public class Poker extends JLabel implements MouseListener {
    private String name;
    private boolean side;
    private boolean clickable = false;
    private boolean clicked = false;

    public Poker(String name, boolean side) {
        this.name = name;
        this.side = side;
        addMouseListener(this);
        if (side) turnFront();
        else turnRear();
        setSize(71, 96);
        setVisible(true);
    }

    ///扑克牌正面朝上
    public void turnFront() {
        setIcon(new ImageIcon("Mk11\\image\\poker\\" + name + ".png"));
        clickable = true;
    }

    ///扑克牌反面朝上
    private void turnRear() {
        setIcon(new ImageIcon("Mk11\\image\\poker\\rear.png"));
        clickable = false;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (clickable) {
            int pixel;
            pixel = clicked ? 20 : -20;
            clicked = !clicked;
            setLocation(new Point(getLocation().x, getLocation().y + pixel));
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * 获取
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     *
     * @return side
     */
    public boolean isSide() {
        return side;
    }

    /**
     * 设置
     *
     * @param side
     */
    public void setSide(boolean side) {
        this.side = side;
    }

    /**
     * 获取
     *
     * @return clickable
     */
    public boolean isClickable() {
        return clickable;
    }

    /**
     * 设置
     *
     * @param clickable
     */
    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    /**
     * 获取
     *
     * @return clicked
     */
    public boolean isClicked() {
        return clicked;
    }

    /**
     * 设置
     *
     * @param clicked
     */
    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public String toString() {
        return "Poker{name = " + name + ", side = " + side + ", clickable = " + clickable + ", clicked = " + clicked + "}";
    }
}
