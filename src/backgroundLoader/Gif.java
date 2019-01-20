 package backgroundLoader;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.*;

import main.GamePanel;


public class Gif {
	
	//pozicio
	private double x;
	private double y;
	
	//kep,mozgas
	private BufferedImage gif;
	private double moveScale;
	
	//konst
	public Gif(String s,double ms) {
		try {
			gif = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//kep visszadasa
	public BufferedImage getImage() {
		return gif;
	}
	
	//pozicio
	public void setPosition(double x, double y) {
		this.x=(x * moveScale) % GamePanel.WIDTH; //Mas kepernyofelbontas miatt
		this.y=(y * moveScale) % GamePanel.HEIGHT;
	}
	
	//kirajzolas
	public void draw (Graphics2D g) {
		g.drawImage(gif, (int)x, (int)y, null);
	}
}
