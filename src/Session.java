
import java.io.*;
import java.util.concurrent.Semaphore;

public class Session extends Thread{
	double InitialTime;
	int SessionID;
	double FinalTime;
	double SessionSize;
	int PacketSize;
	double meanArrivalPacket;
	RNGenerator[] array = new RNGenerator[4];
	Semaphore sem;
	int TypeErrorDist;
	double MeanErrorFrames;
	double CorrelationIP;
	double CorrelationPB;
	double StandardDeviationP;
	double StandardDeviationI;
	double StandardDeviationB;
	int StopCondition;
	String GOPtype;
	double meanSizePacket;
	Frame frame;
	int qtFrames;
	int distanceP;
	String NextFrame;
	int contFrame;
	boolean flag;
	

	//Construtor para tipo de sistema usando frames
	public Session(int SesID, double InitTime, double SesSize, double meanAP, double ErrorFrames, int TypeErrDist, 
			double CorrelationIP, double CorrelationPB, double StandardDeviationP, double StandardDeviationI, 
			double StandardDeviationB, int PacSize, Semaphore s, String typeGOP){
		this.flag = false;
		this.SessionID = SesID;
		this.SessionSize = SesSize;
		this.InitialTime = InitTime;
		this.MeanErrorFrames = ErrorFrames;
		this.TypeErrorDist = TypeErrDist;
		this.CorrelationIP = CorrelationIP;
		this.CorrelationPB = CorrelationPB;
		this.StandardDeviationP = StandardDeviationP;
		this.StandardDeviationI = StandardDeviationI;
		this.StandardDeviationB = StandardDeviationB;
		this.PacketSize = PacSize;
		this.GOPtype = typeGOP;
		this.sem = s;
		this.meanArrivalPacket = meanAP;
		this.GenerateDist();
		this.FinalTime = this.InitialTime + this.SessionSize;
		if(this.FinalTime <= this.InitialTime){
			System.out.printf("1 -> %f, 2 -> %f, 3 -> %f", this.InitialTime, this.FinalTime, SesSize);
			System.out.printf("Valor invalido pra sess�o ", SesID);
			System.exit(0);
		}
		this.qtFrames = Integer.parseInt(this.GOPtype.split(",")[0]);
		this.distanceP = Integer.parseInt(this.GOPtype.split(",")[1]);
		this.contFrame = 1;

	}
	

	//Construtor para tipo de sistema tradicional
	public Session(int SesID, double InitTime, double SesSize, double meanAP, double meanSP, Sem s){
		this.SessionID = SesID;
		this.SessionSize = SesSize;
		this.InitialTime = InitTime;
		this.FinalTime = this.InitialTime + this.SessionSize;
		if(this.FinalTime <= this.InitialTime){
			System.out.printf("Valor invalido pra sess�o ", SesID);
			System.exit(0);
		}
		//this.sem = s;
		this.meanSizePacket = meanSP;
		this.meanArrivalPacket = meanAP;
	}
	
	//m�todo que instancia as distribui��es utilizadas no projeto em um array
	public void GenerateDist(){
		this.array[0] = new ExponentialDistribution();
		//this.array[1] = new valores fixos;
		this.array[2] = new GammaDistribution();
		//this.array[3] = new ParetoDistribution();
	}
	
	public void run(){
		try {
			this.StartSession(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//m�todo que verifica se pr�ximo frame a ser gravado � P ou B de acordo com escolha do tipo de GOP
	public String VerifyNextFrame(){
		if(this.contFrame < this.distanceP){
			this.contFrame++;
			return "B";
		}
		else{
			this.contFrame = 1;
			return "P";
		}
	}
	
	//Inicializa a sess�o verificando tipo do sistema: tradicional ou por GOP
	public int StartSession(int typeSystem) throws InterruptedException{
		double TArrive = this.InitialTime;
		if(typeSystem == 1){
			double ICPac = 0, SizePac;
			ICPac = this.array[0].GeneratedValues(this.meanArrivalPacket, 0);
			//SizePac = this.array[0].GeneratedValues(this.meanSizePacket);
			while(TArrive <= this.FinalTime){
				//this.GeneratePacket(TArrive, SizePac, this.SessionID);
				ICPac = this.array[0].GeneratedValues(this.meanArrivalPacket, 0);
				//SizePac = this.array[0].GeneratedValues(this.meanSizePacket);
				TArrive = TArrive + ICPac;
			}
		}
		else this.GenerateFrame();
		return 0;
	}
	

	//Essa fun��o gera os frames do GOP e estes geram os pacotes
	public void GenerateFrame() throws InterruptedException{
		double timeLastPac = 0, TArrive = this.InitialTime;
		int contFrames;
		String nxFrame;
		while(TArrive <= this.FinalTime){ //Enquanto n�o atingir tempo final da sess�o
			contFrames = 0;
			this.frame = new Frame(this.SessionID, this.meanArrivalPacket, this.CorrelationIP, this.CorrelationPB, this.StandardDeviationP, 
				this.StandardDeviationI, this.StandardDeviationB,this.MeanErrorFrames, this.PacketSize, TArrive);
			this.frame.CalcFrameI(); //Frame I � sempre o primeiro de um GOP
			this.flag = true;
			TArrive = TArrive + this.array[0].GeneratedValues(this.meanArrivalPacket, 0);
			if(TArrive >= this.FinalTime) break;
			while(contFrames <= this.qtFrames){ //de acordo com  tipo de GOP passado, faz a grava��o de frames P e B
				nxFrame = this.VerifyNextFrame();
				if(nxFrame.equals("P")){
					timeLastPac = this.frame.CalcFrameP(TArrive);
				}
				else{
					if(nxFrame.equals("B")){
						timeLastPac = this.frame.CalcFrameB(TArrive);
					}
					else System.out.printf("ErrorFramesGenerate");
				}
				TArrive = TArrive + this.array[0].GeneratedValues(this.meanArrivalPacket, 0);
				if(TArrive >= this.FinalTime) break;
				contFrames++;
			}
		}
		this.sem.release();
	}


	public boolean getFlag() {
		return flag;
	}


	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	public Frame getFrame() {
		return this.frame;
	}


	public void setFrame(Frame frame) {
		this.frame = frame;
	}
	
}
