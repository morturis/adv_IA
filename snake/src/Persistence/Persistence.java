package Persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import input.Tuple;
import input.QLearning.QFunction;
import input.SARSA.SARSAQFunction;

public class Persistence {
	String s;
	private BufferedWriter writer;
	private BufferedReader reader;
	
	public Persistence(String s) throws IOException {
		this.s = s;
	}

	public void saveToFile(SARSAQFunction q) throws IOException {
		File file = new File(s);
		writer = new BufferedWriter(new FileWriter(file));
		Set<Entry<Tuple, Double>> set = q.getMap().entrySet();
		
		for(Entry<Tuple, Double> e: set) {
			if(e.getKey() == null) continue;
			writer.write(e.getKey().toString() + "e" + e.getValue().toString() + "\n");
		}
		writer.write("end");
		writer.flush();
		writer.close();		
	}
	
	public void loadFromFile(SARSAQFunction q) throws IOException {
		File file = new File(s);
		reader = new BufferedReader(new FileReader(file));
		HashMap<Tuple, Double> map = new HashMap<>();
		String[] entryDiv;
		String s = reader.readLine();
		do {
			entryDiv = s.split("e");
			Tuple key = new Tuple(entryDiv[0]);
			Double value = Double.parseDouble(entryDiv[1]);
			map.put(key, value);
			s = reader.readLine();
		}while(!s.equals("end"));
		q.setMap(map);
		reader.close();
	}

	public void saveToFile(QFunction q) throws IOException {
		File file = new File(s);
		writer = new BufferedWriter(new FileWriter(file));
		Set<Entry<Tuple, Double>> set = q.getMap().entrySet();
		
		for(Entry<Tuple, Double> e: set) {
			if(e.getKey() == null) continue;
			writer.write(e.getKey().toString() + "e" + e.getValue().toString() + "\n");
		}
		writer.write("end");
		writer.flush();
		writer.close();		
		
	}
	
	public void loadFromFile(QFunction q) throws IOException {
		File file = new File(s);
		reader = new BufferedReader(new FileReader(file));
		HashMap<Tuple, Double> map = new HashMap<>();
		String[] entryDiv;
		String s = reader.readLine();
		do {
			entryDiv = s.split("e");
			Tuple key = new Tuple(entryDiv[0]);
			Double value = Double.parseDouble(entryDiv[1]);
			map.put(key, value);
			s = reader.readLine();
		}while(!s.equals("end"));
		q.setMap(map);
		reader.close();
	}

}
