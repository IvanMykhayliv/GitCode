void setup()
{
  size(600, 600);
  frameRate(13);
}

void draw()
{
  grid();
}

void grid()
{
  int squareSize = 20;
  for(int i = 0; i < width; i += squareSize)
  {
    for(int j = 0; j < height; j += squareSize)
    {
      stroke(255, 0, 222);
      fill(0);
      textSize(squareSize);
      rect(i, j, squareSize, squareSize);
      fill(149, 0, 112, 200);
      text((int) random(2), i+5, j + squareSize);
    }
  }
}
