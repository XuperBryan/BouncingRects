/**
 * Write a description of class CombineGame here.
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
public class CombineGame extends JComponent
{
    public CombineTarget t;
    public CombineBar b;
    public CombineBar b2;
    public int level = 1;
    public static int frameHeight=700;
    public static int frameWidth=1000;
    public int life = 5;
    public int life2 = 5;
    public int score = 0;
    public int score2 = 0;


    public int speedXb;

    public int speedYb;

    public int speedXt;

    public int speedYt;

    public int collisionMoment = 0;
    //public static int collisionMomentTimer = 0;
    public CombineGame(int frameWidth, int frameHeight){
        t = new CombineTarget(frameWidth,frameHeight);
        b = new CombineBar(frameWidth,frameHeight, 0);
        b2 = new CombineBar(frameWidth,frameHeight, 0);
    }

    public void move(){
        t.move();
        b.move();
        b2.move();
    }

    public void stop(){
        t.stop();
        b.stop();
        b2.stop();
    }

    public void go(){
        t.go();
        if(life>0){
            b.go();
        }
        if(life2>0){
            b2.go();
        }
    }

    public void continueGame(){ //Continue game by pressing enter: restores both lives, keeps level and state of target 
        life = 5;
        life2 = 5;
        collisionMoment = 0;
        go();
    }

    public void restartGame(){
        life = 5;
        life2 = 5;
        score = 0;
        score2 = 0;
        collisionMoment = 0;
        while(level>1){
            t.antishrink();
            level--;
        }
        t.setSpeedX(speedXt);
        t.setSpeedY(speedYt);
        b.setSpeedX(speedXb);
        b.setSpeedY(speedYb);
        b2.setSpeedX(speedXb);
        b2.setSpeedY(speedYb);

        go();
    }

    public void paintComponent(Graphics g)
    {
        Graphics2D g2=(Graphics2D)g;

        //non-poopy text
        g2.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        Random gen = new Random();

        Color targetColor = (Color.red);
        Color barColor1 = (Color.black);
        Color barColor2 = (Color.white);

        Color targetColorB = (Color.lightGray);
        Color barColor1B = (Color.lightGray);
        Color barColor2B = (Color.lightGray);
        if(collisionMoment > 0){
            targetColor = Color.yellow;
            collisionMoment--;
        }
        if(collisionMoment < 0){
            targetColor = Color.yellow;
            collisionMoment++;
        }

        g2.setColor(targetColorB);
        g2.draw(t.rect);
        g2.setColor(targetColor);
        g2.fill(t.rect);

        g2.setColor(barColor1B);
        g2.draw(b.rect);
        g2.setColor(barColor1);
        g2.fill(b.rect);

        g2.setColor(barColor2B);
        g2.draw(b2.rect);
        g2.setColor(barColor2);
        g2.fill(b2.rect);

        move();
        g.setFont(new Font("Open Sans", Font.PLAIN, 40));  
        g2.setColor(Color.black);
        g2.drawString("LEVEL " +level,2*frameWidth/5,frameHeight-50);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20)); 
        g2.drawString("Press space when the small box is in the big box!",5,20);
        g2.drawString("Get the little box to stop inside the big box to move on!",5,40);
        g2.drawString("Big box shrinks every time!",5,60);
        g2.drawString("Level three level gives an extra life!",5,80);
        g.setFont(new Font("Open Sans", Font.PLAIN, 40)); 
        g2.drawString("P1 LIVES: " +life,5,frameHeight-90);
        g2.drawString("P2 LIVES: " +life2,frameWidth-300,frameHeight-90);
        g2.drawString("P1 SCORE: " +score,5,frameHeight-50);
        g2.drawString("P2 SCORE: " +score2,frameWidth-300,frameHeight-50);

        for(int i = 1; i<=life; i++){
            g2.drawString("O",5,frameHeight-90-40*i);
        }
        for(int i = 1; i<=life; i++){
            g2.drawString("O",frameWidth-60,frameHeight-90-40*i);
        }
        revalidate();
        repaint();
    }

    public void levelUp(){
        t.shrink();
        go();
    }

    public static void main(String[] args){
        System.out.println("Game");
        Scanner in = new Scanner(System.in);
            
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        Random gen = new Random();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setVisible(true);
        
        frameWidth = frame.getWidth();
        frameHeight = frame.getHeight();
        CombineGame CombineGame = new CombineGame(frameWidth,frameHeight);
        
        System.out.println("X speed of player?(Recommended 1-2)");
        CombineGame.speedXb = in.nextInt();
        System.out.println("Y speed of player(Recommended 1-2)");
        CombineGame.speedYb = in.nextInt();
        System.out.println("X speed of CombineTarget?(Recommended 1-2)");
        CombineGame.speedXt = in.nextInt();
        System.out.println("Y speed of CombineTarget?(Recommended 1-2)");
        CombineGame.speedYt = in.nextInt();
        
        
        
        CombineGame.t.setSpeedX(CombineGame.speedXt);
        CombineGame.t.setSpeedY(CombineGame.speedYt);
        CombineGame.b.setSpeedX(CombineGame.speedXb);
        CombineGame.b.setSpeedY(CombineGame.speedYb);
        CombineGame.b2.setSpeedX(CombineGame.speedXb);
        CombineGame.b2.setSpeedY(CombineGame.speedYb);
        
        frame.getContentPane().setBackground(Color.cyan);    
        frame.add(CombineGame);
        
        
        frame.addKeyListener(new KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent e){
                    if(e.getKeyCode() == KeyEvent.VK_A){
                        if(CombineGame.life>0){
                            CombineGame.stop();
                            CombineGame.repaint();
                            if(CombineGame.t.check(CombineGame.b.getX(),CombineGame.b.getY(),CombineGame.b.getWidth(),CombineGame.b.getHeight())){
                                CombineGame.level++;
                                CombineGame.score++;
                                CombineGame.collisionMoment = 40;
                                if(CombineGame.level%3==0){
                                    CombineGame.life++;
                                    CombineGame.life2++;
                                }
                                CombineGame.levelUp();
                            }else{
                                frame.getContentPane().setBackground(new Color(gen.nextInt(256),gen.nextInt(256),gen.nextInt(256),gen.nextInt(256)));
                                CombineGame.life--;
                                CombineGame.t.reduceSpeed();

                                if(CombineGame.life>0||CombineGame.life2>0){
                                    CombineGame.go();
                                }
                                CombineGame.repaint();
                            }
                        }
                    }
                    if(e.getKeyCode() == KeyEvent.VK_L){
                        if(CombineGame.life2>0){
                            CombineGame.stop();
                            CombineGame.repaint();
                            if(CombineGame.t.check(CombineGame.b2.getX(),CombineGame.b2.getY(),CombineGame.b2.getWidth(),CombineGame.b2.getHeight())){
                                CombineGame.level++;
                                CombineGame.score2++;
                                CombineGame.collisionMoment = -40;
                                if(CombineGame.level%3==0){
                                    //CombineGame.life2++;
                                }
                                CombineGame.levelUp();
                            }else{
                                frame.getContentPane().setBackground(new Color(gen.nextInt(256),gen.nextInt(256),gen.nextInt(256),gen.nextInt(256)));
                                CombineGame.life2--;
                                CombineGame.t.reduceSpeed();

                                if(CombineGame.life>0||CombineGame.life2>0){
                                    CombineGame.go();
                                }
                                CombineGame.repaint();
                            }
                        }
                    }
                    if((CombineGame.life<=0&&CombineGame.life2<=0)&&e.getKeyCode() == KeyEvent.VK_ENTER){
                        CombineGame.continueGame();
                    }
                    if((CombineGame.life<=0&&CombineGame.life2<=0)&&e.getKeyCode() == KeyEvent.VK_SPACE){
                        CombineGame.restartGame();
                    }
                }

            });
        class TimerListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e){
                CombineGame.move();
                CombineGame.repaint();
            }
        }
        ActionListener listener = new TimerListener();
        Timer t = new Timer(1,listener);
        t.start();
        CombineGame.repaint();
        frame.revalidate();
        frame.repaint();
    }
}