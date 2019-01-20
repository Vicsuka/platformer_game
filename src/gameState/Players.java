package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import backgroundLoader.Background;
import characterHandle.Player;


public class Players extends GameState{

	// bg
	Background bg;
	Font font;
	private int chosen;
	private Player player;
	private boolean identified;
	
	//konstruktor
	public Players(GameStateManager gsm) {
		this.gsm = gsm;
		try {
			bg = new Background("/Backgrounds/main_menu_bg.png",1);
			font = new Font("Monospaced", Font.PLAIN, 12);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {
		chosen = 0;
	}
	
	public void identify(Player p) {
		player = p;
	}
	
	//menu update -> bg update
	public void update() {
		bg.update();
		
	}
	
	//kilistazzuk a jatekosokat: nev, belepesek szama, karater fajta szerint
	public void draw(java.awt.Graphics2D g) {
		bg.draw(g);
		
		if (identified) {
			// Ha beazonositottuk a jatekost akkor a statisztikakat ki kell irni
			double hitperc = player.getAttacks();
			// nehogy nullaval osszunk mar
			if (player.getAttacks() != 0)
				hitperc = (double) player.getHit()/ (double) player.getAttacks();

			hitperc= hitperc*100;
			g.setColor(Color.BLACK);
			g.drawString("Total attacks: ", 60, 50);
			g.drawString("Total hits: ", 60, 70);
			g.drawString("Total hit percentage: ", 60, 90);
			
			
			g.drawString("Total dragon kills: ", 60, 110);
			g.drawString("Total deaths: ", 60, 130);
			g.drawString("Total coins collected: ", 60, 150);
			
			g.setColor(Color.BLUE);
			g.drawString("Games won: "+ player.getWins(), 110, 210);
			
			g.setColor(Color.RED);
			g.drawString(Integer.toString(player.getAttacks()), 200, 50);
			g.drawString(Integer.toString(player.getHit()), 200, 70);
			g.drawString(String.format("%.2f", hitperc)+ "%", 200, 90);
			
			g.drawString(Integer.toString(player.getKills()), 200, 110);
			g.drawString(Integer.toString(player.getDeaths()), 200, 130);
			g.drawString(Integer.toString(player.getCoins()), 200, 150);
		}else {
			// ha nem a reszletes statisztikakat nezzuk akkor az osszes nevet
			int distance = 10;
			g.setColor(Color.RED);
			g.drawString(">", 20, 10+chosen*distance);
			
			g.setColor(Color.BLACK);
			for (int i=0; i < gsm.players.size();i++) {
				g.drawString(gsm.players.get(i).playerName() +" RECORD: "+gsm.players.get(i).getRecord(), 30, 10+i*distance);
			}
		}
	}
	
	//visszaterunk a menube
	public void keyPressed(int k) {
		if (k == KeyEvent.VK_UP) {
			chosen--;
			if (chosen < 0) {
				chosen = gsm.players.size()-1;
			}
		}
		if (k == KeyEvent.VK_DOWN) {
			chosen++;
			if (chosen > gsm.players.size()-1) {
				chosen = 0;
			}
		}
		
		if (k == KeyEvent.VK_ENTER) {
			// Beazonositjuk a jatekost
			if (player == null) {
				if (gsm.players.size() > 0) {
					identified = true;
					identify(gsm.players.get(chosen));
				}
			} else {
				// Alaphelyzetbe rakjuk megint
				identified = false;
				player = null;
			}

		}
		if (k == KeyEvent.VK_ESCAPE) {
			gsm.setState(GameStateManager.MENU);
		}
	}
	
	public void keyReleased(int k) {
		
	}
}
