package kiloboltgame;

import java.awt.Image;
import java.awt.Rectangle;

public class Tile
{
	private int tileX, tileY, speedX, type;
	public Image tileImage;
	private Rectangle r;

	private Robot robot;
	private Background bg;

	public Tile(int x, int y, int typeInt)
	{
		robot = StartingClass.getRobot();
		bg = StartingClass.getBg1();
		
		tileX = x * 40;
		tileY = y * 40;
		type = typeInt;

		r = new Rectangle();

		switch (typeInt)
		{
			case 5:
				tileImage = StartingClass.tileDirt;
				break;
			case 8:
				tileImage = StartingClass.tileGrassTop;
				break;
			case 4:
				tileImage = StartingClass.tileGrassLeft;
				break;
			case 6:
				tileImage = StartingClass.tileGrassRight;
				break;
			case 2:
				tileImage = StartingClass.tileGrassBot;
				break;
			default:
				type = 0;
		}
	}

	public void update()
	{
		speedX = bg.getSpeedX() * 5;
		tileX += speedX;
		r.setBounds(tileX, tileY, 40, 40);
		
		if(r.intersects(Robot.yellowRed) && type != 0)
		{
			checkVerticalCollision(Robot.rect, Robot.rect2);
			checkSideCollision(Robot.rect3, Robot.rect4, Robot.footleft, Robot.footright);
		}
	}

	public void checkVerticalCollision(Rectangle rTop, Rectangle rBot)
	{
		if (rTop.intersects(r))
		{
		}

		if (rBot.intersects(r) && type == 8)
		{
			robot.setJumped(false);
			robot.setSpeedY(0);
			robot.setCenterY(tileY - 63);
		}
	}

	public void checkSideCollision(Rectangle rleft, Rectangle rright, Rectangle leftfoot, Rectangle rightfoot)
	{
		if (type != 5 && type != 2 && type != 0)
		{
			if (rleft.intersects(r))
			{
				robot.setCenterX(tileX + 102);
				robot.setSpeedX(0);
			}
			else if (leftfoot.intersects(r))
			{
				robot.setCenterX(tileX + 185);
				robot.setSpeedX(0);
			}

			if (rright.intersects(r))
			{
				robot.setCenterX(tileX - 62);
				robot.setSpeedX(0);
			}
			else if (rightfoot.intersects(r))
			{
				robot.setCenterX(tileX - 45);
				robot.setSpeedX(0);
			}
		}
	}

	public int getTileX()
	{
		return tileX;
	}

	public void setTileX(int tileX)
	{
		this.tileX = tileX;
	}

	public int getTileY()
	{
		return tileY;
	}

	public void setTileY(int tileY)
	{
		this.tileY = tileY;
	}

	public Image getTileImage()
	{
		return tileImage;
	}

	public void setTileImage(Image tileImage)
	{
		this.tileImage = tileImage;
	}
}
