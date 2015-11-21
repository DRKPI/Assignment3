/**
 * Created by Koch on 11/20/2015.
 */

public class Word implements Comparable {
    String value;
    int count;
    //Constructor for thread hashMap
    Word(String word) {
        this.value = word;
        this.count = 1;
    }
    //Constructor for results hashMap
    Word(String word, int count){
        this.value = word;
        this.count = count;
    }

    @Override
    //compareTo method
    //Purpose: compare key values and put in descending and alphabetical order
    public int compareTo(Object o) {
        //-1 before / 0 new word / 1 after
        if(o instanceof Word) {
            Word secondWord = (Word)o;
            if (this.count > secondWord.count){
                return -1;
            }
            else if(this.count == secondWord.count){
                return this.value.compareTo(secondWord.value);
            }
            else{
                return 1;
            }
        }
        return 0;
    }

    public void increment() {
        this.count++;
    }

    public void addToCount(int count){this.count += count;}

    public String getValue() {return value;}

    public int getCount() {return count;}
}//end Word class
