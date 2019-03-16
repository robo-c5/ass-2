package testing;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.MovePilot;
import lejos.robotics.navigation.Navigator;
import lejos.utility.Delay;
import mapping.*;

public class TestNavigator {

	
	public static void main(String args[]) {
		EV3 ev3Brick = (EV3) BrickFinder.getLocal();
		Keys keys = ev3Brick.getKeys();
		// Motor setup
		EV3LargeRegulatedMotor motor1 = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor motor2 = new EV3LargeRegulatedMotor(MotorPort.B);

		// Wheel setup
		Wheel wheel1 = WheeledChassis.modelWheel(motor1, 5.6f).offset(-6f);
		Wheel wheel2 = WheeledChassis.modelWheel(motor2, 5.6f).offset(6f);

		// Chassis setup
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);

		// Pilot setup
		MovePilot pilot = new MovePilot(chassis);
		OdometryPoseProvider poseP = new OdometryPoseProvider(pilot);		
		Navigator nav = new Navigator(pilot, poseP);
		Maze testMaze = new Maze();
		
		Tile goal1 = (Tile) testMaze.getMazeObject(testMaze.getCoordinate(11, 1));
		Tile goal2 = (Tile) testMaze.getMazeObject(testMaze.getCoordinate(11, 3));
		Tile goal3 = (Tile) testMaze.getMazeObject(testMaze.getCoordinate(5, 3));
		
		
		keys.waitForAnyPress();
		Coordinate metricDestination = goal1.getCentre();
		nav.goTo(metricDestination.getX(), metricDestination.getY());
		keys.waitForAnyPress();
		metricDestination = goal2.getCentre();
		keys.waitForAnyPress();
		metricDestination = goal3.getCentre();
		keys.waitForAnyPress();
		System.exit(0);
	}
}
