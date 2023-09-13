package CircleEater;

import javax.swing.JOptionPane;

public class NextLevelCheck {

	public void checkNextLevel(GamePanel panel) {// checks the state of the game and if a new level should be started
		if (Circle.counter == panel.numOfBalls && panel.level == 1)// level 2
		{

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (GamePanel.seconds >= 5 && !GamePanel.multiplayer)// time bonus
				GamePanel.score1 += 5;
			else if (GamePanel.seconds >= 5 && GamePanel.multiplayer) {
				GamePanel.score1 += 3;
				GamePanel.score2 += 3;

			}

			String audioFilePath = "levelUp.wav";
			AudioPlayer audioplayer = new AudioPlayer();
			audioplayer.play(audioFilePath);

			JOptionPane.showMessageDialog(panel, "Good Job! Press OK to start level 2");

			panel.level = panel.level + 1;
			Circle.counter = 0;
			panel.numOfBalls = 10;
			panel.player.diameter = 60;
			if (GamePanel.multiplayer)
				panel.player2.diameter = 60;
			GamePanel.seconds = 8;

			panel.setCircles(new Circle[panel.numOfBalls]);
			for (int i1 = 0; i1 < panel.numOfBalls; i1++) {
				int x = (i1 + 1) * 90;
				int y = (int) (Math.random() * 600);
				int rad = (int) (Math.random() * 50) + 20;
				panel.circles[i1] = new Circle(x, y, rad, 0, panel);
				panel.circles[i1].start();

			}

		}
		if (Circle.counter == panel.numOfBalls && panel.level == 2)// level 3
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (GamePanel.seconds >= 4 && !GamePanel.multiplayer)// time bonus
				GamePanel.score1 += 5;
			else if (GamePanel.seconds >= 4 && GamePanel.multiplayer) {
				GamePanel.score1 += 3;
				GamePanel.score2 += 3;

			}
			String audioFilePath = "levelUp.wav";
			AudioPlayer audioplayer = new AudioPlayer();
			audioplayer.play(audioFilePath);
			JOptionPane.showMessageDialog(panel, "Good Job! Press OK to start level 3");

			panel.level = panel.level + 1;
			Circle.counter = 0;
			panel.numOfBalls = 10;
			panel.player.diameter = 60;
			if (GamePanel.multiplayer)
				panel.player2.diameter = 60;
			GamePanel.seconds = 15;

			panel.setCircles(new Circle[panel.numOfBalls]);
			for (int i1 = 0; i1 < panel.numOfBalls; i1++) {
				int x = (i1 + 1) * 90;
				int y = (int) (Math.random() * 600);
				int rad = (int) (Math.random() * 50) + 20;
				panel.circles[i1] = new Circle(x, y, rad, 0, panel);
				panel.circles[i1].start();

			}

		}
		if (Circle.counter == panel.numOfBalls && panel.level == 3)// level 4
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (GamePanel.seconds >= 7 && !GamePanel.multiplayer)// time bonus
				GamePanel.score1 += 5;
			else if (GamePanel.seconds >= 7 && GamePanel.multiplayer) {
				GamePanel.score1 += 3;
				GamePanel.score2 += 3;

			}
			String audioFilePath = "levelUp.wav";
			AudioPlayer audioplayer = new AudioPlayer();
			audioplayer.play(audioFilePath);
			if (GamePanel.multiplayer)
				JOptionPane.showMessageDialog(panel,
						"Good Job! Press OK to start level 4 and click on 'E' and shift to reveal a new skill");
			else
				JOptionPane.showMessageDialog(panel,
						"Good Job! Press OK to start level 4 and click on the mouse to reveal a new skill!");

			panel.level = panel.level + 1;
			Circle.counter = 0;
			panel.numOfBalls = 10;
			panel.player.diameter = 60;
			GamePanel.seconds = 20;

			panel.setCircles(new Circle[panel.numOfBalls]);
			for (int i1 = 0; i1 < panel.numOfBalls; i1++) {
				int x = (i1 + 1) * 90;
				int y = (int) (Math.random() * 600);
				int rad = (int) (Math.random() * 50) + 20;
				panel.circles[i1] = new Circle(x, y, rad, 0, panel);
				panel.circles[i1].start();
			}

			panel.monster1.start();
			if (GamePanel.multiplayer) {
				panel.player2.diameter = 60;
				panel.monster2.start();
			}

		}
		if (Circle.counter == panel.numOfBalls && panel.level == 4)// level 5
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (GamePanel.seconds >= 10 && !GamePanel.multiplayer)// time bonus
				GamePanel.score1 += 5;
			else if (GamePanel.seconds >= 10 && GamePanel.multiplayer) {
				GamePanel.score1 += 3;
				GamePanel.score2 += 3;

			}
			String audioFilePath = "levelUp.wav";
			AudioPlayer audioplayer = new AudioPlayer();
			audioplayer.play(audioFilePath);
			JOptionPane.showMessageDialog(panel, "Good Job! Press OK to start level 5");

			panel.level = panel.level + 1;
			Circle.counter = 0;
			Monster newMonster = new Monster(60, 200, 85, panel, panel.player);
			panel.monster1 = newMonster;
			panel.numOfBalls = 10;
			panel.player.diameter = 60;
			GamePanel.seconds = 20;

			panel.setCircles(new Circle[panel.numOfBalls]);
			for (int i1 = 0; i1 < panel.numOfBalls; i1++) {
				int x = (i1 + 1) * 90;
				int y = (int) (Math.random() * 600);
				int rad = (int) (Math.random() * 50) + 20;
				panel.circles[i1] = new Circle(x, y, rad, 0, panel);
				panel.circles[i1].start();

			}
			int x = 90;
			int y = (int) (Math.random() * 600);
			int rad = (int) (Math.random() * 50) + 20;
			Circle c1 = new Circle(x, y, rad, 1, panel);
			panel.spinnerCircles.add(c1);
			c1.start();

			x = 150;
			y = (int) (Math.random() * 600);
			rad = (int) (Math.random() * 50) + 20;
			Circle c2 = new Circle(x, y, rad, 1, panel);
			panel.spinnerCircles.add(c2);
			c2.start();

			newMonster.start();

			if (GamePanel.multiplayer) {
				panel.player2.diameter = 60;
				Monster newMonster2 = new Monster(40, 200, 85, panel, panel.player2);
				panel.monster2 = newMonster2;
				newMonster2.start();

			}

		}
		if (Circle.counter == panel.numOfBalls && panel.spinnerCircles.size() == 0 && panel.level == 5)// level 6
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (GamePanel.seconds >= 10 && !GamePanel.multiplayer)// time bonus
				GamePanel.score1 += 5;
			else if (GamePanel.seconds >= 10 && GamePanel.multiplayer) {
				GamePanel.score1 += 3;
				GamePanel.score2 += 3;

			}
			String audioFilePath = "levelUp.wav";
			AudioPlayer audioplayer = new AudioPlayer();
			audioplayer.play(audioFilePath);
			JOptionPane.showMessageDialog(panel, "Good Job! Press OK to start level 6");

			panel.level = panel.level + 1;
			Circle.counter = 0;
			panel.numOfBalls = 20;
			panel.player.diameter = 60;
			GamePanel.seconds = 15;

			panel.setCircles(new Circle[panel.numOfBalls]);
			for (int i1 = 0; i1 < panel.numOfBalls; i1++) {
				int x = (i1 + 1) * 90;
				int y = (int) (Math.random() * 600);
				int rad = (int) (Math.random() * 50) + 20;
				panel.circles[i1] = new Circle(x, y, rad, 0, panel);
			}
			for (int i1 = 0; i1 < panel.circles.length; i1++) {
				panel.circles[i1].start();
			}

			panel.smartMonster1.start();
			if (GamePanel.multiplayer) {
				panel.player2.diameter = 60;
				panel.smartMonster2.start();

			}

		}
		if (Circle.counter == panel.numOfBalls && panel.level == 6) {// summary of the scores
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (GamePanel.seconds >= 7 && !GamePanel.multiplayer)// time bonus
				GamePanel.score1 += 5;
			else if (GamePanel.seconds >= 7 && GamePanel.multiplayer) {
				GamePanel.score1 += 3;
				GamePanel.score2 += 3;

			}
			String audioFilePath = "levelUp.wav";
			AudioPlayer audioplayer = new AudioPlayer();
			audioplayer.play(audioFilePath);
			if (GamePanel.multiplayer) {
				if (GamePanel.score1 > GamePanel.score2)
					JOptionPane.showMessageDialog(panel, "Good Job! player 1 won with the score:" + GamePanel.score1);
				else if (GamePanel.score1 < GamePanel.score2)
					JOptionPane.showMessageDialog(panel, "Good Job! player 2 won with the score:" + GamePanel.score2);
				else
					JOptionPane.showMessageDialog(panel, "There's a tie");

			} else
				JOptionPane.showMessageDialog(panel, "Good Job! You won!");

			System.exit(1);

		}

	}

}
