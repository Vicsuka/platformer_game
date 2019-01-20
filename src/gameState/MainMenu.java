package gameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import backgroundLoader.Background;
import main.GamePanel;

public class MainMenu extends GameState{
	
	//hatter
	private Background bg;
	
	//valasztas
	private int currentChoice = 0;
	private String[] options = { "Start","Players","Quit"};
	
	//megjelentesi szin, font
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	//konstruktor betolti a kepet, megadja a mozgasi sebesseget, szint, fontot
	public MainMenu(GameStateManager gsm) {
		this.gsm = gsm;
		
		try {
			bg = new Background("/Backgrounds/village_004_1920x1080.png",1);
			bg.setVector(-0.5,0);
			titleColor = new Color(240, 0, 0);
			titleFont = new Font("Monospaced Plain", Font.BOLD, 18);
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
		//g.drawString("Random Player Battlegrounds", 30, 60);
		
		g.setFont(font);
		for (int i = 0; i < options.length; i++) {
			if (i==currentChoice) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.CYAN);
			}
			g.drawString(options[i],(int)(GamePanel.WIDTH/2.3), 100 + i * 20);
		}
		
	}
	
	//a entert nyom a felhasznalo akkor kivalaszjuk a modot
	private void select() {
		if (currentChoice == 0) {
			gsm.setState(GameStateManager.NAMESELECT);
		}
		if (currentChoice == 1) {
			gsm.setState(GameStateManager.PLAYERS);
		}
		if (currentChoice == 2) {
			System.exit(0);
		}
	}
	
	// billentyulenyomas kezelese
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			select();
		}
		if (k == KeyEvent.VK_UP) {
			currentChoice--;
			if (currentChoice < 0) {
				currentChoice = options.length - 1;
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if (currentChoice == options.length) {
				currentChoice = 0;
			}
		}
		if (k == KeyEvent.VK_S) {
			gsm.setState(100);
		}
		if (k == KeyEvent.VK_L) {
			gsm.setState(101);
		}
	}
	
	//billentyufelemes kezeles
	public void keyReleased(int k) {
		
	}
}
