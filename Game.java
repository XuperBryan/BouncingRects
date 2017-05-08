/**
 * Write a description of class Game here.
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
import java.util.Scanner;
import java.util.Random;
public class Game extends JComponent
{
    public Target t;
    public Bar b1;
    public Bar b2;
    public int level = 1;
    public int frameHeight;
    public int frameWidth;
    public int life1 = 5;
    public int life2 = 5;
    public int score1 = 0;
    public int score2 = 0;
    public boolean p1moving = true;
    public boolean p2moving = true;
    public int players;
    public Game(int frameWidth, int frameHeight, int players){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.players = players;
        t = new Target(frameWidth,frameHeight);
        b1 = new Bar(frameWidth,frameHeight);
        if(players==2){
            b2 = new Bar(frameWidth,frameHeight);
        }
    }    
    public void move(){
        if(players==1){
            if(p1moving){
                t.move();
            }
        }else if(players==2){
            if(p1moving||p2moving){
                t.move();
            }
        }
        if(p1moving){
            b1.move();
        }
        if(players==2){
            if(p2moving){
                b2.move();
            }
        }
    }
    public void stop(){
        t.stop();
        b1.stop();
        if(players==2){
            b2.stop();
        }
    }
    public void go(){
        if(players==1){
            if(p1moving){
                t.go();
            }
        }else if(players==2){
            if(p1moving||p2moving){
                t.go();
            }
        }
        if(p1moving){
            b1.go();
        }
        if(players==2){
            if(p2moving){
                b2.go();
            }
        }
    }
    public void restart(){
        t.restart();
        b1.restart();
        level = 1;
        life1 = 5;
        score1 = 0;
        p1moving = true;
        if(players==2){
            b2.restart();
            life2 = 5;
            score2 = 0;
            p2moving = true;
        }
        revalidate();
        repaint();
    }
    public void paintComponent(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g;
        Random gen = new Random();
        g2.setColor(Color.white);
        g2.draw(t.rect);
        g2.setColor(Color.red);
        g2.fill(t.rect);
        g2.setColor(Color.white);
        g2.draw(b1.rect);
        g2.setColor(Color.black);
        if(players==2){
            g2.draw(b2.rect);
        }
        g2.fill(b1.rect);
        if(players==2){
            g2.setColor(Color.white);
            g2.fill(b2.rect);
        }
        move();
        g.setFont(new Font("Times New Roman", Font.PLAIN, 50)); 
        g2.setColor(Color.black);
        g2.drawString("LEVEL " +level,0,(frameHeight/2));
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20)); 
        g2.drawString("Press \"A\" or \"L\" when the small box is in the big box!",5,20);
        g2.drawString("Press \"R\" to restart!",5,40);
        g2.drawString("Get the little box to stop inside the big box to move on!",5,60);
        g2.drawString("Big box shrinks every time!",5,80);
        g2.drawString("Level third level gives an extra life!",5,100);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 30)); 
        g2.drawString("PLAYER 1",0,(frameHeight/2)+50);
        g2.drawString("LIVES: " +life1,0,(frameHeight/2)+100);
        g2.drawString("SCORE: " +score1,0,(frameHeight/2)+150);
        if(players==2){
            g2.setColor(Color.white);
            g2.drawString("PLAYER 2",0,(frameHeight/2)+200);
            g2.drawString("LIVES: " +life2,0,(frameHeight/2)+250);
            g2.drawString("SCORE: " +score2,0,(frameHeight/2)+300);
        }
        revalidate();
        repaint();
    }
    public void levelUp(){
        t.shrink();
        go();
    }
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("X speed of player?(Recommended 1-2)");
        int speedXb = in.nextInt();
        System.out.println("Y speed of player(Recommended 1-2)");
        int speedYb = in.nextInt();
        System.out.println("X speed of target?(Recommended 1-2)");
        int speedXt = in.nextInt();
        System.out.println("Y speed of target?(Recommended 1-2)");
        int speedYt = in.nextInt();
        System.out.println("Number of players? (1 or 2)");
        int players = in.nextInt();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        Random gen = new Random();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        Game game = new Game(frameWidth,frameHeight,players);
        game.t.setSpeedX(speedXt);
        game.t.setSpeedY(speedYt);
        game.b1.setSpeedX(speedXb);
        game.b1.setSpeedY(speedYb);
        if(game.players==2){
            game.b2.setSpeedX(speedXb);
            game.b2.setSpeedY(speedYb);
        }
        frame.getContentPane().setBackground(Color.pink);    
        frame.add(game);
        frame.addKeyListener(new KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent e){
                    if(e.getKeyCode() == KeyEvent.VK_A){
                        if(game.life1>0){
                            game.stop();
                            game.repaint();
                            if(game.t.check(game.b1.getX(),game.b1.getY(),game.b1.getWidth(),game.b1.getHeight())){
                                game.score1++;
                                game.level++;
                                if(game.level%3==0){
                                    game.life1++;
                                }
                                game.levelUp();
                            }else{
                                frame.getContentPane().setBackground(new Color(gen.nextInt(256),gen.nextInt(256),gen.nextInt(256),gen.nextInt(256)));
                                game.life1--;
                                game.t.reduceSpeed();
                                if(game.life1>0){
                                    game.go();
                                }else{
                                    game.p1moving = false;
                                    game.go();    
                                }
                                game.repaint();
                            }
                        }else{
                            game.p1moving = false;
                        }
                    }
                    if(e.getKeyCode() == KeyEvent.VK_R){
                        frame.getContentPane().setBackground(new Color(gen.nextInt(256),gen.nextInt(256),gen.nextInt(256),gen.nextInt(256)));
                        game.restart();
                        game.repaint();
                        frame.repaint();
                    }
                    if(game.players==2){
                        if(e.getKeyCode() == KeyEvent.VK_L){
                            if(game.life2>0){
                                game.stop();
                                game.repaint();
                                if(game.t.check(game.b2.getX(),game.b2.getY(),game.b2.getWidth(),game.b2.getHeight())){
                                    game.score2++;
                                    game.level++;
                                    if(game.level%3==0){
                                        game.life2++;
                                    }
                                    game.levelUp();
                                }else{
                                    frame.getContentPane().setBackground(new Color(gen.nextInt(256),gen.nextInt(256),gen.nextInt(256),gen.nextInt(256)));
                                    game.life2--;
                                    game.t.reduceSpeed();
                                    if(game.life2>0){
                                        game.go();
                                    }else{
                                        game.p2moving = false;
                                        game.go();
                                    }
                                    game.repaint();
                                }
                            }else{
                                game.p2moving = false;
                            }
                        }
                    }
                }
            });
        class TimerListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e){
                game.move();
                game.repaint();
            }
        }
        ActionListener listener = new TimerListener();
        Timer t = new Timer(3,listener);
        t.start();
        game.repaint();
        frame.revalidate();
        frame.repaint();
    }
}