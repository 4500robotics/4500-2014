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
    
    public DriveStick(int port,double deadZoneIn){
        super(port);
        
        deadZone=deadZoneIn;
    }
    public void update(){
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

}
