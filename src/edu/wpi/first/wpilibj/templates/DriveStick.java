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
    
    double deadZone;
    
    public DriveStick(int port,double deadZoneIn){
        super(port);
        
        deadZone=deadZoneIn;
    }
    
    public double getDeadAxisX(){
        return Math.abs(getAxis(Joystick.AxisType.kX))<deadZone ? getAxis(Joystick.AxisType.kX):0;
    }
    
    public double getDeadAxisY(){
        return Math.abs(getAxis(Joystick.AxisType.kY))<deadZone ? getAxis(Joystick.AxisType.kY):0;
    }
    
    public double getDeadTwist(){
        return Math.abs(getAxis(Joystick.AxisType.kTwist))<deadZone ? getAxis(Joystick.AxisType.kTwist):0;
    }
}
