import java.util.concurrent.Semaphore;

class XThread extends Thread {

	static double InitTimeSession;
	static double SessionSize;
	static double MeanArrivalPacket;
	static double MeanSizePacket;
	private int id;
	Semaphore semap;
	
	RNGenerator Rand = new ExponentialDistribution();

	public XThread(String threadName, int id, Semaphore sem) {
		super(threadName); // Initialize thread.
		this.id = id;
		this.semap = sem;
	}

	public void run() {
		if (Graphics.returnDistribution() == "Exponential Distribution") {
			InitTimeSession = Rand.GeneratedValues(Graphics.MeanArrivalSession);
			SessionSize = Rand.GeneratedValues(Graphics.MeanSizeSession);
			MeanArrivalPacket = Rand
					.GeneratedValues(Graphics.MeanArrivalPacket);
			MeanSizePacket = Rand.GeneratedValues(Graphics.MeanSizePacket);
		}
		if (Graphics.returnDistribution() == "Constant Values Distribution") {
			InitTimeSession = Graphics.TimeSession;
			SessionSize = Graphics.MountSession;
			MeanArrivalPacket = Graphics.TimePackets;
			MeanSizePacket = Graphics.MountPackets;
		}
		Session Initiate = new Session(id, InitTimeSession, SessionSize,
				MeanArrivalPacket, MeanSizePacket, semap);
		Initiate.StartSession();
		InitTimeSession += Rand.GeneratedValues(Graphics.MeanArrivalSession);
	}

}

