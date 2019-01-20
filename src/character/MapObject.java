package character;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import backgroundLoader.Animate;
import collosionHandle.Coordinate;

public class MapObject {

	private ArrayList<BufferedImage[]> sprites;
	private Animate animate;
	
	protected int HP;
	protected int x;
	protected int y;
	
	public ArrayList<Coordinate> hitbox;
	
	public MapObject(int x, int y) {
		setPosition(x, y);
		hitbox = new ArrayList<Coordinate>();
		sprites = new ArrayList<BufferedImage[]>();
		try {
			BufferedImage img1 = ImageIO.read(getClass().getResourceAsStream("/Bonus items/PNG/coin_001.png"));
			BufferedImage img2 = ImageIO.read(getClass().getResourceAsStream("/Bonus items/PNG/coin_002.png"));
			BufferedImage img3 = ImageIO.read(getClass().getResourceAsStream("/Bonus items/PNG/coin_003.png"));
			BufferedImage img4 = ImageIO.read(getClass().getResourceAsStream("/Bonus items/PNG/coin_004.png"));
			BufferedImage[] animation = {img1,img2,img3,img4};
			sprites.add(animation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		animate =new Animate();
		animate.setFrames(sprites.get(0));
		animate.setDelay(100);
	}
	
	public void loadHitbox() {
		hitbox.clear();
		int i = x+9;
		int j = y+9;
		for (int hX= i;hX < i+14;hX++)
			for (int hY = j; hY < j+14;hY++) {
				hitbox.add(new Coordinate(hX , hY));
			}
		
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void update() {
		animate.update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		g.drawImage(
				animate.getImage(),
				(int)(x ),
				(int)(y ),
				32,
				32,
				null
			);	
	}
}
