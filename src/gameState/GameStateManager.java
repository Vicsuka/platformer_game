package gameState;

import java.util.ArrayList;

import character.Goblin;
import character.Princess;
import character.Wizard;
import characterHandle.*;

public class GameStateManager {
	
	//allapotok tarolasa
	public ArrayList<GameState> states;
	private int currentState;
	
	
	//jatekbeli adatok tarolasa
	public SaveName names;
	public ArrayList<Player> players;
	public Player player;
	
	//Nevek numerikusan
	public static final int MENU = 0;
	public static final int NAMESELECT = 1;
	public static final int CHARSELECT = 2;
	public static final int PLAYERS = 3;
	public static final int TUTORIAL = 4;
	public static final int LEVEL1 = 5;
	public static final int LEVEL2 = 6;
	public static final int LEVEL3 = 7;
	public static final int LEVEL4 = 8;
	public static final int DEAD = 9;
	
	
	//Alap gsm ugye a menu es a tobbi statusz inicializalasa
	public GameStateManager() {
		states = new ArrayList<GameState>();
		names = new SaveName();
		players = new ArrayList<Player>();
		
		currentState = MENU;
		states.add(new MainMenu(this));
		states.add(new NameSelect(this));
		states.add(new CharSelect(this));
		states.add(new Players(this));
		states.add(new Tutorial(this));
		states.add(new Level1(this));
		states.add(new Level2(this));
		states.add(new Level3(this));
		states.add(new Level4(this));
		states.add(new DeadScreen(this));
	}
	
	//allapotok atvaltasa
	public void setState (int s) {
		if (s == 100) saveProgress();
		else
		if (s ==101) loadProgress(); else
		{
		currentState = s;
		states.get(currentState).init();
		}
	}
	
	//update gsm -> update gamestate
	public void update() {
		states.get(currentState).update();
	}
	
	//draw gsm -> draw gamestate
	public void draw(java.awt.Graphics2D g) {
		states.get(currentState).draw(g);
	}
	
	//keypressed gsm -> keypressed gamestate
	public void keyPressed(int k) {
		states.get(currentState).keyPressed(k);
	}
	
	//keyreleased gsm -> keyreleased gamestate
	public void keyReleased(int k) {
		states.get(currentState).keyReleased(k);
	}
	
	
	//allapotmentes
	public void saveProgress() {
		SaveAll save =new SaveAll(names,players,player);
		save.getClass();
	}
	
	//allapotoltes
	public void loadProgress() {
		LoadAll load = new LoadAll();
		if (load != null && load.save!= null) {
		this.names=load.save.getNames();
		this.players=load.save.getPlayers();
		this.player = load.save.getPlayer();
		
		if (load.save.getPlayer().playerType() == "Mage") {
			this.player.setPlayer(new Wizard());
			} else if (load.save.getPlayer().playerType() == "Goblin") {
				this.player.setPlayer(new Goblin());
				} else {
					this.player.setPlayer(new Princess());
				}
		}
		
	}
}
