import java.util.ArrayList;

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
	double StdDeviationP;
	double StdDeviationI;
	double StdDeviationB;
	double MeanError;
	double FirstTime;
	double PacInterval;
	int SessionID;
	int PacketID;
	double PacketSizeDefault;
	ArrayList<Packets> PacketList = new ArrayList<Packets>();
	
	public Frame(int sesID, double PacInterv, double correlatIP, double correlatPB, double StdDevP, 
			double StdDevI, double StdDevB, double meanErr, int SizePac, double FTime){
		this.SessionID = sesID;
		this.FirstB = true;
		this.FirstI = true;
		this.FirstP = true;
		this.FirstTime = FTime;
		this.PacInterval = PacInterv;
		this.correlationIP = correlatIP;
		this.correlationPB = correlatPB;
		this.StdDeviationB = StdDevB;
		this.StdDeviationI = StdDevI;
		this.StdDeviationP = StdDevP;
		this.PacketID = 0;
		this.PacketSizeDefault = 1500;
		//this.SizeI = 1500; 
		this.MeanError = meanErr;
		this.GenerateDist();
		this.PacketID = 1;
	}

	//Método para gerar as ditribuições usadas no projeto em um array
	public void GenerateDist(){
		this.array[0] = new ExponentialDistribution();
		//this.array[1] = new valores fixos;
		this.array[2] = new GammaDistribution();
		//this.array[3] = new ParetoDistribution();
	}
	
	//Método para obter evento de tempo de chegada do pacote
	public double GetEventFirstPacket(){
		if(this.PacketList.size() > 0)	return this.PacketList.get(0).getPacArriv();
		else return 0;
	}
	
	//Método que retorno ID do próximo pacote a ser criado
	public int IDGenerator(){
		return this.PacketID++;
	}
	
	//Método que cálcula o tamanho do Frame I e pede pra gerar os pacotes
	public synchronized int CalcFrameI(){
		this.SizeI = 1500; //por enquanto valor fixo, depois valor vindo de distribuição
		int QtPac = this.GeneratePacI();
		return QtPac;
	}
	
	//método que calcula o tamanho do Frame P e pede pra gerar os pacotes
	public synchronized double CalcFrameP(double TArrive){
		
		double Error = this.array[0].GeneratedValues(this.MeanError, 0);
		if(this.FirstP){ //Primeiro P do GOP é calcula dessa forma
			this.lastValueP = this.SizeI*(this.correlationIP*this.StdDeviationP)/this.StdDeviationI + Error;
		}
		else{ //Próximos P's do GOP são calculados baseados no ultimo P
			this.lastValueP = this.lastValueP + Error;
		}
		int QtPac = this.GeneratePacP(TArrive);
		return QtPac;
	}
	
	//método que calcula o tamanho do Frame P e pede pra gerar os pacotes
	public synchronized int CalcFrameB(double TArrive){
		double Error = this.array[0].GeneratedValues(this.MeanError, 0);
		if(this.FirstB){ //Primeiro P do GOP é calcula dessa forma
			this.lastValueB = this.lastValueP*(this.correlationPB*this.StdDeviationB)/this.StdDeviationP + Error;
		}
		else{ //Próximos P's do GOP são calculados baseados no ultimo P
			this.lastValueB = this.lastValueB + Error;
		}
		int QtPac = this.GeneratePacB(TArrive);
		return QtPac;
		
	}
	
	// Cria os pacotes do tipo I de acordo com o tamanho maximo de cada pacote
	public synchronized int GeneratePacI(){
		int PacQt = (int) (this.SizeI / 1500);
		double SizeLastPac = this.SizeI % 1500;
		Packets packet;
		for(int i = 0; i< PacQt; i++){	
			packet = new Packets(this.SessionID, this.IDGenerator(),this.FirstTime, this.PacketSizeDefault, 'I');
			this.PacketList.add(packet);
		}
		if(SizeLastPac != 0){
			packet = new Packets(this.SessionID, this.IDGenerator(), this.FirstTime, SizeLastPac, 'I');
			this.PacketList.add(packet);
			return PacQt + 1;
		}
		//System.out.printf("Qt pac: %d frame I ses: %d\n", PacQt, this.SessionID);
		return PacQt;
	}
	
	// Cria os pacotes do tipo B de acordo com o tamanho maximo de cada pacote
	public synchronized int GeneratePacB(double TArrive){
		int PacQt = (int) (this.lastValueB / 1500);
		double SizeLastPac = this.lastValueB % 1500;
		Packets packet;
		for(int i = 0; i< PacQt; i++){
			packet = new Packets(this.SessionID, this.IDGenerator(), TArrive, this.PacketSizeDefault, 'B');
			this.PacketList.add(packet);
		}
		if(SizeLastPac != 0){
			packet = new Packets(this.SessionID, this.IDGenerator(), TArrive, SizeLastPac, 'B');
			this.PacketList.add(packet);
			return PacQt + 1;
		}
		//System.out.printf("Qt pac: %d frame B\n", PacQt);
		return PacQt;
	}
	
	// Cria os pacotes do tipo P de acordo com o tamanho maximo de cada pacote
	public synchronized int GeneratePacP(double TArrive){
		int PacQt = (int) (this.lastValueP / 1500);
		double SizeLastPac = this.lastValueP % 1500;
		Packets packet;
		for(int i = 0; i< PacQt; i++){
			packet = new Packets(this.SessionID, this.IDGenerator(), TArrive, this.PacketSizeDefault, 'P');
			this.PacketList.add(packet);
		}
		if(SizeLastPac != 0){
			packet = new Packets(this.SessionID, this.IDGenerator(), TArrive, SizeLastPac, 'P');
			this.PacketList.add(packet);
			return PacQt + 1;
		}
		//System.out.printf("Qt pac: %d frame P\n", PacQt);
		return PacQt;
	}
}
