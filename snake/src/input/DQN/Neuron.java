package input.DQN;

public abstract class Neuron {
	public static final double LEARNING_RATE = 0.2;
	public static final double DISCOUNT_FACTOR = 0.8;
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

	protected abstract void calcGradient(double expectedValue);
}
