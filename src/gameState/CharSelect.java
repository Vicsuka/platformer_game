package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import backgroundLoader.Background;
import backgroundLoader.Gif;

public class CharSelect extends GameState{
		//hatter
		private Background bg;
		private Gif mage;
		private Gif princess;
		private Gif goblin;
		
		//valasztas
		private static final int MAGE = 0;
		private static final int GOBLIN = 2;
		private String[] roles = {"Mage", "Princess", "Goblin"};
		private int currentChoice = 0;
		
		//megjelentesi szin, font
		private Color titleColor;
		private Font titleFont;
		private Font font;
		
		//konstruktor 
		public CharSelect(GameStateManager gsm){
			this.gsm = gsm;
			
			try {
				mage = new Gif("/Characters/Wizard 1/PNG/wizard_1.png",1);
				princess = new Gif("/Characters/Princess/PNG/princess.png",1);
				goblin = new Gif("/Characters/Goblin 2/PNG/goblin_2.png",1);
				bg = new Background("/Backgrounds/main_menu_bg.png",1);
				bg.setVector(0,0);
				titleColor = new Color(0, 0, 0);
				titleFont = new Font("Monospaced Italic", Font.BOLD, 18);
				font = new Font("Monospaced Italic", Font.PLAIN, 12);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void init() {
			
		}
		
		// menu update -> bg update
		public void update() {
			bg.update();
		}
		
		// menu draw -> bg draw + vizsgaljuk eppen melyiket valasztja a felhasznalo
		public void draw(java.awt.Graphics2D g) {
			
			bg.draw(g);
			
			
			g.setColor(titleColor);
			g.setFont(titleFont);
			g.drawString("Playing as: ", 10, 150);
			g.setFont(font);
			g.setColor(Color.RED);
			g.drawString(gsm.player.playerName(),170, 150);
			
			// Ha meg nem valasztott karaktert, most valaszthat
			if(gsm.player.playerType() == null) {
				int distance = 90;
				mage.setPosition(45,50);
				princess.setPosition(40+distance, 45);
				goblin.setPosition(45+distance*2, 50);
				mage.draw(g);
				princess.draw(g);
				goblin.draw(g);
				
				// Az eppeni valasztas jelzese
				for (int i = 0; i <= GOBLIN; i++) {
					if (i==currentChoice) {
						g.setColor(Color.RED);
					} else {
						g.setColor(Color.BLACK);
					}
					g.drawRect(50 + i*distance,50,50,50);
					}
			} else {
				// Ha mar valaszott akkor kiirjuk ki is o
				g.setColor(Color.BLACK);
				g.drawString("Role: " + gsm.player.playerType(), 140, 50);
			}
			
		}
			
		
		
		// billentyulenyomas kezelese
		public void keyPressed(int k) {
			if (k == KeyEvent.VK_ENTER) {
				// Ha tovabblep es uj jatekos akkor tutorialra megy
				if (gsm.player.playerType() == null) {
					gsm.player.setType(roles[currentChoice]);
					gsm.player.setLevel(GameStateManager.TUTORIAL);
					gsm.setState(GameStateManager.TUTORIAL);
				} else {
					// Ha mar jatszott akkor arra a szintre megy ahol tart viszont rekordot nem donthet
					gsm.player.setPoints(0);
					gsm.setState(gsm.player.getLevel());
				}
				
			}
			if (k == KeyEvent.VK_LEFT) {
				currentChoice--;
				if (currentChoice < 0) {
					currentChoice = GOBLIN;
				}
			}
			if (k == KeyEvent.VK_RIGHT) {
				currentChoice++;
				if (currentChoice > GOBLIN) {
					currentChoice = MAGE;
				}
			}
		}
		
		//billentyufelemes kezeles
		public void keyReleased(int k) {
			
		}
	}

