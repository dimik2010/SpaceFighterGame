package spacegame.univer.spacefighter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;


public class Enemy extends PlaneBase {
  private final int SPEED_MAX = 10;
  private int nextY;
  private int nextX;
  private int speedX = SPEED_MAX / 2;
  private int speedY = SPEED_MAX / 2;
  private Random rand;
  private int bulletDelay;
  private int bulletDelayCounter;


  public Enemy(Context context, int screenWidth, int screenHeight) {
    rand = new Random();
    pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);
    this.minX = 0;
    this.maxY = screenHeight - pic.getHeight();
    this.minY = 0;
    this.maxX = screenWidth - pic.getWidth();
    this.x = rand.nextInt(maxX);
    this.y = 0;
    this.context = context;
    nextX = rand.nextInt(maxX);
    speedX = (int) Math.copySign(speedX, (nextX - x));
    speedY = (int) Math.copySign(speedY, (nextY - y));
    bulletDelay = rand.nextInt(GameView.ENEMY_BULLET_WAIT) + GameView.ENEMY_BULLET_WAIT;
  }

  @Override
  public void update() {
    if (!destroyed) {
      if (!(x <= nextX + Math.abs(speedX) && x >= nextX - Math.abs(speedX))) {
        x += speedX;
      } else {
        nextX = rand.nextInt(maxX);
        speedX = (int) Math.copySign(speedX, (nextX - x));
      }
      if (!(y <= nextY + Math.abs(speedY) && y >= nextY - Math.abs(speedY))) {
        y += speedY;
      } else {
        nextY = rand.nextInt(maxY / 2);
        speedY = (int) Math.copySign(speedY, (nextY - y));
      }
    } else {
      deathTime++;
    }
  }

  @Override
  public Rect getCollisionRect() {
    return new Rect(x, y, x + pic.getWidth(), y + pic.getHeight());
  }



  public Bullet createBullet() {
    if (bulletDelayCounter > bulletDelay) {
      Point top = new Point(x + pic.getWidth() / 2, y + pic.getHeight());
      bulletDelayCounter = 0;
      return new EnemyBullet(context, maxX + pic.getWidth(), maxY + pic.getHeight(), top);
    }
    bulletDelayCounter++;
    return null;
  }




}
