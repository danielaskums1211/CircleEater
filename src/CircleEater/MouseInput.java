package CircleEater;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

		int mx = e.getX();
		int my = e.getY();
		if (mx >= 375 && mx <= 625 && my >= 270 && my <= 340)// checks where the player clicked in order to start the
																// game or exit
		{
			GamePanel.createGame();

		}
		if (mx >= 375 && mx <= 625 && my >= 390 && my <= 460) {
			GamePanel.createMultiplayerGame();
			;

		}
		if (mx >= 375 && mx <= 625 && my >= 510 && my <= 580) {
			System.exit(1);

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
