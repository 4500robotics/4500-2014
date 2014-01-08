/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;
// libraries
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Talon;

public class RobotTemplate extends SimpleRobot {
//initializing stuff
    Joystick driveStick;
    Talon frontLeft;
    Talon rearLeft; 
    Talon frontRight;
    Talon rearRight;
    RobotDrive mainDrive;
    double DEADZONE=.05;
    int count=0;
    
    public void robotInit(){
        //initializing stuff
        driveStick= new Joystick(1);
        frontLeft= new Talon(1);
        rearLeft= new Talon(2);
        frontRight= new Talon(3);
        rearRight= new Talon(4);
        mainDrive=new RobotDrive(frontLeft,rearLeft,frontRight,rearRight);
           }
    
    public void autonomous() {
            
    }

    public void operatorControl() {
        while(isOperatorControl()&&isEnabled()){
            /* mainDrive.mecanumDrive_Cartesian(
            driveStick.getAxis(Joystick.AxisType.kX),
            driveStick.getAxis(Joystick.AxisType.kY), 0,0);
            */
            //Cartesian drive mode with deadzones and twist, with out
            mainDrive.mecanumDrive_Cartesian(
                  joystickDeadzone(driveStick.getAxis(Joystick.AxisType.kX)),
                  joystickDeadzone(driveStick.getAxis(Joystick.AxisType.kY)),
                  driveStick.getAxis(Joystick.AxisType.kTwist),0);
          
            //Basic Drive Cartesian Code
            /*mainDrive.mecanumDrive_Cartesian(
             * driveStick.getAxis(Joystick.AxisType.kX),
             * driveStick.getAxis(Joystick.AxisType.kY),
             * 0, 0);*/
            
            //Basic Drive Polar
            /*mainDrive.mecanumDrive_Polar(
             * driveStick.getAxis(Joystick.AxisType.kThrottle),
             * 0, 0);*/
            
            
            //Super Basic curve Drive with throttle as magnitude
            //mainDrive.drive(driveStick.getAxis(Joystick.AxisType.kThrottle), 0);
            
            //Logging system
            if(count%500==0){System.out.println(count+": "+
                    frontLeft.getSpeed()+", "+
                    rearLeft.getSpeed()+", "+
                    frontRight.getSpeed()+", "+
                    rearRight.getSpeed());
            }
            
            count++;
        }
    }
    
    private double joystickDeadzone(double a){
        if(Math.abs(a)>DEADZONE){
           return a ;
        }
        
        return 0;
    }
    public void disabled(){
        mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    }
}
