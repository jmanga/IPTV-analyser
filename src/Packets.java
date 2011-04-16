
public class Packets {
	double PacketSize;
	int PacketID;
	Codecs PacketCodec;
	double PacketArrival;
	int SessionID;
	char FrameType;

	public Packets(int SesID, int PacID, double PacArrival, double PacSize, String cod){
		this.PacketSize = PacSize;
		this.PacketCodec = Codecs.valueOf(cod);
		this.PacketID = PacID;
		this.SessionID = SesID;
		this.PacketArrival = PacArrival;
	}
	
	public Packets(int SesID, int PacID, double PacArrival, double PacSize){
		this.PacketSize = PacSize;
		this.PacketCodec = Codecs.MPEG4;
		this.PacketID = PacID;
		this.SessionID = SesID;
		this.PacketArrival = PacArrival;
	}
	
	public Packets(int SesID, int PacID, double PacArrival, double PacSize, char FType){
		this.PacketSize = PacSize;
		this.PacketCodec = Codecs.MPEG4;
		this.PacketID = PacID;
		this.SessionID = SesID;
		this.PacketArrival = PacArrival;
		this.FrameType = FType;
	}
	
	public double getPacSize(){
		return this.PacketSize;
	}
	
	public int getSesID(){
		return this.SessionID;
	}
	
	public double getPacArriv(){
		return this.PacketArrival;
	}
	
	public int getPacID(){
		return this.PacketID;
	}
	
	public Codecs getPacCod(){
		return this.PacketCodec;
	}
	
	public void setPacSize(int pacs){
		this.PacketSize = pacs;
	}
	
	public void setPacID(int pacid){
		this.PacketID = pacid;
	}
	
	public void setPacArriv(int pacarr){
		this.PacketArrival = pacarr;
	}

	public void setPacCod(Codecs cod){
		this.PacketCodec = cod;
	}
}
