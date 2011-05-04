
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
	

	//Construtor para tipo de sistema usando frames
	public Session(int SesID, double InitTime, double SesSize, double meanAP, double ErrorFrames, int TypeErrDist, 
			double CorrelationIP, double CorrelationPB, double StandardDeviationP, double StandardDeviationI, 
			double StandardDeviationB, int PacSize, Semaphore s, String typeGOP){
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
			System.out.printf("Valor invalido pra sessão ", SesID);
			System.exit(0);
		}
		this.qtFrames = Integer.parseInt(this.GOPtype.split(",")[0]);
		this.distanceP = Integer.parseInt(this.GOPtype.split(",")[1]);
		this.contFrame = 1;
		//this.flow = new Flow(this.meanArrivalPacket, this.meanSizePacket, this.sem);
	}
	

	//Construtor para tipo de sistema tradicional
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
		//this.flow = new Flow(this.meanArrivalPacket, this.meanSizePacket, this.sem);
	}
	
	//método que instancia as distribuições utilizadas no projeto em um array
	public void GenerateDist(){
		this.array[0] = new ExponentialDistribution();
		//this.array[1] = new valores fixos;
		//this.array[2] = new GammaDistribution();
		//this.array[3] = new ParetoDistribution();
	}
	
	public void run(){
		this.StartSession(2);
	}
	
	//método que verifica se próximo frame a ser gravado é P ou B de acordo com escolha do tipo de GOP
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
	
	//Inicializa a sessão verificando tipo do sistema: tradicional ou por GOP
	public int StartSession(int typeSystem){
		double TArrive = this.InitialTime;
		if(typeSystem == 1){
			double ICPac = 0, SizePac;
			ICPac = this.array[0].GeneratedValues(this.meanArrivalPacket);
			//SizePac = this.array[0].GeneratedValues(this.meanSizePacket);
			while(TArrive <= this.FinalTime){
				//this.GeneratePacket(TArrive, SizePac, this.SessionID);
				ICPac = this.array[0].GeneratedValues(this.meanArrivalPacket);
				//SizePac = this.array[0].GeneratedValues(this.meanSizePacket);
				TArrive = TArrive + ICPac;
			}
		}
		else this.GenerateFrame();
		return 0;
	}
	
	/*
	//Método tradicional para gerar os pacotes
	public double GeneratePacket(double ICPac, double SizePac){
		packet = new Packets(this.SessionID, PacketID, this.PacketArrival, this.PacketSize);

		System.out.printf("ID - %d IDPac - %d T.cheg - %f TamPac  - %f\n", SesID, PacketID,  this.PacketArrival, this.PacketSize);
		//Chamada a função para gravação dos dados passando o pacote como parametro: IDSES, IDPACK, TIMEARRIVALPACK, SIZEPACK
		try {
			Simulator.recordData(packet, this.sem);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.PacketID;
	}*/
	
	//Essa função gera os frames do GOP e estes geram os pacotes
	public void GenerateFrame(){
		double timeLastPac = 0, TArrive = this.InitialTime;
		int contFrames;
		while(TArrive <= this.FinalTime){ //Enquanto não atingir tempo final da sessão
			contFrames = 0;
			this.frame = new Frame(this.SessionID, this.meanArrivalPacket, this.CorrelationIP, this.CorrelationPB, this.StandardDeviationP, 
				this.StandardDeviationI, this.StandardDeviationB,this.MeanErrorFrames, this.PacketSize, TArrive);
			this.frame.CalcFrameI(); //Frame I é sempre o primeiro de um GOP
			String nxFrame;
			TArrive = TArrive + this.array[0].GeneratedValues(this.meanArrivalPacket);
			if(TArrive >= this.FinalTime) break;
			while(contFrames <= this.qtFrames){ //de acordo com  tipo de GOP passado, faz a gravação de frames P e B
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
				TArrive = TArrive + this.array[0].GeneratedValues(this.meanArrivalPacket);
				if(TArrive >= this.FinalTime) break;
				contFrames++;
			}
		}
	}


	public Frame getFrame() {
		return this.frame;
	}


	public void setFrame(Frame frame) {
		this.frame = frame;
	}
	
}
