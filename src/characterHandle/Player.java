package characterHandle;

import java.io.Serializable; 
import character.*;

@SuppressWarnings("serial")
public class Player implements Serializable{
		
		// Fontos jatekos adatok
		private String name;
		private String type;
		private int logins;
		transient private Basic player;
		
		// Statisztikai adatok
		private int level;
		private int points;
		private int hits;
		private int attacks;
		private int kills;
		private int deaths;
		private int coins;
		private int wins;
		private int record;
		
		public void setRecord(int r) {
			record = r;
		}
		
		public int getRecord() {
			return record;
		}
		
		public void winPoint() {
			wins++;
		}
		
		public int getWins() {
			return wins;
		}
		
		public void hitPoint() {
			hits++;
		}
		public int getHit() {
			return hits;
		}
		
		
		public void attackPoint() {
			attacks++;
		}		
		public int getAttacks() {
			return attacks;
		}
		
		
		public void killPoint() {
			kills++;
		}
		public int getKills() {
			return kills;
		}
		
		
		public void deathPoint() {
			deaths++;
		}
		public int getDeaths() {
			return deaths;
		}
		
		
		public void coinPoint() {
			coins++;
		}
		public int getCoins() {
			return coins;
		}
		
		
		
		
		
		//konstruktor
		public Player(String n) {
			name = n;
			type = null;
			record = 0;
		}
		
		public Basic getPlayer() {
			return player;
		}
		
		public void setPlayer(Basic p) {
			player = p;
		}
		
		
		public int getLevel() {
			return level;
		}
		
		public void setLevel(int lvl) {
			level = lvl;
		}
		
		//bizonyos adatok kiirasa
		public String playerName() {
			return name+" Logged in: "+logins+" times";
		}
		
		public String playerType() {
			return type;
		}
		
		public void setType(String t) {
			type = t;
		}
		
		public void logIn() {
			logins++;
		}
		
		public int getLogins() {
			return logins;
		}
		
		public int getPoints() {
			return points;
		}
		
		public void setPoints(int p) {
			points = p;
		}
		
		// Pont adasa, ha levonunk akkor minusz egyszerese
		public void incPoints(int p) {
			points = points + p;
		}
}
