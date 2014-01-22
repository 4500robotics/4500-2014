/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;

public class RobotTemplate extends SimpleRobot {

    JoyStickCustom driveStick;
    JoyStickCustom secondStick;
    Talon frontLeft;
    Talon rearLeft; 
    Talon frontRight;
    Talon rearRight;
    Talon winch;
    Compressor compress;
    RobotDrive mainDrive;
    final double DEADZONE=.08;
    Solenoid pistup;
    Solenoid pistdown;
    Pneumatics armJoint;
    Pneumatics handJoint;
    
    Encoder winchStop;
    Potentiometer armStop;
    Potentiometer handStop;
    
    
    //Counter for teleOp loops
    int count=0;
    
    public void robotInit(){
        driveStick= new JoyStickCustom(1, DEADZONE);
        secondStick=new JoyStickCustom(2, DEADZONE);
        frontLeft= new Talon(1);
        rearLeft= new Talon(2);
        frontRight= new Talon(3);
        rearRight= new Talon(4);
        winch = new Talon(5);
        mainDrive=new RobotDrive(frontLeft,rearLeft,frontRight,rearRight);
        compress=new Compressor(1,1);
        pistup=new Solenoid(1);
        pistdown=new Solenoid(2);
        armJoint=new Pneumatics(1,2);
    }
    

    
    public void autonomous() {
            
    }

    public void operatorControl() {
        while(isOperatorControl()&&isEnabled()){
            
            driveStick.update();
            secondStick.update();
            compress.start();
            
            //Cartesian Drive with Deadzones and Turning
            mainDrive.mecanumDrive_Cartesian(
                driveStick.getDeadAxisX(),
                driveStick.getDeadAxisY(),
                driveStick.getDeadTwist(),0);
          
            moveArm();
            
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
    
    public void moveArm(){
            if (secondStick.getDeadAxisX()>.5) {
                 /*pistup.set(true);
                 pistdown.set(false);*/
                 armJoint.up();
            }
            else if (secondStick.getDeadAxisX()<-.5) {
                /*pistdown.set(true);
                pistup.set(false);*/
                 armJoint.down();
            }
            else {
                /*pistdown.set(false);
                pistup.set(false); */
                 armJoint.stay();
            }
    }
    
    public void moveHand(){
        if (secondStick.getButtonPressed(3)&&!secondStick.getButtonPressed(2)) {
             /*pistup.set(true);
             pistdown.set(false);*/
             handJoint.up();
        }
        else if (!secondStick.getButtonPressed(3)&&secondStick.getButtonPressed(2)) {
            /*pistdown.set(true);
            pistup.set(false);*/
             handJoint.down();
        }
        else {
            /*pistdown.set(false);
            pistup.set(false); */
             handJoint.stay();
        }
    }
    
    public void winch(){
        if(secondStick.getButtonReleased(5)){
            winch.set(.30);
           
        }
    }
        
    public void disabled(){
        mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
        armJoint.stay();
        pistup.set(false);
        pistdown.set(false);
    }
}
