package character;

import java.util.ArrayList;

import collosionHandle.Coordinate;

public abstract class Projectile {
	
	protected int dmg;
	protected int movspeed;
	protected int x;
	protected int y;
	public ArrayList<Coordinate> hitbox;
	
	public abstract int getX();
	public abstract int getY();
	public abstract void loadHitbox();
	
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void setPosition(int x, int y);

}
