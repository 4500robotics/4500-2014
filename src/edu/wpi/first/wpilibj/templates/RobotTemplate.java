/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Timer;

public class RobotTemplate extends SimpleRobot {

    JoyStickCustom driveStick;
    JoyStickCustom secondStick;
    final double DEADZONE=.08;
    
    Talon frontLeft;
    Talon rearLeft;
    Talon frontRight;
    Talon rearRight;
    
    Talon armM;
    Talon rollers;
    
    Compressor compress;
    
    Timer timer;
    
    RobotDrive mainDrive;
    
    //objects for running the arm and hand
    protected final static int RAISING=0,LOWERING=1;
    Pneumatics armJoint;
    AnalogPotentiometer armP;
    
    Pneumatics handJoint;
    AnalogPotentiometer handP;
    
    Winch winch;

    //Counter for teleOp loops
    int count=0;
    
    public  void robotInit(){
        driveStick= new JoyStickCustom(1, DEADZONE);
        secondStick=new JoyStickCustom(2, DEADZONE);
        
        frontLeft= new Talon(1);
        rearLeft= new Talon(2);
        frontRight= new Talon(3);
        rearRight= new Talon(4);
        
        armM= new Talon(6);
        rollers= new Talon(7);
        
        timer=new Timer();
        
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
            
            //updating joysticks
            driveStick.update();
            secondStick.update();
            
            compress.start();
            
            //Cartesian Drive with Deadzones and Turning
            mainDrive.mecanumDrive_Cartesian(
                driveStick.getDeadAxisX(),
                driveStick.getDeadAxisY(),
                driveStick.getDeadTwist(),0);
          
            //calls the 
            moveArm();
            moveHand();
            winch.update(count);
            
            //Increase number of teleop cycles
            count++;
        }
    }
    
    private void moveArm(){
        armM.set(-1*secondStick.getDeadAxisY());
    }
    
    private void moveHand(){
        
        if (secondStick.getButtonPressed(4)&&!secondStick.getButtonPressed(5)) {
             rollers.set(.50);
        }
        else if (!secondStick.getButtonPressed(4)&&secondStick.getButtonPressed(5)) {
             rollers.set(-.50);
        }
        else {
             rollers.set(0);
        }
        
        if (secondStick.getButtonPressed(2)&&!secondStick.getButtonPressed(3)) {
             handJoint.up();
        }
        else if (!secondStick.getButtonPressed(2)&&secondStick.getButtonPressed(3)) {
             handJoint.down();
        }
        else {
             handJoint.stay();
        }
}
    private void drive(){
        
    }

    
        
    public void disabled(){
        mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
        armJoint.stay();
        handJoint.stay();
    }
    
    public void test(){
    }
}
