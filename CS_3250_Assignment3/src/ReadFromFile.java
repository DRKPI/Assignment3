import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadFromFile {
    private int chunkNum = 0;
    public void readFromFile(int size, BufferedReader br, int numThreads, String fileName){
        //Variables
        ArrayList<String> fileLines = new ArrayList<String>();
        String sCurrentLine = "temp";
        myThreads callThread;
        int i = 0;

        //Create Thread pool object to create specified number of threads
        ExecutorService service = Executors.newFixedThreadPool(numThreads);

        //Check for errors while opening, reading, and closing file
        try {

            while (sCurrentLine != null) {
                sCurrentLine = br.readLine();

                //Read from file stream
                // save line to array list
                fileLines.add(sCurrentLine);
                i++;//incrementor to keep track of chunkSize to send to thread

                //when i equals size send fileLines to a thread to be processed
                if (i == size){
                    try {
                        callThread = new myThreads(fileLines, fileName, chunkNum);
                        service.execute(callThread);

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("Thread did not run!");
                    }

                    chunkNum++;//need to send which chunkNum is being processed
                    i = 0;
                    fileLines = new ArrayList<String>();
                }
            }// end while loop
            chunkNum = 0;//need to send which chunk num was just used to append to file name
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //Shutdown the thread pool so no more items added to queue
        service.shutdown();
        //while loop locks access till threads complete their tasks
        while(!service.isTerminated()){

        }
    }//end readFromFile method


    public ArrayList<String> readFromChunk(BufferedReader br){
        //Variables
        ArrayList<String> fileLines = new ArrayList<String>();
        String sCurrentLine = "temp";


        //Check for errors while opening, reading, and closing file
        try {

            while (sCurrentLine != null) {
                sCurrentLine = br.readLine();

                //Read from file stream
                // save line to array list
                fileLines.add(sCurrentLine);

            }// end while loop
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return fileLines;
    }//end readFromFile method
}//end ReadFromFile class
