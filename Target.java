

/**
 * Write a description of class Target here.
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
public class Target extends JComponent
{
    public Rectangle rect;
    public int width = 300;
    public int height = 300;
    public int speedY = 1;
    public int speedX = 0;
    public int frameWidth;
    public int frameHeight;
    public boolean moving;
    public Target(int frameWidth, int frameHeight)
    {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        Random gen = new Random();
        rect = new Rectangle(gen.nextInt(frameWidth-width-1),gen.nextInt(frameHeight-height-1),width,height);
        moving = true;
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.red);
        g2.draw(rect);
        g2.fill(rect);
        repaint();
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
    public void shrink(){
        Rectangle result = rect;
        rect = new Rectangle(result.x,result.y,(int)(result.getWidth()*.9),(int)(result.getHeight()*.9));
        rect.width = (int)(result.getWidth()*.9);
        rect.height = (int)(result.getHeight()*.9);
        if(speedY>0){
            speedY++;
        }else{
            speedY--;
        }
        if(speedX>0){
            speedX++;
        }else{
            speedX--;
        }
        revalidate();
        repaint();
    }
    public void restart(){
        Random gen = new Random();
        rect = new Rectangle(gen.nextInt(frameWidth-width-1),gen.nextInt(frameHeight-height-1),width,height);
        moving = true;
        setSpeedX(1    );
        setSpeedY(1);
        revalidate();
        repaint();
    }
    public void reduceSpeed(){
        if(speedX==1||speedX==-1){
        }else if(speedX>0){
            speedX--;
        }else{
            speedX++;
        }
        if(speedY==1||speedY==-1){
        }else if(speedY>0){
            speedY--;
        }else{
            speedY++;
        }
    }
    public void go(){
        moving = true;
        move();
        repaint();
    }
    public void stop(){
        moving = false;
        repaint();
    }
    public boolean check(int x, int y, int width, int height){
        Rectangle checker = new Rectangle(x,y,width,height);
        return rect.intersects(checker);
    }
}