import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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

                    for (int j = 0; j < fileLines.size(); j++) {
                        try {
                            callThread = new myThreads(fileLines, fileName, chunkNum);
                            service.execute(callThread);

                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println("Thread did not run!");
                        }
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
    }//end readFromFile method

    //Method to write chunk files
//    public void writeChunkFiles(HashMap<String, Integer> hm, String fileName){
//        //Call method to create the output/ directory
//        createDirectory();
//
//        //Sort hashMap before sending to file
//        //TODO sort hashMap in descending order
//
//
//        //Chunk files should be named originalfilename_chunkNum.chunk all lowercase
//        //Write hash map key and values to a chunk file and save in created directory
//        Writer writer = null;
//        try {
//            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output\\" + fileName + "_" + chunkNum + ".chunk")));
//            for (HashMap.Entry<String, Integer> entry : hm.entrySet()) {
//                writer.write(entry.getKey() + "\t" + entry.getValue() + "\n");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }//end writeChunkFiles method
//
//
//    //Method to create the output/ directory
//    public void createDirectory(){
//        //Variables
//        File dirName = new File("output");
//
//        //Create directory
//        try {
//            if (!dirName.exists())
//                dirName.mkdir();
//        }
//        catch (Exception e){
//            e.printStackTrace();
//            System.out.println("Cannot create output directory, please try again!");
//            System.exit(2);
//        }
//    }//end createDirectory method

}//end ReadFromFile class
