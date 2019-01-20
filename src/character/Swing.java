package character;

import java.util.ArrayList;

import collosionHandle.Coordinate;


public class Swing extends Projectile{
	
	public Swing(int x, int y) {
		this.x = x+32;
		this.y = y;
		movspeed = 4;
		
		try {
			hitbox = new ArrayList<Coordinate>();
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
	}
	
	
	public void loadHitbox() {
		hitbox.clear();
		int i = x;
		int j = y+17;
		for (int hX= i;hX < i+6;hX++)
			for (int hY = j; hY < j+15;hY++) {
				hitbox.add(new Coordinate(hX , hY));
			}
	}
	
	public void draw(java.awt.Graphics2D g) { }

	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;	
	}
}

