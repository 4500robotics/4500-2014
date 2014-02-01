/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

public class RobotTemplate extends SimpleRobot {

    
    
    //Counter for teleOp loops
    int count=0;
    ImageProcessor processor;
    
    public void robotInit(){
        System.out.println("Initialized");
        try {
            processor = new ImageProcessor();
        } catch (AxisCameraException ex) {
            ex.printStackTrace();
            throw new java.lang.Error("This is dumb something is wrong witht he camera");
        } catch (NIVisionException ex) {
            ex.printStackTrace();
            throw new java.lang.Error("This is dumb something is wrong witht he camera");
        }
    }
    
    public void autonomous() {
            run();
    }

    public void operatorControl(){
        while(isOperatorControl()&&isEnabled()){
            run();
            
            
            
            
            //Increase number of teleop cycles
            count++;
        }
    }
    
    
    public void run(){
        System.out.println(this.processor.getCameraMaxFPS());
        
        try {
                
                processor.update();
                /*ParticleAnalysisReport reports[] = processor.getOrderedReport(0, 50, 0, 50, 0, 50);//should find a black rectangle
                
                for(int i = 0;i<reports.length;i++){
                    
                    System.out.print("The rectangle is situated at ("+
                            reports[i].boundingRectTop+","+
                            reports[i].boundingRectLeft+") and is "+
                            reports[i].boundingRectHeight+" by "+
                            reports[i].boundingRectWidth);
                    
                    
                }*/
                
            } catch (NIVisionException ex) {
                ex.printStackTrace();
                throw new java.lang.Error("This is dumb something is wrong witht he camera");
            } catch (AxisCameraException ex) {
                ex.printStackTrace();
                throw new java.lang.Error("This is dumb something is wrong witht he camera");
        
            }
    }
    
    public void disabled(){
        
    }
}
