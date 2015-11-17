import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadFromFile {
    public ArrayList<String> readFromFile(int size, BufferedReader br){
        //Class Variables
        ArrayList<String> fileLines = new ArrayList<>();
        String sCurrentLine = "temp";
        int i = 0;

        //variables used while testing system, these will be moved to other classes
        String myStringArray[];
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        String[] myChunk = null;

        //Check for errors while opening, reading, and closing file
        try {

            while (sCurrentLine != null) {
                sCurrentLine = br.readLine();
                //Read from file stream
                // save line to array list
                fileLines.add(sCurrentLine);
                i++;

                //when i equals size send fileLines to a thread to be processed
                if (i == size){

                    for (int j = 0; j < fileLines.size(); j++) {
                       // System.out.println(fileLines.get(j));
                    }
                    i = 0;
                    //fileLines = null;
                }
            }// end while loop
        }
        catch (Exception e) {
            e.printStackTrace();
        }


//        try {
//            //Make all letters lowercase
//            //Split array list into individual words
//            //Remove all non alphabetic characters
//            for(String temp : fileLines){
//                temp = temp.toLowerCase();
//                myStringArray = temp.split("\\s+");
//                for (String word : myStringArray) {
//                    word = word.replaceAll("\\W+", "");
//                    word = word.replaceAll("[1234567890_]", "");//need to remove numbers
//                    word = word.trim();
//
//                    Integer freq = hm.get(word); //use word=key to get value=freq
//                    if(freq == null){ //word doesn't exist
//                        hm.put(word, 1);
//                    }else{ //word exists, increment count
//                        hm.put(word, + freq+1);
//                    }
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        for (HashMap.Entry<String, Integer> entry : hm.entrySet()) {
//            System.out.println(entry.getKey() + entry.getValue() + "\n");
//        }



        return null;// fileLines;
    }//end readFromFile method
}//end ReadFromFile class
