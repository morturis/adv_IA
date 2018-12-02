package input.DQN;


import java.util.ArrayList;
import java.util.List;

import org.encog.engine.network.activation.ActivationClippedLinear;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationRamp;
import org.encog.engine.network.activation.ActivationReLU;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationSoftMax;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.mathutil.rbf.RBFEnum;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.ml.train.strategy.Greedy;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.Train;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.propagation.quick.QuickPropagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.networks.training.strategy.SmartLearningRate;
import org.encog.neural.rbf.RBFNetwork;
import org.encog.neural.rbf.training.SVDTraining;

import input.Action;
import input.State;
import input.Tuple;

public class Network {
	int inputSize;
	int hiddenSize;
	int outputSize;
	int numHidden;
	BasicNetwork n;
	//RBFNetwork n;
	MLDataSet d;
	
	public Network(int inputSize, int hiddenSize,  int numHidden, int outputSize) {
		this.inputSize = inputSize;
		this.hiddenSize = hiddenSize;
		this.outputSize = outputSize;
		this.numHidden = numHidden;
		d = new BasicMLDataSet();
		initN();
		
		
	}

	private void initN() {
		//n = new RBFNetwork(inputSize, hiddenSize, outputSize, RBFEnum.Gaussian);/
		
		n = new BasicNetwork();
		n.addLayer(new BasicLayer(new ActivationLinear(), false, inputSize));
		for(int i = 0; i<numHidden;i++) {
			n.addLayer(new BasicLayer(new ActivationSoftMax(), false, hiddenSize));
		}
		n.addLayer(new BasicLayer(new ActivationRamp(), false, outputSize));
		n.getStructure().finalizeStructure();
		
		
		
		n.reset();
		
	}

	public double function(Tuple tuple) {
		BasicMLData aux = new BasicMLData(tuple.toDoubleArray());
		//System.out.println("si esto no es 1 hay un error "+n.compute(aux).getData().length);
		return n.compute(aux).getData()[0];
	}
	

	public void learn(Tuple t, double[] reward) {	
		d.add(new BasicMLDataPair(new BasicMLData(t.toDoubleArray()), new BasicMLData(reward)));
		if(d.size() == 1000) {
			//MLTrain train = new QuickPropagation(n, d);
			MLTrain train = new ResilientPropagation(n, d);
			//MLTrain train = new Backpropagation(n, d, DQNPlayer.LEARNING_RATE, DQNPlayer.MOMENTUM);
			//SVDTraining train = new SVDTraining(n, d);
			//train.addStrategy(new Greedy());
			//MLTrain train = new Backpropagation(n, d);
			train.iteration();
			System.out.println(train.getError());
			while(train.getError()>5.0) {
				train.iteration();
				System.out.println("- "+train.getError());
			}
			
			d.close();
			d = new BasicMLDataSet();	//empty the dataset
			
		}
	}
	public void debug() {
		
	}

	public void saveToFile(String s) {
		
	}

	public void learn(ReplayMemory r) {
		for(Transition aux: r.sample(DQNPlayer.SAMPLE_SIZE)) {
			Tuple t = new Tuple(aux.initialState, aux.action);
			double[] reward = {aux.reward};
			d.add(new BasicMLDataPair(new BasicMLData(t.toDoubleArray()), new BasicMLData(reward)));
		}
		//MLTrain train = new ResilientPropagation(n, d);
		MLTrain train = new Backpropagation(n, d, DQNPlayer.LEARNING_RATE, DQNPlayer.MOMENTUM);
		train.iteration();
		System.out.println(train.getError());
		while(train.getError()>5.0) {
			train.iteration();
			System.out.println("- "+train.getError());
		}
		
		d.close();
		d = new BasicMLDataSet();	//empty the dataset
		
	}

}
