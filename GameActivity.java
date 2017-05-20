package spacegame.univer.spacefighter;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

public class GameActivity extends AppCompatActivity {
  private GameView gameView;
  private SensorManager sensorManager;
  private Sensor senAccelerometer;
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Display display = getWindowManager().getDefaultDisplay();
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    Point size = new Point();
    display.getRealSize(size);
    gameView = new GameView(this, size.x, size.y, senAccelerometer, sensorManager);
    setContentView(gameView);
  }

  @Override
  protected void onPause() {
    super.onPause();
    gameView.onPause();
  }

  @Override
  protected void onResume() {
    super.onResume();
    gameView.onResume();
  }

}
