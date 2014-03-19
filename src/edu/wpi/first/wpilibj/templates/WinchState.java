/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Chris Cormier
 */
public class WinchState{
    protected final static int WINDING=0,RELEASING=1, HOLDING1=2, HOLDING2=3;
    private int state;
    
    public int getState() {
        
        return state;
    }
    
    public void setState(int in) {
        
        state=in;
    }
    
}