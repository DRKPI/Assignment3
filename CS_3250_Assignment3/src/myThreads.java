import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedReader;

public class myThreads implements Runnable, Comparator{
	//Class variables
	private ArrayList<String> myChunk;
	private Map<String, Integer> hm = new HashMap<String, Integer>();
	
	//Threads Constructor
	public myThreads(ArrayList<String> chunk){
	}//end constructor
	
	public myThreads() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int compare(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return 0;
	}//end compare method
	
	//Overridden run method
	public void run(){
		//Count how many times each word is present
		//First clean out all unnecessary characters and split line into individual words
		//Then write chunks out to files
		writeChunkFiles(countWords(myChunk));
		
		for (int i = 0; i < myChunk.size(); i++) {
			System.out.println(myChunk.get(i));
		}
	}//end run method
	
	
	
	//Performs the counting for the key and values
	public HashMap<String, Integer> countWords(ArrayList<String> myChunk){
		//count the words and save in a hashmap
		try {
			for(String w:myChunk){
				Integer freq = hm.get(w); //use word=key to get value=freq
				if(freq == null){ //word doesn't exist
					hm.put(w + "\t", 1);
				}else{ //word exists, increment count
					hm.put(w + "\t", ++freq);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for (Map.Entry<String, Integer> entry : hm.entrySet()) {
//			System.out.println(entry.getKey() + entry.getValue() + "\n");
//		}
		System.out.println(hm);
		return null;
	}
	
	//Method to remove all non alphabetic characters
	//Will return an array of only words
	public String[] sanitizeAndSplit(ArrayList<String> myChunk){
		String myStringArray[] = {};
				
		try {
			//Make all letters lowercase
			//Split array list into individual words
			//Remove all non alphabetic characters
			for(String temp : myChunk){
				myStringArray = temp.split("\\s+");
				for (String word : myStringArray) {
					word.toLowerCase();
					word.replaceAll("\\W", "");
					word.trim();
				}		
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return myStringArray;
	}//end sanitize method
	
	//Method to write chunk files
	public void writeChunkFiles(HashMap<String, Integer> hm){
		//Write hash map key and values to a chunk file
		
	}//end writeChunkFiles
	
	public ArrayList<String> readFromFile(int size, BufferedReader br){
		//variables
		ArrayList<String> fileLines = new ArrayList<String>();
		String sCurrentLine = " ";
			
		while (size != 0 && sCurrentLine != null) {
			//Check for errors while opening, reading, and closing file
			try {
						
				//Read from file stream
				sCurrentLine = br.readLine();
				if(sCurrentLine != null)
					//Sanitize line before saving
							
							
					//save line to array list
					fileLines.add(sCurrentLine);
							
			} //end try block
			catch (IOException e) {
				e.printStackTrace();
			} //end 2nd catch block
			--size;
		}// end while loop
		//System.out.println(sCurrentLine);
		return fileLines;
	}//end readFromFile

}//end myThreads class