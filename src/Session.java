
import java.io.*;
import java.util.concurrent.Semaphore;

public class Session extends Thread{
	double InitialTime;
	int SessionID;
	double FinalTime;
	double SessionSize;
	int PacketSize;
	double[] meanArrivalPacket = new double[3];// 0 - tipo distrib, 1 e 2 - parametros distribuição
	RNGenerator[] array = new RNGenerator[4];
	Semaphore sem;
	Semaphore sem_get_event;
	int TypeErrorDist;
	double ErrorFrameP;
	double ErrorFrameB;
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
	public Session(int SesID, double InitTime, double SesSize, double[] meanAP, double ErrorFrameP, double ErrorFrameB, 
			double CorrelationIP, double CorrelationPB, double StandardDeviationP, double StandardDeviationI, 
			double StandardDeviationB, int PacSize, Semaphore s, String typeGOP, Semaphore s2){
		this.flag = false;
		this.SessionID = SesID;
		this.SessionSize = SesSize;
		this.InitialTime = InitTime;
		this.ErrorFrameP = ErrorFrameP;
		this.ErrorFrameB = ErrorFrameB;
		//this.TypeErrorDist = TypeErrDist;
		this.CorrelationIP = CorrelationIP;
		this.CorrelationPB = CorrelationPB;
		this.StandardDeviationP = StandardDeviationP;
		this.StandardDeviationI = StandardDeviationI;
		this.StandardDeviationB = StandardDeviationB;
		this.PacketSize = PacSize;
		this.GOPtype = typeGOP;
		//this.sem = s;
		this.sem_get_event = s2;
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

	}
	
/*
	//Construtor para tipo de sistema tradicional
	public Session(int SesID, double InitTime, double SesSize, double meanAP, double meanSP, Sem s){
		this.SessionID = SesID;
		this.SessionSize = SesSize;
		this.InitialTime = InitTime;
		this.FinalTime = this.InitialTime + this.SessionSize;
		if(this.FinalTime <= this.InitialTime){
			System.out.printf("Valor invalido pra sessão ", SesID);
			System.exit(0);
		}
		//this.sem = s;
		this.meanSizePacket = meanSP;
		this.meanArrivalPacket = meanAP;
	}
*/	
	//método que instancia as distribuições utilizadas no projeto em um array
	public void GenerateDist(){
		this.array[0] = new ExponentialDistribution();
		//this.array[1] = new valores fixos;
		this.array[2] = new GammaDistribution();
		//this.array[3] = new ParetoDistribution();
	}
	
	public void run(){
		try {
			this.StartSession();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public int StartSession() throws InterruptedException{
		//double TArrive = this.InitialTime;
		this.GenerateFrame();
		return 0;
	}
	

	//Essa função gera os frames do GOP e estes geram os pacotes
	public void GenerateFrame() throws InterruptedException{
		
		double TArrive = this.InitialTime;
		int contFrames;
		String nxFrame;
		int lastIDPac = 0;
		this.frame = new Frame(this.SessionID, this.meanArrivalPacket, this.CorrelationIP, this.CorrelationPB, this.StandardDeviationP, 
				this.StandardDeviationI, this.StandardDeviationB,this.ErrorFrameB, this.ErrorFrameP, this.PacketSize, TArrive, lastIDPac);
		//System.out.printf("%f e %f", TArrive, this.FinalTime);
		//System.out.printf("%d inicia\n",this.SessionID);
		while(TArrive <= this.FinalTime){ //Enquanto não atingir tempo final da sessão
			contFrames = 0;
			TArrive = this.frame.CalcFrameI(); //Frame I é sempre o primeiro de um GOP
			if(this.flag == false){
				this.sem_get_event.release();
				this.flag = true;
			}			
			//TArrive = TArrive + this.array[0].GeneratedValues(this.meanArrivalPacket, 0);
			if(TArrive >= this.FinalTime) break;
			while(contFrames <= this.qtFrames+1){ //de acordo com  tipo de GOP passado, faz a gravação de frames P e B
				nxFrame = this.VerifyNextFrame();
				if(nxFrame.equals("P")){
					TArrive = this.frame.CalcFrameP();
				}
				else{
					if(nxFrame.equals("B")){
						TArrive = this.frame.CalcFrameB();
					}
					else System.out.printf("ErrorFramesGenerate");
				}
				//TArrive = TArrive + this.array[0].GeneratedValues(this.meanArrivalPacket, 0);
				if(TArrive >= this.FinalTime) break;
				contFrames++;
			}
			//lastIDPac = this.frame.getPacketID() + 1;
			//System.out.printf("%d\n", lastIDPac);
			this.contFrame = 1;
		}
		//System.out.printf("%d finaliza", this.SessionID);
		
		this.sem_get_event.acquire();
		//System.out.printf("antes: %d\n",Simulator.semap.availablePermits());
		Simulator.semap.release();
		//System.out.printf("depois: %d\n",Simulator.semap.availablePermits());
		Simulator.nt--;
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
