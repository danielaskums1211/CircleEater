package CircleEater;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

	Player player;// player1
	Player player2;// player2
	Circle[] circles;// circles array
	Monster monster1;// monster1
	Monster monster2;// monster2
	ArrayList<Shoot> shoots;// dynamic array of bullets
	ArrayList<Circle> spinnerCircles;// dynamic array of spinner circles
	SmartMonster smartMonster1;// smart monster1
	SmartMonster smartMonster2;// smart monster2
	Image img;// background image
	int numOfBalls;// number of the circles
	int level;// count of levels
	JButton pauseButton;// pause button
	boolean paused;// pause flag
	JLabel scoreTxt1;// score text1
	JLabel scoreTxt2;// score text2
	int countBullets;// the limit of shoot bullets
	Timer timer;// timer

	static int score1 = 0;// score of player1
	static int score2 = 0;// score of player2
	static int seconds = 10;// seconds for the timer

	static boolean multiplayer;// multiplayer flag

	public GamePanel() {// constructor of the class
		paused = false;
		countBullets = 5;
		numOfBalls = 5;
		level = 1;
		img = new ImageIcon("background1.jpg").getImage();

		player = new Player(400, 700, "ball.png", this);
		circles = new Circle[numOfBalls];

		shoots = new ArrayList<>();
		spinnerCircles = new ArrayList<>();

		timer = new Timer();
		timer.scheduleAtFixedRate(new SecondsTimer(), 1000, 1000);

		String audioFilePath = "backMusic.wav";// background music
		AudioPlayer audioplayer = new AudioPlayer();
		audioplayer.loop(audioFilePath);

		if (multiplayer) {

			player.img = Toolkit.getDefaultToolkit().getImage("Ball2.png");
			player2 = new Player(470, 700, "ball3.png", this);
			scoreTxt1 = new JLabel("score player 1:" + score1);
			scoreTxt1.setFont(new Font("Verdana", Font.BOLD, 16));
			Dimension size1 = scoreTxt1.getPreferredSize();
			scoreTxt1.setBounds(60, 35, 400, size1.height);
			scoreTxt1.setForeground(Color.green);

			this.setLayout(null);
			add(scoreTxt1);
			scoreTxt2 = new JLabel("score player 2:" + score2);
			scoreTxt2.setFont(new Font("Verdana", Font.BOLD, 16));
			Dimension size2 = scoreTxt2.getPreferredSize();
			scoreTxt2.setBounds(800, 35, 400, size2.height);
			scoreTxt2.setForeground(Color.cyan);
			this.setLayout(null);
			add(scoreTxt2);
			addKeyListener(new KeyboardListener());

		} else {

			pauseButton = new JButton();
			pauseButton.setPreferredSize(new Dimension(80, 50));
			pauseButton.setText("PAUSE");
			pauseButton.setBackground(new Color(235, 67, 52));
			pauseButton.setForeground(Color.black);
			pauseButton.setFont(new Font("arial", Font.BOLD, 14));
			Dimension size1 = pauseButton.getPreferredSize();
			pauseButton.setBounds(460, 20, size1.width, size1.height);
			this.setLayout(null);
			pauseButton.addActionListener(new ButtonListener());
			add(pauseButton);
			scoreTxt1 = new JLabel("score:" + score1);
			scoreTxt1.setFont(new Font("Verdana", Font.BOLD, 16));
			Dimension size2 = pauseButton.getPreferredSize();
			scoreTxt1.setBounds(465, 60, 400, size2.height);
			this.setLayout(null);
			add(scoreTxt1);

			addMouseMotionListener(new MouseMotionListener());
			addMouseListener(new MouseListener());

		}

		this.monster1 = new Monster(60, 200, 80, this, player);
		this.monster2 = new Monster(40, 200, 80, this, player2);

		this.smartMonster1 = new SmartMonster(60, 200, 80, this, player);
		this.smartMonster2 = new SmartMonster(40, 200, 80, this, player2);

		for (int i = 0; i < numOfBalls; i++) {
			int x = (i + 1) * 90;
			int y = (int) (Math.random() * 600);
			int rad = (int) (Math.random() * 50) + 20;

			circles[i] = new Circle(x, y, rad, 0, this);
		}

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}
		setFocusable(true);

	}

	public void paintComponent(Graphics g) {// paint function
		super.paintComponent(g);
		//paints the background
		if (level == 1) {
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);

		} else if (level == 2) {
			img = new ImageIcon("background2.jpg").getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);

		} else if (level == 3) {
			scoreTxt1.setForeground(Color.white);
			if (multiplayer)
				scoreTxt2.setForeground(Color.white);

			img = new ImageIcon("gifBackground.gif").getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);

		} else if (level == 4) {
			scoreTxt1.setForeground(Color.black);
			if (multiplayer)
				scoreTxt2.setForeground(Color.black);
			img = new ImageIcon("background3.jpg").getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		} else if (level == 5) {
			img = new ImageIcon("background4.jpg").getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);

		} else if (level == 6) {
			img = new ImageIcon("gifBackground2.gif").getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		}
		//paints the game components
		for (int i = 0; i < circles.length; i++) {
			if (circles[i] != null && circles[i].isAlive()) {
				circles[i].paint(g);

			}

		}
		if (spinnerCircles.size() > 0) {
			for (int i = 0; i < spinnerCircles.size(); i++) {
				if (spinnerCircles.get(i).isAlive())
					spinnerCircles.get(i).newPaint(g);

			}
		}

		player.paint(g);

		for (int i = 0; i < shoots.size(); i++) {
			if (shoots.get(i).isAlive())
				shoots.get(i).paint(g);

		}
		Font font = new Font("Verdana", Font.BOLD, 20);
		g.setFont(font);
		g.setColor(Color.black);
		String time = String.format("%02d", seconds);
		Image clockImg = new ImageIcon("clock.png").getImage();

		if (multiplayer) {
			g.drawImage(clockImg, 480, 10, 80, 85, null);
			g.drawString(time, 505, 77);
			g.setColor(Color.black);
			g.fillRect(55, 26, 160, 40);
			g.fillRect(795, 26, 160, 40);

			player2.paint(g);
			scoreTxt1.setText("score player 1:" + score1);
			scoreTxt2.setText("score player 2:" + score2);
			if (level == 4 || level == 5)
				monster2.paint(g);
			if (level == 6)
				smartMonster2.paint(g);
		} else {
			scoreTxt1.setText("score:" + score1);
			g.drawImage(clockImg, 900, 10, 80, 85, null);
			g.drawString(time, 925, 77);

		}

		if (level == 4 || level == 5)
			monster1.paint(g);
		if (level == 6)
			smartMonster1.paint(g);

		if (paused) {
			Font f = new Font("Segoe Script", Font.PLAIN, 60);
			g.setFont(f);
			g.setColor(Color.red);
			int width = g.getFontMetrics().stringWidth("Paused");
			int height = g.getFontMetrics().getHeight();
			g.drawString("Paused", getWidth() / 2 - width / 2, getHeight() / 2 - height + 120);
		}

	}

	class SecondsTimer extends TimerTask {// class that functions the timer
		@Override
		public void run() {
			if (!paused && Circle.counter != numOfBalls)
				seconds--;
			if(seconds==0)
			{
				String audioFilePath = "alert.wav";
				AudioPlayer audioplayer = new AudioPlayer();
				audioplayer.play(audioFilePath);
				JOptionPane.showMessageDialog(null, "Time's up!", "Message", JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon("clockIcon.png"));
				System.exit(0);
			}

		}
	}

	class ButtonListener implements ActionListener {// button listener for pausing the game
		@Override
		public void actionPerformed(ActionEvent evt) {
			if (paused) {
				pauseButton.setText("pause");
				paused = false;
				for (int i = 0; i < numOfBalls; i++) {
					if (circles[i] != null) {
						if (circles[i].isPaused) {
							synchronized (circles[i]) {
								circles[i].notify();
							}
							circles[i].isPaused = false;
						}
					}
				}
				for (int i = 0; i < shoots.size(); i++) {

					if (shoots.get(i).isPaused) {
						synchronized (shoots.get(i)) {
							shoots.get(i).notify();
						}
						shoots.get(i).isPaused = false;
					}

				}
				for (int i = 0; i < spinnerCircles.size(); i++) {
					if (spinnerCircles.get(i).isPaused) {
						synchronized (spinnerCircles.get(i)) {
							spinnerCircles.get(i).notify();
						}
						spinnerCircles.get(i).isPaused = false;
					}
				}

				if (monster1.isAlive()) {
					synchronized (monster1) {
						monster1.notify();

					}
					monster1.isPaused = false;

				}
				if (smartMonster1.isAlive()) {
					synchronized (smartMonster1) {
						smartMonster1.notify();

					}
					smartMonster1.setPaused(false);

				}
				if (multiplayer) {

					if (monster2.isAlive()) {
						synchronized (monster1) {
							monster2.notify();

						}
						monster2.isPaused = false;

					}
					if (smartMonster2.isAlive()) {
						synchronized (smartMonster2) {
							smartMonster2.notify();

						}
						smartMonster2.setPaused(false);

					}
				}

			} else {
				pauseButton.setText("START");
				paused = true;
				for (int i = 0; i < numOfBalls; i++) {
					if (circles[i] != null)
						circles[i].isPaused = true;

				}
				for (int i = 0; i < shoots.size(); i++) {
					shoots.get(i).isPaused = true;

				}
				for (int i = 0; i < spinnerCircles.size(); i++) {
					spinnerCircles.get(i).isPaused = true;

				}

				if (monster1.isAlive()) {
					monster1.isPaused = true;
				}
				if (smartMonster1.isAlive()) {
					smartMonster1.setPaused(true);
				}
				if (multiplayer) {
					if (monster2.isAlive()) {
						monster2.isPaused = true;
					}
					if (smartMonster2.isAlive()) {
						smartMonster2.setPaused(true);
					}

				}

			}
		}
	}

	private class MouseMotionListener extends MouseMotionAdapter {// mouse motion class for a single player
		public void mouseMoved(MouseEvent e) {
			player.x = e.getX();
			player.y = e.getY();
		}
	}

	private class MouseListener extends MouseAdapter {// mouse listener for shooting the bullets
		public void mousePressed(MouseEvent e) {
			if (level >= 4 && shoots.size() < countBullets && !multiplayer) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Shoot shoot = new Shoot(player.x, player.y + 20, 20, 1, GamePanel.this);
					shoots.add(shoot);
					shoot.start();

				}
			}

		}
	}

	private class KeyboardListener implements KeyListener {// keyboard listener for a multiplayer game

		@Override
		public void keyPressed(KeyEvent event) {

			if (event.getKeyCode() == KeyEvent.VK_P) {
				paused = true;
				for (int i = 0; i < numOfBalls; i++) {
					if (circles[i] != null)
						circles[i].isPaused = true;

				}
				for (int i = 0; i < shoots.size(); i++) {
					shoots.get(i).isPaused = true;

				}
				for (int i = 0; i < spinnerCircles.size(); i++) {
					spinnerCircles.get(i).isPaused = true;

				}
				if (monster1.isAlive()) {
					monster1.isPaused = true;
				}
				if (smartMonster1.isAlive()) {
					smartMonster1.setPaused(true);
				}
				if (multiplayer) {
					if (monster2.isAlive()) {
						monster2.isPaused = true;
					}
					if (smartMonster2.isAlive()) {
						smartMonster2.setPaused(true);
					}

				}
			}
			if (event.getKeyCode() == KeyEvent.VK_R) {
				paused = false;
				for (int i = 0; i < numOfBalls; i++) {
					if (circles[i] != null) {
						if (circles[i].isPaused) {
							synchronized (circles[i]) {
								circles[i].notify();
							}
							circles[i].isPaused = false;
						}
					}
				}
				for (int i = 0; i < shoots.size(); i++) {

					if (shoots.get(i).isPaused) {
						synchronized (shoots.get(i)) {
							shoots.get(i).notify();
						}
						shoots.get(i).isPaused = false;
					}

				}
				for (int i = 0; i < spinnerCircles.size(); i++) {
					if (spinnerCircles.get(i).isPaused) {
						synchronized (spinnerCircles.get(i)) {
							spinnerCircles.get(i).notify();
						}
						spinnerCircles.get(i).isPaused = false;
					}
				}

				if (monster1.isAlive()) {
					synchronized (monster1) {
						monster1.notify();

					}
					monster1.isPaused = false;

				}
				if (smartMonster1.isAlive()) {
					synchronized (smartMonster1) {
						smartMonster1.notify();

					}
					smartMonster1.setPaused(false);

				}
				if (multiplayer) {

					if (monster2.isAlive()) {
						synchronized (monster1) {
							monster2.notify();

						}
						monster2.isPaused = false;

					}
					if (smartMonster2.isAlive()) {
						synchronized (smartMonster2) {
							smartMonster2.notify();

						}
						smartMonster2.setPaused(false);

					}
				}

			}

			if (!paused) {
				if (event.getKeyCode() == KeyEvent.VK_E) {
					if (level >= 4 && shoots.size() < countBullets) {
						Shoot shoot = new Shoot(player.x, player.y + 20, 20, 1, GamePanel.this);
						shoots.add(shoot);
						shoot.start();

					}
				}
				if (event.getKeyCode() == KeyEvent.VK_SHIFT) {
					if (level >= 4 && shoots.size() < countBullets) {
						Shoot shoot = new Shoot(player2.x, player2.y + 20, 20, 2, GamePanel.this);
						shoots.add(shoot);
						shoot.start();

					}
				}

				if (event.getKeyCode() == KeyEvent.VK_UP) {
					player2.y = player2.y - 8;
				}
				if (event.getKeyCode() == KeyEvent.VK_DOWN) {
					player2.y = player2.y + 8;
				}
				if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
					player2.x = player2.x + 8;
				}
				if (event.getKeyCode() == KeyEvent.VK_LEFT) {
					player2.x = player2.x - 8;
				}
				if (event.getKeyCode() == KeyEvent.VK_W) {
					player.y = player.y - 8;
				}
				if (event.getKeyCode() == KeyEvent.VK_S) {
					player.y = player.y + 8;
				}
				if (event.getKeyCode() == KeyEvent.VK_D) {
					player.x = player.x + 8;
				}
				if (event.getKeyCode() == KeyEvent.VK_A) {
					player.x = player.x - 8;
				}

			}

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public void hideMouseCursor() {// hides the cursor
		// Transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JPanel.
		setCursor(blankCursor);
	}

	public void setCircles(Circle[] circles) {
		this.circles = circles;
	}

	public static void createMenu() {// creates the menu of the game
		JFrame f = new JFrame("Circle Eater by Daniela Skums");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1024, 700);
		f.setResizable(false);
		f.setVisible(true);
		f.setFocusable(false);
		f.getContentPane().addMouseListener(new MouseInput());

		Menu menu = new Menu();
		Graphics g = f.getGraphics();
		menu.render(g);

	}

	public static void createGame() {// creates the single player version of the game

		JFrame f = new JFrame("Circle Eater by Daniela Skums");

		multiplayer = false;
		GamePanel gp = new GamePanel();

		f.add(gp);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1024, 700);
		f.setResizable(false);
		f.setVisible(true);
		f.setFocusable(false);

		gp.hideMouseCursor();
		Circle[] arr = gp.circles;
		for (int i = 0; i < arr.length; i++) {
			arr[i].start();
		}

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}

	}

	public static void createMultiplayerGame() {// creates the multiplayer version of the game

		JFrame f = new JFrame("Circle Eater by Daniela Skums");

		multiplayer = true;

		GamePanel gp = new GamePanel();

		f.add(gp);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1024, 700);
		f.setResizable(false);
		f.setVisible(true);
		f.setFocusable(false);

		gp.hideMouseCursor();
		Circle[] arr = gp.circles;
		for (int i = 0; i < arr.length; i++)
			arr[i].start();

		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {// starts the game

		createMenu();
	}

}
