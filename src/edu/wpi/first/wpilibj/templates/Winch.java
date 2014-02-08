/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Chris Cormier
 */
public class Winch{
    protected final static int WINDING=0,RELEASING=1, HOLDING1=2, HOLDING2=3;
    protected final static double WINCHSPEED=.5,WINCHPOSISTION1=.5;
    
    int releaseStartTime=0,releaseWaitTime=100;
    
    Talon winch;
    Pneumatics winchRelease;
    //WinchState winchS;
    Encoder winchE;
    
    
    private int state;
    
    JoyStickCustom secondStick;
    
    Winch(JoyStickCustom secondStick){
        winchRelease=new Pneumatics(5,6);
        winch= new Talon(5);
        
        setState(HOLDING1);//same as holding used to be
        
        this.secondStick = secondStick;
    }
    
    public int getState(){
        return state;
    }
    
    public void setState(int in){
        state=in;
    }
 
    public void update(int count){
            if(getState()==WINDING){
                if(winchE.getDistance()<=WINCHPOSISTION1){
                    winch.set(WINCHSPEED);
                }else{
                    setState(HOLDING1);
                    
                }
            }else if(getState()==HOLDING1){
                if(secondStick.getButtonReleased(3)&&winchE.getDistance()<=WINCHPOSISTION1){
                    setState(WINDING);
                }else if(secondStick.getButtonReleased(3)&&winchE.getDistance()>=WINCHPOSISTION1){
                    releaseStartTime=count;
                    setState(RELEASING);
                }
            }else if(getState()==RELEASING){
                if(count-releaseStartTime<releaseWaitTime){
                    winchRelease.up();
                }else{
                    winchRelease.stay();
                    setState(HOLDING1);
                    winchE.reset();
                }
            }else setState(HOLDING1);
        }
    
}