package CircleEater;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Menu {
	public Rectangle splayButton = new Rectangle(375, 270, 250, 70);// rectangle of single player button
	public Rectangle mplayButton = new Rectangle(375, 390, 250, 70);// rectangle of multiplayer button
	public Rectangle quitButton = new Rectangle(375, 510, 250, 70);// rectangle of exit

	public void render(Graphics g)// creates how the menu will look
	{
		Graphics2D g2d = (Graphics2D) g;

		Image img = new ImageIcon("CircleEater.jpg").getImage();
		g.drawImage(img, 0, 0, 1024, 700, null);
		g2d.setColor(Color.black);

		g.fillRect(375, 270, 250, 70);
		g.fillRect(375, 390, 250, 70);
		g.fillRect(375, 510, 250, 70);

		Font fnt0 = new Font("Century", Font.BOLD, 80);
		g.setFont(fnt0);
		g.drawString("Circle Eater", 245, 200);
		g2d.setColor(Color.white);

		Font fnt1 = new Font("Serif", Font.BOLD, 29);
		g.setFont(fnt1);
		g2d.draw(splayButton);
		g.drawString("Single Game", splayButton.x + 43, splayButton.y + 45);
		g2d.draw(mplayButton);
		g.drawString("Multiplayer Game", mplayButton.x + 12, mplayButton.y + 46);
		g2d.draw(quitButton);
		g.drawString("Quit", quitButton.x + 92, quitButton.y + 46);

	}

}
