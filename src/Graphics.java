
import javax.swing.JOptionPane;


public class Graphics {
	
	static int ThreadQuantity = 0;		// Threads = Sessions	
	static String DT = null;
	static double DI = 0;
	
	static String bullet = null;
	String[] Catch = null;
	static String s = null;
	
	static double SizeSessions;
	static double ArrivalPackets;
	static double ArrivalSessions;
	
	public Graphics(){
		
		Object[] possibilities = {"Exponential Distribution", "Constant Values Distribution"};
		 s = (String) JOptionPane.showInputDialog(null, "Chose wich distribution you prefer:\n",
		                    "Software Simulador de dados",
		                    JOptionPane.QUESTION_MESSAGE, null, possibilities,
		                    "Exponential Distribution");

		bullet = JOptionPane.showInputDialog("Enter with the quantity of sessions");  
		ThreadQuantity = Integer.parseInt(bullet);
		
		
		if(s == "Exponential Distribution"){
			bullet = JOptionPane.showInputDialog("Enter with the mean of sessions arrival");  
			ArrivalSessions = Double.parseDouble(bullet);
			bullet = JOptionPane.showInputDialog("Enter with the mean of sessions size");  
			SizeSessions = Double.parseDouble(bullet);
			bullet = JOptionPane.showInputDialog("Enter with the mean of packets arrival");  
			ArrivalPackets = Double.parseDouble(bullet);
		}
		else if(s == "Constant Values Distribution"){
			bullet = JOptionPane.showInputDialog("Enter with the fixed arrival of sessions (in seconds)");
			ArrivalSessions = Double.parseDouble(bullet);
			bullet = JOptionPane.showInputDialog("Enter with the fixed sessions size (in seconds)");  
			SizeSessions = Double.parseDouble(bullet);
			bullet = JOptionPane.showInputDialog("Enter with the fixed arrival of packets (in seconds)");  
			ArrivalPackets = Double.parseDouble(bullet);			
		}

	}
	
	static void printGraphics(){
		System.out.println(s);
	}
	static int returnThreadQ(){
		return ThreadQuantity;
	}
	static String returnDistribution(){
		return s;
	}
}



