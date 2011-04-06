import java.util.concurrent.Semaphore;
import java.io.*;


public class Sem {
	Semaphore s;
	
	
	public Sem(Semaphore sem) {
        s = sem;
	}

	public void Wait() throws InterruptedException{
		s.acquire();
	}
	
	public void Signal() throws InterruptedException{
		s.release();
	}
}
