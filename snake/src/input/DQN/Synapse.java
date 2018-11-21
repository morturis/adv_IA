package input.DQN;

class Synapse{
	
	public Neuron start, end;
	public double weight;                
	public static double deltaWeight = 0.0;
	
	public Synapse(Neuron start, Neuron end, double weight) {
		super();
		this.start = start;
		this.end = end;
		this.weight = weight;
	}   
	
	
}