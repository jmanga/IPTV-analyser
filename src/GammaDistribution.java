import java.io.*;
import cern.jet.random.*;

public class GammaDistribution implements RNGenerator{
	double alpha;
	double lambda;
	Gamma gamma;
	
	public GammaDistribution(double a, double l) { 
		this.alpha = a;
		this.lambda = l;
	    this.gamma = new Gamma(this.alpha, this.lambda, null);
	}

	public GammaDistribution() { 
	    this.gamma = new Gamma(1, 1, null);
	}
	
	@Override
	public double GeneratedValues(double a, double l) {
		return gamma.nextDouble(a,l);
	}

	@Override
	public int StartSeed() {
		double test;
		test = this.GeneratedValues(this.alpha, this.lambda);
		System.out.printf("%d\n", test);
		return 0;
	}
}
