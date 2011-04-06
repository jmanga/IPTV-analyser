import java.io.*;
import java.util.concurrent.Semaphore;

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
	Semaphore sem;
	
	public Session(int SesID, double InitTime, double SesSize, double meanAP, double meanSP, Semaphore s){
		this.SessionID = SesID;
		this.SessionSize = SesSize;
		this.InitialTime = InitTime;
		this.FinalTime = this.InitialTime + this.SessionSize;
		if(this.FinalTime <= this.InitialTime){
			System.out.printf("Valor invalido pra sessão ", SesID);
			System.exit(0);
		}
		this.sem = s;
		this.meanSizePacket = meanSP;
		this.meanArrivalPacket = meanAP;
		this.flow = new Flow(this.meanArrivalPacket, this.meanSizePacket, this.sem);
		array[0]= new ExponentialDistribution();
		
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

