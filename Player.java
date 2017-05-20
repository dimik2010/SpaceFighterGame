package spacegame.univer.spacefighter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;


public class Player extends PlaneBase {
  private final int SPEED_MAX = 40;
  private final int GRAVITY_STOP = -10;
  private final int SPEED_MIN = 1;
  private final int HORIZONTAL_SPEED_MAX = 10;
  private boolean boost = false;
  private int speed = 1;
  private int horizontalSpeed = HORIZONTAL_SPEED_MAX/2;
  private boolean movingLeft;
  private boolean movingRight;
  public Player(Context context, int screenWidth, int screenHeight) {

    pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.plane);

    this.x = screenWidth/2;
    this.context = context;
    this.minX = 0;
    this.maxY = screenHeight-pic.getHeight();
    this.minY = 0;
    this.maxX = screenWidth-pic.getWidth();
    this.y = maxY;
  }
  @Override
  public void update () {
    if (!destroyed) {
      if (boost)
        speed += 4;
      else
        speed -= 2;
      if (speed > SPEED_MAX)
        speed = SPEED_MAX;
      else if (speed < SPEED_MIN)
        speed = SPEED_MIN;
      y -= speed + GRAVITY_STOP;
      if (y < minY)
        y = minY;
      else if (y > maxY)
        y = maxY;
      if (movingLeft) {
        x -= horizontalSpeed;
        if (x < minX)
          x = minX;
      }
      if (movingRight) {

        x += horizontalSpeed;
        if (x > maxX)
          x = maxX;
      }
    } else {
      deathTime++;
    }
  }

  public void setBoost(boolean boost) {
    this.boost = boost;
  }

  public void setMovingLeft(boolean movingLeft) {
    this.movingLeft = movingLeft;
  }

  public void setMovingRight(boolean movingRight) {
    this.movingRight = movingRight;
  }
  public int getSpeed() {
    return speed;
  }

  public void setHorizontalSpeed(int horizontalSpeed) {
    this.horizontalSpeed = horizontalSpeed;
  }

  public int getLifes() {
    return lifes;
  }

  @Override
  public Bullet createBullet() {
    Point top = new Point(x + pic.getWidth() / 2, y + 1);
    return new PlayerBullet(context, maxX + pic.getWidth(), maxY + pic.getHeight(), top);
  }
}
