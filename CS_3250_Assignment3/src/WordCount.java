import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class WordCount {
	//inputValidation method
	//Purpose: Verify number of args is correct and if args[0] is a file or directory
	public static ArrayList<String> inputValidation(String[] myArgs){
		//Variables
		ArrayList<String> filesInFolder = new ArrayList<String>();
		File file_DirectoryFromArgs = new File(myArgs[0]);

		//Error check the input
		//Check if number of arguments are as expected and if values of arguments are as expected
		//if < or > 3 arguments print error
		if(myArgs.length != 3){
			System.out.println("Usage: java WordCount <file|directory> <chunk size 10-5000> <num of threads 1-100>");
			System.out.println("Arguments are wrong!");
			System.exit(1);
		}
		//if chunkSize is too small or big print error
		else if(Integer.parseInt(myArgs[1]) < 10 || Integer.parseInt(myArgs[1]) > 5000){
			System.out.println("Usage: java WordCount <file|directory> <chunk size 10-5000> <num of threads 1-100>");
			System.out.println("Chunksize is wrong!");
			System.exit(1);
		}
		//if number of threads is too small or big print error
		else if(Integer.parseInt(myArgs[2]) < 1 || Integer.parseInt(myArgs[2]) > 100) {
			System.out.println("Usage: java WordCount <file|directory> <chunk size 10-5000> <num of threads 1-100>");
			System.out.println("Thread is wrong!");
			System.exit(1);
		}



		//Check if args[0] is a file
		if(file_DirectoryFromArgs.exists() && !file_DirectoryFromArgs.isDirectory()){
			String fileName = myArgs[0];
			//Save file name into arraylist. Using args[0] so that the name is a string
			filesInFolder.add(fileName);

//			// Catch and print error if file does not open or file_DirectoryFromArgs does not exist
//			try {
//
//				//Open file stream
//				br = new BufferedReader(new FileReader(file_DirectoryFromArgs));
//			} catch (FileNotFoundException e1) {
//				System.out.println("No such file/directory: " + file_DirectoryFromArgs);
//			}//end 1st catch block
		}
		//Check if args[0] is a directory
		else if(file_DirectoryFromArgs.isDirectory()){

				//TODO Write code that puts files from dir into filesInFolder arraylist


//			try {
//
////				filesInFolder = Files.walk( Paths.get( args[0] ) )
////						.filter( Files::isRegularFile )
////						.map( Path::toFile )
////						.collect( Collectors.toList() );
//
////				Files.walk(Paths.get(args[0])).forEach(filePath -> {
////					if(Files.isRegularFile(filePath)){
//////						String namesOfFiles = args[0];
////						fileList.add( filePath );
////					}
////				});
//			} catch (IOException e) {
//				//
//				System.out.println("No such file/directory: " + file_DirectoryFromArgs);
//				e.printStackTrace();
//			}
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
		//Variables for arg array
		String[] myArgs = args;
		ArrayList<String> filesInFolder = new ArrayList<String>();
		//Call validation method to verify all input
		filesInFolder = inputValidation(myArgs);


		//Class variables
		int chunkSize = Integer.parseInt(args[1]);
		int numThreads = Integer.parseInt(args[2]);
		BufferedReader br = null;
		ReadFromFile rf = new ReadFromFile();
		List<List<String>> chunkArray = new ArrayList<List<String>>();
		
		//Create Thread pool object to create specified number of threads
		ExecutorService service = Executors.newFixedThreadPool(numThreads);

		//for
		for(int i = 0; i < filesInFolder.size(); ++i) {
			//Open file stream
			try {
				br = new BufferedReader(new FileReader(filesInFolder.get(i)));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			int j = 0;
			for(String file : filesInFolder) {

				//Get file chunk from file
				chunkArray.add(rf.readFromFile(chunkSize, br));
				try {
					//Start thread pool
					ArrayList<String> myPass = (ArrayList<String>) chunkArray.get(j);
					service.execute(new myThreads(myPass));
					++j;
				} catch (Exception e) {
					System.out.println("Thread was not created.");
					e.printStackTrace();
				}
			}

		}

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
		}
		

	}//end main method

}//end WordCount class
