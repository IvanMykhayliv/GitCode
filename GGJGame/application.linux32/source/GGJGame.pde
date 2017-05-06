Lockpick core, handle;

ArrayList<Locklava> locklavas;
ArrayList<TumblePin> pins;
ArrayList<PImage> menuIcons;

PImage lockTumble, meterBackdrop, meterOutline;

PowerMeter power, cops;

color c;
int r, g, b;

color bg;
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

void setup()
{
  size(600, 600);
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
            rSelector = int(random(248,378)),
              new TargetLine (216 + (54 * u) + u,
              //230
              rSelector2 = int(random(206,230)),
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

void draw()
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

void mousePressed(){
  if((gameState == BACKSTORY || gameState == LOSE) && mouseY > 450){
    gameState = MENU;
  }
}

void mouseReleased(){
  for(TumblePin pin : pins)
  {
  if(pin.bIntersects(core))
  {
  pin.setY(pin.getY() - power.getPower());
  pin.enableGravity(false);
  }
  }
}

boolean overRect(int x, int y, int width, int height)  {
  if (mouseX >= x && mouseX <= x+width && 
      mouseY >= y && mouseY <= y+height) {
    return true;
  } else {
    return false;
  }
}

void reset(){
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
            rSelector = int(random(248,378)),
              new TargetLine (216 + (54 * u) + u,
              //230
              rSelector2 = int(random(206,230)),
              32,
              2))
              );
  }
}