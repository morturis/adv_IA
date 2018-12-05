package persistence;

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

public class Persistence{
	File output;
	private BufferedWriter writer;
	private BufferedReader reader;
	
	public Persistence (String name){
		output=new File(name);
		System.out.println(output.getAbsolutePath());
		
	}
	
	public synchronized void saveToFile(HashMap<Tuple, Double> map1, HashMap<Tuple, Double> map2) throws IOException{
		writer = new BufferedWriter(new FileWriter(output));
		Set<Entry<Tuple, Double>> set = map1.entrySet();
		writer.write("1{\n");
		
		System.out.println("saving p1");
		for(Entry<Tuple, Double> e : set){
			writer.write(e.getKey().toString() + "b" + e.getValue() + "\n");
			System.out.println("x");
			
		}
		writer.write("}\n");
		
		
		
		set = map2.entrySet();
		writer.write("2{\n");

		System.out.println("saving p2");
		for(Entry<Tuple, Double> e : set){
			writer.write(e.getKey().toString() + "b" + e.getValue() + "\n");
			System.out.println("x");
		}
		writer.write("}\n");
		writer.flush();
		writer.close();
	}
	/*
	public synchronized void loadFromFile(Player player1, Player player2) throws IOException {
		reader = new BufferedReader(new FileReader(output));
		
		HashMap<Tuple, Double> loadedMap1 = new HashMap<>();
		String[] entryDivision; 
		
		String s = reader.readLine();
		s = reader.readLine();
		while(!(s.equals("}"))) {
			entryDivision = s.split("b");
			Tuple key = new Tuple(entryDivision[0]);
			Double value = Double.parseDouble(entryDivision[1]);
			loadedMap1.put(key, value);
			s = reader.readLine();
		}

		player1.setMap(loadedMap1);

		HashMap<Tuple, Double> loadedMap2 = new HashMap<>();
		s = reader.readLine();
		s = reader.readLine();
		while(!(s.equals("}"))) {
			entryDivision = s.split("b");
			Tuple key = new Tuple(entryDivision[0]);
			Double value = Double.parseDouble(entryDivision[1]);
			loadedMap2.put(key, value);
			s = reader.readLine();
		}
		
		player2.setMap(loadedMap2);
		
		reader.close();
	}
	*/
}