package testing;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;

public class CalibrateColourSensors {

	public static void main(String[] args) throws InterruptedException {
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		Keys keys = ev3brick.getKeys();
		EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S2);
		SensorMode sm = colorSensor.getRGBMode();
		
		float[] samples = new float[sm.sampleSize()];

		while (keys.getButtons() != Keys.ID_ESCAPE) {
			Delay.msDelay(2);
			sm.fetchSample(samples, 0);
			Thread.sleep(50);
			
			LCD.clear();
			for (int j = 0; j < 3; j++) {
				LCD.drawString("Right: " + samples[j], 1, j);
			}

		}
		colorSensor.close();
		
	}

}
