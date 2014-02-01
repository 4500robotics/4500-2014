/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.camera.*;

/**
 *
 * @author DE
 */
public class ImageProcessor {
    private AxisCamera camera;
    private ColorImage currentImage;
    private int imageNumber;
    
    
    /**
     * 
     * @throws AxisCameraException
     * @throws NIVisionException 
     * The default constructor for class. It initializes the image to the 
     * current image that the camera is receiving.
     */
    ImageProcessor() throws AxisCameraException, NIVisionException{
        camera = AxisCamera.getInstance("10.45.0.47");
        imageNumber = 0;
        //sets the camera up with default values
        writeCameraBrightness(100);
        writeCameraColorLevel(100);
        writeCameraCompression(0);//very little to no compression
        writeCameraExposureControl(AxisCamera.ExposureT.automatic);
        writeCameraExposurePriority(AxisCamera.ExposurePriorityT.imageQuality);
        writeCameraMaxFPS(30);
        writeCameraResolution(AxisCamera.ResolutionT.k640x480);
        writeCameraRotation(AxisCamera.RotationT.k0);//assumed upright
        writeCameraWhiteBalance(AxisCamera.WhiteBalanceT.automatic);
        
        //currentImage = camera.getImage();
        
    }

    /**
     * 
     * @throws AxisCameraException
     * @throws NIVisionException 
     * Updates the image from the camera.
     */
    public void update() throws AxisCameraException, NIVisionException{
        if(camera.freshImage()){//only get a new image if it exists
            currentImage.free();//the image is stored in a c array so even though 
                                //java has a garbage collector we still need to manually free the image
            currentImage = camera.getImage();
            System.out.println("Got image number"+(++imageNumber));
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="These are all the getters and setters" >
    /**
     * gets the camera resolution
     * @return 
     */
    public AxisCamera.ResolutionT getCameraResolution(){
        
        return camera.getResolution();
    }
    
    /**
     * Gets the camera rotation
     * @return 
     */
    public AxisCamera.RotationT getCameraRotation(){
        return camera.getRotation();
    }
    
    /**
     * Gets the white balance of the camera
     * @return 
     */
    public AxisCamera.WhiteBalanceT getCameraWhiteBalance(){
        return camera.getWhiteBalance();
    }
    
    /**
     * Gets the camera's max FPS.
     * @return 
     */
    public int getCameraMaxFPS(){
        return camera.getMaxFPS();
    }
    
    /**
     * Returns the exposure mode that the camera is using.
     * @return 
     */
    public AxisCamera.ExposureT getCameraExposureControl(){
        return camera.getExposureControl();
    }
    
    /**
     * Gets the exposure priority that the camera is using.
     * @return 
     */
    public AxisCamera.ExposurePriorityT getCameraExposurePriority(){
        return camera.getExposurePriority();
    }
    
    /**
     * Writes the brightness to the camera. It should be a n integer representing 
     * the current brightness of the axis camera with 0 being the darkest and 100 
     * being the brightest.
     * @param brightness 
     */
    public void writeCameraBrightness(int brightness){
        camera.writeBrightness(brightness);
    }
    
    /**
     * Writes the color level to the camera. Value must be an integer from 0 to 
     * 100 with 0 being black and white.
     * @param value 
     */
    public void writeCameraColorLevel(int value){
        camera.writeColorLevel(value);
    }
    
    /**
     * Writes the compression level to the camera. Value is the level of JPEG compression 
     * to use from 0 to 100 with 0 being the least compression.
     * @param value 
     * 
     */
    public void writeCameraCompression(int value){
        camera.writeCompression(value);
    }
    
    
    /**
     * Writes the exposure control the the camera
     * @param value 
     */
    public void writeCameraExposureControl(AxisCamera.ExposureT value){
        camera.writeExposureControl(value);
    }
    
    /**
     * Writes the exposure priority to the camera.
     * @param value 
     */
    public void writeCameraExposurePriority(AxisCamera.ExposurePriorityT value){
        camera.writeExposurePriority(value);
    }
    
    /**
     * Writes the max fps to the camera.
     * @param fps 
     */
    public void writeCameraMaxFPS(int fps){
        camera.writeMaxFPS(fps);
    }
    
    /**
     * writes the resolution tot he camera
     * @param value 
     */
    public void writeCameraResolution(AxisCamera.ResolutionT value){
        camera.writeResolution(value);
    }
    
    /**
     * Writes the rotation the camera
     * @param value 
     */
    public void writeCameraRotation(AxisCamera.RotationT value){
        camera.writeRotation(value);
    }
    
    /**
     * Writes the white balance to the camera.
     * @param WhiteBalance 
     */
    public void writeCameraWhiteBalance(AxisCamera.WhiteBalanceT WhiteBalance){
        camera.writeWhiteBalance(WhiteBalance);
    }
    
    //</editor-fold>
    
    /**
     * Gets all the particle analysis reports ordered from largest area to smallest.
     * The particles are found by filtering the RGB values between the low and high values.
     * They should be in the range 0-255 inclusive.
     * @return 
     */
    public ParticleAnalysisReport[] getOrderedReport(int redLow, int redHigh, int greenLow, int greenHigh, int blueLow, int blueHigh) throws NIVisionException{
        return (currentImage.thresholdRGB(redLow, redHigh, greenLow, greenHigh, blueLow, blueHigh)).getOrderedParticleAnalysisReports();
    }
    
}
