import java.util.concurrent.Semaphore;

class XThread extends Thread {

	static double PacketsArrival;
	static double SessionsArrival;
	static double SessionSize;

	private int id;
	Semaphore semap;
	
	RNGenerator Rand = new ExponentialDistribution();

	public XThread(String threadName, int id, Semaphore sem) {
		super(threadName); // Initialize thread.
		this.id = id;
		this.semap = sem;
	}

	public void run() {
		
		double Error = 5;
		double CorrelationIP = 5;
		double StandardDeviationP = 5;
		double StandardDeviationI = 5;
		
		if (Graphics.returnDistribution() == "Exponential Distribution") {
			SessionsArrival = Rand.GeneratedValues(Graphics.ArrivalSessions);
			SessionSize = Rand.GeneratedValues(Graphics.SizeSessions);
			PacketsArrival = Rand.GeneratedValues(Graphics.ArrivalPackets);

		}
		if (Graphics.returnDistribution() == "Constant Values Distribution") {
			SessionsArrival = Rand.GeneratedValues(Graphics.ArrivalSessions);
			SessionSize = Rand.GeneratedValues(Graphics.SizeSessions);
			PacketsArrival = Rand.GeneratedValues(Graphics.ArrivalPackets);
			
		}
		Session Initiate = new Session(id, SessionsArrival, SessionSize, PacketsArrival, semap,
				Error, CorrelationIP, StandardDeviationP, StandardDeviationI);
		Initiate.StartSession(1,0,0);	// método tradicional ou fodão (futuro)
		SessionsArrival += Rand.GeneratedValues(Graphics.ArrivalSessions);
	}

}




