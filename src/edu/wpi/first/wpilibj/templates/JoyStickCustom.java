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
public class JoyStickCustom extends Joystick {
    
    double deadZone,xAxis,yAxis,twist,zAxis;
    boolean buttonPressed[],buttonReleased[],buttonJustPressed[];
    
    public JoyStickCustom(int port,double deadZoneIn) {
        
        super(port);
        
        deadZone=deadZoneIn;
        
        buttonReleased= new boolean[13];
        buttonJustPressed= new boolean[13];
        buttonPressed = new boolean[13];
        
        for(int x=0;x<=12;x++) {
            
           buttonReleased[x]= false;
           buttonJustPressed[x]= false;
           buttonPressed[x]= false;
           
        }
        
    }
    public void update() {
        
        //compare the current buttons compared to the previous button
        for(int x=1;x<=12;x++) {
           buttonJustPressed[x]= !buttonPressed[x]&&getRawButton(x);
           buttonReleased[x]= buttonPressed[x]&&!getRawButton(x);
        }
        
        //get the pushed buttons on this update cycle
        for(int x=1;x<=12;x++) {
           buttonPressed[x]= getRawButton(x);
        }
        
        xAxis=getAxis(Joystick.AxisType.kX);
        yAxis=getAxis(Joystick.AxisType.kY);
        twist=getAxis(Joystick.AxisType.kTwist);
        zAxis=getAxis(Joystick.AxisType.kThrottle);
    }
    
    //create deadzones for the joysticks
    public double getDeadAxisX() {
        
        return Math.abs(xAxis)>deadZone ? xAxis:0;
        
    }
    
    public double getDeadAxisY() {
        
        return Math.abs(yAxis)>deadZone ? yAxis:0;
        
    }
    
    public double getDeadTwist() {
        
        return Math.abs(twist)>deadZone ? twist:0;
        
    }
    
    public double getDeadAxisZ() {
        
        return Math.abs(zAxis)>deadZone ? zAxis:0;
        
    }
        
    public boolean getButtonPressed(int in) {
        
        return buttonPressed[in];
        
    }
    
    public boolean getButtonJustPressed(int in) {
        
        return buttonJustPressed[in];
        
    }
    
    
    public boolean getButtonReleased(int in) {
        
        return buttonReleased[in];
        
    }
}