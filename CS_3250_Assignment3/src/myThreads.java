import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class myThreads implements Runnable{
	//Class variables
	private final ArrayList<String> myChunk;
	private HashMap<String, Integer> hm = new HashMap<String, Integer>();
	private String fileName;
	private int chunkNum;

	//Threads Constructor
	public myThreads(ArrayList<String> chunk, String name, int num){
		this.myChunk = chunk;
		fileName = name;
		chunkNum = num;
		this.run();
	}//end constructor

	public void run(){
		//Count how many times each word is present
		//First clean out all unnecessary characters and split line into individual words
		//Then write chunks out to files

		sanitizeAndSplit(myChunk);

//		for (int i = 0; i < myChunk.size(); i++) {
//			System.out.println(myChunk.get(i));
//		}
	}//end run method

	//Overridden run method
//	public Runnable run2(){
//		//Count how many times each word is present
//		//First clean out all unnecessary characters and split line into individual words
//		//Then write chunks out to files
//
//		sanitizeAndSplit(myChunk);
//
////		for (int i = 0; i < myChunk.size(); i++) {
////			System.out.println(myChunk.get(i));
////		}
//		return null;
//	}//end run method





	//Method to remove all non alphabetic characters
	//Will return an array of only words
	public void sanitizeAndSplit(ArrayList<String> myChunk){
		String myStringArray[] = {};

		//Make all letters lowercase
		//Split array list into individual words
		//Remove all non alphabetic characters
		//Put into hashMap
		try {

			for(String temp : myChunk){
				if(temp != null) {
					temp = temp.toLowerCase();
					myStringArray = temp.split("\\s+");
					for (String word : myStringArray) {
						word = word.replaceAll("\\W+", "");
						word = word.replaceAll("[1234567890_]", "");//need to remove numbers
						word = word.trim();
						countWords(word);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}//end sanitize method


	//Performs the counting for the key and values
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

		writeChunkFiles(hm);

//			for (HashMap.Entry<String, Integer> entry : hm.entrySet()) {
//				System.out.println(entry.getKey() + "\t" + entry.getValue() + "\n");
//			}

	}

	//Method to write chunk files
	public void writeChunkFiles(HashMap<String, Integer> hm){
		//Call method to create the output/ directory
		createDirectory();

//		for (HashMap.Entry<String, Integer> entry : hm.entrySet()) {
//			System.out.println(entry.getKey() + "\t" + entry.getValue() + "\n");
//		}


		//Chunk files should be named originalfilename_chunkNum.chunk all lowercase
		//Write hash map key and values to a chunk file
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

		//chunkNum = 0;

	}//end writeChunkFiles

	//Method to create the output/ directory
	public void createDirectory(){
		//Variables
		File dirName = new File("output");
		File[] files = dirName.listFiles();
		//check if output/ already exists. If exists delete it before creating new one
		if(dirName.isDirectory()){
			//Check if files are in folder that need to be deleted
//			if(files != null) {
//				//delete files in folder
//				for (File f : files) {
//					f.delete();
//				}
//			}
//			//Delete the directory before creating a new one
//			try {
//				dirName.delete();
//			}
//			catch(Exception e){
//				e.printStackTrace();
//				System.out.println("Cannot delete output directory, please try again!");
//				System.exit(0);
//			}
		}

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
	}//end createDirectory

}//end myThreads class