package input.DQN;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ReplayMemory {
	ArrayList<Transition> mem;
	int size;
	
	
	
	public ReplayMemory(int size) {
		super();
		this.size = size;
		mem = new ArrayList<>();
	}

	public void add(Transition t) {
		if(mem.size() == size) {
			mem.remove(0);
		}
		mem.add(t);
	}

	public Set<Transition> sample(int sampleSize){
		if(mem.size() < sampleSize) sampleSize = mem.size();
		Set<Transition> s = new HashSet<>();
		for(int i = 0; i<sampleSize;i++) {
			s.add(mem.get(ThreadLocalRandom.current().nextInt(0, mem.size())));
		}
		return s;
	}
}
