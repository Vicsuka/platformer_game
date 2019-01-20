package backgroundLoader;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

import main.GamePanel;

public class Background {
	
	//maga a kep
	public BufferedImage image;
	
	//pozicio, mozgas
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	//gyorsasag
	private double moveScale;
	
	//konstruktor
	public Background(String s,double ms) {
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//pozicio megadasa
	public void setPosition(double x, double y) {
		this.x=(x * moveScale) % GamePanel.WIDTH; //Mas kepernyofelbontas miatt
		this.y=(y * moveScale) % GamePanel.HEIGHT;
	}	
	
	//gyorsasag beallitas
	public void setVector(double dx, double dy) {
		this.dx=dx;
		this.dy=dy;
	}
	
	//rajzolas with respect of dx,dy
	public void update () {
		x+=dx;
		y+=dy;
	}
	
	//konkret kirajzolas, hatarok nezese
	public void draw (Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
		if (x < 0) {
			g.drawImage(image, (int)x+GamePanel.WIDTH, (int)y, null);
			if (x < -720) this.x=0;
		}
		if (x > 0) {
			g.drawImage(image, (int)x-GamePanel.WIDTH, (int)y, null);
		}
	}
}
