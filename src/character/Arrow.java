package character;

import java.util.ArrayList;

import backgroundLoader.*;
import collosionHandle.Coordinate;

public class Arrow extends Projectile{
	
	// A kep amit majd rajzolunk
	Gif display;
	
	public Arrow(int x, int y) {
		this.x = x + 32;
		this.y = y + 24;
		movspeed = 5;
		
		
		
		try {
			hitbox = new ArrayList<Coordinate>();
			display = new Gif("/Projectiles/myarrow_1.png",1);
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
	
	// frissites...
	public void update() {
		x = x + movspeed;
		display.setPosition(x, y);
	}
	
	// hitbox betoltese, mindig kinullazuk alapbol
	public void loadHitbox() {
		hitbox.clear();
		int i = x;
		int j = y+5;
		for (int hX= i;hX < i+9;hX++)
			for (int hY = j; hY < j+4;hY++) {
				hitbox.add(new Coordinate(hX , hY));
			}
	}
	
	// rajzolas
	public void draw(java.awt.Graphics2D g) {
		g.drawImage(
				display.getImage(),
				(int)(x ),
				(int)(y ),
				16,
				16,
				null
			);
		
	}
	
	// pozicio
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	

}
