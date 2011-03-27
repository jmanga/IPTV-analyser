
class XThread extends Thread{

	XThread(){
		
	}
	
	XThread(String threadName){
		super(threadName);                       // Initialize thread.
		System.out.println(this);
	    start();
	}
	
	public void run(){
		//Display info about this particular thread
		System.out.println(Thread.currentThread().getName());
	}
}

