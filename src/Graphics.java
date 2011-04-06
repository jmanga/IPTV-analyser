import javax.swing.JOptionPane;


public class Graphics {
	
	static int ThreadQuantity = 0;		// Threads = Sessions	
	static String DT = null;
	static double DI = 0;
	
	static double MeanSizePacket;
	static double MeanArrivalPacket;
	static double MeanSizeSession;
	static double MeanArrivalSession;
	
	static String bullet = null;
	String[] Catch = null;
	static String s = null;
	
	static double TimeSession;
	static double TimePackets;
	static double MountSession;
	static double MountPackets;
	
	public Graphics(){
		
		Object[] possibilities = {"Exponential Distribution", "Constant Values Distribution"};
		 s = (String) JOptionPane.showInputDialog(null, "Chose wich distribution you prefer:\n",
		                    "Software Simulador de dados",
		                    JOptionPane.QUESTION_MESSAGE, null, possibilities,
		                    "Exponential Distribution");

		bullet = JOptionPane.showInputDialog("Enter with the quantity of sessions");  
		ThreadQuantity = Integer.parseInt(bullet);
		
		if(s == "Exponential Distribution"){
			bullet = JOptionPane.showInputDialog("Enter with the mean of session arrival");  
			MeanArrivalSession = Double.parseDouble(bullet);
			bullet = JOptionPane.showInputDialog("Enter with the mean of session size");  
			MeanSizeSession = Double.parseDouble(bullet);
			bullet = JOptionPane.showInputDialog("Enter with the mean of packet arrival");  
			MeanArrivalPacket = Double.parseDouble(bullet);
			bullet = JOptionPane.showInputDialog("Enter with the mean of packet size");  
			MeanSizePacket = Double.parseDouble(bullet);
		}
		else if(s == "Constant Values Distribution"){
			bullet = JOptionPane.showInputDialog("Enter with the session time (in seconds)");  
			TimeSession = Double.parseDouble(bullet);
			bullet = JOptionPane.showInputDialog("Enter with the packets time (in seconds)");
			TimePackets = Double.parseDouble(bullet);
			bullet = JOptionPane.showInputDialog("Enter with the mount of time (in seconds)");  
			MountSession = Double.parseDouble(bullet);
			bullet = JOptionPane.showInputDialog("Enter with the mount of packets (in seconds)");
			MountPackets = Double.parseDouble(bullet);
			
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

