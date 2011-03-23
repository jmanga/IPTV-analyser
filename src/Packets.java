
public class Packets {
	int PacketSize;
	int PacketID;
	Codecs PacketCodec;

	public Packets(String cod){
		this.PacketSize = 0;
		this.PacketCodec = Codecs.valueOf(cod);
	}
	
	public Packets(){
		this.PacketSize = 0;
		this.PacketCodec = Codecs.MPEG4;
	}
	
	public int getPacSize(){
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
