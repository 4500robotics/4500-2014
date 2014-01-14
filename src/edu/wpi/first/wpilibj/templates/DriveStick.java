/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;

import java.lang.Math;

/**
 *
 * @author Daedalus
 */
public class DriveStick extends Joystick{
    
    double deadZone,xAxis,yAxis,twist;
    boolean buttonPressed[],buttonReleased[];
    
    public DriveStick(int port,double deadZoneIn){
        super(port);
        
        deadZone=deadZoneIn;
        
        buttonReleased= new boolean[13];
        
        for(int x=0;x<=12;x++){
           buttonReleased[x]= false;
        }
        
        buttonPressed = new boolean[13];
        
        for(int x=0;x<=12;x++){
           buttonPressed[x]= false;
        }
        
    }
    public void update(){
        
        for(int x=1;x<=12;x++){
           buttonReleased[x]= buttonPressed[x]&&!getRawButton(x);
        }
        
        for(int x=1;x<=12;x++){
           buttonPressed[x]= getRawButton(x);
        }
        
        xAxis=getAxis(Joystick.AxisType.kX);
        yAxis=getAxis(Joystick.AxisType.kY);
        twist=getAxis(Joystick.AxisType.kTwist);
    }
    public double getDeadAxisX(){
        return Math.abs(xAxis)>deadZone ? xAxis:0;
    }
    
    public double getDeadAxisY(){
        return Math.abs(yAxis)>deadZone ? yAxis:0;
    }
    
    public double getDeadTwist(){
        return Math.abs(twist)>deadZone ? twist:0;
    }

    public boolean getButtonPressed(int in){
        return buttonPressed[in];
    }
    
    public boolean getButtonReleased(int in){
        return buttonReleased[in];
    }
}
