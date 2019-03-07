
import java.util.Arrays;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.SampleProvider;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class EV3Setup {

	private static MovePilot pilot;

	private static EV3ColorSensor cs;

	private static EV3IRSensor ir;

	public static void main(String[] args) {
		// Brick setup
		EV3 ev3brick = (EV3) BrickFinder.getLocal();
		// Wait for input
		waitForAnyPress(ev3brick.getKeys());
		while (ev3brick.getKeys().getButtons() != Keys.ID_ESCAPE) {
			float[] colourSampleRaw = getColourSample();

			float[] irSampleRaw = getIRSample();
			String[] colourSample = new String[colourSampleRaw.length];
			String[] irSample = new String[irSampleRaw.length];

			for (int i = 0; i < colourSampleRaw.length; i++) {
				colourSample[i] = String.valueOf(colourSampleRaw[i]);
			}
			for (int i = 0; i < irSampleRaw.length; i++) {
				irSample[i] = String.valueOf(irSampleRaw[i]);
			}
			LCD.drawString(Arrays.deepToString(colourSample), 0, 0);
			LCD.drawString(Arrays.deepToString(irSample), 0, 1);
			Delay.msDelay(1000);
		}
		// setup arbitratorInit and then start it
		arbitratorInit();
	}

	private static void setPilot(MovePilot givenPilot) {
		pilot = givenPilot;
	}

	public static MovePilot getPilot() {
		if (pilot == null)
			setPilot(pilotInit());
		return pilot;
	}

	// colour sensor getter and setter
	private static void setColourSensor(EV3ColorSensor givencs) {
		cs = givencs;
	}

	public static EV3ColorSensor getColourSensor() {
		if (cs == null)
			setColourSensor(colourSensorInit());
		return cs;
	}
	//

	// ir sensor getter and setter
	private static void setIRSensor(EV3IRSensor givenir) {
		ir = givenir;
	}

	public static EV3IRSensor getIRSensor() {
		if (ir == null)
			setIRSensor(irSensorInit());
		return ir;
	}
	//

	// get colour and ir samples
	public static float[] getColourSample() {
		SensorMode sm = getColourSensor().getRGBMode();
		float[] sample = new float[sm.sampleSize()];
		sm.fetchSample(sample, 0);
		return sample;
	}

	public static float[] getIRSample() {
		SensorMode sm = getIRSensor().getDistanceMode();
		float[] sample = new float[sm.sampleSize()];
		sm.fetchSample(sample, 0);
		return sample;
	}
	//

	private static void waitForAnyPress(Keys keys) {
		LCD.clear();
		LCD.drawString("Press any key...", 5, 0);
		keys.waitForAnyPress();
		LCD.clear();
	}

	// init
	private static EV3IRSensor irSensorInit() {
		EV3IRSensor irSensor;
		irSensor = new EV3IRSensor(SensorPort.S1);
		return irSensor;
	}

	private static EV3ColorSensor colourSensorInit() {
		return new EV3ColorSensor(SensorPort.S2);
	}

	private static void arbitratorInit() {
		// Start Arbitrator
		new Arbitrator(new Behavior[] {}).go();
	}

	public static MovePilot pilotInit() {
		// Motor setup
		EV3LargeRegulatedMotor motor1 = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor motor2 = new EV3LargeRegulatedMotor(MotorPort.B);

		// Wheel setup
		Wheel wheel1 = WheeledChassis.modelWheel(motor1, 5.5).offset(-5.95);
		Wheel wheel2 = WheeledChassis.modelWheel(motor2, 5.5).offset(5.95);

		// Chassis setup
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);

		// Pilot setup
		MovePilot pilot = new MovePilot(chassis);

		// Speed setup
		pilot.setLinearSpeed(2.5f); // 7.5 is way too fast
		pilot.setAngularSpeed(20f);
		pilot.setLinearAcceleration(2.5f);
		pilot.setAngularAcceleration(20f);

		return pilot;
	}
	//
}
