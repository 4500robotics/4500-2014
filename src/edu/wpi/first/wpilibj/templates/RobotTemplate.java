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
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;

public class RobotTemplate extends SimpleRobot {

    JoyStickCustom driveStick;
    JoyStickCustom secondStick;
    Talon frontLeft;
    Talon rearLeft; 
    Talon frontRight;
    Talon rearRight;
    
    Compressor compress;
    RobotDrive mainDrive;
    final double DEADZONE=.08;
    
    Winch winch;
    
    protected final static int RAISING=0,LOWERING=1;
    Pneumatics armJoint;
    AnalogPotentiometer armP;
    
    Pneumatics handJoint;
    AnalogPotentiometer handP;
    
    /*protected final static int WINDING=0,RELEASING=1, HOLDING=2;
    Talon winch;
    Pneumatics winchRelease;
    WinchState winchS;
    Encoder winchE;
    int releaseStartTime=0,releaseWaitTime=100;
    protected final static double WINCHSPEED=.5,WINCHPOSISTION1=.5;*/

    //Counter for teleOp loops
    int count=0;
    
    public void robotInit(){
        driveStick= new JoyStickCustom(1, DEADZONE);
        secondStick=new JoyStickCustom(2, DEADZONE);
        
        frontLeft= new Talon(1);
        rearLeft= new Talon(2);
        frontRight= new Talon(3);
        rearRight= new Talon(4);
        
        mainDrive=new RobotDrive(frontLeft,rearLeft,frontRight,rearRight);
        compress=new Compressor(1,1);
        
        armJoint=new Pneumatics(1,2);
        handJoint=new Pneumatics(3,4);
        
        winch = new Winch(secondStick);
        
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
            moveHand();
            winch.update(count);
            
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
    
    private void moveArm(){
            if (secondStick.getButtonPressed(6)&&!secondStick.getButtonPressed(7)) {
                 armJoint.up();
            }
            else if (!secondStick.getButtonPressed(6)&&secondStick.getButtonPressed(7)) {
                 armJoint.down();
            }
            else {
                 armJoint.stay();
            }
    }
    
    private void moveHand(){
        if (secondStick.getButtonPressed(11)&&!secondStick.getButtonPressed(10)) {
             handJoint.up();
        }
        else if (!secondStick.getButtonPressed(11)&&secondStick.getButtonPressed(10)) {
             handJoint.down();
        }
        else {
             handJoint.stay();
        }
}

    
        
    public void disabled(){
        mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
        armJoint.stay();
        handJoint.stay();
    }
}
