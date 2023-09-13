package CircleEater;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Circle extends Thread {
	int diameter;// the circle's size
	int x;// the location of the circle
	int y;
	int dx;// the movement size of the circle
	int dy;
	int spinner;// regular circle=0, spinner circle=1
	Color color;// color of the circle
	GamePanel panel;// the game panel
	boolean isPaused;// is the game paused or not
	int startAngle;// data for painting the spinner circles
	int angleIncrement;
	static int counter = 0;// counter of eaten circles

	public Circle(int x, int y, int diameter, int spinner, GamePanel panel) {// constructor of the class
		Random rand = new Random();
		this.diameter = diameter;
		this.x = x;
		this.y = y;
		this.panel = panel;
		this.dx = 1;
		this.dy = 1;
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		this.spinner = spinner;
		this.color = new Color(r, g, b);
		this.isPaused = false;
		this.startAngle = 0;
		this.angleIncrement = 6;
	}

	public void run() {// when a circle starts working it does the actions below
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
		Player p2 = null;

		while (true) {// check if the game is paused
			if (isPaused) {
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {

				Circle[] circles = panel.circles;
				ArrayList<Circle> spinners = panel.spinnerCircles;

				int level = panel.level;

				int index = 0;
				int height = panel.getHeight();
				int width = panel.getWidth();
				if (x + diameter / 2 >= width)// if the circle collides with the screen boundaries it changes its direction
					dx = -1;

				if (x - diameter / 2 <= 0)
					dx = 1;

				if (y + diameter / 2 >= height)
					dy = -1;

				if (y - diameter / 2 <= 0)
					dy = 1;

				this.x += dx;// the movement of the circle
				this.y += dy;
				if (spinner == 1)
					startAngle += angleIncrement;

				for (int i = 0; i < circles.length; i++) {// if the circle collides with another circle it changes its direction
					if (circles[i] != this) {
						if (circles[i] != null) {
							if (calcDistance(x - circles[i].x,
									y - circles[i].y) <= ((diameter / 2) + (circles[i].diameter / 2))) {

								dx *= -1;
								dy *= -1;

							}
						}

					} else
						index = i;

				}
				if (spinner == 1) {
					for (int i = 0; i < spinners.size(); i++) {
						if (spinners.get(i) != this) {
							if (calcDistance(spinners.get(i).x - x,
									spinners.get(i).y - y) < ((spinners.get(i).diameter / 2) + (diameter / 2))) {// collision between two spinners

								this.dx *= -1;
								this.dy *= -1;
								spinners.get(i).dx *= -1;
								spinners.get(i).dy *= -1;
								this.angleIncrement = -angleIncrement;

							}
						}

					}
					for (int j = 0; j < circles.length; j++) {// if a spinner circle collides with a regular circle, a new spinner circle is added
						if (circles[j] != null) {
							if (calcDistance(x - circles[j].x,
									y - circles[j].y) < ((diameter / 2) + (circles[j].diameter / 2))) {

								this.dx *= -1;
								this.dy *= -1;
								circles[j].dx *= -1;
								circles[j].dy *= -1;
								this.angleIncrement = -angleIncrement;
								if (panel.spinnerCircles.size() < 4) {
									Circle newCircle = new Circle(this.x + 15, this.y + 5, this.diameter - 3, 1, panel);
									panel.spinnerCircles.add(newCircle);
									newCircle.start();
								}

							}
						}
					}
				}

				Player p = panel.player;
				if (GamePanel.multiplayer)
					p2 = panel.player2;

				if ((calcDistance(x - p.x, y - p.y) <= (diameter / 2 + p.diameter / 2)) && diameter <= p.diameter) {// collision with player check

					if (spinner == 1) {
						panel.spinnerCircles.remove(this);// collision with spinner
					} else {
						counter++;
						circles[index] = null;// collision with regular circle

					}
					p.diameter = p.diameter + 4;
					GamePanel.score1 = GamePanel.score1 + 1;

					NextLevelCheck check = new NextLevelCheck();
					check.checkNextLevel(panel);

					break;
				}
				if (GamePanel.multiplayer) {// collision with player check (for a multiplayer game)
					if ((calcDistance(x - p2.x, y - p2.y) < (diameter / 2 + p2.diameter / 2))
							&& diameter < p2.diameter) {
						if (spinner == 1) {
							panel.spinnerCircles.remove(this);
						} else {
							counter++;
							circles[index] = null;

						}
						p2.diameter = p2.diameter + 4;
						GamePanel.score2 = GamePanel.score2 + 1;
						NextLevelCheck check = new NextLevelCheck();
						check.checkNextLevel(panel);
						break;
					}
				}

				if ((calcDistance(x - p.x, y - p.y) < (diameter / 2 + p.diameter / 2)) && diameter > p.diameter) {// disqualification
					panel.timer.cancel();
					String audioFilePath = "gameOver.wav";
					AudioPlayer audioplayer = new AudioPlayer();
					audioplayer.play(audioFilePath);
					if (GamePanel.multiplayer) {
						if (GamePanel.score1 > GamePanel.score2)
							JOptionPane.showMessageDialog(panel, "Player 1 won with the score:" + GamePanel.score1);
						else if (GamePanel.score1 == GamePanel.score2)
							JOptionPane.showMessageDialog(panel, "There's a tie!");

						else
							JOptionPane.showMessageDialog(panel, "Player 2 won with the score:" + GamePanel.score2);

					} else
						JOptionPane.showMessageDialog(panel, "You lost!");

					System.exit(1);
				}

				if (GamePanel.multiplayer) {
					if ((calcDistance(x - p2.x, y - p2.y) < (diameter / 2 + p2.diameter / 2))
							&& diameter > p2.diameter) {
						panel.timer.cancel();
						String audioFilePath = "gameOver.wav";
						AudioPlayer audioplayer = new AudioPlayer();
						audioplayer.play(audioFilePath);
						if (GamePanel.score1 > GamePanel.score2)
							JOptionPane.showMessageDialog(panel, "Player 1 won with the score:" + GamePanel.score1);
						else if (GamePanel.score1 == GamePanel.score2)
							JOptionPane.showMessageDialog(panel, "There's a tie!");

						else
							JOptionPane.showMessageDialog(panel, "Player 2 won with the score:" + GamePanel.score2);
						System.exit(1);
					}
				}

				if (level == 1 || level == 2 || level == 4) {// speed of the game
					try {
						Thread.sleep(4);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (level == 3 || level == 5 || level == 6) {
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		}

	}

	public void paint(Graphics gr) {// the paint functions

		gr.setColor(color);
		gr.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
	}

	public void newPaint(Graphics g) {
		g.setColor(color);
		for (int i = 0; i < 6; i++) {
			g.fillArc(x - diameter / 2, y - diameter / 2, diameter, diameter, startAngle + i * 60, 30);
		}
	}

	public double calcDistance(int a, int b) {// distance between 2 points function

		return Math.sqrt(Math.pow(a, 2.0) + Math.pow(b, 2.0));

	}

}
