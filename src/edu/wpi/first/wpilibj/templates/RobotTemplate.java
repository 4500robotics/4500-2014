/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.Math;
public class RobotTemplate extends SimpleRobot {

    JoyStickCustom driveStick;
    JoyStickCustom secondStick;
    final double DEADZONE=.20;
    
    final int LOWSHOOTSTOP=321,HIGHSHOOTSHOP=310;
    
    Talon frontLeft;
    Talon rearLeft;
    Talon frontRight;
    Talon rearRight;
    
    Timer timer;
    
    Compressor compress;
    
    RobotDrive mainDrive;
    
    AnalogChannel distance;
    Ultrasonic ultra;
    
    //objects for running the arm and hand
    protected final static int RAISING=0,LOWERING=1;
    AnalogPotentiometer armP;
    
    Pneumatics handJoint;
    AnalogPotentiometer handP;
    
    boolean first=true,auto=true;
    int shooting=0;
    Victor armM,rollers;
    
    int armMoving=0;//0=not moving, 1 moving up, -1 moving down
    //objects for running the winch system
    protected final static int WINDING=0,RELEASING=1, HOLDING=2;
    Winch winch;
    WinchState winchS;
    Encoder winchE;
    int releaseStartTime=0,releaseWaitTime=100;
    protected final static double WINCHSPEED=.5,WINCHPOSISTION=.5;
    
    double timestart=0;
    
    
    //Counter for teleOp loops
    int count=0;
    
    public void robotInit(){
        
        driveStick= new JoyStickCustom(1, DEADZONE);
        secondStick=new JoyStickCustom(2, DEADZONE);
        
        frontLeft= new Talon(1);
        rearLeft= new Talon(2);
        frontRight= new Talon(3);
        rearRight= new Talon(4);
        
        timer=new Timer();
        timer.start();
        
        armM = new Victor(7);
        rollers =new Victor(6);
        
        mainDrive=new RobotDrive(frontLeft,rearLeft,frontRight,rearRight);

        mainDrive.setInvertedMotor(RobotDrive.MotorType.kFrontRight,true);
        mainDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        
        armP = new AnalogPotentiometer(1);
        distance= new AnalogChannel(2);
        ultra=new Ultrasonic(6,7);
        ultra.setAutomaticMode(true);
        
        compress=new Compressor(5,1);
        
        handJoint=new Pneumatics(3,4);
        
        //ultra = new Ultrasonic(6,5);
        //ultra.setAutomaticMode(true);
        
        winch = new Winch(secondStick);
        
    }
    
    public void autonomous() {
        if(timestart<timer.get()){
            timestart=timer.get();
        }
        /*while(winch.winchE.get()>winch.WINCHPOSISTION1){
        winch.winch.set(winch.WINCHSPEED);
        }
        
        winch.winch.set(0);
        handJoint.up();
        
        double x=armP.get();
        
        if((int)(x*100)<287){//2.8744441750000003 is real value
        armM.set(1);
        }else{
        armM.set(0);
        }
        
        mainDrive.mecanumDrive_Cartesian(1, 0, 0, 0);
        while(distance.getValue()*.977>3){//placeholder distance
        }
        
        winch.winchRelease.up();
        mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);*/
        while(timer.get()-timestart<1){
            mainDrive.mecanumDrive_Cartesian(0, -1, 0, 0);
        }
        mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
    }

    public void operatorControl() {
        while(isOperatorControl()&&isEnabled()){
            //updating joysticks
            driveStick.update();
            secondStick.update();
            
            System.out.println("Ultra: "+ultra.getRangeInches());
            compress.start();
            //Cartesian Drive with Deadzones and Turning
            mainDrive.mecanumDrive_Cartesian(
            driveStick.getDeadAxisX(),
            driveStick.getDeadAxisY(),
            driveStick.getDeadAxisZ(),
            0);
            
                
            if(shooting!=0){
                shootUpdate();
                //winch.setState(RELEASING);
            }else{
                //shooting=secondStick.getButtonJustPressed(1)&&winch.getState()==HOLDING;
                moveArm();
                moveHand();
                
                

                if(secondStick.getButtonPressed(9)&&winch.limitSwitch.get()){
                    //System.out.println("hello");
                    winch.winch.set(1);}
                else {//System.out.println("bye");
                    winch.winch.set(0);}
                
                if(secondStick.getButtonPressed(1)) {
                    shooting=1;
                } else if (secondStick.getButtonPressed(10)){
                    winch.winchRelease.down();
                }
                else{ 
                    winch.winchRelease.up();
                    }
            }
             
            //calls the winch
            winch.update(timer.get());
            log();
        }
    }
    
    private void shootUpdate(){
        double x=armP.get();
        winch.winch.set(0);
        switch(shooting){
            case 1:
            if((int)(x*100)>LOWSHOOTSTOP){//3.059408287 moving up
                armM.set(-1);
            }else{
                shooting=2;
                armM.set(-.05);
                System.out.println("Done 1");
                }
            break;  
                
            case 2:    
                if(winch.getState()==HOLDING){
                    if(first){
                        handJoint.up();
                        System.out.println("Set Releasing");
                        winch.setState(RELEASING);
                        first=false;
                    }else{
                        //handJoint.up();
                        System.out.println("Done 2");
                       shooting=0;
                       first=true;
                        }
                }else{
                    moveHand();
                    moveArm();
                }
                break;
            case 3:
                winch.winch.set(0);
             if((int)(x*100)<381){//3.8146784110000005 moving down
                            armM.set(.25);
                        }else{
                            shooting=0;
                            first=true;
            handJoint.up();
            break;
         }
        
        default: 
            break;
    }
    }
    
    private void moveArm(){
        double z=armP.get(),x=secondStick.getDeadAxisY();
        System.out.println("pot: "+z);
        switch(armMoving){
            case 0:
                armM.set(-0.12);
                 if(Math.abs(secondStick.getDeadAxisY())>.25){
                    double Y=(x)/(Math.abs(x));
                    armMoving=(int) (Y); //reminder 1 is up, -1 is down
                 }else if(secondStick.getButtonPressed(11)){
                     //armMoving=2;
                 }
                 /*if(Math.abs(x)>0){
                 armM.set(x*-1);
                 }else{
                 armM.set(-.11);
                 }*/
                 break;
            case 1: //moving up
                if((int)(z*100)>321){//3.059408287 moving up
                   armM.set(-1);
                }else{
                    armMoving=0;
                    armM.set(0);
                }
                break;
            case -1://moving down
                if((int)(z*100)<390){//3.8146784110000005 moving doen
                    armM.set(.33);
                }else{
                    armMoving=0;
                    armM.set(0);
                }break;
            case 2:
            if((int)(z*100)>371){
            armM.set(-1);
            }else if((int)(z*100)<369){
            armM.set(1);
            }else{
            armMoving=0;
            }
            break;
            }
    }

             /*if (secondStick.getDeadAxisY()>0) {
             
             }
             else if (secondStick.getDeadAxisY()<0) {
             armJoint.down();
             }
             else {
             armJoint.stay();
             }*/
    
    private void moveHand(){
        if (secondStick.getButtonPressed(2)&&!secondStick.getButtonPressed(3)) {
             handJoint.up();
        }
        else if (!secondStick.getButtonPressed(2)&&secondStick.getButtonPressed(3)) {
             handJoint.down();
        }
        else {
             handJoint.stay();
        }
        
        if (secondStick.getButtonPressed(4)&&!secondStick.getButtonPressed(5)) {
             rollers.set(-1);
        }
        else if (!secondStick.getButtonPressed(4)&&secondStick.getButtonPressed(5)) {
             rollers.set(1);
        }
        else {
             rollers.set(0);
        }
}

    public void log(){
        SmartDashboard.putNumber("Front Left Drive",frontLeft.get());
        SmartDashboard.putNumber("Rear Left Drive",rearLeft.get());
        SmartDashboard.putNumber("Front Right Drive",frontRight.get());
        SmartDashboard.putNumber("Rear Right Drive",rearRight.get());
        
        SmartDashboard.putNumber("Shooting?",shooting);
        
        SmartDashboard.putNumber("Potentiometer", armP.get());
        SmartDashboard.putNumber("Ultra", ultra.getRangeInches());
        
        SmartDashboard.putNumber("Time",timer.get());
        winch.log();
        
    }
        
    public void disabled(){
        mainDrive.mecanumDrive_Cartesian(0, 0, 0, 0);
        handJoint.stay();
    }
    
    public void setAuto(boolean inB){
        auto=inB;
        
    }
    
    public void test(){
    }
}
