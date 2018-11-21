package input.DQN;

public abstract class Neuron {
	protected double result;
	protected double gradient;
	
	double getResult(){
		return result;
	}
	
	double getGradient() {
		return gradient;
	}
	
	abstract void calcOutput();
	
	double outputDerivative() {
		return (1-result*result);
	}
	
	abstract void updateWeights();
}
