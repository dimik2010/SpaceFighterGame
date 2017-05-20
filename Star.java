package spacegame.univer.spacefighter;


import java.util.Random;

public class Star {
  private int speed;
  private float radius;
  private int x;
  private int y;
  private int screenHeight, screenWeight;
  Random random;

  public Star(int screenHeight, int screenWeight) {
    this.screenHeight = screenHeight;
    this.screenWeight = screenWeight;
    random = new Random();
    radius = random.nextFloat() * (4.0f-1.0f) + 1.0f;
    speed = random.nextInt(10);
    x = random.nextInt(screenWeight);
    y = random.nextInt(screenHeight);
  }

  public void update(int playerSpeed)
  {
    y += speed;
    y += playerSpeed;
    if (y > screenHeight) {
      speed = random.nextInt(10);
      x = random.nextInt(screenWeight);
      y = 0;
    }
  }

  public float getRadius() {
    return radius;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
