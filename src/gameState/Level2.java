package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.*;

import java.util.concurrent.ThreadLocalRandom;

import backgroundLoader.Background;
import character.*;

public class Level2 extends GameState{
			//hatter
			private Background bg;
			
			//jatekos
			private Basic player;
			
			// erme
			private MapObject obj;
			
			// ellenseg
			private Enemy enemy;
			
			// hanyat kelljen megolni
			private int toKill;
			
			// sarkany lovesei gyorsasaga
			private int phase;
			
			//sarkany tamadasa
			private int doAttack;
			
			// tud-e spawnolnie coint
			private boolean canSpawnCoin = true;
			
			// tud-e spawnolni a jatek azaz van e folyamatban ellenseg
			private boolean canSpawnEnemy = true;
			
			// a coin ahogy esik
			private int coinFallSpeed;
			private int coinInc = 3;
			
			// A pontvaltozasok leirasa
			private boolean pointChange = false;
			private int pointAmount;
			
			//lovedekek tarolasa
			private ArrayList<Projectile> projectiles;
			private ArrayList<Projectile> enemyProjectiles;
			
			//megjelentesi szin, font
			private Color color;
			private Font font;
			private Font pointsFont;
			private Font descfont;
			
			//timer
			private int timer;
			private int dots;
			
			//lock
			private boolean pressingLeft;
			private boolean pressingRight;
			
			//shots to come
			private int toDodge;
			
			
			//konstruktor 
			public Level2(GameStateManager gsm){
				this.gsm = gsm;
				projectiles = new ArrayList<Projectile>();
				enemyProjectiles = new ArrayList<Projectile>();
				
				try {
					bg = new Background("/Backgrounds/level2.png",1);
					bg.setVector(0,0);
					
					color = new Color(0, 0, 0);
					font = new Font("Monospaced", Font.BOLD, 13);
					descfont = new Font("Monospaced", Font.ROMAN_BASELINE, 12);
					pointsFont = new Font("Monospaced", Font.CENTER_BASELINE, 10);
					
					// csak ugy, hogy ne legyen null exception rossz esetben
					player = new Wizard();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			// az adott szintnek a szekvenciaja
			public void levelseq() {
				// ha dobhat coint akkor van ra eselye hogy dob
				if (canSpawnCoin) {
					int coinSpawn = ThreadLocalRandom.current().nextInt(1, 50 + 1);
					if (coinSpawn == 27) {
						canSpawnCoin = false;
						coinFallSpeed = 0;
						int coinX = ThreadLocalRandom.current().nextInt(1, 319 + 1);
						int coinY = 1;
						obj = new MapObject(coinX,coinY);
					}
				} else if (obj != null){
					// Ha mar esik lefel coin akkor vizsgalni kell hogy felvette-e a coint
					if (obj != null) {
						playerTakeCoin();
					}
					
					// esik lefele
					coinFallSpeed += 1;
					if (coinFallSpeed == coinInc) {
						obj.setPosition(obj.getX(), obj.getY()+2);
						coinFallSpeed = 0;
					}
					
					// Tulesett a coin
					if (obj.getY() > 250) {
						canSpawnCoin = true;
					}
				}
				// ha tud enemyt spawnolni akkor van ra eselye
				if (canSpawnEnemy) {
					int enemySpawn = ThreadLocalRandom.current().nextInt(1, 100 + 1);
					if (enemySpawn == 27) {
						canSpawnEnemy = false;
						int enemyX = 320;
						int enemyY = 180+phase*5;
						enemy = new Enemy();
						enemy.setPosition(enemyX, enemyY);
					} 
				} else if(enemy != null) {
					// Ha mar van enemy akkor nezni kell hogy ki talal el kit
					if (enemy != null) {
						playerHitEnemy();
						enemyHitPlayer();
					}
					
					// tamadas ciklus
					doAttack += 1;
					if (doAttack%(45*phase) == 0) {
						if (toDodge > 0) {
							enemy.setAttacking(true);
							enemyProjectiles.add(new Iceball(enemy.getX(),enemy.getY()));
							toDodge -= 1;
						}
					}
					
					// Atert a masik oldalra a sarkany
					if (enemy.getX() < -30) {
						if (enemy.getX() > -50) {
							gsm.player.incPoints(-1*15);
							pointChange = true;
							pointAmount = -15;
						}
						canSpawnEnemy = true;
						toKill -= 1;
						enemy = null;
					}
				}
			}
			
			// Annak vizsgalata, hogy a jatekos eltalata-e az ellenseget
			public void playerHitEnemy() {
				int projectilecount = projectiles.size();
				int enemyhitboxsize = enemy.hitbox.size();
								
				for (int i = 0; i < projectilecount; i++) {
					int projectilehitboxsize= projectiles.get(i).hitbox.size();
					for (int j = 0; j < projectilehitboxsize;j++) {
						for (int k = 0; k < enemyhitboxsize; k++) {
							// Ha talalt akkor kivesszuk a lovedeket.
							if (projectiles.get(i).hitbox.get(j).equals(enemy.hitbox.get(k))) {
								projectiles.remove(i);
								if (enemy.getHP() > 0) {
									enemy.setHP(-1*player.getDmg());
									gsm.player.hitPoint();
									// Meghalt-e
									if (enemy.getHP() < 1) {
										enemy.setPosition(-50, 1);
									}
								}
								
								// Azert hogy tuti kilepjen az osszesbol kulunben Exceptiont dobna
								k = 1000;
								j = 1000;
								i = 1000;
								}
						}
					}
				}
			}
			
			// jatekos felvette-e a coint
			public void playerTakeCoin() {
				int playerhitboxsize = player.hitbox.size();
				int coinhitboxsize = obj.hitbox.size();
				
				for (int i = 0; i < playerhitboxsize; i++) {
					for (int j = 0; j < coinhitboxsize; j++) {
						// felvette
						if (player.hitbox.get(i).equals(obj.hitbox.get(j))) {
							obj.setPosition(obj.getX(), 300);
							gsm.player.incPoints(10);
							gsm.player.coinPoint();
							pointChange = true;
							pointAmount = 10;
							i = 1000;
							j = 1000;
						}
						
					}
				}
			}
			
			// Sarkany eltalata-e a jatekost ugyanaz mint a jatekos->sarkanyt
			public void enemyHitPlayer() {
				int playerhitboxsize = player.hitbox.size();
				int projectilecount = enemyProjectiles.size();
				
				for (int i = 0; i < projectilecount; i++) {
					int projectilehitboxsize= enemyProjectiles.get(i).hitbox.size();
					for (int j = 0; j < projectilehitboxsize;j++) {
						for (int k = 0; k < playerhitboxsize; k++) {
							if (enemyProjectiles.get(i).hitbox.get(j).equals(player.hitbox.get(k))) {
								enemyProjectiles.remove(i);
								if (player.getHP() > 0) {
									player.setHP(-1*enemy.getDmg());
									gsm.player.incPoints(-2);
									pointChange = true;
									pointAmount = -2;
									if (player.getHP() < 1) {
										gsm.player.incPoints(-30);
										gsm.player.deathPoint();
										pointChange = true;
										pointAmount = -30;
										gsm.setState(GameStateManager.DEAD);
									}
								}
								// Kilepes..
								k = 1000;
								j = 1000;
								i = 1000;
								}
						}
					}
				}	
			}
			
			
			// Ha tortent pontvaltozas akkor az egyfolytaban megjelenitjuk
			// Ha minusz pont akkor piros, ha plusz akkor zold
			public void pointChange(java.awt.Graphics2D g) {
				if (pointChange) {
					g.setFont(new Font("Monospaced", Font.CENTER_BASELINE, 8));
					if (pointAmount > 0) {
						g.setColor(Color.GREEN);
						g.drawString("+"+Integer.toString(pointAmount), 60, 37);
					} else {
						g.setColor(Color.RED);
						g.drawString(Integer.toString(pointAmount), 60, 37);
					}
				}
				// aktualis pontot mindig kiirjuk
				g.setFont(pointsFont);
				g.setColor(Color.BLACK);
				g.drawString("Score:" + gsm.player.getPoints(), 5, 37);	
			}
			
			// Minden fontosan ki kell tisztiani a palyarol
			public void init() {
				player.setPosition(-30, 180);
				player.setHP(player.getMAXHP());
				toKill = 5;
				projectiles.clear();
				enemyProjectiles.clear();
				enemy = null;
				obj = null;
				
				if (gsm.player.playerType().equals("Mage")) {
					player = new Wizard();
					} else if (gsm.player.playerType().equals("Goblin")) {
						player = new Goblin();
						} else {
							player = new Princess();
						}
				
				canSpawnCoin = true;
				canSpawnEnemy = true;
				
				timer = 300;	
				
				phase = 3;
				doAttack = 0;
				
				toDodge = 10;
			}
			
			
			// mindent lefrissitunk kezdve a szintnek a szekvenciajaval
			// figyelni kell nehogy valamire nullpointerexceptiont kapjunk
			public void update() {
				if (toDodge == 0 && enemyProjectiles.size() == 0) {
					enemy.setPosition(-50, 1);
					phase-=1;
					gsm.player.incPoints(5);
					pointChange = true;
					pointAmount = 5;
					toDodge = 10;
				}
				
				if (phase == 0) {
					toKill = 0;
				}
				
				if (toKill == 0) {
					enemy = null;
					obj = null;
					canSpawnEnemy = false;
					canSpawnCoin = false;
					if (timer%30 == 10) {
						dots = 0;
					}else if(timer%30 == 20) {
						dots = 1;
					}else if (timer%30 == 0) {
						dots = 2;
					}
					if (timer > 0) {
						timer -= 1;
					} else {
						gsm.player.setLevel(GameStateManager.LEVEL3);
						gsm.setState(GameStateManager.LEVEL3);
					}
				}
				
				levelseq();
				
				if (enemy != null) {
				enemy.update();
				enemy.loadHitbox();
				}
				
				if (obj != null) {
					obj.update();
					obj.loadHitbox();
				}
				
				player.update();
				player.loadHitbox();
				for (int i = 0; i < projectiles.size(); i++) {
					projectiles.get(i).update();
					projectiles.get(i).loadHitbox();
				}
				
				for (int i = 0; i < enemyProjectiles.size(); i++) {
					enemyProjectiles.get(i).update();
					enemyProjectiles.get(i).loadHitbox();
				}	
			}
			
			// Kirajzolunk mindent ami nem null
			public void draw(java.awt.Graphics2D g) {
				bg.draw(g);
				
				if (enemy != null) {
					enemy.draw(g);
				}
				
				if (obj != null) {
					obj.draw(g);
				}		
				
				player.draw(g);
				
				// loszerek kirajzolasa es eltavoltitasa ha tulmentek egy hataron
				for (int i = 0; i < projectiles.size(); i++) {
					projectiles.get(i).draw(g);
					if (projectiles.get(i).getX() > 350) {
						gsm.player.incPoints(-1*player.getDmg());
						pointChange = true;
						pointAmount = -1*player.getDmg();
						projectiles.remove(i);
					}
					
					if (gsm.player.playerType().equals("Princess")) {
						for (int j = 0; j < projectiles.get(i).hitbox.size();j++) {
							if (((projectiles.get(i).getX() - (player.getX()+32)) > 10) || ((projectiles.get(i).getX() - (player.getX()+32)) < -10)) {
								gsm.player.incPoints(-1*player.getDmg());
								pointChange = true;
								pointAmount = -1*player.getDmg();
								projectiles.remove(i);
								break;
							}
							// A swing nagyon kis teruleten lehet csak jelen
							if (projectiles.get(i).getY() - player.getY() > 5 || projectiles.get(i).getY() - player.getY() < -5) {
								gsm.player.incPoints(-1*player.getDmg());
								pointChange = true;
								pointAmount = -1*player.getDmg();
								projectiles.remove(i);
								break;
							}
						}
					}
				}
				
				for (int i = 0; i < enemyProjectiles.size(); i++) {
					enemyProjectiles.get(i).draw(g);
					if (enemyProjectiles.get(i).getX() < -40) {
						enemyProjectiles.remove(i);
					}
				}
				
				//leiras
				g.setFont(descfont);
				g.setColor(Color.WHITE);
				g.drawString("Level 2", 130, 15);
				
				
				//hp kiirasa
				g.setFont(font);
				g.setColor(color);
				g.drawString("HP", 20, 10);
				g.setColor(Color.RED);
				g.fillRect(5, 12, 10*player.getHP(), 5);
				
				//pontok
				pointChange(g);
				
				//mennyi van meg hatra...
				if (enemy != null) {
					g.setColor(Color.WHITE);
					g.setFont(descfont);
					g.drawString("Hold on for: " + Integer.toString(toDodge), 110, 25);
				}
				
				//ha van enemy annak a hpjanak kiirasa
				
				
				//loszer megjelenitese
				if (gsm.player.playerType().equals("Mage") || gsm.player.playerType().equals("Goblin")) {
					g.setFont(descfont);
					g.setColor(color);
					g.drawString("Ammo:", 5, 28);
					int ammo = 3 - projectiles.size();
					g.drawString(Integer.toString(ammo), 50, 28);
					
				}
			
				//hitboxmode(g);
				
				//ha vege a szintnek akkor a kovi szintre lepunk
				if (toKill == 0) {
					g.setFont(descfont);
					g.setColor(Color.RED);
					g.drawString("Well done!", 130, 45);
					g.drawString("Proceeding to next level", 60, 55);
					for (int i= dots; i<3;i++) {
						g.drawString(".", 235-i*4, 55);
					}
				}
				
			}
			
			//fixelt hitboxmod
			public void hitboxmode(java.awt.Graphics2D g) {
				
				if (player != null && player.hitbox != null) {
					g.setColor(Color.BLACK);
					for(int i = 0; i < player.hitbox.size();i++) {
						g.fillRect(player.hitbox.get(i).getx(),player.hitbox.get(i).gety(), 1, 1);
					}
				}
				
				if (projectiles != null) {
					for (int i = 0; i < projectiles.size(); i++) {
						g.setColor(Color.LIGHT_GRAY);
						for (int j = 0 ; j < projectiles.get(i).hitbox.size();j++) {
							g.fillRect(projectiles.get(i).hitbox.get(j).getx(), projectiles.get(i).hitbox.get(j).gety(), 1, 1);
						}
					}
				}
				
				if (enemyProjectiles != null) {
					for (int i = 0; i < enemyProjectiles.size(); i++) {
						g.setColor(Color.LIGHT_GRAY);
						for (int j = 0 ; j < enemyProjectiles.get(i).hitbox.size();j++) {
							g.fillRect(enemyProjectiles.get(i).hitbox.get(j).getx(),enemyProjectiles.get(i).hitbox.get(j).gety(), 1, 1);
						}
					}
				}
				
				if (enemy != null) {
					g.setColor(Color.BLUE);
					for (int i = 0; i < enemy.hitbox.size();i++) {
						g.fillRect(enemy.hitbox.get(i).getx(), enemy.hitbox.get(i).gety(), 1, 1);
					}
				}
				
				if (obj != null) {
					g.setColor(Color.YELLOW);
					for (int i = 0; i < obj.hitbox.size();i++) {
						g.fillRect(obj.hitbox.get(i).getx(), obj.hitbox.get(i).gety(), 1, 1);
					}
				}
				
			}
			
			
			// billentyulenyomas kezelese
			public void keyPressed(int k) {
				if (k == KeyEvent.VK_ENTER) {
					gsm.setState(GameStateManager.MENU);
				}
				if(k == KeyEvent.VK_LEFT) {
					pressingLeft = true;
					player.setWalking(false);
					player.setWalking(true);
					player.setLeft(true);
					player.setRight(false);
				}
				if(k == KeyEvent.VK_RIGHT) {
					pressingRight = true;
					player.setWalking(false);
					player.setWalking(true);
					player.setLeft(false);
					player.setRight(true);
				}
				if(k == KeyEvent.VK_UP) {
					player.setJumping(true);					
				}
				if(k == KeyEvent.VK_DOWN) player.setCrouching(true);
				if(k == KeyEvent.VK_SPACE) { 
					player.setAttacking(true);					
				}
				if(k == KeyEvent.VK_SHIFT) player.setRunning(true);
			}
			
			//billentyufelemes kezeles
			public void keyReleased(int k) {
				if(k == KeyEvent.VK_LEFT) {
					pressingLeft = false;
					if (!pressingRight) {
						player.setWalking(false);
					}
				}
				if(k == KeyEvent.VK_RIGHT)  {
					pressingRight = false;
					if (!pressingLeft) {
					 player.setWalking(false);
					}
					
				}
				if(k == KeyEvent.VK_UP) player.setJumping(false);
				if(k == KeyEvent.VK_DOWN) player.setCrouching(false);
				if(k == KeyEvent.VK_SPACE) {
					player.setCrouching(false);
					int x = player.getX();
					int y = player.getY();
					player.setAttacking(false);
					gsm.player.attackPoint();
					if (gsm.player.playerType().equals("Mage")) {
						if (projectiles.size() < 3) {
							projectiles.add(new Fireball(x,y));
						}
						} else if (gsm.player.playerType().equals("Goblin")) {
							if (projectiles.size() < 3) {
								projectiles.add(new Arrow(x,y));
							}
							} else {
								projectiles.add(new Swing(x,y));
							}
				}
				if(k == KeyEvent.VK_SHIFT) player.setRunning(false);
			}
}

