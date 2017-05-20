package spacegame.univer.spacefighter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;


public class PlayerBullet extends Bullet {
  PlayerBullet(Context context, int screenWidth, int screenHeight, Point topOfPlane) {
    super(context, screenWidth, screenHeight, topOfPlane);
    pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_bullet);
    x = topOfPlane.x - pic.getWidth();
    y = topOfPlane.y-pic.getHeight()-1;
    maxX = screenWidth;
    maxY = screenHeight;
    this.context = context;

  }

  @Override
  public void update() {
    y -= speed;
    if (y < 0 - pic.getHeight())
      isOnScreen = false;
  }
}
