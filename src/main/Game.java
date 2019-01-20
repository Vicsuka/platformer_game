package main;

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {
		JFrame ablak = new JFrame("VICSUKA'S BATTLEGROUNDS");
		ablak.setContentPane(new GamePanel()); //megcsinaljuk a gamepanelt amiben kezeljuk a jatek contentet
		ablak.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //kilepes utan bezar mindent
		ablak.setResizable(false); //nem ujrameretezheto
		ablak.pack(); //felbontast rendezni
		ablak.setVisible(true); //ablak lathatosaga
	}

}
