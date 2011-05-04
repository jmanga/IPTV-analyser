
import java.io.*;

public interface RNGenerator {
	//int RandomNumber = 0;
	//String DistributionName = "";
	
	public abstract int StartSeed();
	public abstract double GeneratedValues(double meanSizePacket);
}
