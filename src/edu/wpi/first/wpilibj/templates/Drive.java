/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Daedalus
 */
public class Drive extends RobotDrive{
    
    	public Drive(Talon frontLeftMotor, Talon rearLeftMotor,
			Talon frontRightMotor, Talon rearRightMotor) {
		super(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
	}
    
}
