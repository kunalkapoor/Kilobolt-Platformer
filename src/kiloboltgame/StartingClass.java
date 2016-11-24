package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import kiloboltgame.framework.Animation;

public class StartingClass extends Applet implements Runnable, KeyListener
{
	enum GameState
	{
		Running, Dead
	}
	
	GameState state = GameState.Running;
	private static Robot robot;
	public static Heliboy hb1, hb2;
	public static int score = 0, countdown = 3;
	private Font font = new Font(null, Font.BOLD, 30);
	private Image image, currentSprite, character, character2, character3, characterDown, characterJumped, background, heliboy, heliboy2, heliboy3, heliboy4,
			heliboy5;
	public static Image tileGrassTop, tileGrassBot, tileGrassLeft, tileGrassRight, tileDirt;
	private URL base;
	private Graphics second;
	private static Background bg1, bg2;
	private Animation anim, hanim;
	private ArrayList<Tile> tileArray = new ArrayList<>();
	
	private Thread thread;

	@Override
	public void init()
	{
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);

		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");

		try
		{
			base = getDocumentBase();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		character = getImage(base, "data/character.png");
		character2 = getImage(base, "data/character2.png");
		character3 = getImage(base, "data/character3.png");

		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");

		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");

		background = getImage(base, "data/background.png");

		tileDirt = getImage(base, "data/tiledirt.png");
		tileGrassTop = getImage(base, "data/tilegrasstop.png");
		tileGrassBot = getImage(base, "data/tilegrassbot.png");
		tileGrassLeft = getImage(base, "data/tilegrassleft.png");
		tileGrassRight = getImage(base, "data/tilegrassright.png");

		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);

		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);

		currentSprite = anim.getImage();
	}

	private void restart()
	{
		robot = null;
		hb1 = hb2 = null;
		score = 0;
		countdown = 3;
		bg1 = bg2 = null;
		tileArray = new ArrayList<>();
	
		state = GameState.Running;

		start();
	}
	
	@Override
	public void start()
	{
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);

		robot = new Robot();
		hb1 = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);

		try
		{
			loadMap("data/map1.txt");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		thread = new Thread(this);
		thread.start();
	}

	private void loadMap(String fileName) throws IOException
	{
		ArrayList<String> lines = new ArrayList<>();
		String line;

		BufferedReader reader = new BufferedReader(new FileReader(fileName));

		while ((line = reader.readLine()) != null)
		{
			if (!line.startsWith("!"))
				lines.add(line);
		}

		for (int j = 0; j < lines.size(); j++)
		{
			line = lines.get(j);
			for (int i = 0; i < line.length(); i++)
				tileArray.add(new Tile(i, j, Character.getNumericValue(line.charAt(i))));
		}
		
		reader.close();
	}

	@Override
	public void stop()
	{
		// TODO Auto-generated method stub
		super.stop();
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void run()
	{
		while (state == GameState.Running)
		{
			robot.update();
			if (robot.isJumped())
				currentSprite = characterJumped;
			else if (robot.isDucked())
				currentSprite = characterDown;
			else
				currentSprite = anim.getImage();

			ArrayList<Projectile> projectiles = robot.getProjectiles();
			Iterator<Projectile> it = projectiles.iterator();
			while (it.hasNext())
			{
				Projectile p = it.next();
				if (p.isVisible())
					p.update();
				else
					it.remove();
			}

			updateTiles();
			hb1.update();
			hb2.update();

			bg1.update();
			bg2.update();

			animate();
			repaint();

			try
			{
				Thread.sleep(17);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			if(robot.getCenterY() > 500)
				state = GameState.Dead;
		}
		repaint();
	}

	public void animate()
	{
		anim.update(10);
		hanim.update(50);
	}

	@Override
	public void update(Graphics g)
	{
		if (image == null)
		{
			image = createImage(getWidth(), getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}

	@Override
	public void paint(Graphics g)
	{
		if(state == GameState.Running)
		{
			g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
			g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
			paintTiles(g);
			
			ArrayList<Projectile> projectiles = robot.getProjectiles();
			Iterator<Projectile> it = projectiles.iterator();
			while (it.hasNext())
			{
				Projectile p = it.next();
				g.setColor(Color.YELLOW);
				g.fillRect(p.getX(), p.getY(), 10, 5);
			}
	
			g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
			g.drawImage(hanim.getImage(), hb1.getCenterX() - 48, hb1.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb2.getCenterX() - 48, hb2.getCenterY() - 48, this);
	
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
		}
		else
		{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Dead", 360, 240);
			g.drawString("Press Enter to restart...", 250, 300);
		}
	}

	private void updateTiles()
	{
		Iterator<Tile> it = tileArray.iterator();
		while (it.hasNext())
			it.next().update();
	}

	private void paintTiles(Graphics g)
	{
		Tile t;
		Iterator<Tile> it = tileArray.iterator();
		while (it.hasNext())
		{
			t = it.next();
			g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP:
				robot.jump();
				break;
			case KeyEvent.VK_DOWN:
				robot.duck();
				break;
			case KeyEvent.VK_LEFT:
				robot.moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				robot.moveRight();
				break;
			case KeyEvent.VK_SPACE:
				robot.jump();
				break;
			case KeyEvent.VK_CONTROL:
				if (robot.isDucked() == false && robot.isJumped() == false)
				{
					robot.shoot();
					robot.setReadyToFire(false);
				}
				break;
			case KeyEvent.VK_ENTER:
				if(state == GameState.Dead)
					restart();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
			case KeyEvent.VK_UP:
				break;
			case KeyEvent.VK_DOWN:
				currentSprite = anim.getImage();
				robot.stopDuck();
				break;
			case KeyEvent.VK_LEFT:
				robot.stopLeft();
				break;
			case KeyEvent.VK_RIGHT:
				robot.stopRight();
				break;
			case KeyEvent.VK_SPACE:
				break;
			case KeyEvent.VK_CONTROL:
				robot.setReadyToFire(true);
				break;
		}
	}

	public static Background getBg1()
	{
		return bg1;
	}

	public static void setBg1(Background bg1)
	{
		StartingClass.bg1 = bg1;
	}

	public static Background getBg2()
	{
		return bg2;
	}

	public static void setBg2(Background bg2)
	{
		StartingClass.bg2 = bg2;
	}

	public static Robot getRobot()
	{
		return robot;
	}
}
