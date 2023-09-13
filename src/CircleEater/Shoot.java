package CircleEater;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;


public class Shoot extends Thread {

	int x;// the location of the bullet
	int y;
	int dx;// the movement size of the bullet
	int diameter;// the size of the bullet
	GamePanel panel;// game panel
	Image img;// image of the bullet
	boolean isPaused;// pause flag
	int playerBullet;// to which player the bullet belongs

	public Shoot(int x, int y, int diameter, int playerBullet, GamePanel panel) {// constructor of the class
		this.x = x;
		this.y = y;
		this.dx = 2;
		this.diameter = diameter;
		this.panel = panel;
		this.isPaused = false;
		this.playerBullet = playerBullet;
		this.img = Toolkit.getDefaultToolkit().getImage("bullet.png");

	}

	@SuppressWarnings("deprecation")
	public void run() {
		boolean flag = true;
		while (flag) {
			if (isPaused) {// checks if the game is paused
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {

				Circle[] arr = panel.circles;
				ArrayList<Circle> spinnerArr = panel.spinnerCircles;
				int height = panel.getHeight();
				int width = panel.getWidth();

				if (x + diameter / 2 >= width) {// if the bullet collides with the screen boundaries it stops to exist
					flag = false;
					panel.shoots.remove(this);

				}

				if (x - diameter / 2 <= 0) {
					flag = false;
					panel.shoots.remove(this);
				}

				if (y + diameter / 2 >= height) {
					flag = false;
					panel.shoots.remove(this);

				}

				if (y - diameter / 2 <= 0) {
					flag = false;
					panel.shoots.remove(this);

				}

				x += dx;

				Rectangle bullet = new Rectangle(x, y, diameter, diameter + 20);

				for (int i = 0; i < arr.length; i++) {

					if (arr[i] != null) {

						if (collisionCircle(arr[i], bullet)) {// if the bullet collides with one of the circles it adds score to the player and stops to exist
							Circle.counter++;
							if (playerBullet == 1) {
								panel.player.diameter = panel.player.diameter + 4;
								GamePanel.score1 = GamePanel.score1 + 2;
							} else {
								panel.player2.diameter = panel.player2.diameter + 4;
								GamePanel.score2 = GamePanel.score2 + 2;
							}

							flag = false;
							panel.shoots.remove(this);
							arr[i].stop();
							arr[i] = null;

							NextLevelCheck check = new NextLevelCheck();
							check.checkNextLevel(panel);

						}

					}

				}
				for (int i = 0; i < spinnerArr.size(); i++) {// if the bullet collides with a spinner circle it stops to exist
					if (collisionCircle(spinnerArr.get(i), bullet)) {
						flag = false;
						panel.shoots.remove(this);
					}
				}
				if (collisionMonster(panel.monster1))// if the bullet collides with a monster it stops to exist
				{
					flag = false;
					panel.shoots.remove(this);

				}
				if (collisionMonster(panel.monster2)) {
					flag = false;
					panel.shoots.remove(this);

				}

				try {
					Thread.sleep(6);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public boolean collisionMonster(Monster m) {// checks if there is a collision between a bullet and a monster
		Rectangle bullet = new Rectangle(x, y, diameter + 15, diameter + 20);
		Rectangle monster = new Rectangle(m.x, m.y, m.diameter, m.diameter);
		if (bullet.intersects(monster))
			return true;
		return false;

	}

	public boolean collisionCircle(Circle c1, Rectangle r1) {// checks if there is a collision between the bullet and a circle
		float closestX = clamp(c1.x, r1.x, r1.x + r1.width);
		float closestY = clamp(c1.y, r1.y - r1.height, r1.y);

		float distanceX = c1.x - closestX;
		float distanceY = c1.y - closestY;

		return Math.pow(distanceX, 2) + Math.pow(distanceY, 2) < Math.pow(c1.diameter / 2, 2);
	}

	public static float clamp(float value, float min, float max) {// auxiliary function for collisionCircle()
		float x = value;
		if (x < min) {
			x = min;
		} else if (x > max) {
			x = max;
		}
		return x;
	}

	public void paint(Graphics gr) {// paint function

		if (this.isAlive())
			gr.drawImage(img, x, y, diameter + 15, diameter + 20, null);

	}

}
