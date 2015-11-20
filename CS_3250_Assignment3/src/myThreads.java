import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class myThreads implements Runnable, Comparator{
	//Class variables
	private final ArrayList<String> myChunk;
	private Map<String, Integer> hm = new HashMap<String, Integer>();
	private Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
	private String fileName;
	private int chunkNum;

	//Threads Constructor
	public myThreads(ArrayList<String> chunk, String name, int num){
		fileName = name;
		chunkNum = num;
		this.myChunk = chunk;
		this.run();
	}//end constructor

	public void run(){
		//Call sanitize method to start the following process:
		//First clean out all unnecessary characters and split line into individual words
		//Count how many times each word is present in file while saving word and count to a hashMap
		//Then sort map in descending order and write chunks out to files

		sanitizeAndSplit(myChunk);
	}//end run method


	//Method to remove all non alphabetic characters
	//Will return an array of only words
	public void sanitizeAndSplit(ArrayList<String> myChunk){
		//Variables
		String myStringArray[] = {};

		//Make all letters lowercase
		//Split array list into individual words
		//Remove all non alphabetic characters
		//Call wordCount to put in hashMap
		try {

			for(String temp : myChunk){
				if(temp != null) {
					temp = temp.toLowerCase();
					myStringArray = temp.split("\\s+");
					for (String word : myStringArray) {
						word = word.replaceAll("\\W+", "");
						//word = word.replaceAll("[1234567890_]", "");
						word = word.trim();
						countWords(word);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end sanitize method


	//Performs the counting for the key and values and places key and value into a hashMap
	public void countWords(String word){
		//Each chunk file should be in descending order and keys lowercased
		//count the words and save in a hashmap
		try {

			Integer freq = hm.get(word); //use word=key to get value=freq
			if(freq == null){ //word doesn't exist
				hm.put(word, 1);
			}else{ //word exists, increment count
				hm.put(word, freq+1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

//		ReadFromFile rf = new ReadFromFile();
//		rf.writeChunkFiles(hm, fileName);
		writeChunkFiles(hm);
	}//end countWords method


	//Method to write chunk files
	public void writeChunkFiles(Map<String, Integer> hm){
		//Call method to create the output/ directory
		createDirectory();

		//Sort hashMap before sending to file
		//TODO sort hashMap in descending order


		//Chunk files should be named originalfilename_chunkNum.chunk all lowercase
		//Write hash map key and values to a chunk file and save in created directory
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output\\" + fileName + "_" + chunkNum + ".chunk")));
			for (HashMap.Entry<String, Integer> entry : hm.entrySet()) {
				writer.write(entry.getKey() + "\t" + entry.getValue() + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}//end writeChunkFiles method


	//Method to create the output/ directory
	public void createDirectory(){
		//Variables
		File dirName = new File("output");

		//Create directory
		try {
			if (!dirName.exists())
			dirName.mkdir();
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("Cannot create output directory, please try again!");
			System.exit(2);
		}
	}//end createDirectory method

	@Override
	public int compare(Object a, Object b) {

		return 0;
	}
}//end myThreads class