import java.io.*;
import cern.jet.random.*;

public class GammaDistribution implements RNGenerator{
	double alpha;
	double lambda;
	Gamma gamma;
	cern.jet.random.engine.RandomEngine engine;
	
	public GammaDistribution(double a, double l) { 
		this.alpha = a;
		this.lambda = l;
	    this.gamma = new Gamma(this.alpha, this.lambda, null);
	}

	public GammaDistribution() { 
		this.engine = new cern.jet.random.engine.MersenneTwister((int)System.currentTimeMillis());
	    this.gamma = new Gamma(1, 1, engine);
	}
	
	@Override
	public double GeneratedValues(double a, double l) {
		//this.gamma.setState(a, l);
		this.engine = this.gamma.makeDefaultGenerator();
		double s = this.gamma.nextDouble(a,l);
		return s;
	}

	@Override
	public int StartSeed() {
		double test;
		test = this.GeneratedValues(this.alpha, this.lambda);
		System.out.printf("%d\n", test);
		return 0;
	}
}
