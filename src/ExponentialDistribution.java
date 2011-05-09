
import java.io.*;

public class ExponentialDistribution implements RNGenerator{
	int u;
	double f_x;			// value between 0 and 1 (probability function)
	double x;			// continuous random variable 
	//float Lambda;		// Lambda value is a unit of time
	float mean;			// 1/Lambda
	double R;			// random number

	
	public ExponentialDistribution() { 
	    this.u = u; 
	    this.f_x = 0;
	    this.x = 0;
	}
	
	public int getU(){
		return this.u;
	}
	
	public double getFx(){
		return this.f_x;
	}
	
	public double getX(){
		return this.x;
	}
	
	public void setU(int mi){
		this.u = mi;
	}
	
	public void setFx(float fx){
		this.f_x = fx;
	}
	
	public void setX(float xx){
		this.x = xx;
	}
	
	@Override
	public int StartSeed(){
		if(this.x >= 0 && this.x <= 1) return 1;
		else return 0;
	}

	//
	@Override
	public double GeneratedValues(double mean, double arg) {
		R = Math.random();
		x = (-mean*Math.log(1-R));
		//int valid = StartSeed();
		//if(valid == 1) System.out.printf("Valid Number for exponential distribution.\n");
		//else System.out.printf("Invalid Number for exponential distribution.\n");
		return x;
	}
}
