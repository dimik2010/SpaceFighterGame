package spacegame.univer.spacefighter;

import android.graphics.Rect;


public interface Destroyable {
  public Rect getCollisionRect();
  public void update();
  public void destroy();


}
