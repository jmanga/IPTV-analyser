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
	double MeanErrorP;
	double MeanErrorB;
	double TimePac;
	double[] PacInterval = new double[3]; //media intervalo entre pacotes
	int SessionID;
	int PacketID;
	double PacketSizeDefault;
	double PacketSize;
	ArrayList<Packets> PacketList = new ArrayList<Packets>();
	
	public Frame(int sesID, double[] PacInterv, double correlatIP, double correlatPB, double StdDevP, 
			double StdDevI, double StdDevB, double meanErrB, double meanErrP, int SizePac, double FTime){
		this.SessionID = sesID;
		this.FirstB = true;
		this.FirstI = true;
		this.FirstP = true;
		this.TimePac = FTime;
		this.PacInterval = PacInterv;
		this.correlationIP = correlatIP;
		this.correlationPB = correlatPB;
		this.StdDeviationB = StdDevB;
		this.StdDeviationI = StdDevI;
		this.StdDeviationP = StdDevP;
		//this.PacketID = lastID;
		this.PacketSizeDefault = 1500;
		this.PacketSize = SizePac;
		//this.SizeI = 1500; 
		this.MeanErrorB = meanErrB;
		this.MeanErrorP = meanErrP;
		this.GenerateDist();
		this.PacketID = 1;
	}

	public double getTimePac() {
		return TimePac;
	}

	public void setTimePac(double timePac) {
		TimePac = timePac;
	}

	public int getSessionID() {
		return SessionID;
	}

	public void setSessionID(int sessionID) {
		SessionID = sessionID;
	}

	public int getPacketID() {
		return PacketID;
	}

	public void setPacketID(int packetID) {
		PacketID = packetID;
	}

	public void remove_packet(){
		this.PacketList.remove(0);
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
		else return -1;
	}
	
	//Método que retorno ID do próximo pacote a ser criado
	public int IDGenerator(){
		//System.out.printf("ID -> %d\n", this.PacketID);
		return this.PacketID++;
	}
	
	public void Test() throws InterruptedException{
		Packets packet;
		for(int cont = 1;cont < 6; cont++){
			this.VerifySemaphore();
			this.TimePac = this.TimePac + 1;
		    packet = new Packets(this.SessionID, cont, this.TimePac, 100, 'I');
		    this.PacketList.add(packet);
		    
		}
	}
	
	public void VerifySemaphore() throws InterruptedException{
		Simulator.sem_critic_region.acquire();
		if(this.PacketList.isEmpty()){
			Simulator.sem_get_event.release();
		}
		Simulator.sem_critic_region.release();
	}
	
	//Método que cálcula o tamanho do Frame I e pede pra gerar os pacotes
	public synchronized double CalcFrameI() throws InterruptedException{
		this.SizeI = 1500; //por enquanto valor fixo, depois valor vindo de distribuição
		this.GeneratePacI();
		this.lastValueB = 0;
		this.lastValueP = 0;
		this.FirstB = true;
		this.FirstP = true;
		return this.TimePac;
	}
	
	public synchronized void CalcFirstFrameP(){
		double Error = this.MeanErrorP;
		this.lastValueP = this.SizeI*(this.correlationIP*this.StdDeviationP)/this.StdDeviationI + Error;
	}
	
	//método que calcula o tamanho do Frame P e pede pra gerar os pacotes
	public synchronized double CalcFrameP() throws InterruptedException{
		
		double Error = this.MeanErrorP;
		if(this.FirstP){ //Primeiro P do GOP é calculado dessa forma
			this.FirstP = false;
		}
		else{ //Próximos P's do GOP são calculados baseados no ultimo P
			this.lastValueP = this.lastValueP + Error;
		}
		this.GeneratePacP();
		return this.TimePac;
	}
	
	//método que calcula o tamanho do Frame P e pede pra gerar os pacotes
	public synchronized double CalcFrameB() throws InterruptedException{
		double Error = this.MeanErrorB;
		if(this.FirstB){ //Primeiro B do GOP é calcula dessa forma
			this.CalcFirstFrameP();
			this.lastValueB = this.lastValueP*(this.correlationPB*this.StdDeviationB)/this.StdDeviationP + Error;
			this.FirstB = false;
		}
		else{ //Próximos P's do GOP são calculados baseados no ultimo P
			this.lastValueB = this.lastValueB + Error;
		}
		this.GeneratePacB();
		return this.TimePac;
		
	}
	
	public double CalculateValueDistribution(){
		int typedistrib = (int)this.PacInterval[0];
		double parameter1 = this.PacInterval[1];
		double parameter2 = this.PacInterval[2];
		if(typedistrib != 1){
			return this.array[typedistrib].GeneratedValues(parameter1, parameter2);
		}
		else{
			return parameter1;
		}
	}
	
	// Cria os pacotes do tipo I de acordo com o tamanho maximo de cada pacote
	public synchronized double GeneratePacI() throws InterruptedException{	
		int PacQt = (int) (this.SizeI / this.PacketSize);
		double SizeLastPac = this.SizeI % this.PacketSize;
		this.VerifySemaphore();
		Packets packet;
		for(int i = 0; i< PacQt; i++){	
			this.TimePac = this.TimePac + this.CalculateValueDistribution();
			packet = new Packets(this.SessionID, this.IDGenerator(), this.TimePac, this.PacketSize, 'I');
			this.PacketList.add(packet);
		}
		if(SizeLastPac != 0){
			this.TimePac = this.TimePac + this.CalculateValueDistribution();
			packet = new Packets(this.SessionID, this.IDGenerator(), this.TimePac, SizeLastPac, 'I');
			this.PacketList.add(packet);
			return PacQt + 1;
		}
		//System.out.printf("Qt pac: %d frame I ses: %d\n", PacQt, this.SessionID);
		
		return 0;
	}
	
	// Cria os pacotes do tipo B de acordo com o tamanho maximo de cada pacote
	public synchronized double GeneratePacB() throws InterruptedException{
		
		int PacQt = (int) (this.lastValueB / this.PacketSize);
		double SizeLastPac = this.lastValueB % this.PacketSize;
		this.VerifySemaphore();
		Packets packet;
		for(int i = 0; i< PacQt; i++){
			this.TimePac = this.TimePac + this.CalculateValueDistribution();
			packet = new Packets(this.SessionID, this.IDGenerator(), this.TimePac, this.PacketSize, 'B');
			this.PacketList.add(packet);
		}
		if(SizeLastPac != 0){
			this.TimePac = this.TimePac + this.CalculateValueDistribution();
			packet = new Packets(this.SessionID, this.IDGenerator(), this.TimePac, SizeLastPac, 'B');
			this.PacketList.add(packet);
			return PacQt + 1;
		}
		//System.out.printf("Qt pac: %d frame B\n", PacQt);

		return PacQt;
	}
	
	// Cria os pacotes do tipo P de acordo com o tamanho maximo de cada pacote
	public synchronized double GeneratePacP() throws InterruptedException{
		
		int PacQt = (int) (this.lastValueP / this.PacketSize);
		double SizeLastPac = this.lastValueP % this.PacketSize;
		this.VerifySemaphore();
		Packets packet;
		for(int i = 0; i< PacQt; i++){
			this.TimePac = this.TimePac + this.CalculateValueDistribution();
			packet = new Packets(this.SessionID, this.IDGenerator(), this.TimePac, this.PacketSize, 'P');
			this.PacketList.add(packet);
		}
		if(SizeLastPac != 0){
			this.TimePac = this.TimePac + this.CalculateValueDistribution();
			packet = new Packets(this.SessionID, this.IDGenerator(), this.TimePac, SizeLastPac, 'P');
			this.PacketList.add(packet);
			return PacQt + 1;
		}
		//System.out.printf("Qt pac: %d frame P\n", PacQt);

		return 0;
	}
}
