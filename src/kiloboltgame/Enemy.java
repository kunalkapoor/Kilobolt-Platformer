package kiloboltgame;

import java.awt.Rectangle;

public class Enemy
{
	private int maxHealth, currentHealth, power, speedX, centerX, centerY;
	public Rectangle r;
	private Background bg;
	private Robot robot;

	public int health;
	private int movementSpeed;

	public Enemy()
	{
		r = new Rectangle(0, 0, 0, 0);
		bg = StartingClass.getBg1();
		robot = StartingClass.getRobot();

		health = 5;
	}

	public void update()
	{
		follow();

		centerX += speedX;
		speedX = bg.getSpeedX() * 5 + movementSpeed;

		r.setBounds(centerX - 25, centerY - 25, 50, 60);

		if (r.intersects(Robot.yellowRed))
			checkCollision();
	}

	private void checkCollision()
	{
		if (r.intersects(Robot.rect) || r.intersects(Robot.rect2) || r.intersects(Robot.rect3) || r.intersects(Robot.rect4))
			System.out.println("Collision");
	}

	private void follow()
	{
		if (centerX < -95 || centerX > 810)
			movementSpeed = 0;
		else if(Math.abs(robot.getCenterX() - centerX) < 5)
			movementSpeed = 0;
		else
		{
			if(robot.getCenterX() >= centerX)
				movementSpeed = 1;
			else
				movementSpeed = -1;
		}
	}

	public void die()
	{
	}

	public void attack()
	{
	}

	public int getMaxHealth()
	{
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth)
	{
		this.maxHealth = maxHealth;
	}

	public int getCurrentHealth()
	{
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth)
	{
		this.currentHealth = currentHealth;
	}

	public int getPower()
	{
		return power;
	}

	public void setPower(int power)
	{
		this.power = power;
	}

	public int getSpeedX()
	{
		return speedX;
	}

	public void setSpeedX(int speedX)
	{
		this.speedX = speedX;
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

	public Background getBg()
	{
		return bg;
	}

	public void setBg(Background bg)
	{
		this.bg = bg;
	}
}
