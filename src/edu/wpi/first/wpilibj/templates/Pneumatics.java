/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author DE
 */
public class Pneumatics{
    
    Solenoid pistTop;
    Solenoid pistBottom;
    
    public Pneumatics(int portTop, int portBottom){
       pistTop= new Solenoid(portBottom);
       pistBottom= new Solenoid(portTop);
    }
    
    public void up(){
        pistTop.set(false);
        pistBottom.set(true);
    }
    
    public void down(){
        pistTop.set(true);
        pistBottom.set(false);  
    }
        
    public void stay(){
        pistTop.set(false);
        pistBottom.set(false);
    }
}
