import java.io.*;

import javax.swing.JOptionPane;


public class ThreadExecution{
	
	static int ThreadQuantity = 0;		// Threads = Sessions
	
	static int MeanSizePacket = 0;
	static int MeanArrivalPacket = 0;
	static int MeanSizeSession = 0;
	static int MeanArrivalSession = 0;
	
	static double ArrivalTimePacket = 0;
	static double SizePacket = 0;
	static double InitTimeSession = 0;
	static double SessionTime = 0;
	static double SessionSize = 0;
	

	public static void main(String[] args) throws IOException, InterruptedException{

		String bullet = JOptionPane.showInputDialog("Enter with the quantity of sessions");  
		ThreadQuantity = Integer.parseInt(bullet);
		//bullet = JOptionPane.showInputDialog("Enter with the mean of packet size");  
		//MeanSizePacket = Integer.parseInt(bullet);
		//bullet = JOptionPane.showInputDialog("Enter with the mean of packet arrival");  
		//MeanArrivalPacket = Integer.parseInt(bullet);
		//bullet = JOptionPane.showInputDialog("Enter with the mean of session size");  
		//MeanSizeSession = Integer.parseInt(bullet);
		bullet = JOptionPane.showInputDialog("Enter with the mean of session arrival");  
		MeanArrivalSession = Integer.parseInt(bullet);
		bullet = JOptionPane.showInputDialog("Enter with the mean of session size");  
		MeanSizeSession = Integer.parseInt(bullet);
		bullet = JOptionPane.showInputDialog("Enter with the mean of packet arrival");  
		MeanArrivalPacket = Integer.parseInt(bullet);
		bullet = JOptionPane.showInputDialog("Enter with the mean of packet size");  
		MeanSizePacket = Integer.parseInt(bullet);
		
	    Thread MainThread =  new Thread(new XThread(),"MainThread");
	    
	    Thread[] ThreadsArray = new Thread[ThreadQuantity+1]; 

	    //Start the threads
	    MainThread.start();

	    RNGenerator Receiver = new ExponentialDistribution();
   
	    for(int id = 1; id < ThreadQuantity+1; id++){
	    	Thread Thread =  new Thread(new XThread(),"Sessão"+id);   	
	    	ThreadsArray[id] = Thread;
	    	ThreadsArray[id].start();
	    	
		    InitTimeSession = Receiver.GeneratedValues((double) MeanArrivalSession);
		    SessionTime = Receiver.GeneratedValues((double) MeanArrivalSession);
		    SessionSize = Receiver.GeneratedValues((double) MeanSizeSession);
		    
	    	Session Initiate = new Session(id, InitTimeSession, SessionSize, MeanArrivalPacket, MeanSizePacket);
	    	Initiate.StartSession();
	    	//System.out.println(Initiate.InitialTime);
	    }
	    
	        
	    try{
	    	Thread.currentThread();
	    		//The sleep() method is invoked on the main thread to cause a one second delay.
	        Thread.sleep(1000);
	    }catch(InterruptedException e){}
	
	    //Display info about the main thread    
	    //System.out.println(Thread.currentThread()); 
	    
	}
}
