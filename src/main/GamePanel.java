package main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

import gameState.GameStateManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	//meretek megadasa
	public static final int WIDTH = 320; //szelesseg
	public static final int HEIGHT = 240;	//magassag
	public static final int SCALE = 2;	//skala
	
	//jatekmotor
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long realFPS = 1000 / FPS; //ms- mertekegysegben, azert kell, hogy minden gepen egyforman fusson
	
	//grafikai elemek
	private BufferedImage image;
	private Graphics2D g;
	
	//Jatek allapotanak kezeloje
	private GameStateManager gsm;
	
	
	//ablak adatok megadasa
	public GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
	}
	
	
	//annak a biztositasa, hogy a gamepanel ossze legyen kotve a jframeel es hozzaadja a billentyuzetleutesfigyelot. Automatikusan lefut.
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	//a jatekkepernyo inicializalasa CSAK EGYSZER
	private void init() {
		image = new BufferedImage(WIDTH , HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		
		gsm = new GameStateManager();
	}
	
	//kirajzolja a kepet az ablakra MINDEN CIKLUSBAN
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image,0,0,WIDTH * SCALE, HEIGHT * SCALE,null);
		g2.dispose();
	}
	
	
	//amikor elinditjuk a jatekot egy szalon akkor itt hivodik meg minden, ezen belul van a fo jatek futasi ciklus
	public void run() {
		init();
		
		long start;
		long elapsed;
		long wait;
		
		//fo jatek futasi ciklus
		while(running) {
			start = System.nanoTime();
			update();
			draw();
			drawToScreen();
			elapsed = System.nanoTime() - start;
			wait = realFPS - (elapsed / 1000000); 
			/* nanosec es millisec kozotti atvaltas millioval es annyi ideig alszik a thread
			 * amennyi a realFPS minusz eltelt ido (Mert az mar eltelt)
			 * */
			
			try {
				if (wait < 1) {
					Thread.sleep(1);
				} else {
				Thread.sleep(wait);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//game update -> gsm update
	private void update() {
		gsm.update();
	}
	
	//game draw -> gsm draw
	private void draw() {
		gsm.draw(g);
	}
	
	//billentyu kod elkuldve a rendszernek
	public void keyTyped(KeyEvent k) {
		
	}
	
	//billentyu lenyomva -> gsm
	public void keyPressed(KeyEvent k) {
		gsm.keyPressed(k.getKeyCode());
	}
	
	//billentyu felengedve -> gsm
	public void keyReleased(KeyEvent k) {
		gsm.keyReleased(k.getKeyCode());
	}


}
