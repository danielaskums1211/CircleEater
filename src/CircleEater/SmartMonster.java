package CircleEater;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class SmartMonster extends Monster {// this class extends the main class- Monster
	private Image img;// image of the monster
	private boolean isPaused;// pause flag

	public SmartMonster(int x, int y, int diameter, GamePanel panel, Player player) {// constructor of the class
		super(x, y, diameter, panel, player);
		this.isPaused = false;
		this.img = Toolkit.getDefaultToolkit().getImage("smartMonster.png");

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

						if (collisionCircle(arr[i], monsterRec)) {// if the monster collides with one of the circles it changes their direction and makes their size bigger
							arr[i].dx = arr[i].dx * -1;
							arr[i].dy = arr[i].dy * -1;
							arr[i].diameter = arr[i].diameter + 2;

							if (arr[i].x > x) {
								x -= 10;

							}
							if (arr[i].x < x) {
								x += 10;

							}

						}

					}

				}

				if (collisionPlayer(p) && diameter < p.diameter) {// collision with player
					p.diameter = p.diameter + 5;
					if (Circle.counter != panel.numOfBalls)
						GamePanel.score1 = GamePanel.score1 + 10;
					this.stop();
					break;

				}

				if (collisionPlayer(p) && diameter > p.diameter)// disqualification
				{
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

	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

}
