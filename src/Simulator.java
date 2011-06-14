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
	static public int nt = 0;
	
	private int id = 1;
	static Semaphore semap = new Semaphore(5, true); // 15 sessões simultaneas
	Semaphore sem_get_event = new Semaphore(-4, true);
	
	RNGenerator Rand = new ExponentialDistribution();
	static List<Session> list = new ArrayList<Session>(); //lista de sessões
	
	static boolean check = false, StopSystem = false;
	static int clock = 0, contWr = 0, times = 0;
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
		final int PacketSize = 1500;
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
		ArgStopCritery = 25;
						
				
		//new Graphics();
		//thread com loop para criação das sessões
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while(!StopSystem){
					try {
						semap.acquire();
						nt++;
						//System.out.printf("antes qt %d\n",list.size());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//System.out.printf("num thread = %d e semaforo = %d\n",Simulator.nt, semap.availablePermits());			
					Session Initiate = new Session(id, SessionArrival, SessionSize, PacketsArrival, ErrorP, ErrorB, CorrelationIP, 
							CorrelationPB, StandardDeviationP, StandardDeviationI, StandardDeviationB, PacketSize, semap, GOP, sem_get_event);
					list.add(Initiate);
					Initiate.start();
					SessionArrival = SessionArrival + Rand.GeneratedValues(MeanSessionArrival[1], MeanSessionArrival[2]);
					//SessionSize = Rand.GeneratedValues(Graphics.SizeSessions); 
					id++;
					if(VerifyStopCritery(SessionArrival, contWr) == 0){ //Sempre verifica o criterio de parada 
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
			//if(list.size() > 0){ //Verifica se há alguma sessão inserida na lista de sessões
			    
				try {
					sem_get_event.acquire();
					System.out.printf("qt %d\n",list.size());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*for(int cont = 0; cont < getList().size(); cont++){
					System.out.printf("q = %d\n",list.size());
			    	System.out.printf("qtde = %d\n", list.get(cont).frame.PacketList.size());
					if(list.get(cont).frame.PacketList.isEmpty()){
						System.out.printf("fim\n");
			    		Session ses = list.remove(cont);
			    		ses = null;
			    	}
			    }*/
				
				auxSes = list.get(0); //Pega a primeira sessão
				lessSize = auxSes.getFrame().GetEventFirstPacket(); //Pega o evento da primeira sessão
				//if(lessSize == 0) lessSize = 100000; //Caso não haja Pacotes na lista de pacotes da sessão...
				IDlessSize = 0;						 //a variavel recebe um valor gigante para não ser descartada
				for(int cont = 1; cont < getList().size(); cont++){  //loop para verificar o menor evento
					auxSes = getList().get(cont);
					//if(auxSes.getFrame().PacketList.size() > 0){
					if(lessSize > auxSes.getFrame().GetEventFirstPacket() && lessSize >= TimeSystem){
						lessSize = auxSes.getFrame().GetEventFirstPacket();
						IDlessSize = cont;
					}
					//}
				}
				if(list.get(IDlessSize).getFrame().PacketList.size() > 0){
					System.out.printf("%d\n", IDlessSize);
					recordData(list.get(IDlessSize).getFrame().PacketList.get(0)); //Com o menor evento em mãos...
					list.get(IDlessSize).getFrame().remove_packet();     //Ele é gravado em arquivo e removido
				
				}
				sem_get_event.release();
			//}
		}
		
		System.exit(1);
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		/*double test_value = 0;
		GammaDistribution test_gamma = new GammaDistribution();
		hep.aida.bin.DynamicBin1D bin = new hep.aida.bin.DynamicBin1D();
		cern.colt.list.DoubleArrayList numbers = new cern.colt.list.DoubleArrayList(1000);
		
		
		for(int x = 0; x < 1000; x++){
			test_value = test_gamma.GeneratedValues(12.99, 2.5);
			System.out.printf("%f\n", test_value);
			numbers.add(test_value);
		}
		bin.addAllOf(numbers);
		System.out.println(bin);
		System.exit(0);*/
		
		Simulator MainThread =  new Simulator();
		new Thread(MainThread).start();
		//System.out.wait(10000);
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
		if(VerifyStopCritery(SessionArrival, contWr) == 0){ //Sempre verifica o criterio de parada 
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
