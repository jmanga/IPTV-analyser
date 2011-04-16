
public class Frame {
	double SizeI;
	double lastValueP;
	double lastValueB;
	boolean FirstI;
	boolean FirstP;
	boolean FirstB;
	RNGenerator[] array = new RNGenerator[4];
	int SizePacs;
	double correlationIP;
	double correlationPB;
	double meanP;
	double meanI;
	double meanB;
	double Error;
	
	public double CalcStdDeviation(double num){
		return num*num;
	}
	
	public Frame(){
		this.FirstB = true;
		this.FirstI = true;
		this.FirstP = true;
		//this.SizeI = 1500; 
		
	}
	
	public double CalcFrameI(){
		this.SizeI = 1500; //por enquanto valor fixo, depois valor vindo de distribuição
		//this.GeneratePacI();
		return this.SizeI;
	}
	
	public double CalcFrameP(){
		if(this.FirstP){
			this.lastValueP = this.SizeI*(this.correlationIP*this.CalcStdDeviation(this.meanP))/this.CalcStdDeviation(this.meanI) + this.Error;
		}
		else{
			this.lastValueP = this.lastValueP + this.Error;
		}
		//this.GeneratePacP();
		return 0;
	}
	
	public double CalcFrameB(){
		if(this.FirstB){
			this.lastValueB = this.lastValueP*(this.correlationPB*this.CalcStdDeviation(this.meanB))/this.CalcStdDeviation(this.meanP) + this.Error;
		}
		else{
			this.lastValueB = this.lastValueB + this.Error;
		}
		//this.GeneratePacB();
		return 0;
	}
	
/*	public Packets GeneratePacI(){
		Packets packet;
		//packet = new Packets(SesID, PacketID, this.PacketArrival, this.PacketSize);
		return packet;
	}
	
	public Packets GeneratePacB(){
		Packets packet;
		//packet = new Packets(SesID, PacketID, this.PacketArrival, this.PacketSize);
	}
	
	public Packets GeneratePacP(){
		Packets packet;
		//packet = new Packets(SesID, PacketID, this.PacketArrival, this.PacketSize);
	}*/
}
