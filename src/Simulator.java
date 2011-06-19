import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.io.*;

public class Simulator extends Thread{

	static double[] PacketsArrival = new double[3];//0 - tipo distrib, 1 e 2 - parametros da distrib
	static double[] MeanSessionArrival = new double[3];
	static double[] MeanSessionSize = new double[3];
	static double TimeSystem = 0;
	static double SessionArrival = 0;
	static double SessionSize = 20;
	static public int SimultaneousSessions = 15;
	
	static int id = 1;
	static Semaphore semap = new Semaphore(1, true); // 15 sessões simultaneas
	static Semaphore sem_get_event = new Semaphore((SimultaneousSessions-1)*-1, true);
	static Semaphore sem_critic_region = new Semaphore(1, true);
	
	static RNGenerator Rand = new ExponentialDistribution();
	static List<Session> list = new ArrayList<Session>(); //lista de sessões
	
	static boolean check = false, StopSystem = false;
	static int contWr = 0, times = 0;
	static double timeSessionSaved = 0;
	static OutputFile Rec = new OutputFile("IPTVdata.txt");
	static DecimalFormat df = new DecimalFormat("###.############"); //formato de gravação no arquivo
	static int StopCritery, ArgStopCritery;
		
	public synchronized void run(){
		
		
		
		//Inicialmente os valores serão os relacionados abaixo
		final double ErrorP = 5;
		final double ErrorB = 5;
		final double CorrelationIP = 0.5;
		final double CorrelationPB = 0.5;
		final double StandardDeviationP = 5;
		final double StandardDeviationI = 5;
		final double StandardDeviationB = 5;
		//final int TypeErrorDist = 1;
		final int PacketSize = 500;
		final String GOP = new String("6,3");
		MeanSessionArrival[0] = 0;
		MeanSessionArrival[1] = 5;
		MeanSessionArrival[2] = 0;
		MeanSessionSize[0] = 0;
		MeanSessionSize[1] = 20;
		MeanSessionSize[2] = 0;
		PacketsArrival[0] = 0;
		PacketsArrival[1] = 3;
		PacketsArrival[2] = 0;
		StopCritery = 2;
		ArgStopCritery = 1000;
						
				
		//new Graphics();
		//thread com loop para criação das sessões
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int cont = 0; cont < SimultaneousSessions;cont++){
				//while(!StopSystem){
					/*try {
						semap.acquire();
						nt++;
						//System.out.printf("antes qt %d\n",list.size());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(id < 5 ){
					    System.out.printf("num thread = %d começo %f e tamanho %f\n",id, SessionArrival, SessionSize);			
					}*/
					Session Initiate = new Session(id, SessionArrival, SessionSize, PacketsArrival, ErrorP, ErrorB, CorrelationIP, 
							CorrelationPB, StandardDeviationP, StandardDeviationI, StandardDeviationB, PacketSize, GOP);
					list.add(Initiate);
					try {
						SetTimeArriveLastSession(SessionArrival);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Initiate.start();
					SessionArrival = SessionArrival + 10;
					//SessionArrival = SessionArrival + Rand.GeneratedValues(MeanSessionArrival[1], MeanSessionArrival[2]);
					//SessionSize = Rand.GeneratedValues(Graphics.SizeSessions); 
					id++;
				}
				//while(VerifyStopCritery(SessionArrival, contWr) != 0){ //Sempre verifica o criterio de parada 
				//	StopSystem = true;								 //interrompendo a criação de threads
				//}
			}
		}).start();
		
		Session auxSes;
		double lessSize;
		int IDlessSize;
			
		// Loop responsável por gerar o evento inserindo-o em um arquivo
		while(!StopSystem){
			//if(list.size() > 0){ //Verifica se há alguma sessão inserida na lista de sessões
			    
				try {
					//System.out.printf("antes2 \n");
					sem_get_event.acquire();
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				
				int cont = 0;
				//System.out.printf("depois2\n");
				auxSes = list.get(cont); //Pega a primeira sessão
				lessSize = auxSes.getFrame().GetEventFirstPacket(); //Pega o evento da primeira sessão
				cont++;
				/*while(lessSize == -1 && cont < 3){
					auxSes = list.get(cont);
					lessSize = auxSes.getFrame().GetEventFirstPacket();
					cont++;
				}*/
				IDlessSize = 0;						 //a variavel recebe um valor gigante para não ser descartada
				for(; cont < getList().size(); cont++){  //loop para verificar o menor evento
					auxSes = getList().get(cont);
					//if(auxSes.getFrame().PacketList.size() > 0){
					if((auxSes.getFrame().GetEventFirstPacket() != -1) && lessSize > auxSes.getFrame().GetEventFirstPacket() && lessSize >= TimeSystem){
						lessSize = auxSes.getFrame().GetEventFirstPacket();
						IDlessSize = cont;
					}
					//}
				}
				if(list.get(IDlessSize).getFrame().PacketList.size() > 0){
					recordData(list.get(IDlessSize).getFrame().PacketList.get(0)); //Com o menor evento em mãos...
					list.get(IDlessSize).getFrame().remove_packet();     //Ele é gravado em arquivo e removido
				
				}
				try {
					sem_critic_region.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//if(!list.get(IDlessSize).getFrame().PacketList.isEmpty()){
				    sem_get_event.release();
				//}
				sem_critic_region.release();
		}
		
		System.exit(1);
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Simulator MainThread =  new Simulator();
		new Thread(MainThread).start();
		//System.out.wait(10000);
	}

	public static void SetTimeArriveLastSession(double time) throws InterruptedException{
		semap.acquire();
		timeSessionSaved = time;
		semap.release();
	}
	
	public static double GetTimeArriveLastSession() throws InterruptedException{
		semap.acquire();
		timeSessionSaved =  timeSessionSaved + Rand.GeneratedValues(MeanSessionArrival[1], MeanSessionArrival[2]);
		semap.release();
		return timeSessionSaved;
	}
	
	public static void recordData(Packets pac){
		int SesID, PacID;
		double TimeArrival;
		int SizePac;
		char TypeF;

		SesID = pac.getSesID(); //Desempacota os dados
		PacID = pac.getPacID();
		TimeArrival = TimeSystem = pac.getPacArriv();
		SizePac = (int) Math.round(pac.getPacSize());
		TypeF = pac.getFType();

		String str = Integer.toString(SesID) + "\t" + Integer.toString(PacID) + "\t" + TypeF +
				"\t" + df.format(TimeArrival) + "\t\t" + SizePac;
		//System.out.printf(str);
		
		try{
			check = Rec.WriteFile(str, check); //Chama obj OutputFile para gravar o pacote, check verifica se deve ser apagado arquivo já criado 
			contWr++; // Variável utilizada para verificar critério de parada por numero de pacotes
			
		} catch (FileNotFoundException e) {
			System.out.printf("Error semaphore");
			e.printStackTrace();
		}
		if(VerifyStopCritery(TimeArrival, contWr) == 0){ //Sempre verifica o criterio de parada 
			StopSystem = true;		
		}//interrompendo a criação de threads
		pac = null;
	}
	
	//método para verificar se critério de parada é atendido
	public static int VerifyStopCritery(double crit1, int crit2){
		switch(StopCritery){
			case 1: if(ArgStopCritery <= crit1) return 0;
				break;
			case 2: if(ArgStopCritery <= crit2) return 0;
				break;
			default: break;
		}
		return 1;
	}

	public static void setList(List<Session> list) {
		Simulator.list = list;
	}

	public static List<Session> getList() {
		return list;
	}
}
