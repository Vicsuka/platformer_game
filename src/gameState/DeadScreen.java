package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import backgroundLoader.Background;


public class DeadScreen extends GameState{

	// bg
	Background bg;
	Font font;
	
	//konstruktor
	public DeadScreen(GameStateManager gsm) {
		this.gsm = gsm;
		try {
			bg = new Background("/Backgrounds/main_menu_bg.png",1);
			font = new Font("Monospaced", Font.PLAIN, 12);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		if (gsm.player.getPoints() > gsm.player.getRecord()) {
			gsm.player.setRecord(gsm.player.getPoints());
		}
		gsm.player.setPoints(0);
	}
	
	//menu update -> bg update
	public void update() {
		bg.update();
		
	}
	
	//infok
	public void draw(java.awt.Graphics2D g) {
		g.setColor(Color.BLACK);
		g.setFont(font);
		bg.draw(g);
		g.drawString("You died at level: "+ Integer.toString(gsm.player.getLevel()-4), 90, 115);
		g.drawString("Pres Q to return to main menu", 80, 220);
	}
	
	//visszaterunk a menube
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_Q) {
			gsm.setState(GameStateManager.MENU);
		}
	}
	
	public void keyReleased(int k) {
		
	}
}
