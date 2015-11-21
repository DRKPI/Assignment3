import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class WordCount {
	//Class variable
	private static String directoryName;
	private static Map<String, Word> hm = new HashMap<>();

	//inputValidation method
	//Purpose: Verify number of args is correct and if args[0] is a file or directory
	public static ArrayList<String> inputValidation(String[] myArgs){
		//Variables
		ArrayList<String> filesInFolder = new ArrayList<>();
		File file_DirectoryFromArgs = new File(myArgs[0]);
		File[] files = file_DirectoryFromArgs.listFiles();

		//Error check the input
		//Check if number of arguments are as expected and if values of arguments are as expected
		//if < or > 3 arguments print error
		if(myArgs.length != 3){
			System.out.println("Usage: java WordCount <file|directory> <chunk size 10-5000> <num of threads 1-100>");
			//System.out.println("Arguments are wrong!");
			System.exit(1);
		}
		//if chunkSize is too small or big print error
		else if(Integer.parseInt(myArgs[1]) < 10 || Integer.parseInt(myArgs[1]) > 5000){
			System.out.println("Usage: java WordCount <file|directory> <chunk size 10-5000> <num of threads 1-100>");
			//System.out.println("Chunksize is wrong!");
			System.exit(1);
		}
		//if number of threads is too small or big print error
		else if(Integer.parseInt(myArgs[2]) < 1 || Integer.parseInt(myArgs[2]) > 100) {
			System.out.println("Usage: java WordCount <file|directory> <chunk size 10-5000> <num of threads 1-100>");
			//System.out.println("Thread is wrong!");
			System.exit(1);
		}

		//Check if args[0] is a file
		if(file_DirectoryFromArgs.exists() && !file_DirectoryFromArgs.isDirectory()){
			directoryName = myArgs[0];
			//Save file name into arraylist.
			filesInFolder.add(directoryName);
		}
		//Check if args[0] is a directory
		else if(file_DirectoryFromArgs.exists() && file_DirectoryFromArgs.isDirectory()){
			directoryName = myArgs[0];
			//Copy file names into arraylist
			for (File f: files) {
				//Save file names into arraylist.
				filesInFolder.add(f.getName());
			}
		}
		//if not a file or directory print error message
		else{
			System.out.println("No such file/directory: " + file_DirectoryFromArgs);
		}

		return filesInFolder;
	}//end validation method


	//main method
	//Purpose is to start program and validate input and run threads method
	public static void main(String[] args) {
		//Variables
		int chunkSize = Integer.parseInt(args[1]);
		int numThreads = Integer.parseInt(args[2]);
		BufferedReader br = null;
		ReadFromFile rf = new ReadFromFile();
		ArrayList<String> filesInFolder = inputValidation(args);

		//Delete the output folder if it exists
		File dirName = new File("output");
		File[] files = dirName.listFiles();

		//check if output/ already exists. If exists check for files and delete them
		try {
			if (dirName.isDirectory()) {
				//Check if files are in folder that need to be deleted
				if (files != null) {
					//delete files in folder
					for (File f : files) {
						f.delete();
					}
				}
				//Delete the directory before before starting new run of program
				dirName.delete();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Cannot delete output directory, please try again!");
			System.exit(2);
		}

		//for loop to open and read each individual file
		for (String file : filesInFolder) {
			try {

				FileReader reader = new FileReader(directoryName + "\\" + file);
				br = new BufferedReader(reader);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			rf.readFromFile(chunkSize, br, numThreads, file);

		}

		//Call getResults method to start process of combining chunk files
		getResults();
		//Call writeResults method to write results file
		writeResults();

		//close any streams
		try {
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("br did not close!");
		}
	}//end main method


	//Method getResults
	//Purpose: Read chunk files back into an ArrayList
	private static void getResults() {
		//Variables
		File directoryOfChunks = new File("output");
		File[] files = directoryOfChunks.listFiles();
		ArrayList<String> filesInFolder = new ArrayList<>();
		ArrayList<String> chunkResults = new ArrayList<>();
		BufferedReader br = null;
		ReadFromFile rf = new ReadFromFile();

		//Get files from output folder
		if(directoryOfChunks.exists() && directoryOfChunks.isDirectory()){
			directoryName = "output";
			//Copy file names into arraylist
			for (File f: files) {
				//Save file names into arraylist.
				filesInFolder.add(f.getName());
			}
		}
		//if not a file or directory print error message
		else{
			System.out.println("No such file/directory: " + directoryOfChunks);
		}

		//for loop to open and read each individual file
		for (String file : filesInFolder) {
			try {

				FileReader reader = new FileReader(directoryName + "\\" + file);
				br = new BufferedReader(reader);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			chunkResults.addAll(rf.readFromChunk(br));
		}

		//Call sanitizeAndSplit
		sanitizeAndSplit(chunkResults);
	}//end getResults method


	//Method sanitizeAndSplit
	//Purpose: Split the key value pairs back up and save into String Array
	//			Read in all chunk file into one StringArray and combine counts for like keys
	//			Save into a hashMap
	public static void sanitizeAndSplit(ArrayList<String> myChunk){
		//Variables
		String myStringArray[] = {};

		try {
			//step through string array and put key value pairs into a string
			for(String temp : myChunk){
				//Split the key and values as put into hashMap
				if(temp != null) {
					myStringArray = temp.split("\\s+");
					Word word = hm.get(myStringArray[0]);
					if(word == null) {
						hm.put(myStringArray[0], new Word(myStringArray[0], Integer.parseInt(myStringArray[1])));
					}
					else{
						hm.get(myStringArray[0]).addToCount(Integer.parseInt(myStringArray[1]));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//end sanitize method


	//Method writeResults
	//Purpose: Sort hashmap into a treeMap and write out to results.txt file
	private static void writeResults() {
		//Sort hashMap before sending to file
		TreeSet<Word> mySet = new TreeSet<>(hm.values());

		//Create result.txt file and write treeMap out to it
		Writer writer = null;
		try {
			//System.out.println("Here");
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output\\results.txt")));
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
	}//end writeResults method
}//end WordCount class
