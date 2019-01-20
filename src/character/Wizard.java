package character;

import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.awt.*;

import backgroundLoader.Animate;
import collosionHandle.Coordinate;

@SuppressWarnings("serial")
public class Wizard extends Basic implements Serializable{

		//animacio tarolasa
		private ArrayList<BufferedImage[]> sprites;
		private Animate animate;
		private Animate lockedAnimation;
		
		//sajat adatok
		private static final int STANDING = 0;
		private static final int ATTACKING = 1;
		private static final int CROUCHING = 2;
		private static final int DIEING = 3;
		private static final int HITTING = 4;
		private static final int JUMPING = 5;
		private static final int RUNNING = 6;
		private static final int WALKING = 7;
		private int currentAction = 0;
		private int prevAction = 0;
		
		//konstrutkor, osszes animacio betoltese
		public Wizard() {
			HP = 4;
			MAXHP = 4;
			dmg = 3;
			range = 40;
			movspeed = 1;
			jumpheight = 125;
			
			try {
				
				hitbox = new ArrayList<Coordinate>();
				
				sprites = new ArrayList<BufferedImage[]>();
				
				BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1.png"));
				BufferedImage[] stand = {img};
				sprites.add(stand);
				
				BufferedImage img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_attack_001.png"));
				BufferedImage img2 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_attack_002.png"));
				BufferedImage img3 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_attack_003.png"));
				BufferedImage img4 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_attack_004.png"));
				BufferedImage[] attack = {img1,img2,img3,img4};
				sprites.add(attack);
				
				img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_crouch_001.png"));
				img2 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_crouch_002.png"));
				BufferedImage[] crouch = {img2};
				sprites.add(crouch);
				
				img = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_die_001.png"));
				img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_die_002.png"));
				img2 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_die_003.png"));
				img3 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_die_004.png"));
				img4 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_die_005.png"));
				BufferedImage[] die = {img,img1,img2,img3,img4};
				sprites.add(die);
				
				img = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_hit_001.png"));
				img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_hit_002.png"));
				BufferedImage[] hit = {img,img1};
				sprites.add(hit);
				
				img = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_jump_001.png"));
				img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_jump_002.png"));
				img2 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_jump_003.png"));
				img3 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_jump_004.png"));
				BufferedImage[] jump = {img,img1};
				sprites.add(jump);
				
				img = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_run_001.png"));
				img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_run_002.png"));
				img2 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_run_003.png"));
				img3 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_run_004.png"));
				img4 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_run_005.png"));
				BufferedImage img5 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_run_006.png"));
				BufferedImage img6 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_run_007.png"));
				BufferedImage img7 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_run_008.png"));
				BufferedImage[] run = {img,img1,img2,img3,img4,img5,img6,img7};
				sprites.add(run);
				
				img = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_walk_001.png"));
				img1 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_walk_002.png"));
				img2 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_walk_003.png"));
				img3 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_walk_004.png"));
				img4 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_walk_005.png"));
				img5 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_walk_006.png"));
				img6 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_walk_007.png"));
				img7 = ImageIO.read(getClass().getResourceAsStream("/Characters/Wizard 1/PNG/wizard_1_walk_008.png"));
				BufferedImage[] walk = {img,img1,img2,img3,img4,img5,img6,img7};
				sprites.add(walk);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			animate =new Animate();
			animate.setFrames(sprites.get(STANDING));
			animate.setDelay(1000);
			
			setPosition(-30, 180);
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
		
public void move () {
		
			
			if ((jumping || startedJumping) && canJump) {				
				if ((!startedJumping)) {
					canJump = false;
					startedJumping = true;
				}
				
				if (startedJumping) {
					if (dy < jumpheight/3) {
						y -= 2;
						dy++;		
					} else {
						startedJumping = false;
						falling = true;
						dy = 1;
					}
				}
			} else if(falling) {
				if (y<180) {
					y += 2;
				}else {
					falling = false;
					canJump = true;
				}
			}
			
			if (walking && !crouching && !attacking && !jumping ) {
				
				if (running && !falling) {
					if (movspeed < 2) {
						movspeed = movspeed +1 ;
					} else movspeed = 1;
				} else movspeed = 1;
				
				if (walkingLeft) {
					if (x < -30) {
						x = -30;
					}
					else {
						x = x - movspeed;
					}
				} else if (walkingRight) {
					if (x > 280) {
						x = 280;
						}
					else {
						x = x + movspeed;
					}
				}
			}
		}

		public void loadHitbox() {
			//hitbox
			hitbox.clear();
			if (crouching) {
				int i = x+20;
				int j = y+25;
				for (int hX= i;hX < i+20;hX++)
					for (int hY = j; hY < j+20;hY++) {
						hitbox.add(new Coordinate(hX , hY));
					}
			} else {
				int i = x+20;
				int j = y+20;
				for (int hX= i;hX < i+20;hX++)
					for (int hY = j; hY < j+25;hY++) {
						hitbox.add(new Coordinate(hX , hY));
					}
			}
		}
		
		//melyik allapotban van es ilyenkor melyik animaciot kell lejatszani
		public void update() {
			
			move();
			setPosition(x,y);
			
			if (attacking) {
				if (currentAction != ATTACKING) {
				currentAction = ATTACKING;
				animate.setFrames(sprites.get(ATTACKING));
				animate.setDelay(60);
				}
			} else if (crouching) {
				if (currentAction != CROUCHING) {
				currentAction = CROUCHING;
				animate.setFrames(sprites.get(CROUCHING));
				animate.setDelay(400);
				}
			} else if (dying) {
				if (currentAction != DIEING) {
				currentAction = DIEING;
				animate.setFrames(sprites.get(DIEING));
				animate.setDelay(60);
				}
			} else if (damaging) {
				if (currentAction != HITTING) {
				currentAction = HITTING;
				animate.setFrames(sprites.get(HITTING));
				animate.setDelay(40);
				}
			} else if (jumping) {
				if (currentAction != JUMPING) {
				currentAction = JUMPING;
				animate.setFrames(sprites.get(JUMPING));
				animate.setDelay(100);
				}
			} else if (walking && !falling && !jumping && !startedJumping) {
					if (running) {
						if (currentAction != RUNNING) {
							currentAction = RUNNING;
							animate.setFrames(sprites.get(RUNNING));
							animate.setDelay(100);
						}
					} else if (currentAction != WALKING) {
						currentAction = WALKING;
						animate.setFrames(sprites.get(WALKING));
						animate.setDelay(80);
					}
				
			} else {
				if (y<180) falling = true; 
				if (currentAction != STANDING) {
				currentAction = STANDING;
				animate.setFrames(sprites.get(STANDING));
				animate.setDelay(1000);
				}
			}
			
			if (jumping || dying || damaging) {
				lockedAnimation = animate;
				if (animate.isFinished()) {
					animate.setFrames(sprites.get(STANDING));
					animate.setDelay(1000);
					
					
				} else {
					
				}
			}
			
			animate.update();
		}
		
		//x,y,hp..
		public int getX() {
			return this.x;
		}
				
		public int getY() {
			return this.y;
		}
		
		public int getHP() {
			return this.HP;
		}
		
		public int getDmg() {
			return this.dmg;
		}
		
		public void setHP(int dmg) {
			this.HP = this.HP+dmg;
		}
		
		public int getMAXHP() {
			return MAXHP;
		}
		//kirajzolasa
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