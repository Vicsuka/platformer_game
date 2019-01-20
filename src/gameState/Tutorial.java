package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.*;

import backgroundLoader.Background;
import character.*;

public class Tutorial extends GameState{
			//hatter
			private Background bg;
			
			//jatekos
			public Basic player;
			private MapObject obj;
			private Enemy enemy;
			
			//lovedekek
			private ArrayList<Projectile> projectiles;
			
			//megjelentesi szin, font
			private Color color;
			private Font font;
			private Font descfont;
			
			//lock
			private boolean pressingLeft;
			private boolean pressingRight;
			
			//konstruktor 
			public Tutorial(GameStateManager gsm){
				this.gsm = gsm;
				projectiles = new ArrayList<Projectile>();
				
				try {
					bg = new Background("/Backgrounds/level.png",1);
					bg.setVector(0,0);
					
					color = new Color(0, 0, 0);
					font = new Font("Monospaced", Font.BOLD, 13);
					descfont = new Font("Monospaced", Font.ROMAN_BASELINE, 12);
					
					player = new Wizard();
					
					obj = new MapObject(70,150);
					
					enemy = new Enemy();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			// alaphelyzet
			public void init() {
				gsm.player.setPoints(0);
				projectiles.clear();	
				
				if (gsm.player.playerType().equals("Mage")) {
					player = new Wizard();
					} else if (gsm.player.playerType().equals("Goblin")) {
						player = new Goblin();
						} else {
							player = new Princess();
						}
				
			}
			
			// mindent updatelunk
			public void update() {
				bg.update();
				obj.update();
				obj.loadHitbox();
				player.update();
				player.loadHitbox();
				enemy.update();
				enemy.loadHitbox();
				for (int i = 0; i < projectiles.size(); i++) {
					projectiles.get(i).update();
					projectiles.get(i).loadHitbox();
				}
				
				// eltalaja-e a jatekos a sarkanyt 
				
				int projectilecount = projectiles.size();
				int enemyhitboxsize = enemy.hitbox.size();
				
				for (int i = 0; i < projectilecount; i++) {
					int projectilehitboxsize= projectiles.get(i).hitbox.size();
					for (int j = 0; j < projectilehitboxsize;j++) {
						for (int k = 0; k < enemyhitboxsize; k++) {
							if (projectiles.get(i).hitbox.get(j).equals(enemy.hitbox.get(k))) {
								projectiles.clear();
								gsm.player.setLevel(GameStateManager.LEVEL1);
								//gsm.player.setPlayer(player);
								gsm.setState(GameStateManager.LEVEL1);
								k = 1000;
								j = 1000;
								i = 1000;
								}
						}
					}
				}
				
				
			}
			
			// menu draw -> bg draw
			public void draw(java.awt.Graphics2D g) {
			
				bg.draw(g);
				player.draw(g);
				obj.draw(g);
				// loszerek kilovese
				for (int i = 0; i < projectiles.size(); i++) {
					projectiles.get(i).draw(g);
					if (projectiles.get(i).getX() > 350) {
						projectiles.remove(i);
					}
					
					if (gsm.player.playerType().equals("Princess")) {
						for (int j = 0; j < projectiles.get(i).hitbox.size();j++) {
							if (((projectiles.get(i).getX() - (player.getX()+32)) > 10) || ((projectiles.get(i).getX() - (player.getX()+32)) < -10)) {
								projectiles.remove(i);
								break;
							}
							if (projectiles.get(i).getY() - player.getY() > 5 || projectiles.get(i).getY() - player.getY() < -5) {
								projectiles.remove(i);
								break;
							}
						}
					}
				}
				
				//leiras
				g.setFont(descfont);
				g.setColor(Color.WHITE);
				g.drawString("Take these", 57, 148);
				
				g.drawString("Kill these", 190, 148);
				enemy.setPosition(190, 141);
				enemy.draw(g);
				
				
				//hp kiirasa
				g.setFont(font);
				g.setColor(color);
				g.drawString("HP", 20, 10);
				g.setColor(Color.RED);
				g.fillRect(5, 12, 10*player.getHP(), 5);
				
				
				//loszer megjelenitese
				if (gsm.player.playerType().equals("Mage") || gsm.player.playerType().equals("Goblin")) {
					g.setColor(color);
					g.drawString("Ammo: ", 5, 30);
					int ammo = 3 - projectiles.size();
					g.drawString(Integer.toString(ammo), 50, 30);
					
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
