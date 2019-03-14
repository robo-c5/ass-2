package testing;

import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;

public class DrawSquares {
	
	public static void main(String args[]) {
		EV3 ev3Brick = (EV3) BrickFinder.getLocal();
		Keys keys = ev3Brick.getKeys();
		// Motor setup
		EV3LargeRegulatedMotor motor1 = new EV3LargeRegulatedMotor(MotorPort.A);
		EV3LargeRegulatedMotor motor2 = new EV3LargeRegulatedMotor(MotorPort.B);

		// Wheel setup
		Wheel wheel1 = WheeledChassis.modelWheel(motor1, 5.6f).offset(-6.0f);
		Wheel wheel2 = WheeledChassis.modelWheel(motor2, 5.6f).offset(6.0f);

		// Chassis setup
		Chassis chassis = new WheeledChassis(new Wheel[] { wheel1, wheel2 }, WheeledChassis.TYPE_DIFFERENTIAL);

		// Pilot setup
		MovePilot pilot = new MovePilot(chassis);

		// Speed setup
		pilot.setLinearSpeed(5.0f); // 7.5 is way too fast
		pilot.setAngularSpeed(40.0f);
		pilot.setLinearAcceleration(2.5f);
		pilot.setAngularAcceleration(20f);
		
		keys.waitForAnyPress();
		while(keys.getButtons() != Keys.ID_ESCAPE) {
			pilot.travel(40);
			
			if(keys.getButtons() == Keys.ID_ESCAPE) {
				break;
			}
			
			pilot.rotate(90);
			
			if(keys.getButtons() == Keys.ID_ESCAPE) {
				break;
			}			
		}
		pilot.stop();
	}
}