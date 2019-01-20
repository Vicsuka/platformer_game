package character;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import backgroundLoader.Animate;
import collosionHandle.Coordinate;

public class Enemy {
		// adatok
		protected int HP;
		protected int currHP;
		protected int dmg;
		protected int range;
		protected int movspeed;
		protected int jumpheight;
		protected int x;
		protected int y;
		protected int dx;
		protected int dy;
		public ArrayList<Coordinate> hitbox;
		
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
		
		//animacio tarolasa
		private ArrayList<BufferedImage[]> sprites;
		private Animate animate;
		private Animate lockedAnimation;
			
		//sajat adatok
		//private static final int STANDING = 0;
		private static final int ATTACKING = 1;
		//private static final int DIEING = 2;
		private static final int HITTING = 3;
		private static final int FLYING = 4;
		private int attackAnimation = 0;
		private int hittingAnimation = 0;
		private int currentAction = 0;
		private int prevAction = 0;
		
		public Enemy() {
			HP = 10;
			currHP = 10;
			dmg = 1;
			range = 40;
			movspeed = 1;
			jumpheight = 150;
			
			try {
				// konstrualasa a listanak
				hitbox = new ArrayList<Coordinate>();
				
				// szinten
				sprites = new ArrayList<BufferedImage[]>();
				
				// Osszes animacionak a betoltese, eltaroljuk mindet 
				
				BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1.png"));
				BufferedImage[] stand = {img};
				sprites.add(stand);
				
				BufferedImage img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_attack_001.png"));
				BufferedImage img2 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_attack_002.png"));
				BufferedImage img3 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_attack_003.png"));
				BufferedImage img4;
				BufferedImage[] attack = {img1,img2,img3};
				sprites.add(attack);
				
				img = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_die_001.png"));
				img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_die_002.png"));
				img2 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_die_003.png"));
				img3 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_die_004.png"));
				img4 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_die_005.png"));
				BufferedImage[] die = {img,img1,img2,img3,img4};
				sprites.add(die);
				
				img = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_hit_001.png"));
				img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_hit_002.png"));
				BufferedImage[] hit = {img,img1};
				sprites.add(hit);
				
				img = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_fly_001.png"));
				img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_fly_002.png"));
				img2 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_fly_003.png"));
				img3 = ImageIO.read(getClass().getResourceAsStream("/Characters/Dragon 1/PNG/dragon_1_fly_004.png"));
				BufferedImage[] fly = {img,img1,img2,img3};
				sprites.add(fly);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Alaphelyzetbe helyezes 
			animate =new Animate();
			animate.setFrames(sprites.get(FLYING));
			animate.setDelay(250);
			currentAction = FLYING;
			
			setPosition(290,200);
		}
		
				//pozico
				public void setPosition(int x, int y) {
					this.x = x;
					this.y = y;
				}
				
				//mit csinal a karakter
				public void setAction(int i) {
					prevAction = currentAction;
					currentAction = i;
				}
				
				public boolean isFinished() {
					return lockedAnimation.isFinished();
				}
				
				public void setWalking(boolean b) {
					walking = b;
				}
				public void setRunning(boolean b) {
					running = b;
				}
				public void setJumping(boolean b) {
					jumping = b;
				}
				public void setAttacking(boolean b) {
					attacking = b;
				}
				public void setCrouching(boolean b) {
					crouching = b;
				}
				public void setDying(boolean b) {
					dying = b;
				}
				public void setDamaging(boolean b) {
					damaging = b;
				}
				
				public void setRight(boolean b) {
					walkingRight = b;
				}
				public void setLeft(boolean b) {
					walkingLeft = b;
				}
				
				public int getAction() {
					return currentAction;
				}
				
				public int getPrevAction() {
					return prevAction;
				}
				
				public void move() {
					
				}

				public void update() {
					
					// mozgaslogika
					
					if (damaging) {
						if (currentAction != HITTING) {
							currentAction = HITTING;
							animate.setFrames(sprites.get(HITTING));
							animate.setDelay(60);
							damaging = false;
						}
					} else if (attacking) {
						if (currentAction != ATTACKING) {
						currentAction = ATTACKING;
						animate.setFrames(sprites.get(ATTACKING));
						animate.setDelay(100);
						attacking = false;
						}
					} else {
						if (attackAnimation > 20) {
							attacking = false;
							attackAnimation = 0;
							if (currentAction != FLYING) {
								currentAction = FLYING;
								animate.setFrames(sprites.get(FLYING));
								animate.setDelay(250);
							}
						} else attackAnimation += 1;
						
						
						if (hittingAnimation > 20) {
							damaging = false;
							hittingAnimation = 0;
							if (currentAction != FLYING) {
								currentAction = FLYING;
								animate.setFrames(sprites.get(FLYING));
								animate.setDelay(250);
							}
						} else hittingAnimation += 1;
					}
					
					// animacio frissitese
					animate.update();
				}
				
				// hitbox betoltese
				public void loadHitbox() {
					hitbox.clear();
					int i = x+5;
					int j = y+25;
					for (int hX= i;hX < i+45;hX++)
						for (int hY = j; hY < j+16;hY++) {
							hitbox.add(new Coordinate(hX , hY));
						}
				}
				
				public int getX() {
					return x;
				}
						
				public int getY() {
					return y;
				}
				
				public int getHP() {
					return HP;
				}
				
				public void setHP(int dmg) {
					HP = HP + dmg;
				}
				
				public int getDmg() {
					return dmg;
				}
				
				public void draw(Graphics2D g) {
						
					
						g.drawImage(
							animate.getImage(),
							(int)(x ),
							(int)(y ),
							64,
							64,
							null
						);
					
				}
}
