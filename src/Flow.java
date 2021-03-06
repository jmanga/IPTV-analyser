import java.io.*;
import java.util.concurrent.Semaphore;
import java.lang.*;
 
import cern.jet.random.engine.RandomEngine;
import cern.jet.stat.Probability;

// variavel tempo de gera��o de pacotes, tempo n�o zera, soh aumenta

public class Flow {
	double PacketSize;
	double PacketArrival;
	//RNGenerator RandomNumberFlow;
	int PacketID;
	double meanSizePacket;
	double meanArrivalPacket;
	Packets packet;
	Semaphore sem;
	
	public Flow(double meanArrivPac, double meanSizePac, Semaphore s) { 
	    this.PacketSize = 0; 
	    this.PacketArrival = 0;
	    this.PacketID = 0;
	    this.meanArrivalPacket = meanArrivPac;
	    this.meanSizePacket = meanSizePac;
	    this.sem = s;
	}
	
	public double getPacketsize(){
		return this.PacketSize;
	}
	
	public double getPacketarrival(){
		return this.PacketArrival;
	}
	
	public void setPacketsize(int pacs){
		this.PacketSize = pacs;
	}
		
	public void setPacketarrival(int paca){
		this.PacketArrival = paca;
	}
	
	//M�todo tradicional para gerar os pacotes
	public double GeneratePacket(int PacketID, double ICPac, double SizePac, int SesID){
		this.PacketArrival = ICPac;
		this.PacketSize = SizePac;
		packet = new Packets(SesID, PacketID, this.PacketArrival, this.PacketSize);

		System.out.printf("ID - %d IDPac - %d T.cheg - %f TamPac  - %f\n", SesID, PacketID,  this.PacketArrival, this.PacketSize);
		//Chamada a fun��o para grava��o dos dados passando o pacote como parametro: IDSES, IDPACK, TIMEARRIVALPACK, SIZEPACK
		try {
			ThreadExecution.recordData(packet, this.sem);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.PacketID;
	}
	
	//Essa fun��o gera os frames do GOP e estes geram os pacotes
	public void GenerateFrame(){
		//Packets[] pac = new Packets[];
		
	}
}