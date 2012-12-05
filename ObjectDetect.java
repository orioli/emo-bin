import java.io.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import lejos.nxt.Button;
import lejos.nxt.*;
import lejos.robotics.objectdetection.*;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;

import lejos.nxt.LCD;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;

/* @author Jose Bernegueres emo-bin project 2012.Nov.27th, Al Ain */
/* important: make sure usb ports dont go to suspend mode in Windows */

// * Example leJOS Project with an ant build file
// * ./../../../../../../../leJOS_NXJ_0.9.1beta-3/bin/nxjc ./ObjectDetect.java
// * ./../../../../../../../leJOS_NXJ_0.9.1beta-3/bin/nxjlink -o ObjectDetect.nxj ObjectDetect
// * ./../../../../../../../leJOS_NXJ_0.9.1beta-3/bin/nxjupload -r ObjectDetect.nxj

// THIS RUNS ON NEXT
public class ObjectDetect implements FeatureListener {
    
	private static DataOutputStream dOut;
	public static int MAX_DETECT = 30;
	public static void main(String[] args) throws Exception {

        // --- OPEN USB CONNECTION TO PC ---
        LCD.drawString("waiting usb", 0, 0);
		USBConnection conn = USB.waitForConnection();
		dOut = conn.openDataOutputStream();
		//DataInputStream dIn = conn.openDataInputStream();

		LCD.drawString("Do not touch!", 0, 0);
        
		ObjectDetect listener = new ObjectDetect();
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S4);
		RangeFeatureDetector fd = new RangeFeatureDetector(us, MAX_DETECT, 100);
		fd.addListener(listener);
		Button.ENTER.waitForPressAndRelease();
        Button.waitForAnyPress();
	}
	
	public void featureDetected(Feature feature, FeatureDetector detector) {
		int range = (int)feature.getRangeReading().getRange();
		//Sound.playTone(1200 - (range * 10), 100);
		//System.out.println("Range:" + range);
        // send range to PC
        try {
			if (range < 23)
			{
				dOut.writeInt(range);
				dOut.flush();
				Thread.sleep(1000);
			}
        } catch (Exception e) {
            System.err.println("IO Exception sending range");
            System.out.println("Exception ioe" );
        }

	}
}

