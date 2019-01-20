package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.*;

import java.util.concurrent.ThreadLocalRandom;

import backgroundLoader.Background;
import character.*;

public class Level4 extends GameState{
			//hatter
			private Background bg;
			
			//jatekos
			private Basic player;
			
			// erme
			private MapObject obj;
			
			// ellenseg
			private ArrayList<Enemy> enemies;
			
			// hanyat kelljen megolni
			private int toKill;
			
			//tamadasi ciklus
			private int doAttack;
			
			//sarkany loveseinek gyorsasaga
			private int phase;
			
			//sarkany repules
			private int flyingSpeed;
			private int flyingInc = 3;
			
			// tud-e spawnolni a jatek azaz van e folyamatban ellenseg
			private boolean canSpawnEnemy = true;
			
			// tud-e spawnolnie coint
			private boolean canSpawnCoin = true;
			
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
			
			//lock
			private boolean pressingLeft;
			private boolean pressingRight;
			
			
			//konstruktor 
			public Level4(GameStateManager gsm){
				this.gsm = gsm;
				projectiles = new ArrayList<Projectile>();
				enemyProjectiles = new ArrayList<Projectile>();
				enemies = new ArrayList<Enemy>();
				
				try {
					bg = new Background("/Backgrounds/level4.png",1);
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
					int coinSpawn = ThreadLocalRandom.current().nextInt(1, 500 + 1);
					if (coinSpawn == 127) {
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
						flyingSpeed = 0;
						int enemyX = 320;
						int enemyY = 180;
						enemies.add(new Enemy());
						enemies.add(new Enemy());
						enemies.add(new Enemy());
						for(int i = 0; i < enemies.size();i++)
							enemies.get(i).setPosition(enemyX, enemyY-30*i);
					} 
				} else if(enemies.size() != 0) {
					// Ha mar van enemy akkor nezni kell hogy ki talal el kit
					if (enemies.size() != 0) {
						playerHitEnemy();
						enemyHitPlayer();
					}
					
					//sarkanyok repulnek
					flyingSpeed += 1;
					if (flyingSpeed == flyingInc) {
						for(int i = 0; i < enemies.size();i++) {
							enemies.get(i).setPosition(enemies.get(i).getX()-1,enemies.get(i).getY());
						}
						flyingSpeed = 0;
					}
					
					
					//tamadasok
					doAttack += 1;
					
					if (doAttack%(102*phase) == 0 && (enemies.size() != 0)) {
							enemies.get(0).setAttacking(true);
							enemyProjectiles.add(new Iceball(enemies.get(0).getX(),enemies.get(0).getY()));
					}
					
					if (doAttack%(49*phase) == 0 && (enemies.size() > 1)) {
						enemies.get(1).setAttacking(true);
						enemyProjectiles.add(new Iceball(enemies.get(1).getX(),enemies.get(1).getY()));
					}
					
					if (doAttack%(31*phase) == 0 && (enemies.size() > 2)) {
						enemies.get(2).setAttacking(true);
						enemyProjectiles.add(new Iceball(enemies.get(2).getX(),enemies.get(2).getY()));
					}
					
					// Atert a masik oldalra a sarkany
					for (int i = 0; i < enemies.size();i++) {
						if (enemies.get(i).getX() < -30) {
							if (enemies.get(i).getX() > -50) {
								gsm.player.incPoints(-1*15);
								pointChange = true;
								pointAmount = -15;
							}
							toKill -= 1;
							enemies.remove(i);
						}
					}
				}
			}
			
			// Annak vizsgalata, hogy a jatekos eltalata-e az ellenseget
			public void playerHitEnemy() {
				
				for (int q = 0; q < enemies.size();q++) {
					//////////////
					int projectilecount = projectiles.size();
					int enemyhitboxsize = enemies.get(q).hitbox.size();
									
					for (int i = 0; i < projectilecount; i++) {
						int projectilehitboxsize= projectiles.get(i).hitbox.size();
						for (int j = 0; j < projectilehitboxsize;j++) {
							for (int k = 0; k < enemyhitboxsize; k++) {
								// Ha talalt akkor kivesszuk a lovedeket.
								if (projectiles.get(i).hitbox.get(j).equals(enemies.get(q).hitbox.get(k))) {
									projectiles.remove(i);
									if (enemies.get(q).getHP() > 0) {
										enemies.get(q).setHP(-1*player.getDmg());
										gsm.player.hitPoint();
										// Meghalt-e
										if (enemies.get(q).getHP() < 1) {
											enemies.get(q).setPosition(-50, 1);
											gsm.player.incPoints(15);
											gsm.player.killPoint();
											pointChange = true;
											pointAmount = 15;
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
					/////////////////////
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
									player.setHP(-1*enemies.get(0).getDmg());
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
				canSpawnCoin = true;
				canSpawnEnemy = true;
				player.setPosition(-30, 180);
				player.setHP(player.getMAXHP());
				timer = 300;
				phase = 3;
				doAttack = 0;
				toKill = 10;
				projectiles.clear();
				enemyProjectiles.clear();
				enemies.clear();
				obj = null;
				
				if (gsm.player.playerType().equals("Mage")) {
					player = new Wizard();
					} else if (gsm.player.playerType().equals("Goblin")) {
						player = new Goblin();
						} else {
							player = new Princess();
						}
			}
			
			
			// mindent lefrissitunk kezdve a szintnek a szekvenciajaval
			// figyelni kell nehogy valamire nullpointerexceptiont kapjunk
			public void update() {
				
				if (enemies.size() == 0 && !canSpawnEnemy) {
					canSpawnEnemy = true;
					phase -= 1;		
				}
				
				if (phase == 0) {
					toKill = 0;
				}
				
				if (toKill == 0) {
					enemies.clear();
					obj = null;
					canSpawnEnemy = false;
					canSpawnCoin = false;
					if (timer > 0) {
						timer -= 1;
					} else {
						gsm.player.setLevel(GameStateManager.TUTORIAL);
						gsm.setState(GameStateManager.MENU);
					}
				}
				
				levelseq();
				
				if (enemies.size() != 0) {
					for (int i = 0; i < enemies.size();i++) {
						enemies.get(i).update();
						enemies.get(i).loadHitbox();
					}
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
				
				if (enemies.size() != 0) {
					for (int i = 0; i < enemies.size();i++) {
						enemies.get(i).draw(g);
					}
				}
				
				if (obj != null) {
					obj.draw(g);
				}		
				
				player.draw(g);
				
				// loszerek kirajzolasa es eltavoltitasa ha tulmentek egy hataron
				for (int i = 0; i < projectiles.size(); i++) {
					projectiles.get(i).draw(g);
					if (projectiles.get(i).getX() - player.getX() > 360) {
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
				g.setColor(Color.BLACK);
				g.drawString("Final Level", 130, 15);
				
				
				//hp kiirasa
				g.setFont(font);
				g.setColor(color);
				g.drawString("HP", 20, 10);
				g.setColor(Color.RED);
				g.fillRect(5, 12, 10*player.getHP(), 5);
				
				//pontok
				pointChange(g);
				
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
					g.setColor(Color.BLACK);
					g.drawString("Congratulations!", 100, 45);
					g.drawString("You won the game!", 100, 55);
					if (phase == 0){
						gsm.player.winPoint();
						gsm.player.incPoints(100);
						pointChange = true;
						pointAmount = 100;
						if (gsm.player.getPoints() > gsm.player.getRecord()) {
							gsm.player.setRecord(gsm.player.getPoints());
						}
						gsm.player.setPoints(0);
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
				
				if (enemies.size() != 0) {
					g.setColor(Color.BLUE);
					for (int i = 0; i < enemies.size();i++) {
						for (int j = 0; j < enemies.get(i).hitbox.size();j++) {
							g.fillRect(enemies.get(i).hitbox.get(j).getx(), enemies.get(i).hitbox.get(j).gety(), 1, 1);
						}
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

