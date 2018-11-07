package persistence;

import java.io.BufferedReader;
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
	private FileWriter stream;
	private BufferedReader reader;
	
	public Persistence (String name){
		output=new File(name);
	}

	public synchronized void saveToFile(HashMap<Tuple, Integer> map1, HashMap<Tuple, Integer> map2) throws IOException{
		stream = new FileWriter(output);
		Set<Entry<Tuple, Integer>> set = map1.entrySet();
		stream.write("1{\n");
		
		for(Entry<Tuple, Integer> e : set){
			stream.write(e.getKey().toString() + "-" + e.getValue() + "\n");
			
		}
		stream.write("}\n");
		
		set = map2.entrySet();
		stream.write("2{\n");
		for(Entry<Tuple, Integer> e : set){
			stream.write(e.getKey().toString() + "-" + e.getValue() + "\n");
		}
		stream.write("}\n");
	}
	
	public synchronized void loadFromFile() throws IOException {
		reader = new BufferedReader(new FileReader(output));
		HashMap<Tuple, Integer> loadedMap = new HashMap<>();
		String[] entryDivision; 
		
		String s = reader.readLine();
		s = reader.readLine();
		while(s.equals("}")) {
			entryDivision = s.split("-");
			Tuple key = new Tuple(entryDivision[0]);
			Integer value = Integer.parseInt(entryDivision[1]);
			loadedMap.put(key, value);
			s = reader.readLine();
		}
		
		// TODO : pasarle al aiplayer 1 su mapa ya cargado
		loadedMap.clear();
		
		s = reader.readLine();
		s = reader.readLine();
		while(s.equals("}")) {
			entryDivision = s.split("-");
			Tuple key = new Tuple(entryDivision[0]);
			Integer value = Integer.parseInt(entryDivision[1]);
			loadedMap.put(key, value);
			s = reader.readLine();
		}
		
		// TODO : pasarle al aiplayer 2 su mapa ya cargado
	}
}