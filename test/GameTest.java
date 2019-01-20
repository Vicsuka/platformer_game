import backgroundLoader.*;
import character.*;
import characterHandle.*;
import gameState.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.junit.*;

public class GameTest {
	Animate animation;
	
	Basic wizard;
	Basic princess;
	Basic goblin;
	
	Projectile arrow;
	Projectile fireball;
	Projectile iceball;
	Projectile swing;
	
	Player player;
	
	LoadAll loader;
	SaveAll save;
	SaveName names;
	
	GameStateManager gsm;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testAnimation() {
		animation = new Animate();
		
		BufferedImage img1 = null;
		try {
			img1 = ImageIO.read(getClass().getResourceAsStream("/Bonus items/PNG/coin_001.png"));
			BufferedImage img2 = ImageIO.read(getClass().getResourceAsStream("/Bonus items/PNG/coin_002.png"));
			BufferedImage img3 = ImageIO.read(getClass().getResourceAsStream("/Bonus items/PNG/coin_003.png"));
			BufferedImage img4 = ImageIO.read(getClass().getResourceAsStream("/Bonus items/PNG/coin_004.png"));
			BufferedImage[] pics = {img1,img2,img3,img4};
			animation.setFrames(pics);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Assert.assertFalse("Finish check", animation.isFinished());
		Assert.assertEquals("Starting check", 0, animation.getFrame());
		Assert.assertEquals("Picture check", img1, animation.getImage());
	}
	
	@Test
	public void testCharacterBasics() {
		wizard = new Wizard();
		princess = new Princess();
		goblin = new Goblin();
		
		//wizard test
		wizard.setPosition(0, 0);
		wizard.setRight(true);
		wizard.setWalking(true);
		wizard.update();
		Assert.assertEquals("Single move check", 1,wizard.getX());
		
		wizard.update();
		wizard.update();
		wizard.update();
		Assert.assertEquals("Multi move check", 4, wizard.getX());
		
		wizard.setRunning(true);
		wizard.update();
		Assert.assertEquals("Sprinting move check", 6,wizard.getX());
		
		wizard.setWalking(false);
		wizard.canJump = false;
		wizard.setJumping(true);
		wizard.update();
		Assert.assertEquals("Jumping blocked check", 0, wizard.getY());
		
		wizard.setJumping(false);
		wizard.loadHitbox();
		Assert.assertEquals("Hitbox loaded check", 20, wizard.hitbox.get(0).gety());
		
		wizard.setCrouching(true);
		wizard.loadHitbox();
		Assert.assertEquals("Crouching hitbox check", 25, wizard.hitbox.get(0).gety());
		
		
		//princess test
		princess.setPosition(0, 0);
		princess.setRight(true);
		princess.setWalking(true);
		princess.update();
		Assert.assertEquals("Single move check", 1,princess.getX());
		
		princess.update();
		princess.update();
		princess.update();
		Assert.assertEquals("Multi move check", 4, princess.getX());
		
		princess.setRunning(true);
		princess.update();
		Assert.assertEquals("Sprinting move check", 7,princess.getX());
		
		princess.setWalking(false);
		princess.canJump = false;
		princess.setJumping(true);
		princess.update();
		Assert.assertEquals("Jumping blocked check", 0, princess.getY());
		
		princess.setJumping(false);
		princess.loadHitbox();
		Assert.assertEquals("Hitbox loaded check", 22, princess.hitbox.get(0).gety());
		
		princess.setCrouching(true);
		princess.loadHitbox();
		Assert.assertEquals("Crouching hitbox check", 27, princess.hitbox.get(0).gety());
		
		//goblin test
		goblin.setPosition(0, 0);
		goblin.setRight(true);
		goblin.setWalking(true);
		goblin.update();
		Assert.assertEquals("Single move check", 1,goblin.getX());
		
		goblin.update();
		goblin.update();
		goblin.update();
		Assert.assertEquals("Multi move check", 4, goblin.getX());
		
		goblin.setRunning(true);
		goblin.update();
		Assert.assertEquals("Sprinting move check", 7,goblin.getX());
		
		goblin.setWalking(false);
		goblin.canJump = false;
		goblin.setJumping(true);
		goblin.update();
		Assert.assertEquals("Jumping blocked check", 0, goblin.getY());
		
		goblin.setJumping(false);
		goblin.loadHitbox();
		Assert.assertEquals("Hitbox loaded check", 20, goblin.hitbox.get(0).gety());
		
		goblin.setCrouching(true);
		goblin.loadHitbox();
		Assert.assertEquals("Crouching hitbox check", 25, goblin.hitbox.get(0).gety());
	}
	
	@Test
	public void testProjectileBasics() {
		arrow = new Arrow(1,1);
		fireball = new Fireball(1,1);
		iceball = new Iceball(1,1);
		swing = new Swing(1,1);
		
		//arrow test
		arrow.setPosition(0, -1);
		Assert.assertEquals("Position check X", 0, arrow.getX());
		Assert.assertEquals("Position check Y", -1, arrow.getY());
		
		arrow.update();
		Assert.assertEquals("Move check", 5, arrow.getX());
		
		arrow.loadHitbox();
		Assert.assertEquals("Hitbox check", 5, arrow.hitbox.get(0).getx());
		Assert.assertEquals("Hitbox check", 4, arrow.hitbox.get(0).gety());
		
		//fireball test
		fireball.setPosition(0, -1);
		Assert.assertEquals("Position check X", 0, fireball.getX());
		Assert.assertEquals("Position check Y", -1, fireball.getY());
		
		fireball.update();
		Assert.assertEquals("Move check", 2, fireball.getX());
		
		fireball.loadHitbox();
		Assert.assertEquals("Hitbox check", 5, fireball.hitbox.get(0).getx());
		Assert.assertEquals("Hitbox check", 2, fireball.hitbox.get(0).gety());
		
		//iceball test
		iceball.setPosition(0, -1);
		Assert.assertEquals("Position check X", 0, iceball.getX());
		Assert.assertEquals("Position check Y", -1, iceball.getY());
		
		iceball.update();
		Assert.assertEquals("Move check", -2, iceball.getX());
		
		iceball.loadHitbox();
		Assert.assertEquals("Hitbox check", 1, iceball.hitbox.get(0).getx());
		Assert.assertEquals("Hitbox check", 2, iceball.hitbox.get(0).gety());
		
		//swing test
		swing.setPosition(0, -1);
		Assert.assertEquals("Position check X", 0, swing.getX());
		Assert.assertEquals("Position check Y", -1, swing.getY());
		
		swing.update();
		Assert.assertEquals("Move check", 4, swing.getX());
		
		swing.loadHitbox();
		Assert.assertEquals("Hitbox check", 4, swing.hitbox.get(0).getx());
		Assert.assertEquals("Hitbox check", 16, swing.hitbox.get(0).gety());
	}
	
	@Test
	public void testFiles(){

		player = new Player("MARK");
		ArrayList<Player> temp = new ArrayList<Player>();
		names = new SaveName();
		names.allNames.add("MARK");
		Assert.assertEquals("Name added check", 0, names.findName("M", "A", "R", "K"));
		
		save = new SaveAll(names, temp, player);
		player = null;
		names = null;
		
		loader = new LoadAll();
		names= loader.save.getNames();
		Assert.assertEquals("ReLoaded name check", 0, names.findName("M", "A", "R", "K"));
	}
	
	@Test
	public void testMechanics() {
		gsm = new GameStateManager();
		Assert.assertEquals("Gamestates number check", 10,gsm.states.size());
		
		gsm.setState(0);
		gsm.setState(1);
		gsm.setState(2);
		gsm.setState(3);
		
		player = new Player("MARK");
		goblin = new Goblin();
		player.setPlayer(goblin);
		player.setLevel(0);
		player.setType("Goblin");
		gsm.player = player;
		
		gsm.setState(4);
		gsm.update();
		Assert.assertEquals("Point check", 0, gsm.player.getPoints());
		
		gsm.setState(5);
		gsm.update();
		Assert.assertEquals("Point check", 0, gsm.player.getPoints());
		
		gsm.setState(6);
		gsm.update();
		Assert.assertEquals("Point check", 0, gsm.player.getPoints());
		
		gsm.setState(7);
		gsm.update();
		Assert.assertEquals("Point check", 0, gsm.player.getPoints());
		
		gsm.setState(8);
		gsm.update();
		Assert.assertEquals("Point check", 0, gsm.player.getPoints());
		
		gsm.player.setPoints(100);
		Assert.assertEquals("Point check", 100, gsm.player.getPoints());
		gsm.setState(9);
		Assert.assertEquals("Point reset check", 0, gsm.player.getPoints());
		
		/* 93 sec
		Level3 level = new Level3(gsm);
		for(int i = 0; i < 1000; i++) {
			level.update();
		}
		Assert.assertEquals("Player death check", 1, gsm.player.getDeaths());
		*/
	}
	
}
