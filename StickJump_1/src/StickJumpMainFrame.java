import java.awt.Color; 
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class StickJumpMainFrame extends JFrame {
	ImageIcon ic = new ImageIcon("images/stickman2.png");
	Image img = ic.getImage();
	
	ImageIcon board = new ImageIcon("images/board.png");
	Image bd = board.getImage();
	
	ImageIcon background = new ImageIcon("images/jungle1.jpg");
	Image bg = background.getImage();
	
	
	private static final int D_W = 600;
	private static final int D_H = 800;
	DrawPanel drawPanel = new DrawPanel();

	int with = 1600;
	int height = 768;

	// int previousY = 0;
	int unit = 5;

	List<Line> lines = new ArrayList<Line>();
	boolean down = false;
	boolean keyPress = false;
	
	int x=50,y=50,h=50;
	double dx=0,dy=0;

	public StickJumpMainFrame() {

		add(drawPanel);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

		// lines = new ArrayList<Line>();
		for (int i = 0; i < 10; i++) {
			Line line = new Line();
			Random rx = new Random();
			line.x = rx.nextInt(600);

			Random ry = new Random();
			line.y = i*60;

			Random rw = new Random();
			line.with = rw.nextInt(50) + 120;

			lines.add(line);
		}

	

	}

	private class DrawPanel extends JPanel {
		public DrawPanel() {
			InputMap inputMap = getInputMap(WHEN_IN_FOCUSED_WINDOW);
			ActionMap actionMap = getActionMap();
			
			
			
			String VK_LEFT = "VK_LEFT";
			KeyStroke W = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
			inputMap.put(W, VK_LEFT);
			actionMap.put(VK_LEFT, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					x-=10;

				}
			});

			String VK_RIGHT = "VK_RIGHT";
			KeyStroke WVK_RIGHT = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
			inputMap.put(WVK_RIGHT, VK_RIGHT);
			actionMap.put(VK_RIGHT, new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					x+=5;

				}
			});

			ActionListener listener = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {

					dy+=0.2;
					y+=dy;
					if(y>400) dy=-10;
					
					if(y<h){
						for (Line line : lines) {
						y=h;
						line.y=line.y-(int)dy;
						if(line.y>700){
							Random rw = new Random();
							line.y=10;
							line.x = rw.nextInt(600) + 120;
							}
						}
					}
								
					for (Line line : lines) {
						if((x>line.x && x<line.x+line.with)&& (y>=line.y && y<=line.y+10 && dy>0)){
							dy=-10;
						}
					}
					
					drawPanel.repaint();
					
				}
			};
			Timer timer = new Timer(10, listener);
			
			timer.start();

		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			drawBackground(g);
			drawValuesSteps(g);
			drawValues(g);
			
		}
		private void drawBackground(Graphics g) {
		g.drawImage(bg,0,0,getWidth(),getHeight(),this);
		}
		private void drawValuesSteps(Graphics g) {

			for (Line line : lines) {

				Graphics stepBorder = (Graphics) g;
				stepBorder.setColor(Color.black);
				// stepBorder.s;
				Graphics step = (Graphics) g;
				
				step.drawImage(bd, line.x+25, line.y, line.with, 30,this);
			}
		}

		private void drawValues(Graphics g) {

			//try {

				//g.drawImage(ImageIO.read(new File("ressource/bg.png")), 0, 0, this);
				
				
				
			Graphics step = (Graphics) g;
			step.setColor(Color.black);

				
				
				int currentX=x;
				int currentY=y;
				

				//step.fillRect(currentX, currentY, unit * 2, unit);
				//step.fillRect(currentX + unit * 3, currentY, unit * 2, unit);
				//step.fillRect(currentX + unit * 6, currentY, unit * 2, unit);
				//step.fillRect(currentX + unit * 9, currentY, unit * 2, unit);
				// 2
				//step.setColor(Color.black);
				//step.drawOval(currentX, currentY, currentY, currentY, currentX, currentY);
				//g.drawString("jump",currentX+unit, currentY-unit);
				//g.drawOval(currentX+unit, currentY-unit, unit*10, unit*10);

				step.fillRect(currentX + unit * 4, currentY, unit * 2, unit);
				step.drawImage(img, currentX + unit,currentY-unit*10,unit*10, unit*10, this);
				
	
				
			
		}

		public Dimension getPreferredSize() {
			return new Dimension(D_W, D_H);
		}
	}


	private class Line {
		int x;
		int y;
		int with;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new StickJumpMainFrame();

			}
		});
	}
}