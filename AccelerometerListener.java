package spacegame.univer.spacefighter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


public class AccelerometerListener implements SensorEventListener {
  private float lastValue;
  private Player player;
  private final int MOVING_SPEED = 5;

  public AccelerometerListener(Player player) {
    this.player = player;
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    float xChange = lastValue - event.values[0];
    if (xChange < -1) {
      player.setMovingRight(false);
      player.setHorizontalSpeed((int)(Math.abs(xChange) * MOVING_SPEED));
      player.setMovingLeft(true);
    } else if (xChange > 1) {
      player.setMovingLeft(false);
      player.setHorizontalSpeed((int)(xChange * MOVING_SPEED));
      player.setMovingRight(true);
    } else {
      player.setMovingLeft(false);
      player.setMovingRight(false);
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {

  }
}
