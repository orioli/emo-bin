//package org.lejos.pcsample.usbsend;
import java.text.DateFormat;
import java.util.Calendar;

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

public class USBSend {

    public static long SMILE_TIME = 3000;
	public static int CAPACITY = 50000;
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
        //Timer timer = new Timer();
        try{
            f1.setImage("img/smile.jpg");
            Playme.playClip("wav/nintendo1.wav");
            Thread.sleep(1000);
        }catch(Exception e){
            System.out.println("One more excetion in ini");
        }
        
        // USB loop start
		int x=0,n=0,s = 0; // number of cans collected
        //Reminder r = new Reminder(4, f1);
        while (n < CAPACITY)
	  	{
		    try {
						f1.setImage("img/flat.png");
						x = inDat.readInt(); // blocking
		                n = n + 1 ;
                		f1.setImage("img/smile.jpg");
                		Playme.playClip("wav/nintendo1.wav");
						Calendar cal = Calendar.getInstance();
						DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL,
						DateFormat.MEDIUM);
						System.out.println( );
						System.out.println(n + "," + df.format(cal.getTime()));
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
