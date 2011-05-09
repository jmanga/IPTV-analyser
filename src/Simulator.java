import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.ArrayList;
import java.text.DecimalFormat;
import java.io.*;

public class Simulator extends Thread{

	static double PacketsArrival;
	static double SessionsArrival;
	static double SessionSize;
	static double TimeSystem = 0;

	private int id = 1;
	Semaphore semap = new Semaphore(30, true);
	
	RNGenerator Rand = new ExponentialDistribution();
	static List<Session> list = new ArrayList<Session>();
	
	static boolean check = false, StopSystem = false;
	static int clock = 0, contWr = 0, times = 0;
	static OutputFile Rec = new OutputFile("IPTVdata.txt");
	static DecimalFormat df = new DecimalFormat("#.############");
	static int StopCritery, ArgStopCritery;
		
	public synchronized void run(){
		
		//Inicialmente os valores serão os relacionados abaixo
		final double Error = 5;
		final double CorrelationIP = 0.5;
		final double CorrelationPB = 0.5;
		final double StandardDeviationP = 5;
		final double StandardDeviationI = 5;
		final double StandardDeviationB = 5;
		final int TypeErrorDist = 1;
		final int PacketSize = 1500;
		final String GOP = new String("6,3");
		SessionsArrival = 5;
		SessionSize = 20;
		PacketsArrival = 1;
		StopCritery = 2;
		ArgStopCritery = 750;
				
		//new Graphics();
		//thread com loop para criação das sessões
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!StopSystem){
					try {
						semap.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Session Initiate = new Session(id, SessionsArrival, SessionSize, PacketsArrival, Error, TypeErrorDist, CorrelationIP, 
							CorrelationPB, StandardDeviationP, StandardDeviationI, StandardDeviationB, PacketSize, semap, GOP);
					list.add(Initiate);
					Initiate.start();
					SessionsArrival = SessionsArrival + Rand.GeneratedValues(10, 0);
					//SessionSize = Rand.GeneratedValues(Graphics.SizeSessions); 
					id++;
					if(VerifyStopCritery(SessionsArrival, contWr) == 0){ //Sempre verifica o criterio de parada 
						StopSystem = true;								 //interrompendo a criação de threads
					}
					
				}
			}
		}).start();
		
		Session auxSes;
		double lessSize;
		int IDlessSize;
		
		try {
			Thread.sleep(1000); //Dorme por 10ms para assim criar uma nova thread de sessão
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Loop responsável por gerar o evento inserindo-o em um arquivo
		while(!StopSystem){
			if(list.size() > 0){ //Verifica se há alguma sessão inserida na lista de sessões
				auxSes = list.get(0); //Pega a primeira sessão
				if(auxSes.getFlag()){ // Verifica se um objeto frame é válido para essa sessão
					lessSize = auxSes.getFrame().GetEventFirstPacket(); //Pega o evento da primeira sessão
					if(lessSize == 0) lessSize = 100000; //Caso não haja Pacotes na lista de pacotes da sessão...
					IDlessSize = 0;						 //a variavel recebe um valor gigante para não ser descartada
					for(int cont = 1; cont < getList().size(); cont++){  //loop para verificar o menor evento
						auxSes = getList().get(cont);
						if( auxSes.getFlag()){
							if(auxSes.getFrame().PacketList.size() > 0){
								if(lessSize > auxSes.getFrame().GetEventFirstPacket() && lessSize >= TimeSystem){
									lessSize = auxSes.getFrame().GetEventFirstPacket();
									IDlessSize = cont;
								}
							}
						}
					}
				if(list.get(IDlessSize).getFrame().PacketList.size() > 0){
					recordData(list.get(IDlessSize).getFrame().PacketList.get(0)); //Com o menor evento em mãos...
					getList().get(IDlessSize).getFrame().PacketList.remove(0);     //Ele é gravado em arquivo e removido
				}
				}
			}
		}
		
		System.exit(1);
		
	}
	
	public static void main(String[] args) throws InterruptedException {

		Simulator MainThread =  new Simulator();
		new Thread(MainThread).start();
		//System.out.wait(10000);
	}

	public static void recordData(Packets pac){
		int SesID, PacID;
		double TimeArrival, SizePac;
		char TypeF;
		//Sem semaphore = new Sem(s);

		SesID = pac.getSesID(); //Desempacota os dados
		PacID = pac.getPacID();
		TimeArrival = TimeSystem = pac.getPacArriv();
		SizePac = pac.getPacSize();
		TypeF = pac.getFType();

		String str = Integer.toString(SesID) + "\t" + Integer.toString(PacID) + "\t" + TypeF +
				"\t" + df.format(TimeArrival) + "\t\t" + df.format(SizePac);
		
		
		try{
			check = Rec.WriteFile(str, check); //Chama obj OutputFile para gravar o pacote, check verifica se deve ser apagado arquivo já criado 
			contWr++; // Variável utilizada para verificar critério de parada por numero de pacotes
			
		} catch (FileNotFoundException e) {
			System.out.printf("Error semaphore");
			e.printStackTrace();
		}

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
