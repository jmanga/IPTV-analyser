
public class Packets {
	double PacketSize;
	int PacketID;
	Codecs PacketCodec;

	public Packets(int PacID, double PacS, String cod){
		this.PacketSize = PacS;
		this.PacketCodec = Codecs.valueOf(cod);
		this.PacketID = PacID;
	}
	
	public Packets(int PacID, double PacS){
		this.PacketSize = 0;
		this.PacketCodec = Codecs.MPEG4;
		this.PacketID = PacID;
	}
	
	public double getPacSize(){
		return this.PacketSize;
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
	
	public void setPacCod(Codecs cod){
		this.PacketCodec = cod;
	}
}
