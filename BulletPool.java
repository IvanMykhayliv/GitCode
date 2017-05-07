package com.game.source.main;

import java.awt.Graphics2D;

//Game code for an Object Pool of Bullets
public class BulletPool 
{
	private Game gam;
	private Origin intendedO;
	private boolean bIsShooting;
	private int inVelX, inVelY, inMaxLimit, cooldownCounter;
	private Bullet[] bullets;
	private int MAX_BULLET_SIZE;
	private int numBulActive;
	
	/*public BulletPool(int bulPoolSize, int bulSize, int iML, Game ga)
	{
		gam = ga;
		MAX_BULLET_SIZE = bulPoolSize;
		numBulActive = 0;
		bullets = new Bullet[MAX_BULLET_SIZE];
		inMaxLimit = iML;
		cooldownCounter = 0;
		bIsShooting = false;
		
		for(int i = 0; i < MAX_BULLET_SIZE; i++)
		{
			//bullets[i].disableUpdate();
			//bullets[i].disableRender();
			bullets[i] = new Bullet(0,0,bulSize,bulSize);
		}
	}*/
	
	public BulletPool(int bulPoolSize, int bulSize, Game ga, Origin nO, int iVelX, int iVelY, int iML)
	{
		gam = ga;
		intendedO = nO;
		bIsShooting = false;
		inVelX = iVelX;
		inVelY = iVelY;
		MAX_BULLET_SIZE = bulPoolSize;
		//numBulActive = 0;
		bullets = new Bullet[MAX_BULLET_SIZE];
		inMaxLimit = iML;
		cooldownCounter = 0;
		
		for(int i = 0; i < MAX_BULLET_SIZE; i++)
		{
			//bullets[i].disableUpdate();
			//bullets[i].disableRender();
			bullets[i] = new Bullet(0,0,bulSize,bulSize);
		}
	}
	
	private void checkForPlats(int index)
	{
		/*for(int i = 0; i < numBulActive; i++)
		for(int i = 0; i < MAX_BULLET_SIZE; i++)
		{
			//if(bullets[i].stopAtPlats())
			{
				//deactivateBullet(i);
			}
		}*/
		if(bullets[index].getX() < 0 - bullets[index].getVelX())
		{
			deactivateBullet(index);
		}
		
		if(bullets[index].getX()  > (Game.WIDTH * Game.SCALE) - bullets[index].getW()  + 9 - bullets[index].getVelX())
		{
			deactivateBullet(index);		
		}

		if(bullets[index].getY() < 0 - bullets[index].getVelY())
		{
			deactivateBullet(index);
		}
		
		if (bullets[index].getY() > (Game.HEIGHT * Game.SCALE) - bullets[index].getH() + 9 - bullets[index].getVelY())
		{
			deactivateBullet(index);
		}
		
		for(Platform tempPlat: gam.getPlayBounds())
		{
			if
			(
					/*
					(Collisions.collidesLeft(bullets[index], tempPlat) && tempPlat.bIsLeftSolid()) ||
					(Collisions.collidesUp(bullets[index], tempPlat) && tempPlat.bIsTopSolid()) ||
					(Collisions.collidesRight(bullets[index], tempPlat) && tempPlat.bIsRightSolid()) ||
					(Collisions.collidesDown(bullets[index], tempPlat) && tempPlat.bIsBottomSolid()) ||
					*/
					(Collisions.collidesIn(bullets[index], tempPlat))
			)
			{
				deactivateBullet(index);
				//System.out.println("Collided");
			}
		}
			//return false;
	}
	
	public void update()
	{	
		for(int i = 0; i < numBulActive; i++)
		{
			checkForPlats(i);
			//bullets[i].update();
		}
		
		//Prevents all the bullets from getting OOB rather than just some of them using
		//only one for loop.
		for(int i = 0; i < numBulActive; i++)
		{
				//checkForPlats(i);
				bullets[i].update();
		}
		
		if(bIsShooting)
			cooldown();
	}
	
	public void render(Graphics2D g, float interp)
	{
		for(int i = 0; i < numBulActive; i++)
		//for(int i = 0; i < MAX_BULLET_SIZE; i++)
		{
			bullets[i].render(g, interp);
		}
	}
	
	public void fireBullet()
	{
	if(numBulActive < MAX_BULLET_SIZE && cooldownCounter <= 0)
	{
		/*bullets[numBulActive].setOrigin(intendedO);
		bullets[numBulActive].activate();
		bullets[numBulActive].setVel(inVelX, inVelY);
		numBulActive++;*/
		activateBullet(numBulActive, intendedO);
		bIsShooting = true;
		//cooldown();
	}
	/*else if (numBulActive < 0 || numBulActive > MAX_BULLET_SIZE)
	{
		numBulActive = 0;
	}*/
	}
	
	public int getIntendedVelX() { return inVelX; }
	
	public int getIntendedVelY() { return inVelY; }
	
	public Origin getIntendedOrigin() { return intendedO; }
	
	public void setIntendedVelX(int iVelX) { inVelX = iVelX; }

	public void setIntendedVelY(int iVelY) { inVelY = iVelY; }
	
	public void setIntendedVel(int iVelX, int iVelY)
	{
		inVelX = iVelX;
		inVelY = iVelY;
	}
	
	public void setIntendedOrigin(Origin nO) { intendedO = nO; }
	
	public void setIntendedVelAndOrigin(int iVelX, int iVelY, Origin nO)
	{
		inVelX = iVelX;
		inVelY = iVelY;
		intendedO = nO;
	}
	private void activateBullet(int index, Origin o)
	{
		if(index >= numBulActive)
		{
			Bullet tempBul = bullets[numBulActive];
			bullets[numBulActive] = bullets[index];
			bullets[index] = tempBul;
			bullets[numBulActive].setOrigin(o);
			bullets[numBulActive].activate();
			bullets[numBulActive].setVel(inVelX, inVelY);
			
			numBulActive++;
		}
	}
	
	private void deactivateBullet(int index)
	{
		if(index < numBulActive)
		{
			numBulActive--;

			Bullet tempBul = bullets[numBulActive];
			bullets[numBulActive] = bullets[index];
			bullets[index] = tempBul;
			bullets[numBulActive].deactivate();
		}
	}
	public void dB(int index)
	{
		deactivateBullet(index);
	}
	private void cooldown()
	{
		cooldownCounter++;
		
		if(cooldownCounter > inMaxLimit)
		{
			cooldownCounter = inMaxLimit;
			cooldownCounter = 0;
			bIsShooting = false;
		}
	}
	
	public Bullet[] getPool()
	{
		return bullets;
	}
	
	public Bullet getBullets(int index)
	{
		return bullets[index];
	}
	
	public int getNA()
	{
		return numBulActive;
	}
}
