package character;

import java.util.ArrayList;

import backgroundLoader.*;
import collosionHandle.Coordinate;

public class Fireball extends Projectile{
	
	Gif display;
	
	public Fireball(int x, int y) {
		this.x = x + 32;
		this.y = y + 24;
		movspeed = 2;
		
		try {
			hitbox = new ArrayList<Coordinate>();
			display = new Gif("/Projectiles/myfireball_002.png",1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void update() {
		x = x + movspeed;
		//display.setPosition(x, y);
	}
	
	public void loadHitbox() {
		hitbox.clear();
		int i = x+3;
		int j = y+3;
		for (int hX= i;hX < i+7;hX++)
			for (int hY = j; hY < j+9;hY++) {
				hitbox.add(new Coordinate(hX , hY));
			}
	}
	
	public void draw(java.awt.Graphics2D g) {
		g.drawImage(
				display.getImage(),
				(int)(x),
				(int)(y),
				16,
				16,
				null
			);
		
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	

}
