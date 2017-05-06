class Lockpick
{
  private int coreX;
  private int coreY;
  private int velX;
  private int velY;
  private int rectWidth;
  private int rectHeight;
  private float maxHealth;
  private float currentHealth;
 
  Lockpick(int x, int y, int rectW, int rectH, float health)
  {
    coreX = x;
    coreY = y;
    velX = 0;
    velY = 0;
    rectWidth = rectW;
    rectHeight = rectH;
    maxHealth = health;
    currentHealth = maxHealth;
  }
 
  public int getX()
  {
    return coreX;
  }
 
  public void setX(int xCor)
  {
    coreX = xCor;
  }
 
  public int getY()
  {
    return coreY;
  }
 
  public void setY(int yCor)
  {
    coreY = yCor;
  }
 
  public int getVelX()
  {
  return velX;
  }
 
  public void setVelX(int vX)
  {
  velX = vX;
  }
 
  public int getVelY()
  {
  return velY;
  }
 
  public void setVelY(int vY)
  {
  velY = vY;
  }
 
  public int getRectWidth()
  {
    return rectWidth;
  }
 
  public void setRectWidth(int diffWidth)
  {
    rectWidth = diffWidth;
  }
 
  public int getRectHeight()
  {
    return rectHeight;
  }
 
  public void setRectHeight(int diffHeight)
  {
    rectHeight = diffHeight;
  }
 
  public float getHealth(){
    return currentHealth;
  }
  
  public void setHealth(float heal){
     currentHealth = heal;
  }
  
  public void increaseHealth(float i){
    if(currentHealth + i > maxHealth){
      currentHealth = maxHealth;
    }else{
      currentHealth += i;
    }
  }
  
  public void decreaseHealth(float i){
    if(currentHealth - i < 0){
      currentHealth = 0;
    }else{
      currentHealth -= i;
    }
  }

  public boolean bIntersects(Locklava l)
  {
    if(
      coreX + rectWidth >= l.getX() &&
      coreX <= l.getX() + l.getRectWidth() &&
      coreY + rectHeight >= l.getY() &&
      coreY <= l.getY() + l.getRectHeight()
      )
      
      return true;
      else
        return false;
    }
 
  public void render()
  {
    fill(255 - (255*(float)(currentHealth/maxHealth)), 75, 75);
    rect(coreX, coreY, rectWidth, rectHeight);
  }
}