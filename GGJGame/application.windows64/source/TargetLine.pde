//Can't be lower than 200 for the Y Value (See: Lower Y Bound Var)
class TargetLine
{
  int x, y, w, h;
  
  color c;
  int r, g, b;
  
  
  TargetLine(int xCor, int yCor, int wdth, int hght)
  {
    x = xCor;
    y = yCor;
    w = wdth;
    h = hght;
    
    r = 255;
    g = 0;
    b = 0;
    
    c = color(r,g,b);
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
  
  public int getW()
  {
    return w;
  }
 
  public int getH()
  {
    return h;
  }
  
  public void setC(int red, int green, int blue)
  {
    r = red;
    g = green;
    b = blue;
  }
  
  public void render()
  {
    fill(c);
    rect(x,y,w,h);
  }
}