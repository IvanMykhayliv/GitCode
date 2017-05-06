import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GGJGame extends PApplet {

Lockpick core, handle;

ArrayList<Locklava> locklavas;
ArrayList<TumblePin> pins;
ArrayList<PImage> menuIcons;

PImage lockTumble, meterBackdrop, meterOutline;

PowerMeter power, cops;

int c;
int r, g, b;

int bg;
int gray;

int xLockMinBound;
int xLockMaxBound;
int yLockMinBound;
int yLockMaxBound;
int lockHealth;

int rSelector, rSelector2;

int tarCounter, tarCountMax;

private static final int MENU = 0;
private static final int PLAY = 1;
private static final int BACKSTORY = 2;
private static final int QUIT = 3;
private static final int WIN = 4;
private static final int LOSE = 5;

int gameState = MENU;
int copPower;
int iLevel;

boolean canReset = false;
boolean winTest = true;

//TargetLine tar;

public void setup()
{
  
  frameRate(60);
  noStroke();
 
  xLockMinBound = 188;
  xLockMaxBound = 448;
  yLockMinBound = 200;
  yLockMaxBound = 394;
  lockHealth = 100;
  
  copPower = 1000;
  iLevel = 0;
 
  locklavas = new ArrayList<Locklava>();
  pins = new ArrayList<TumblePin>();
  menuIcons = new ArrayList<PImage>();
  menuIcons.add(loadImage("Play.png"));
  menuIcons.add(loadImage("BackStory.png"));
  menuIcons.add(loadImage("Quit.png"));
  
  core = new Lockpick (200, 100, 16, 16, lockHealth);
  handle = new Lockpick(core.getX() - 64, core.getY() + 16, 80, 16, lockHealth);
 
  power = new PowerMeter(30, 600, 50, -400, 0, 100);
  cops = new PowerMeter(525, 600, 50, -400, 0, copPower);
  
  core.setVelX(mouseX);
  core.setVelY(mouseY);
   
  for(int t = 0; t < 5; t++)
  locklavas.add(new Locklava (180 + (55 * t), 200, 32, 64));
 
  for(int u = 0; u < 4; u++)
  {
  pins.add(
            new TumblePin (216 + (54 * u) + u,
            232,
            16,
            16,
            rSelector = PApplet.parseInt(random(248,378)),
              new TargetLine (216 + (54 * u) + u,
              //230
              rSelector2 = PApplet.parseInt(random(206,230)),
              32,
              2))
              );
  }
  
  tarCounter = 0;
  tarCountMax = 4;
 
  lockTumble = loadImage("LockTumbleFinal.png");
  meterBackdrop = loadImage("PowerMeterBackdrop.png");
  meterOutline = loadImage("PowerMeterOutline.png");
 
  r = 0;
  g = 0;
  b = 0;
  c = color(r, g, b);
 
  gray = 50;
  bg = color(gray);
}

public void draw()
{
  background(bg);
 
 
 if(gameState == MENU){
   iLevel = 0;
   textSize(32);
   fill(255);
   text("Post-Hot Topic Gothpick Simulator", 32, 124);
   
   for(int t = 0; t < menuIcons.size(); t++){
     image(menuIcons.get(t), 75 + (150 * t), 250);
     if(overRect(75 + (150*t), 250, menuIcons.get(t).width, menuIcons.get(t).height)){
       if(mousePressed && mouseButton == LEFT){
         gameState = t + 1;
         if(gameState == PLAY){
           noCursor();
         }
       }
     }
   }
   
 }else if(gameState == PLAY){
   
  textSize(16);
   
  image(lockTumble, 100, 200); //400 x 226
  image(meterBackdrop, 30, 200);
  image(meterBackdrop, 525, 200);
 
  if(mouseX > xLockMinBound && mouseX < xLockMaxBound)
  {
  core.setX(mouseX);
  handle.setX(mouseX - 64);
  }
  else if(mouseX <= xLockMinBound)
  {
  core.setX(xLockMinBound);
  handle.setX(xLockMinBound - 64);
  }
  else
  {
  core.setX(xLockMaxBound);
  handle.setX(xLockMaxBound - 64);
  }
  if(mouseY > yLockMinBound && mouseY < yLockMaxBound)
  {
  core.setY(mouseY);
  handle.setY(mouseY + 16);
  }
  else if(mouseY <= yLockMinBound)
  {
  core.setY(yLockMinBound);
  handle.setY(yLockMinBound + core.getRectHeight());
  }
  else
  {
  core.setY(yLockMaxBound);
  handle.setY(yLockMaxBound + 16);
  }
 
  if(mousePressed && (mouseButton == LEFT))
  {
  power.increasePower(1);
  }
  else
  {
  power.setPower(0);
  }
    
  for(Locklava lav : locklavas)
  {
  if(core.bIntersects(lav) || handle.bIntersects(lav))
  {
  core.decreaseHealth(1);
  handle.setHealth(core.getHealth());
  fill(255);
  //text("Intersecting", 10, 30);
  }
  fill(bg);
  lav.render();
  }
 
  for(TumblePin pin : pins)
  {
  if(pin.getY() <= yLockMinBound + pin.getYOffset() - 4)
    pin.setY(yLockMinBound + pin.getYOffset() - 4);
    
  if(pin.bIntersects(core))
  {
    pin.setY(core.getY() - pin.getRectHeight());
    pin.enableGravity(false);
  }else{
    pin.enableGravity(true);
  }
    
  if(pin.bIntersectsTarget())
  {
    pin.setY(
              pin.getTarget().getY() + pin.getTarget().getH()
             );
    pin.setGravVal(0);
    winTest = winTest && true;
  }else{
    winTest = false;
  }
  fill(255,0,255);
  pin.render();
  pin.getTarget().render();
  }
  
  if(cops.getPercentFilled() == 1 || core.getHealth() == 0){
     gameState = LOSE;
  }
  
  if(winTest == true){
    gameState = WIN;
  }else{
    winTest = true;
  }
 
  fill(255);
  //Lockpick rendering
  core.render();
  handle.render();
 
  fill(255);
 
  r = (int)(255 * power.getPercentFilled());
  g = (int)(255 * (1 - power.getPercentFilled()));
  b = 0;
  c = color(r,g,b);
  fill(c);
  power.render();
  
  r = (int)(255 * cops.getPercentFilled());
  b = (int)(255 * (1 - cops.getPercentFilled()));
  g = 0;
  c = color(r,g,b);
  fill(c);
  cops.render();
  cops.increasePower(1);
  
  image(meterOutline, 5, 187);
  image(meterOutline, 500, 187);
  
  fill(255);
  text("Level: " + iLevel, 12, 16);
  text("Flick Power", 12, 187);
  text("Cop Proximity", 490, 187);
  
 }else if(gameState == BACKSTORY){
   textSize(32);
   fill(200);
   text("You are a member of an ancient lock \npicking cult of which you are \nthe only member. Your parrents were \nonce part of this, but they tragically \ndied in a lockpicking incedent.", 10, 30); 
   text("Click Here to return to the menu.", 50, 500);
 }else if(gameState == QUIT){
   exit();
 }else if(gameState == WIN){
   winTest = true;
   iLevel++;
   cops.setMaxPower(copPower - (10 * iLevel));
   reset();
   gameState = PLAY;
 }else if(gameState == LOSE){
   core.setHealth(lockHealth);
   handle.setHealth(lockHealth);
   textSize(32);
   fill(200);
   text("You Completed: " + iLevel + " Levels", 100, 200);
   reset();
   //iLevel = 0;
   text("Click here to go back to the menu", 50, 500);
 }
 
}

public void mousePressed(){
  if((gameState == BACKSTORY || gameState == LOSE) && mouseY > 450){
    gameState = MENU;
  }
}

public void mouseReleased(){
  for(TumblePin pin : pins)
  {
  if(pin.bIntersects(core))
  {
  pin.setY(pin.getY() - power.getPower());
  pin.enableGravity(false);
  }
  }
}

public boolean overRect(int x, int y, int width, int height)  {
  if (mouseX >= x && mouseX <= x+width && 
      mouseY >= y && mouseY <= y+height) {
    return true;
  } else {
    return false;
  }
}

public void reset(){
  cursor();
  winTest = true;
  
  cops.setPower(0);
  
  pins = new ArrayList<TumblePin>();
  for(int u = 0; u < 4; u++)
  {
  pins.add(
            new TumblePin (216 + (54 * u) + u,
            232,
            16,
            16,
            rSelector = PApplet.parseInt(random(248,378)),
              new TargetLine (216 + (54 * u) + u,
              //230
              rSelector2 = PApplet.parseInt(random(206,230)),
              32,
              2))
              );
  }
}
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
//Can't be lower than 200 for the Y Value (See: Lower Y Bound Var)
class TargetLine
{
  int x, y, w, h;
  
  int c;
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
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GGJGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
