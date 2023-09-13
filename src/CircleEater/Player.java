package CircleEater;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Player extends Thread {
	int diameter;// the player's size
	int x;// the location of the player
	int y;
	GamePanel panel;// the game panel
	Image img;// the image of the player

	public Player(int x, int y, String image,GamePanel panel) {//constructor of the class
		this.diameter = 60;
		this.x = x;
		this.y = x;
		this.panel = panel;
		this.img = Toolkit.getDefaultToolkit().getImage(image);
		start();

	}

	public void run() {//when a player starts working it does the actions below
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
			
			panel.repaint();
		}
	}

	public void paint(Graphics g) {//paint function

		g.drawImage(img, x, y, diameter, diameter, null);

	}

	

}
