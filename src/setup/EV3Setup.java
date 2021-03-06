package setup;

import behaviours.*;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.*;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class EV3Setup
{

	private static MovePilot				pilot;

	private static PoseProvider				poseP;

	private static Navigator				navPilot;

	private static EV3ColorSensor			cs;

	private static EV3IRSensor				ir;

	private static EV3						ev3Brick;

	private static EV3MediumRegulatedMotor	irMotor;

	public EV3Setup()
	{
		// Brick setup
		ev3Brick = (EV3) BrickFinder.getLocal();
		setIRSensor(irSensorInit());
		setColourSensor(colourSensorInit());
		// Wait for input
		waitForAnyPress(ev3Brick.getKeys());
	}

	public static boolean escapePressed()
	{
		return ev3Brick.getKeys().getButtons() == Keys.ID_ESCAPE;
	}

	private static void setPilot(MovePilot givenPilot)
	{
		pilot = givenPilot;
	}

	public static MovePilot getPilot()
	{
		if (pilot == null)
			setPilot(pilotInit());
		return pilot;
	}

	public static Navigator getNav()
	{
		if (navPilot == null)
			setNav(pilot);
		return navPilot;
	}

	public static void setNav(MovePilot pilot)
	{
		poseP = new OdometryPoseProvider(pilot);
		navPilot = new Navigator(pilot, poseP);
	}

	// colour sensor getter and setter
	private static void setColourSensor(EV3ColorSensor givencs)
	{
		cs = givencs;
	}

	public static EV3ColorSensor getColourSensor()
	{
		if (cs == null)
			setColourSensor(colourSensorInit());
		return cs;
	}
	//

	// ir sensor getter and setter
	private static void setIRSensor(EV3IRSensor givenir)
	{
		ir = givenir;
	}

	public static EV3IRSensor getIRSensor()
	{
		if (ir == null)
			setIRSensor(irSensorInit());
		return ir;
	}
	//

	// get colour and ir samples
	public static float[] getColourSample()
	{
		SensorMode sm = getColourSensor().getRGBMode();
		//SensorMode sm = getColourSensor().getColorIDMode();
		float[] sample = new float[sm.sampleSize()];
		sm.fetchSample(sample, 0);
		return sample;
	}

	public static float getIRSample()
	{
		SensorMode sm = getIRSensor().getDistanceMode();
		float[] sample = new float[sm.sampleSize()];
		sm.fetchSample(sample, 0);
		return sample[0];
	}
	//

	private static void waitForAnyPress(Keys keys)
	{
		LCD.clear();
		LCD.drawString("EV3Setup.java-", 0, 5);
		LCD.drawString("Press any key...", 0, 6);
		keys.waitForAnyPress();
		LCD.clear();
	}

	// init
	private static EV3IRSensor irSensorInit()
	{
		EV3IRSensor irSensor;
		irSensor = new EV3IRSensor(SensorPort.S1);
		return irSensor;
	}

	private static EV3ColorSensor colourSensorInit()
	{
		return new EV3ColorSensor(SensorPort.S2);
	}

	public static void startArbitrator()
	{
		// add behaviours to, and then start, Arbitrator
		new Arbitrator(new Behavior[] { new CheckNeighbours(), new AbleToEnd() }).go();// new EndArbitrator() }).go();
		// ^new OutsideMaze(),
	}

	private static EV3MediumRegulatedMotor irMotorInit()
	{
		EV3MediumRegulatedMotor irMotor = new EV3MediumRegulatedMotor(MotorPort.C);
		irMotor.setSpeed(360);
		return irMotor;
	}

	public static EV3MediumRegulatedMotor getirMotor()
	{
		if (irMotor == null)
		{
			irMotor = irMotorInit();
		}
		return irMotor;
	}

	private static MovePilot pilotInit()
	{
		// Motor setup
		EV3LargeRegulatedMotor motor1 = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor motor2 = new EV3LargeRegulatedMotor(MotorPort.B);

		// Wheel setup
		Wheel wheel1 = WheeledChassis.modelWheel(motor1, 5.6).offset(-5.95);
		Wheel wheel2 = WheeledChassis.modelWheel(motor2, 5.6).offset(5.95);

		// Chassis setup
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);

		// Pilot setup
		MovePilot pilot = new MovePilot(chassis);

		// Speed setup
		pilot.setLinearSpeed(10f);//200f); // 7.5 is way too fast
		pilot.setAngularSpeed(40f);//200f);
		pilot.setLinearAcceleration(200f);
		pilot.setAngularAcceleration(200f);

		return pilot;
	}
	//
}
