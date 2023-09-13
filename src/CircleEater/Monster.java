package CircleEater;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Monster extends Thread {
	int diameter;// the size of the monster
	int x;// the location of the circle
	int y;
	int dx;// the movement size of the circle
	int dy;
	GamePanel panel;// the game panel
	Image img;// image of the monster
	boolean isPaused;// pause flag
	Player p;// player to chase

	public Monster(int x, int y, int diameter, GamePanel panel, Player player) {// constructor of the class
		this.diameter = diameter;
		this.x = x;
		this.y = y;
		this.panel = panel;
		this.dx = 1;
		this.dy = 1;
		this.isPaused = false;
		this.img = Toolkit.getDefaultToolkit().getImage("monster.png");
		this.p = player;

	}

	@SuppressWarnings("deprecation")
	public void run() {// when a monster starts working it does the actions below
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
		while (true) {
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
				if (x + diameter / 2 >= width)// if the monster collides with the screen boundaries it changes its direction
					dx = -1;

				if (x - diameter / 2 <= 0)
					dx = 1;

				if (y + diameter / 2 >= height)
					dy = -1;

				if (y - diameter / 2 <= 0)
					dy = 1;

				if (p.x > x && dx == 1)// checks the players location in order to chase
					x += dx;
				else if (p.x > x && dx == -1)
					x -= dx;

				if (p.y > y && dy == 1)
					y += dy;
				else if (p.y > y && dy == -1)
					y -= dy;

				if (p.x < x && dx == 1)
					x -= dx;
				else if (p.x < x && dx == -1)
					x += dx;

				if (p.y < y && dy == 1)
					y -= dy;
				else if (p.y < y && dy == -1)
					y += dy;

				Rectangle monsterRec = new Rectangle(x, y, diameter, diameter + 20);

				for (int i = 0; i < arr.length; i++) {

					if (arr[i] != null) {

						if (collisionCircle(arr[i], monsterRec)) {// if the monster collides with one of the circles it changes their direction
							arr[i].dx = arr[i].dx * -1;
							arr[i].dy = arr[i].dy * -1;

							if (arr[i].x > x) {
								x -= 10;

							}
							if (arr[i].x < x) {
								x += 10;

							}

						}

					}

				}
				for (int i = 0; i < spinnerArr.size(); i++) {// if the monster collides with a spinner circle it changes their direction
					if (collisionCircle(spinnerArr.get(i), monsterRec)) {
						spinnerArr.get(i).dx=spinnerArr.get(i).dx*-1;
						spinnerArr.get(i).dy=spinnerArr.get(i).dy*-1;

					}
				}

				if (collisionPlayer(p) && diameter < p.diameter) {// collision with player
					p.diameter = p.diameter + 5;
					if (Circle.counter != panel.numOfBalls)
						GamePanel.score1 = GamePanel.score1 + 5;
					this.stop();

					break;

				}

				if (collisionPlayer(p) && diameter > p.diameter) {// disqualification
					panel.timer.cancel();
					String audioFilePath = "gameOver.wav";
					AudioPlayer player = new AudioPlayer();
					player.play(audioFilePath);
					JOptionPane.showMessageDialog(panel, "You lost!");
					System.exit(1);

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

	public void paint(Graphics gr) {// paint function

		if (this.isAlive())
			gr.drawImage(img, x, y, diameter, diameter + 20, null);

	}

	public boolean collisionCircle(Circle c1, Rectangle r1) {// checks if there is a collision between the monster and a circle
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

	public boolean collisionPlayer(Player p) {// checks if there is a collision between the monster and a player
		Rectangle monster = new Rectangle(x, y, diameter, diameter + 20);
		Rectangle player = new Rectangle(p.x, p.y, p.diameter, p.diameter);
		if (monster.intersects(player))
			return true;
		return false;

	}

}
