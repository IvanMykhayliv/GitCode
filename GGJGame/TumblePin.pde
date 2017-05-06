class TumblePin
{
  private int x;
  private int y;
  private int yOffset;
  private int wdth;
  private int hght;
  private int bottom;
  
  private float gravity;
  
  private boolean bGravity;
  
  public boolean bCanMove;
  
  private TargetLine tar;
  
  TumblePin(int xCor, int yCor, int w, int h, int yStop)
  {
    bCanMove = false;
    x = xCor;
    y = yCor;
    wdth = w;
    hght = h;
    yOffset = 12;
    gravity = 1;
    bottom = yStop;
    bGravity = true;
  }
  
  TumblePin(int xCor, int yCor, int w, int h, int yStop, TargetLine t)
  {
    bCanMove = false;
    x = xCor;
    y = yCor;
    wdth = w;
    hght = h;
    yOffset = 12;
    gravity = 1;
    bottom = yStop;
    bGravity = true;
    tar = t;
  }
 
  public int getX()
  {
  return x;
  }
 
  public void setX(int xCor)
  {
  x = xCor;
  }
 
  public int getY()
  {
  return y;
  }
 
  public void setY(int yCor)
  {
  y = yCor;
  }
 
 public int getYOffset()
  {
  return yOffset;
  }
 
  public void setYOffset(int yO)
  {
  yOffset = yO;
  }
 
  public int getRectWidth()
  {
  return wdth;
  }
 
  public void setRectWidth(int diffWidth)
  {
  wdth = diffWidth;
  }
 
  public int getRectHeight()
  {
  return hght;
  }
 
  public void setRectHeight(int diffHeight)
  {
  hght = diffHeight;
  }
 
  public boolean bIntersects(Lockpick l)
  {
    if(
      l.getX() + l.getRectWidth() >= x &&
      l.getX() <= x + wdth &&
      l.getY() + l.getVelY() >= y + yOffset &&
      l.getY() + l.getVelY() <= y + hght
      ){
      
      return true;
    }else{
      return false;
    }
  }
  
  public boolean bIntersectsTarget()
  {
    if(
        y <= tar.getY() + tar.getH()
      )
      {
        return true;
      }
      else
      {
        return false;
      }
  }
  
  public void enableGravity(boolean g){
    bGravity = g;
  }
  
  public boolean getGravity()
  {
    return bGravity;
  }
  
  public float getGravVal()
  {
    return gravity;
  }
  
  public void setGravVal(float g)
  {
    gravity = g;
  }
  
  public TargetLine getTarget()
  {
    return tar;
  }
 
  public void render()
  {
    if(bGravity)
    {
      if(y < bottom)
      {
        y += gravity;
      }
    }
    rect(x, y, wdth, hght);
  }
}