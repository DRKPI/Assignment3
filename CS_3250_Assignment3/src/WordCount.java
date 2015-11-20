import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WordCount {
	//Class variable
	private static String directoryName;

	//inputValidation method
	//Purpose: Verify number of args is correct and if args[0] is a file or directory
	public static ArrayList<String> inputValidation(String[] myArgs){
//		//Variables
		ArrayList<String> filesInFolder = new ArrayList<String>();
		File file_DirectoryFromArgs = new File(myArgs[0]);
		File[] files = file_DirectoryFromArgs.listFiles();

		//Error check the input
		//Check if number of arguments are as expected and if values of arguments are as expected
		//if < or > 3 arguments print error
//		if(myArgs.length != 3){
//			System.out.println("Usage: java WordCount <file|directory> <chunk size 10-5000> <num of threads 1-100>");
//			System.out.println("Arguments are wrong!");
//			System.exit(1);
//		}
//		//if chunkSize is too small or big print error
//		else if(Integer.parseInt(myArgs[1]) < 10 || Integer.parseInt(myArgs[1]) > 5000){
//			System.out.println("Usage: java WordCount <file|directory> <chunk size 10-5000> <num of threads 1-100>");
//			System.out.println("Chunksize is wrong!");
//			System.exit(1);
//		}
//		//if number of threads is too small or big print error
//		else if(Integer.parseInt(myArgs[2]) < 1 || Integer.parseInt(myArgs[2]) > 100) {
//			System.out.println("Usage: java WordCount <file|directory> <chunk size 10-5000> <num of threads 1-100>");
//			System.out.println("Thread is wrong!");
//			System.exit(1);
//		}

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
		String[] myArgs = args;
		int chunkSize = Integer.parseInt(args[1]);
		int numThreads = Integer.parseInt(args[2]);
		BufferedReader br = null;
		ReadFromFile rf = new ReadFromFile();
		ArrayList<String> arrayListOfLines = new ArrayList<String>();
		List<List<String>> chunkArray = new ArrayList<List<String>>();
		ArrayList<String> filesInFolder = new ArrayList<String>();

		//Call validation method to verify all input
		filesInFolder = inputValidation(myArgs);

		//Create Thread pool object to create specified number of threads
		ExecutorService service = Executors.newFixedThreadPool(numThreads);

		//Delete the output folder if it exists
		File dirName = new File("output");
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
			//Delete the directory before before starting new run of program
			try {
				dirName.delete();
			}
			catch(Exception e){
				e.printStackTrace();
				System.out.println("Cannot delete output directory, please try again!");
				System.exit(2);
			}
		}

		//for loop to create a BufferedReader for each file being read
//		for (int i = 0; i < filesInFolder.size(); ++i) {
//			//Open file stream
//			try {
//				FileReader reader = new FileReader(filesInFolder.get(i));
//				br = new BufferedReader(reader);
//			} catch (FileNotFoundException e1) {
//				e1.printStackTrace();
//			}

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
		//}//end for loop to create threads


		//Shutdown the thread pool so no more items added to queue
		service.shutdown();
		//while loop locks access till threads complete their tasks
		while(!service.isTerminated()){

		}

		//close any streams
		try {
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("br did not close!");
		}
	}//end main method

}//end WordCount class
