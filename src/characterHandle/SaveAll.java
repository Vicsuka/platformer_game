package characterHandle;

import java.util.ArrayList;
import java.io.*;

@SuppressWarnings("serial")
public class SaveAll implements Serializable{
	// menteshez szukseges adatok
	SaveName names;
	ArrayList<Player> players;
	Player player;
	String playertype;
	
	// konstrutkorban beallitjuk
	public SaveAll(SaveName n,ArrayList<Player> p,Player pl) {
		if (n != null && p != null && pl != null) {
		names = n;
		players = p;
		player = pl;
		playertype = player.playerType();
		save();
		}
	}
	
	// elmentjuk az allast
	public void save() {
		try {
	        FileOutputStream fileOut =new FileOutputStream("savefile.txt");
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(this);
	        out.close();
	        fileOut.close();
	        System.out.printf("Saved!");
	     } catch (IOException i) {
	        i.printStackTrace();
	     }
	}
	
	// visszaadogatjuk az adatokat
	
	public SaveName getNames() {
		return names;
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}
