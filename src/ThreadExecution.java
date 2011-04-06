import java.io.*;
import java.util.concurrent.Semaphore;
import java.text.DecimalFormat;

import javax.swing.JOptionPane;


import java.io.*;

public class ThreadExecution{
	
	static boolean check = false;
	static OutputFile Rec = new OutputFile("IPTVdata.txt");
	static DecimalFormat df = new DecimalFormat("#.############");
	
	public static void main(String[] args) throws IOException, InterruptedException{
	    //Start the Main Thread
		Thread MainThread =  new Thread("MainThread");
		MainThread.start();
	    new Graphics();
	    Semaphore sem = new Semaphore(Graphics.ThreadQuantity, true);
	    
    	for(int id = 1; id <= Graphics.returnThreadQ(); id++){
    		new XThread("Session" + id, id, sem).start();
    	}
	}
	
	public static synchronized void recordData(Packets pac, Semaphore s) throws InterruptedException{
		int SesID, PacID;
		double TimeArrival, SizePac;
		Sem semaphore = new Sem(s);

		SesID = pac.getSesID();
		PacID = pac.getPacID();
		TimeArrival = pac.getPacArriv();
		SizePac = pac.getPacSize();

		String str = Integer.toString(SesID) + "\t" + Integer.toString(PacID) + 
				"\t" + df.format(TimeArrival) + "\t\t" + df.format(SizePac) + "\n";
		
		//semaphore.Wait();
		try{
			semaphore.Wait();
			check = Rec.WriteFile(str, check);
			semaphore.Signal();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//semaphore.Signal();
		pac = null;
	}

}


