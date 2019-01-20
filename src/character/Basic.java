package character;

import java.util.ArrayList;

import collosionHandle.Coordinate;


public abstract class Basic {

	
	// adatok
	protected int HP;
	protected int MAXHP;
	protected int dmg;
	protected int range;
	protected int movspeed;
	protected int jumpheight;
	protected int x;
	protected int y;
	protected int dx;
	protected int dy;
	
	// hitbox
	public ArrayList<Coordinate> hitbox;
	
	//lovedek
	public Projectile shot;
	
	//allapotvaltozok
	protected boolean walking;
	protected boolean running;
	protected boolean jumping;
	protected boolean attacking;
	public boolean crouching;
	protected boolean dying;
	protected boolean damaging;
	protected boolean falling;
	public boolean startedJumping = false;
	public boolean canJump = true;
	
	protected boolean walkingRight;
	protected boolean walkingLeft;
	
	//allapotvaltozok bellitasa
	public abstract void setWalking(boolean b);
	public abstract void setRunning(boolean b);
	public abstract void setJumping(boolean b);
	public abstract void setAttacking(boolean b);
	public abstract void setCrouching(boolean b);
	public abstract void setDying(boolean b);
	public abstract void setDamaging(boolean b);
	
	public abstract void setRight(boolean b);
	public abstract void setLeft(boolean b);
	
	public abstract void setHP(int dmg);
	
	// hitbox betoltese
	public abstract void loadHitbox(); 
	
	//fuggvenyek
	public abstract boolean isFinished();
	public abstract int getX();
	public abstract int getY();
	public abstract int getHP();
	public abstract int getDmg();
	public abstract int getMAXHP();
	
	// alapfuggvenyek
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void setPosition(int x, int y);
	public abstract void setAction(int i);
	public abstract int getAction();
	public abstract int getPrevAction();
}
