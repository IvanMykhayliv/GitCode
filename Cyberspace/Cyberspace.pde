void setup()
{
  size(600,600);
  frameRate(13);
}

void draw()
{
  grid();
}

void grid()
{
  int bigness = 20;
  for(int i = 0; i < width; i+=bigness)
  {
    for(int j = 0; j < height; j+=bigness)
    {
      stroke(255,0,222);
      fill(0);
      textSize(bigness);
      rect(i,j,bigness,bigness);
      fill(149,0,112,200);
      text((int)random(2),i+5,j+bigness);
    }
  }
}
