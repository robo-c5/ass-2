

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

		// Pilot initialisation
		setPilot(pilotInit());
		irSensorInit();
		colourSensorInit();
		// Wait for input
		waitForAnyPress(ev3brick.getKeys());
		// setup arbitratorInit and then start it
		arbitratorInit();
		
		while(ev3brick.getKeys().getButtons() != Keys.ID_ESCAPE)
		{
			LCD.clear();
			float[] colourSampleRaw = getColourSample();
			float[] irSampleRaw = getIRSample();
			String[] colourSample = new String[colourSampleRaw.length];
			String[] irSample = new String[irSampleRaw.length];
			
			for (int i = 0; i < colourSampleRaw.length; i++)
			{
				colourSample[i] = String.valueOf(colourSampleRaw[i]);
			}
			for (int i = 0; i < irSampleRaw.length; i++)
			{
				irSample[i] = String.valueOf(irSampleRaw[i]);
			}
			
			LCD.drawString(Arrays.deepToString(colourSample), 0, 7);
			LCD.drawString(Arrays.deepToString(irSample), 0, 8);
			Delay.msDelay(10);
		}
	}
	
	private static void setPilot(MovePilot givenPilot)
	{
		pilot = givenPilot;
	}
	
	public static MovePilot getPilot()
	{
		if (pilot == null)
			return pilotInit();
		return pilot;
	}
	
	private static void setColourSensor(EV3ColorSensor givencs)
	{
		cs = givencs;
	}
	
	public static EV3ColorSensor getColourSensor()
	{
		if (cs == null)
			return colourSensorInit();
		return cs;
	}
	
	public static float[] getColourSample()
	{
		SensorMode sm = getColourSensor().getRGBMode();
		float[] sample = new float[getColourSensor().sampleSize()];	
		sm.fetchSample(sample, 0);
		return sample;
	}
	
	public static EV3IRSensor getIRSensor()
	{
		if (ir == null)
			return irSensorInit();
		return ir;
	}
	
	public static float[] getIRSample()
	{
		SensorMode sm = getIRSensor().getDistanceMode();
		float[] sample = new float[getIRSensor().sampleSize()];	
		sm.fetchSample(sample, 0);
		return sample;
	}


	private static void waitForAnyPress(Keys keys) {
		LCD.clear();
		LCD.drawString("Press any key...", 5, 0);
		keys.waitForAnyPress();
		LCD.clear();
	}

	private static EV3IRSensor irSensorInit() {
		EV3IRSensor irSensor;
		irSensor = new EV3IRSensor(SensorPort.S2);
		return irSensor;
	}
	
	private static EV3ColorSensor colourSensorInit() {
		return new EV3ColorSensor(SensorPort.S4);
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

}
