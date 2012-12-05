import javax.swing.Timer;
import java.awt.event.*;

//package org.lejos.pcsample.usbsend;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;
 
/**
 * This is a PC-side of emo-bin
 * Run the program by:
 * 
 *   java USBSend
 * 
 * Your NXT should be running a sample such as ObjectDetect.nxj.
 * If u are runnign Android you dont need the NXT says Tony
 * 
 * @author Jose Berengueres, adapted from Lawrie Griffiths
 *
 */
public class USBSend2 {

    public static long SMILE_TIME = 3000;
	public static int CAPACITY = 50;
    public static FullScreen f1 = new FullScreen("img/flat.png");
    
    public static void main(String args[]) {

        
		NXTConnector conn = new NXTConnector();
		conn.addLogListener(new NXTCommLogListener(){
                public void logEvent(String message) {
                    System.out.println("USBSend Log.listener: "+message);
                }
                public void logEvent(Throwable throwable) {
                    System.out.println("USBSend Log.listener - stack trace: ");
                     throwable.printStackTrace();
                }
            } 
        );
        
		if (!conn.connectTo("usb://")){
			System.err.println("No NXT found using USB");
			System.exit(1);
		}
		
		DataInputStream inDat = new DataInputStream(conn.getInputStream());
		//DataOutputStream outDat = new DataOutputStream(conn.getOutputStream());
		
        
        // --------------- intial face -----------        
        int delay = 4000;
        class FadeSmile implements ActionListener {
            public void actionPerformed( ActionEvent evt ){
                try{
                    f1.setImage("img/flat.png");
                }catch(Exception e){
                    System.out.println("Exception!!");
                }
            }
        }
        
        FadeSmile fs = new FadeSmile();
        Timer t = new Timer(delay,fs);
        t.start();
        
        try{
            f1.setImage("img/smile.jpg");
            Playme.playClip("wav/nintendo1.wav");
            Thread.sleep(1000);
        }catch(Exception e){
            System.out.println("One more excetion in ini");
        }
        
        // USB loop start
		int x=0,n=0,s = 0; // number of cans collected
        while (n < CAPACITY)
		{
			try {
                x = inDat.readInt(); // blocking
                Thread.sleep(2000); // fake

                n = n + 1 ;
                System.out.println( n + " Received " + x);
                f1.setImage("img/smile.jpg");
                t.restart();
                Playme.playClip("wav/nintendo1.wav");
                Thread.sleep(1000);
                
                if ( n%10 == 9){  //every ten cans
                    f1.setImage("img/milestone.png");
                    Playme.playClip("wav/" + s%10 +".wav");
                    f1.setImage("img/w.png");
                    Thread.sleep(1000);
                    s = s + 1 ;

                }
                
	        } catch (Exception e) {
                System.err.println("IO Exception reading reply");
                break;
	        }
            
		}
		
		try {
			inDat.close();
			//outDat.close();
			System.out.println("Closed data streams");
		} catch (IOException ioe) {
			System.err.println("IO Exception Closing connection");
		}
		
		try {
			conn.close();
			System.out.println("Closed connection");
		} catch (IOException ioe) {
			System.err.println("IO Exception Closing connection");
		}
        
        // notify that bin is full
        // send email
        // change dislplay
        // say I am full
	}
}
