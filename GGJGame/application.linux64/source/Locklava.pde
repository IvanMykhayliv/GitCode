class Locklava
{
  private int coreX;
  private int coreY;
  private int rectWidth;
  private int rectHeight;
  
  Locklava(int x, int y, int rectW, int rectH)
  {
    coreX = x;
    coreY = y;
    rectWidth = rectW;
    rectHeight = rectH;
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
  
    public int getRectWidth()
  {
    return rectWidth;
  }
 
  public void setRectWidth(int w)
  {
    rectWidth = w;
  }
 
  public int getRectHeight()
  {
    return rectHeight;
  }
 
  public void setRectHeight(int h)
  {
    rectHeight = h;
  }
 
  public void render()
  {
    rect(coreX, coreY, rectWidth, rectHeight);
  }
}