package spacegame.univer.spacefighter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;



public abstract class Bullet implements Destroyable {
  protected int x;
  protected int y;
  protected int minX;
  protected int maxX;
  protected int minY;

  protected int maxY;
  protected int speed = 40;
  protected Context context;
  public boolean isOnScreen = true;
  protected Bitmap pic;
  Bullet(Context context, int screenWidth, int screenHeight, Point topOfPlane) {
  }

  @Override
  public Rect getCollisionRect() {
    return new Rect(x, y, x+pic.getWidth(), y+pic.getHeight());
  }

  @Override
  public void destroy() {
    pic = null;
  }

  public void setOnScreen(boolean onScreen) {
    isOnScreen = onScreen;
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
}
