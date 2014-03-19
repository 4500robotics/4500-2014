/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Chris Cormier
 */
public class Winch{
    String states[] =new String[3];
    
    protected final static int WINDING=0,RELEASING=1, HOLDING1=2, HOLDING2=3;
    
    protected final double WINCHSPEED=1;
    
    int WINCHPOSISTION1=-500;
    
    DigitalInput limitSwitch;
    
    double releaseStartTime=0,releaseWaitTime=1;
    
    Victor winchM;
    Pneumatics winchRelease;
    Encoder winchE;
    //WinchState winchS;
    
    private int state;
    
    JoyStickCustom secondStick;
    
    Winch(JoyStickCustom inStick){
        winchRelease=new Pneumatics(1,2);
        winchM= new Victor(5);
        
        states[0]="WINDING";
        states[1]="RELEASING";
        states[2]="HOLDING";
        setState(HOLDING1);//same as holding used to be
        
        limitSwitch= new DigitalInput(4);
        
        secondStick = inStick;
        
        winchE= new Encoder(3,2);
        winchE.start();
    }
    
    public int getState() {
        
        return state;
    }
    
    public void setState(int in) {
        state=in;
    }
 
    public void update(double time) {
        switch(getState()) {
            case WINDING:
                winchRelease.up();
                if(limitSwitch.get()) {
                    winchM.set(WINCHSPEED);
                } else {
                    winchM.set(0);
                    setState(HOLDING1);
                }
                break;
            case HOLDING1:
                releaseStartTime=time;
                winchRelease.up();
                break;
            
            case RELEASING:
                System.out.println("Got Releasing");
                if(time-releaseStartTime<releaseWaitTime){
                    System.out.println("Releasing");
                    winchRelease.down();
                    winchM.set(WINCHSPEED);
                }else{
                    setState(WINDING);
                //<editor-fold defaultstate="collapsed" desc="C's Code">
                /*if(limitSwitch.get()){
                winchRelease.up();
                winchM.set(WINCHSPEED);
                }else{
                winchM.set(0);
                setState(HOLDING1);
                }*/
                //</editor-fold>
                }
                break;
            default:
                System.out.println("ERROR in Winch");
                break;
            }
    }
    
    /*public void update(double time){
        if(secondStick.getButtonPressed(1)){
            winchRelease.down();
            winchE.reset();
        }else{
            winchRelease.up();
            if(secondStick.getButtonPressed(9)&&winchE.get()>-733){
                winchM.set(1.00);
            }else {
                    winchM.set(0);
            }
        }
        
        //System.out.println("Encoder"+winchE.getRaw());
    }*/
    public void log(){
        SmartDashboard.putString("Winch State", states[getState()]);
        SmartDashboard.putBoolean("Winch Limit Switch", limitSwitch.get());
    }
}