import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.io.*;

public class Simulator extends Thread{

	//lista de parâmetros estáticos
	static GraphInter GUI = new GraphInter();
	
	static double[] PacketsArrival = new double[3];//0 - tipo distrib, 1 e 2 - parametros da distrib
	static double[] MeanSessionArrival = new double[3];
	static double[] MeanSessionSize = new double[3];
	static double TimeSystem = 0;
	static double SessionArrival = 0;
	static double SessionSize = 40;
	static public int SimultaneousSessions = 30;
	
	static int id, globalID = 0;
	static Semaphore semap = new Semaphore(1, true); // 15 sessões simultaneas
	static Semaphore sem_get_event = new Semaphore((SimultaneousSessions-1)*-1, true);
	static Semaphore sem_critic_region = new Semaphore(1, true);
	static Semaphore semap2 = new Semaphore(1, true);
	
	static RNGenerator Rand = new ExponentialDistribution();
	static List<Session> list = new ArrayList<Session>(); //lista de sessões
	
	static boolean check = false, StopSystem = false;
	static int contWr = 0, times = 0;
	static double timeSessionSaved = 0;
	static OutputFile Rec;
	static DecimalFormat df = new DecimalFormat("###.############"); //formato de gravação no arquivo
	static int StopCritery, ArgStopCritery;
		
	public synchronized void run(){
				
		//Inicialmente os valores serão os relacionados abaixo
		final double ErrorP = GUI.getErroP();
		final double ErrorB = GUI.getErroB();
		final double CorrelationIP = GUI.getCorrelIP();
		final double CorrelationPB = GUI.getCorrelPB();
		final double StandardDeviationP = GUI.getDesvPadP();
		final double StandardDeviationI = GUI.getDesvPadI();
		final double StandardDeviationB = GUI.getDesvPadB();
		//final int TypeErrorDist = 1;
		final int PacketSize = GUI.getTamPacote();
		final String GOP = Integer.toString(GUI.getTipoGOP1()) + "," + Integer.toString(GUI.getTipoGOP2());
		MeanSessionArrival = GUI.getRetChegSes();
		MeanSessionSize = GUI.getRetTamSes();
		PacketsArrival = GUI.getRetChegPac();
		StopCritery = GUI.getjCBCriterioParadaOptions();
		ArgStopCritery = GUI.getCriterioParada();
		Rec = new OutputFile(GUI.getNomeArquivo() + ".txt");
						
				
		//new Graphics();
		//thread com loop para criação das sessões
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int cont = 0; cont < SimultaneousSessions;cont++){
					SessionSize = CalculateValueDistribution(MeanSessionSize);
					try {
						id = GetIDSession();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
					//SessionArrival = SessionArrival + 10;
					SessionArrival = SessionArrival + CalculateValueDistribution(MeanSessionArrival);
					
					//id++;
				}
			}
		}).start();
		
		Session auxSes;
		double lessSize;
		int IDlessSize;
			
		// Loop responsável por gerar o evento inserindo-o em um arquivo
		while(!StopSystem){
			    
				try {
					sem_get_event.acquire();				
				} catch (InterruptedException e) {	
					e.printStackTrace();
				}
				
				int cont = 0;
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
					if((auxSes.getFrame().GetEventFirstPacket() != -1) && lessSize > auxSes.getFrame().GetEventFirstPacket() && lessSize >= TimeSystem){
						lessSize = auxSes.getFrame().GetEventFirstPacket();
						IDlessSize = cont;
					}
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
		
		/*for(int cont = 0; cont < list.size(); cont++){
			list.get(cont).interrupt();
		}*/
		Rec.Order(contWr, GUI.getNomeArquivo() + ".txt");
		System.exit(1);
	}
	
	public static void main(String[] args) throws InterruptedException {
		GUI.setVisible(true);	
		//Simulator MainThread =  new Simulator();
		//new Thread(MainThread).start();
	}

	public static void SetTimeArriveLastSession(double time) throws InterruptedException{
		semap.acquire();
		timeSessionSaved = time;
		semap.release();
	}
	
	public static double GetTimeArriveLastSession() throws InterruptedException{
		semap.acquire();
		timeSessionSaved =  timeSessionSaved + CalculateValueDistribution(MeanSessionArrival);
		semap.release();
		return timeSessionSaved;
	}
	
	public static int GetIDSession() throws InterruptedException{
		semap.acquire();
		globalID++;
		semap.release();
		return globalID;
	}
	
	public static double CalculateValueDistribution(double[] vet){
		int typedistrib = (int)vet[0];
		double parameter1 = vet[1];
		double parameter2 = vet[2];
		if(typedistrib != 1){
			return Rand.GeneratedValues(parameter1, parameter2);
		}
		else{
			return parameter1;
		}
	}
	
	//Metodo responsável por salvar o evento em um arquivo
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
		
		try{
			check = Rec.WriteFile(str, check); //Chama obj OutputFile para gravar o pacote, check verifica se deve ser apagado arquivo já criado 
			contWr++; // Variável utilizada para verificar critério de parada por numero de pacotes
			
		} catch (FileNotFoundException e) {
			System.out.printf("Error recording document");
			e.printStackTrace();
		}
		if(VerifyStopCritery(TimeArrival, contWr) == 0){ //Sempre verifica o criterio de parada 
			StopSystem = true;		
		}//finalizando o programa
		pac = null;
	}
	
	//método para verificar se critério de parada é atendido
	public static int VerifyStopCritery(double crit1, int crit2){
		switch(StopCritery){
			case 1: if(ArgStopCritery <= crit1) return 0;
				break;
			case 0: if(ArgStopCritery <= crit2) return 0;
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
