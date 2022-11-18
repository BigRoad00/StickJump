import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


class PlatformPosition {
   int x, y;
}

public class StickmanJump_2 extends JFrame implements Runnable, KeyListener {
   final int width = 400; 
   final int height = 533;  
   
   boolean isRunning;
   Thread thread;
   BufferedImage view, background, platform, stick; 
   
   PlatformPosition[] platformsPosition;
   int x = 100, y = 100, h = 150; // 50 50 50
   float dy = 0;
   boolean right, left;
    
   public StickmanJump_2() {
	   setResizable(false);
	   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   pack();
	   setLocationRelativeTo(null);
	   setPreferredSize(new Dimension(WIDTH, HEIGHT));
	   addKeyListener(this);
	   
	   setSize(600,600);
	   setVisible(true);

   }
   
   public static void main(String[] args) {
      new StickmanJump_2();
   }
   
   @Override
   public void addNotify() {
      super.addNotify();
      if(thread == null) {
         thread = new Thread(this);
         isRunning = true;
         thread.start();
      }
   }
   
   public void start() {
      try {
         view = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
         background = ImageIO.read(getClass().getResource("images/background.jpg"));
         platform = ImageIO.read(getClass().getResource("images/board.png"));
         stick = ImageIO.read(getClass().getResource("images/stickman2.png"));
         
         platformsPosition = new PlatformPosition[20];
         
         for(int i = 0; i<10; i++) {
            platformsPosition[i] = new PlatformPosition();
            platformsPosition[i].x = new Random().nextInt(400); //500
            platformsPosition[i].y = new Random().nextInt(533);  //650
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   public void update() {
      
      if(right) {
         x += 3;
      } else if (left) {
         x -= 3;
      }
      dy += 0.2; 
      y += dy;
      if(y > 500) {       // 400 533
         dy = -10;
      }
      if (y < h) {
         for(int i = 0; i< 10; i++) {
            y = h;
            platformsPosition[i].y = platformsPosition[i].y - (int)dy;
            if(platformsPosition[i].y > 533) {
               platformsPosition[i].y = 0;
               platformsPosition[i].x = new Random().nextInt(400);
            }
         }
      }
      for(int i= 0; i<10; i++) {
         if((x + 50 > platformsPosition[i].x) &&
               (x + 20 < platformsPosition[i].x + 68) &&
               (y + 70 > platformsPosition[i].y) &&
               (y + 70 < platformsPosition[i].y + 14) &&
               (dy > 0)) {
            dy = -10;
         }
      }
   }
   public void draw() {
      Graphics2D g2 =(Graphics2D)view.getGraphics();
      g2.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
      g2.drawImage(stick, x, y, stick.getWidth(), stick.getHeight(), null);
      for(int i=0; i<10; i++) {
         g2.drawImage(
            platform,
            platformsPosition[i].x,
            platformsPosition[i].y,
            platform.getWidth(),
            platform.getHeight(),
            null
         );
      }
      Graphics g = getGraphics();
      g.drawImage(view, 0, 0, WIDTH, HEIGHT, null);
      g.dispose();
   }
   
   @Override
   public void run() {
      try {
         requestFocus();
         start();
         while (isRunning) {
            update(); 
            draw();
            Thread.sleep(1000/60);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   @Override
   public void keyTyped(KeyEvent e) {
      
   }
   @Override
   public void keyPressed(KeyEvent e) {
      if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
         right = true;
      }
      if(e.getKeyCode() == KeyEvent.VK_LEFT) {
         left = true;
      }
   }
   
   @Override
   public void keyReleased(KeyEvent e) {
      if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
         right = false;
      }
      if(e.getKeyCode() == KeyEvent.VK_LEFT) {
         left = false;
      }
   }
}