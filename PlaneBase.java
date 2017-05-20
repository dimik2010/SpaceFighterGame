package spacegame.univer.spacefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;


public abstract class PlaneBase implements Destroyable {
  public static final int LIFE_NUMS = 4;
  protected int x;
  protected int y;
  protected int minX;
  protected int maxX;
  protected int minY;
  protected int maxY;
  protected Bitmap pic;
  protected Context context;
  protected int lifes = LIFE_NUMS;
  protected int deathTime;
  protected boolean destroyed = false;
  @Override
  public Rect getCollisionRect() {
    return new Rect(x, y, x+pic.getWidth(), y+pic.getHeight());
  }

  @Override
  public void destroy() {
    if (lifes > 0) {
      lifes--;
    } else if (!destroyed) {
      destroyed = true;
      pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion);
    }
  }

  public boolean isDestroyed() {
    return destroyed;
  }

  public int getDeathTime() {
    return deathTime;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Bitmap getPic() {
    return pic;
  }

  abstract public Bullet createBullet();

}
