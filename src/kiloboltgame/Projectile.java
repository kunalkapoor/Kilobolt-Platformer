package kiloboltgame;

import java.awt.Rectangle;

public class Projectile
{
	private int x, y, speedX;
	private boolean visible;
	private Rectangle r;

	private Background bg;

	public Projectile(int startX, int startY)
	{
		bg = StartingClass.getBg1();
		
		x = startX;
		y = startY;
		speedX = 7;
		visible = true;

		r = new Rectangle(0, 0, 0, 0);
	}

	public void update()
	{
		x += speedX;
		r.setBounds(x, y, 10, 5);
		
		if (x > 800)
		{
			visible = false;
			r = null;
		}
		else
			checkCollision();
		
	}

	private void checkCollision()
	{
		if(r.intersects(StartingClass.hb1.r))
		{
			visible = false;
			StartingClass.score += 1;
			
			if(--StartingClass.hb1.health <= 0)
			{
				StartingClass.hb1.setCenterX(-100);
				StartingClass.score += 5;
			}
		}
		
		if(r.intersects(StartingClass.hb2.r))
		{
			visible = false;
			StartingClass.score += 1;
			
			if(--StartingClass.hb2.health <= 0)
			{
				StartingClass.hb2.setCenterX(-100);
				StartingClass.score += 5;
			}
		}
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getSpeedX()
	{
		return speedX;
	}

	public void setSpeedX(int speedX)
	{
		this.speedX = speedX;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
}
