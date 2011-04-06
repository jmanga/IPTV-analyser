import java.io.*;
import java.util.concurrent.Semaphore;

// variavel tempo de geração de pacotes, tempo não zera, soh aumenta

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
	
	public double GeneratePacket(int PacketID, double ICPac, double SizePac, int SesID){
		this.PacketArrival = ICPac;
		this.PacketSize = SizePac;
		packet = new Packets(SesID, PacketID, this.PacketArrival, this.PacketSize);

		System.out.printf("ID - %d IDPac - %d T.cheg - %f TamPac  - %f\n", SesID, PacketID,  this.PacketArrival, this.PacketSize);
		//Chamada a função para gravação dos dados passando o pacote como parametro: IDSES, IDPACK, TIMEARRIVALPACK, SIZEPACK
		try {
			ThreadExecution.recordData(packet, this.sem);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.PacketID;
	}
}