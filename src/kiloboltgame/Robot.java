package kiloboltgame;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Robot
{
	final int JUMPSPEED = -15;
	final int MOVESPEED = 5;

	private int centerX = 100;
	private int centerY = 377;
	private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean ducked = false;
	private boolean readyToFire = true;

	private static Background bg1;
	private static Background bg2;

	private int speedX = 0;
	private int speedY = 0;

	public static Rectangle rect;
	public static Rectangle rect2;
	public static Rectangle rect3;
	public static Rectangle rect4;
	public static Rectangle yellowRed;
	public static Rectangle footleft;
	public static Rectangle footright;

	private ArrayList<Projectile> projectiles;

	public Robot()
	{
		bg1 = StartingClass.getBg1();
		bg2 = StartingClass.getBg2();
		
		rect = new Rectangle(0, 0, 0, 0);
		rect2 = new Rectangle(0, 0, 0, 0);
		rect3 = new Rectangle(0, 0, 0, 0);
		rect4 = new Rectangle(0, 0, 0, 0);
		yellowRed = new Rectangle(0, 0, 0, 0);
		footleft = new Rectangle(0, 0, 0, 0);
		footright = new Rectangle(0, 0, 0, 0);
		
		projectiles = new ArrayList<>();
	}
	
	public void update()
	{
		if (speedX < 0)
		{
			centerX += speedX;
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);
		}
		else if (speedX == 0)
		{
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);
		}
		else
		{
			if (centerX <= 200)
				centerX += speedX;
			else
			{
				bg1.setSpeedX(-MOVESPEED / 5);
				bg2.setSpeedX(-MOVESPEED / 5);
			}
		}

		centerY += speedY;

		speedY += 1;
		
		if(speedY > 3)
			jumped = true;
		
		if (centerX + speedX <= 60)
			centerX = 61;

		rect.setRect(centerX - 34, centerY - 63, 68, 63);
		rect2.setRect(rect.getX(), rect.getY() + 63, 68, 64);
		rect3.setRect(rect.getX() - 26, rect.getY() + 32, 26, 20);
		rect4.setRect(rect.getX() + 68, rect.getY() + 32, 26, 20);
		yellowRed.setRect(centerX - 110, centerY - 110, 180, 180);
		footleft.setRect(centerX - 50, centerY + 20, 50, 15);
		footright.setRect(centerX, centerY + 20, 50, 15);
	}

	public void moveRight()
	{
		movingRight = true;
		if (ducked == false)
			speedX = MOVESPEED;
	}

	public void moveLeft()
	{
		movingLeft = true;
		if (ducked == false)
			speedX = -MOVESPEED;
	}

	public void stopRight()
	{
		movingRight = false;
		stop();
	}

	public void stopLeft()
	{
		movingLeft = false;
		stop();
	}

	private void stop()
	{
		if (movingLeft == false && movingRight == false)
			speedX = 0;
		else if (movingLeft == true && movingRight == false)
			moveLeft();
		else if (movingLeft == false && movingRight == true)
			moveRight();
	}

	public void shoot()
	{
		if (!readyToFire)
			return;
		Projectile p = new Projectile(centerX + 50, centerY - 25);
		projectiles.add(p);
	}

	public void jump()
	{
		if (jumped == false)
		{
			speedY = JUMPSPEED;
			jumped = true;
		}
	}

	public void duck()
	{
		if (jumped == false)
		{
			ducked = true;
			speedX = 0;
		}
	}

	public void stopDuck()
	{
		ducked = false;
		if (movingLeft == true && movingRight == false)
			moveLeft();
		else if (movingLeft == false && movingRight == true)
			moveRight();
	}

	public int getCenterX()
	{
		return centerX;
	}

	public void setCenterX(int centerX)
	{
		this.centerX = centerX;
	}

	public int getCenterY()
	{
		return centerY;
	}

	public void setCenterY(int centerY)
	{
		this.centerY = centerY;
	}

	public boolean isJumped()
	{
		return jumped;
	}

	public void setJumped(boolean jumped)
	{
		this.jumped = jumped;
	}

	public int getSpeedX()
	{
		return speedX;
	}

	public void setSpeedX(int speedX)
	{
		this.speedX = speedX;
	}

	public int getSpeedY()
	{
		return speedY;
	}

	public void setSpeedY(int speedY)
	{
		this.speedY = speedY;
	}

	public boolean isMovingLeft()
	{
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft)
	{
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight()
	{
		return movingRight;
	}

	public void setMovingRight(boolean movingRight)
	{
		this.movingRight = movingRight;
	}

	public boolean isDucked()
	{
		return ducked;
	}

	public void setDucked(boolean ducked)
	{
		this.ducked = ducked;
	}

	public int getJUMPSPEED()
	{
		return JUMPSPEED;
	}

	public int getMOVESPEED()
	{
		return MOVESPEED;
	}

	public ArrayList<Projectile> getProjectiles()
	{
		return projectiles;
	}

	public boolean isReadyToFire()
	{
		return readyToFire;
	}

	public void setReadyToFire(boolean readyToFire)
	{
		this.readyToFire = readyToFire;
	}
}
