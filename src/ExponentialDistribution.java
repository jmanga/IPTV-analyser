import java.io.*;

public class ExponentialDistribution implements RNGenerator{
	int u;
	float f_x;			// value between 0 and 1 (probability function)
	float x;			// continuous random variable 
	//float Lambda;		// Lambda value is a unit of time
	float mean;			// 1/Lambda
	float R;			// random number

	
	public ExponentialDistribution() { 
	    this.u = u; 
	    this.f_x = 0;
	    this.x = 0;
	}
	
	public int getU(){
		return this.u;
	}
	
	public float getFx(){
		return this.f_x;
	}
	
	public float getX(){
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
		return 0;
	}

	@Override
	public double GeneratedValues(float meanSizePacket) {
		x = (float) (mean*Math.log(1-R));
		return x;
	}
}
