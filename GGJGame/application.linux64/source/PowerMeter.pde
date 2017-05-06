class PowerMeter{
  
  private int x;
  private int y;
  private int wdth;
  private int hght;
  private int currentPower;
  private int minPower;
  private int maxPower;
  
  PowerMeter(int xLoc, int yLoc, int w, int h, int minPow, int maxPow){
    x = xLoc;
    y = yLoc;
    wdth = w;
    hght = h;
    minPower = minPow;
    maxPower = maxPow;
    currentPower = minPower;
  }
  
  public void increasePower(int i){
    if(currentPower != maxPower)
      currentPower += i;
    if(currentPower > maxPower)
      currentPower = maxPower;
  }
  
  public void decreasePower(int i){
    if(currentPower != minPower)
      currentPower -= i;
    if(currentPower < minPower)
      currentPower = minPower;
  }
  
  public int getX(){
    return x;
  }
  
  public int getY(){
    return y;
  }
  
  public int getPower(){
    return currentPower;
  }
  
  public void setX(int sx){
    x = sx;
  }
  
  public void setY(int sy){
    y = sy;
  }
  
  public void setPower(int p){
    currentPower = p;
  }
  
  public void setMinPower(int minP){
    minPower = minP;
  }
  
  public void setMaxPower(int maxP){
    maxPower = maxP;
  }
  
  public float getPercentFilled(){
    return (float)((currentPower - minPower) / (float)(maxPower - minPower));
  }
  
  public void render(){
    float tmpPcnt = getPercentFilled();
    rect(x, y, wdth, (float)(hght * tmpPcnt));
  }
  
}