import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class myThreads implements Runnable{
	//Class variables
	private final ArrayList<String> myChunk;
	private HashMap<String, Integer> hm = new HashMap<String, Integer>();
	
	//Threads Constructor
	public myThreads(ArrayList<String> chunk){
		this.myChunk = chunk;
	}//end constructor
	
	//Overridden run method
	public void run(){
		//Count how many times each word is present
		//First clean out all unnecessary characters and split line into individual words
		//Then write chunks out to files

		writeChunkFiles(countWords(sanitizeAndSplit(myChunk)));
		
//		for (int i = 0; i < myChunk.size(); i++) {
//			System.out.println(myChunk.get(i));
//		}
	}//end run method
	
	
	
	//Performs the counting for the key and values
	public HashMap<String, Integer> countWords(String[] myChunk){
		//Each chunk file should be in descending order and keys lowercased
		//count the words and save in a hashmap
		try {
			for(String w:myChunk){
				Integer freq = hm.get(w); //use word=key to get value=freq
				if(freq == null){ //word doesn't exist
					hm.put(w, 1);
				}else{ //word exists, increment count
					hm.put(w, + freq+1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (HashMap.Entry<String, Integer> entry : hm.entrySet()) {
			System.out.println(entry.getKey() + entry.getValue() + "\n");
		}

		return hm;
	}
	
	//Method to remove all non alphabetic characters
	//Will return an array of only words
	public String[] sanitizeAndSplit(ArrayList<String> myChunk){
		String myStringArray[] = {};

		//Make all letters lowercase
		//Split array list into individual words
		//Remove all non alphabetic characters
		//Put into hashMap
		try {

			for(String temp : myChunk){
				temp = temp.toLowerCase();
				myStringArray = temp.split("\\s+");
				for (String word : myStringArray) {
					word = word.replaceAll("\\W+", "");
					word = word.replaceAll("[1234567890_]", "");//need to remove numbers
					word = word.trim();

					//Put sanitized words into hashMap and count
					Integer freq = hm.get(word); //use word=key to get value=freq
					if(freq == null){ //word doesn't exist
						hm.put(word, 1);
					}else{ //word exists, increment count
						hm.put(word, + freq+1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		for (HashMap.Entry<String, Integer> entry : hm.entrySet()) {
			System.out.println(entry.getKey() + entry.getValue() + "\n");
		}

//		//For testing purposes
//		for (int i = 0; i < myStringArray.length; ++i) {
//			System.out.println(myStringArray[i]);
//		}
		return null; //myStringArray;
	}//end sanitize method
	
	//Method to write chunk files
	public void writeChunkFiles(HashMap<String, Integer> hm){
		//Call method to create the output/ directory
		createDirectory();

		//Chunk files should be named originalfilename_chunkNum.chunk all lowercase
		//Write hash map key and values to a chunk file
//		Writer writer = null;
//		try {
//			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("filename.chunk", "UTF-8")));
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				writer.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}



	}//end writeChunkFiles

	//Method to create the output/ directory
	public void createDirectory(){
		//Variables
		File dirName = new File("output/");
		File[] files = dirName.listFiles();
		//check if output/ already exists. If exists delete it before creating new one
		if(dirName.isDirectory()){
			//Check if files are in folder that need to be deleted
			if(files != null) {
				//delete files in folder
				for (File f : files) {
					f.delete();
				}
			}
			//Delete the directory before creating a new one
			try {
				dirName.delete();
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Cannot delete output directory, please try again!");
				System.exit(0);
			}
		}

		//Create directory
		try {
			dirName.mkdir();
		}
		catch (Exception e){
			e.printStackTrace();
			System.out.println("Cannot create output directory, please try again!");
			System.exit(0);
		}
	}//end createDirectory

}//end myThreads class