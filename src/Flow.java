import java.io.*;

// variavel tempo de geração de pacotes, tempo não zera, soh aumenta

public class Flow {
	double PacketSize;
	double PacketArrival;
	//RNGenerator RandomNumberFlow;
	int PacketID;
	float meanSizePacket;
	float meanArrivalPacket;
	Packets packet;
	
	public Flow(float meanArrivPac, float meanSizePac) { 
	    this.PacketSize = 0; 
	    this.PacketArrival = 0;
	    this.PacketID = 0;
	    this.meanArrivalPacket = meanArrivPac;
	    this.meanSizePacket = meanSizePac;
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
	
	public double GeneratePacket(double ICPac, double SizePac){
		//this.PacketID = this.PacketID + 1;
		this.PacketArrival = ICPac;
		this.PacketSize = SizePac;
		packet = new Packets();
		//Chamada a função para gravação dos dados passando o pacote como parametro: IDSES, IDPACK, TIMEARRIVALPACK, SIZEPACK
		return this.PacketID;
	}
}
