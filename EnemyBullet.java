package spacegame.univer.spacefighter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;



public class EnemyBullet extends Bullet {

  EnemyBullet(Context context, int screenWidth, int screenHeight, Point topOfPlane) {
    super(context, screenWidth, screenHeight, topOfPlane);
    pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_bullet);
    x = topOfPlane.x + pic.getWidth();
    y = topOfPlane.y + pic.getHeight();
    maxX = screenWidth;
    maxY = screenHeight;
    this.context = context;
    speed = 20;
  }

  @Override
  public void update() {
    y += speed;
    if (y >  maxY)
      isOnScreen = false;
  }

}
