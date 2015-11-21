import java.io.*;
import java.util.*;

public class myThreads implements Runnable{
	//Class variables
	private final ArrayList<String> myChunk;
	private Map<String, Word> hm = new HashMap<>();
	private String fileName;
	private int chunkNum;

	//Threads Constructor
	public myThreads(ArrayList<String> chunk, String name, int num){
		fileName = name;
		chunkNum = num;
		this.myChunk = chunk;
		//this.run();
	}//end constructor

	public void run(){
		//Call sanitize method to start the following process:
		//First clean out all unnecessary characters and split line into individual words
		//Count how many times each value is present in file while saving value and count to a hashMap
		//Then sort map in descending order and write chunks out to files

		sanitizeAndSplit(myChunk);
		writeChunkFiles(hm);
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
						//value = value.replaceAll("[1234567890_]", "");
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
	public void countWords(String str){
		//Each chunk file should be in descending order and keys lowercased
		//count the words and save in a hashmap
		try {

			Word word = hm.get(str); //use value=key to get value=freq
			if(word == null){ //value doesn't exist
				hm.put(str, new Word(str));
			}else{ //value exists, increment count
				hm.get(str).increment();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}//end countWords method


	//Method to write chunk files
	public void writeChunkFiles(Map<String, Word> hm){
		//Call method to create the output/ directory
		createDirectory();

		//Sort hashMap before sending to file
		//TODO sort hashMap in descending order
		TreeSet<Word> mySet = new TreeSet<>(hm.values());

		//Chunk files should be named originalfilename_chunkNum.chunk all lowercase
		//Write hash map key and values to a chunk file and save in created directory
		Writer writer = null;
		try {

			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output\\" + fileName + "_" + chunkNum + ".chunk")));
			for (Word word : mySet) {
				writer.write(word.getValue() + "\t" + word.getCount() + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
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

}//end myThreads class