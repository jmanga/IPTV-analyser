import java.io.*;


public class Session {
	double InitialTime;
	//int ArrivalTimeSession;
	//int ArrivalTimePacket;
	int SessionID;
	double FinalTime;
	double SessionSize;
	//double meanSizeSession;
	double meanSizePacket;
	//double meanArrivalSession;
	double meanArrivalPacket;
	Flow flow;
	//RNGenerator RandomNumber;
	RNGenerator[] array = new RNGenerator[4];

	
	public Session(int SesID, /*int ArrivTP,*/ double InitTime, double SesSize, double meanAP, double meanSP){
		this.SessionID = SesID;
		//this.ArrivalTimePacket = ArrivTP;
		this.SessionSize = SesSize;
		this.InitialTime = InitTime;
		this.FinalTime = this.InitialTime + this.SessionSize;
		if(this.FinalTime <= this.InitialTime){
			System.out.printf("Valor invalido pra sessão ", SesID);
			System.exit(0);
		}
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
		double ICPac = 0,SizePac, TArrive = this.InitialTime;
		ICPac = this.array[0].GeneratedValues(this.meanArrivalPacket);
		SizePac = this.array[0].GeneratedValues(this.meanSizePacket);
		while(TArrive <= this.FinalTime){
			PacketID += 1;
			flow.GeneratePacket(PacketID, TArrive, SizePac, this.SessionID);
			ICPac = this.array[0].GeneratedValues(this.meanArrivalPacket);
			SizePac = this.array[0].GeneratedValues(this.meanSizePacket);
			TArrive = TArrive + ICPac;
		}
		return 0;
	}
	
	
}
