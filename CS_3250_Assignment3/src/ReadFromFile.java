import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFromFile {
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
                   //save line to array list
                    fileLines.add(sCurrentLine);

            } //end try block
            catch (IOException e) {
                e.printStackTrace();
            } //end catch block
            --size;
        }// end while loop

        return fileLines;
    }//end readFromFile method
}//end ReadFromFile class
