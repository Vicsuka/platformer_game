package backgroundLoader;

import java.awt.image.BufferedImage;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Animate implements Serializable{
	
	// beallitasok
	// kepek eltarolasa
	transient private BufferedImage[] frames;
	private int indexFrame;
	
	// idotartamok
	private long start;
	private long delay;
	private boolean finished;
	
	// konstruktor
	public Animate(){
		finished = false;
	}
	
	//a gifnek az egyes kepei
	public void setFrames(BufferedImage[] gifs) {
		frames = gifs;
		indexFrame = 0;
		start = System.nanoTime();
		finished = false;
	}
	
	//milyen gyors legyen
	public void setDelay(long d) {
		delay = d;
	}
	
	//hol tartunk szamilag
	public int getFrame() {
		return indexFrame;
	}
	
	//hol tartunk kepileg
	public BufferedImage getImage() {
		return frames[indexFrame];
	}
	
	//vege van e
	public boolean isFinished() {
		return finished;
	}

	//gif lejatszasa
	public void update() {
		if (delay < 0) return;
		
		long elapsed = (System.nanoTime() - start) /1000000;
		if (elapsed > delay) {

			indexFrame++;
			start = System.nanoTime();
		}
		
		if (indexFrame == frames.length) {
			indexFrame = 0;
			finished = true;
		}
	}
}
