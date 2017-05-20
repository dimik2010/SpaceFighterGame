package spacegame.univer.spacefighter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;


public class GameView extends SurfaceView implements Runnable {
  private volatile boolean playing;
  public static final Object changeBullets = new Object();
  public static final int DESTROY_TIME = 10;
  public static final int ENEMY_BULLET_WAIT = 20;
  private final int MAX_ENEMY_NUM = 4;
  private int enemiesNum;
  private Thread gameThread = null;
  private Paint paint;
  private Canvas canvas;
  private SurfaceHolder surfaceHolder;
  private Player player;
  private Star[] stars;
  private List<Enemy> enemies;
  private List<Bullet> bullets;
  private Context context;
  private int screenWidth;
  private int screenHeight;

  public GameView(Context context, int screenWidth, int screenHeight, Sensor senAccelerometer, SensorManager sensorManager) {
    super(context);
    this.context = context;
    playing = true;
    surfaceHolder = getHolder();
    paint = new Paint();
    player = new Player(context, screenWidth, screenHeight);
    enemies = new ArrayList<>();
    bullets = new ArrayList<>();
    sensorManager.registerListener(new AccelerometerListener(player), senAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    this.screenHeight = screenHeight;
    this.screenWidth = screenWidth;
    stars = new Star[100];
    for (int i = 0; i < stars.length; i++) {
      stars[i] = new Star(screenHeight, screenWidth);
    }
  }

  public void onPause() {
    playing = false;
    try {
      gameThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void onResume() {
    playing = true;
    gameThread = new Thread(this);
    gameThread.start();
  }

  @Override
  public void run() {
    while (playing) {
      update();
      draw();
      control();
    }
  }

  private void control() {
    try {
      gameThread.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void draw() {
    if (surfaceHolder.getSurface().isValid()) {
      canvas = surfaceHolder.lockCanvas();
      if (canvas == null) {
        return;
      }
      canvas.drawColor(Color.BLACK);
      for (Star star : stars) {
        paint.setColor(Color.WHITE);
        canvas.drawCircle(star.getX(), star.getY(), star.getRadius(), paint);
      }
      canvas.drawBitmap(player.getPic(), player.getX(), player.getY(), paint);
      for (Enemy enemy : enemies)
        canvas.drawBitmap(enemy.getPic(), enemy.getX(), enemy.getY(), paint);
      for (int i = 0; i < bullets.size(); ++i) {
        canvas.drawBitmap(bullets.get(i).getPic(), bullets.get(i).getX(), bullets.get(i).getY(), paint);
      }
      paint.setTextAlign(Paint.Align.RIGHT);
      paint.setTextSize(50);
      canvas.drawText(("LIFES " + Integer.toString(player.getLifes()+1)), screenWidth, 50, paint);
      if (player.isDestroyed()) {
        if (player.getDeathTime() > DESTROY_TIME) {
          paint.setTextSize(140);
          paint.setTextAlign(Paint.Align.CENTER);
          canvas.drawText("GAME OVER", screenWidth / 2, screenHeight / 2, paint);
          playing = false;
        }
      }
      surfaceHolder.unlockCanvasAndPost(canvas);
    }
  }

  private void update() {

    Bullet bullet;
    if (enemies.size() == 0) {
      if (enemiesNum < MAX_ENEMY_NUM) {
        enemiesNum++;
      }
      for (int i = 0; i < enemiesNum; i++) {
        enemies.add(new Enemy(context, screenWidth, screenHeight));
      }
    } else {
      for (int i = 0; i < enemies.size(); i++) {
        enemies.get(i).update();
        if ((enemies.get(i).isDestroyed())) {
          if (enemies.get(i).getDeathTime() > DESTROY_TIME)
            enemies.remove(i);
        } else {
          bullet = enemies.get(i).createBullet();
          if (bullet != null)
            bullets.add(bullet);
        }
      }
    }
    if (bullets.size() != 0) {
      for (int i = 0; i < bullets.size(); i++) {
        synchronized (changeBullets) {
          if (!bullets.get(i).isOnScreen) {
            bullets.remove(i);
            continue;
          }
          bullets.get(i).update();
        }
        for (Enemy enemy : enemies) {
          if (Rect.intersects(player.getCollisionRect(), enemy.getCollisionRect())) {
            player.destroy();
          } else if (Rect.intersects(enemy.getCollisionRect(), bullets.get(i).getCollisionRect())) {
            enemy.destroy();
            bullets.remove(i);
            break;
          } else if (Rect.intersects(player.getCollisionRect(), bullets.get(i).getCollisionRect())) {
            player.destroy();
            bullets.remove(i);
            break;
          }
        }
      }
    }
    player.update();
    for (Star star : stars) {
      star.update(player.getSpeed());
    }

  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int y = (int) event.getY();
    switch (event.getAction() & MotionEvent.ACTION_MASK) {
      case MotionEvent.ACTION_DOWN:
        if (y > screenHeight - 400) {
          synchronized (changeBullets) {
            bullets.add(player.createBullet());
          }
        } else {
          player.setBoost(true);
        }
        break;
      case MotionEvent.ACTION_UP:
        player.setBoost(false);
        break;
    }
    return true;
  }
}
