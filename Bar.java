
/**
 * Write a description of class Bar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import java.util.Random;
public class Bar extends JComponent
{
    public Rectangle rect;
    public int width = 25;
    public int height = 25;
    public int speedX = 2;
    public int speedY = 0;
    public int frameWidth;
    public int frameHeight;
    private boolean moving;
    public Bar(int frameWidth, int frameHeight)
    {
        Random gen = new Random();
        rect = new Rectangle(gen.nextInt(frameWidth-width-1),gen.nextInt(frameHeight-height-1),width,height);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        moving = true;
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g;
        g2.draw(rect);
        g2.fill(rect);
    }
    public void move(){
        if(moving){
            if(rect.getX()>=frameWidth-rect.getWidth()||rect.getX()<0){
                speedX*=-1;
            }
            if(rect.getY()>=frameHeight-rect.getHeight()||rect.getY()<0){
                speedY*=-1;
            }
            rect.translate(speedX,speedY);
            repaint();
        }
    }
    public void go(){
        moving = true;
        move();
        repaint();
    }
    public void setSpeedX(int speed){
        Random gen = new Random();
        int chance = gen.nextInt(2);
        if(chance==1){
            speedX = speed;
        }else{
            speedX = -speed;
        }
        repaint();
    }
    public void setSpeedY(int speed){
        Random gen = new Random();
        int chance = gen.nextInt(2);
        if(chance==1){
            speedY = speed;
        }else{
            speedY = -speed;
        }
        repaint();
    }
    public void stop(){
        moving = false;
        repaint();
    }
    public void restart(){
        Random gen = new Random();
        rect = new Rectangle(gen.nextInt(frameWidth-width-1),gen.nextInt(frameHeight-height-1),width,height);
        moving = true;
    }
    public void fixBounds(int w, int h){
        frameWidth = w;
        frameHeight = h;
    }
    public int getWidth(){
        return rect.width;
    }
    public int getHeight(){
        return rect.height;
    }
    public int getX(){
        return rect.x;
    }
    public int getY(){
        return rect.y;
    }
}