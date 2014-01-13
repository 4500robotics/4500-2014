/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class RobotTemplate extends SimpleRobot {

    DriveStick driveStick;
    Joystick secondStick;
    Talon frontLeft;
    Talon rearLeft; 
    Talon frontRight;
    Talon rearRight;
    Compressor compress;
    RobotDrive mainDrive;
    double DEADZONE=.08;
    Solenoid pistup;
    Solenoid pistdown;
    Pnumatics engage;
    
    //Counter for teleOp loops
    int count=0;
    
    public void robotInit(){
        driveStick= new DriveStick(1, DEADZONE);
        frontLeft= new Talon(1);
        rearLeft= new Talon(2);
        frontRight= new Talon(3);
        rearRight= new Talon(4);
        mainDrive=new RobotDrive(frontLeft,rearLeft,frontRight,rearRight);
        compress=new Compressor(1,1);
        pistup=new Solenoid(1);
        pistdown=new Solenoid(2);
        engage =new Pnumatics();
    }
    
    public void compressor() {
        
        compress.start();
    
    }
    
    public void autonomous() {
            
    }

    public void operatorControl() {
        while(isOperatorControl()&&isEnabled()){
            
            driveStick.update();
            //Simple Cartesian Drive
            /* mainDrive.mecanumDrive_Cartesian(
                driveStick.getAxis(Joystick.AxisType.kX),
                driveStick.getAxis(Joystick.AxisType.kY), 0,0);
            */
            
            //Cartesian Drive with Deadzones and Turning
            mainDrive.mecanumDrive_Cartesian(
                driveStick.getDeadAxisX(),
                driveStick.getDeadAxisY(),
                driveStick.getDeadTwist(),0);
          
            if (secondStick.getRawButton(3)) {
                System.out.print("Button 3");
                pistup.set(true);
            }
            else if (secondStick.getRawButton(4)) {
                System.out.print("Button 4");
                pistdown.set(true);
            }
            else {
                pistdown.set(false);
                pistup.set(false);
            }
               
            //Simple Polar test Drive with throttle as magnitude, NOTE: is backwards
            /*mainDrive.mecanumDrive_Polar(
                * driveStick.getAxis(Joystick.AxisType.kThrottle),
                * 0, 0);*/
            
            
            //Simple curve Test Drive
            //mainDrive.drive(driveStick.getAxis(Joystick.AxisType.kThrottle), 0);
            
            //logger
            /*if(count%500==0){System.out.println(count+": "+
                frontLeft.getSpeed()+", "+
                rearLeft.getSpeed()+", "+
                frontRight.getSpeed()+", "+
                rearRight.getSpeed());
            }*/
            
            //Increase number of teleop cycles
            count++;
        }
    }
    
    
    
    
    public void disabled(){
        mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    }
}
