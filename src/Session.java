import java.io.*;

public class Session {
	float InitialTime;
	int ArrivalTimeSession;
	int ArrivalTimePacket;
	int SessionID;
	float FinalTime;
	float SessionSize;
	float meanSizeSession;
	float meanSizePacket;
	float meanArrivalSession;
	float meanArrivalPacket;
	Flow flow;
	//RNGenerator RandomNumber;
	RNGenerator[] array = new RNGenerator[4];

	
	public Session(int SesID, /*int ArrivTP,*/ float InitTime, float SesSize, float meanAP, float meanSP){
		this.SessionID = SesID;
		//this.ArrivalTimePacket = ArrivTP;
		this.SessionSize = SesSize;
		this.InitialTime = InitTime;
		this.FinalTime = this.InitialTime + this.SessionSize;
		//this.meanSizeSession = meanSS;
		this.meanSizePacket = meanSP;
		//this.meanArrivalSession = meanAS;
		this.meanArrivalPacket = meanAP;
		this.flow = new Flow(this.meanArrivalPacket, this.meanSizePacket);
		array[0]= new ExponentialDistribution();
		//this.RandomNumber = new ExponentialDistribution(media);
	}
	
	public int Time(){
		//inserir chamada a função que contem o contador de tempo
		return 0;
	}
	
	public int StartSession(){
		int PacketID = 0;
		double ICPac,SizePac;
		while(Time() <= this.FinalTime){
			PacketID += 1;
			ICPac = this.array[0].GeneratedValues(this.meanArrivalPacket);
			SizePac = this.array[0].GeneratedValues(this.meanSizePacket);
			flow.GeneratePacket(ICPac,SizePac);
		}
		
		return SessionID;
	}
	
	
}
