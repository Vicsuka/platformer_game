package gameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import backgroundLoader.Background;
import main.GamePanel;
import characterHandle.*;

public class NameSelect extends GameState{

	//hatter
	private Background bg;
	
	//nev
	private String[] nev = {"A","A","A","A"};
	private int charplus = 0;
	private int currentChar = 0;
	private int animateSize = 12;
	private int direction = 1;
	private int slow = 0;
	
	//megjelentesi szin, font
	private Color titleColor;
	private Font titleFont;
	private Font font;
	private Font animatedFont;
	
	//konstruktor..
	public NameSelect(GameStateManager gsm) {
		this.gsm = gsm;
		
		try {
			bg = new Background("/Backgrounds/main_menu_bg.png",1);
			bg.setVector(0,0);
			titleColor = new Color(40, 0, 0);
			titleFont = new Font("Monospaced Italic", Font.BOLD, 15);
			font = new Font("Monospaced Italic", Font.PLAIN, 12);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	

	public void init() {
		
	}
	
	public void update() {
		bg.update();
	}
	
	//draw -> bg draw + nevvalaszta
	public void draw(java.awt.Graphics2D g) {
		bg.draw(g);
		
		animatedFont =new Font("Arial Bold", Font.PLAIN, animateSize);
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Pick a name", 110, 70);
		
		g.setFont(font);
		for (int i = 0; i < nev.length; i++) {
			if (i==currentChar) {
				g.setColor(Color.RED);
				g.setFont(animatedFont);
			} else {
				g.setColor(Color.BLACK);
				g.setFont(font);
			}
			g.drawString(nev[i],(int)(GamePanel.WIDTH/2.6) + i * 15, 100);
		}
		
		// animacio, kicsit le kellett lassitani
		slow++;
		if (slow > 10) {
			animateSize= animateSize + direction;
			slow = 0;
		}
		if (animateSize > 14) {
			direction = -1;
		} else if (animateSize < 12) {
			direction = 1;
		}
		
	}
	
	
	//ha entert nyom a felhasznalo
	private void select() {
			//megnezzuk, hogy szerepel-e a nev a nevnyilvantartasba, ha igen akkor az lesz a jatekos, ha nem akkor uj jatekost es nevet csinalunk
			int index = gsm.names.findName(nev[0],nev[1],nev[2],nev[3]);
			if (index < 0) {
				gsm.names.allNames.add(nev[0]+nev[1]+nev[2]+nev[3]);
				gsm.player = new Player(nev[0]+nev[1]+nev[2]+nev[3]);
				gsm.players.add(gsm.player);
			} else {
				gsm.player = gsm.players.get(index);
				gsm.players.get(index).logIn();
			}
			//karaktervalasztas
			gsm.setState(2);
		}
		
	// Fel- es lefele ascii kod szerint adogatunk hozza A es Z kozott, oldalra currentchar szerint
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_ENTER) {
			select();
		}
		if (k == KeyEvent.VK_UP) {
			charplus++;
			if (charplus > 25) {
				charplus = 0;
			}else if (charplus < 0) {
				charplus = 25;
			}
			nev[currentChar] = Character.toString ((char) ('A' + charplus));
		}
		if (k == KeyEvent.VK_DOWN) {
			charplus--;
			if (charplus > 25) {
				charplus = 0;
			}else if (charplus < 0) {
				charplus = 25;
			}
			
			nev[currentChar] = Character.toString ((char) ('A' + charplus));
		}
		if (k == KeyEvent.VK_LEFT) {
			charplus = 0;
			currentChar--;
			if (currentChar < 0) {
				currentChar = nev.length - 1;
			}
		}
		if (k == KeyEvent.VK_RIGHT) {
			charplus = 0;
			currentChar++;
			if (currentChar >= nev.length) {
				currentChar = 0;
			}
		}
	}
	
	public void keyReleased(int k) {
		
	}
	
}

