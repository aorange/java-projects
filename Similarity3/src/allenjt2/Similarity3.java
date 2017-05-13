package allenjt2;

import java.util.HashSet;
import java.util.*;
/**
 * Created by Allen Tang on 1/17/2017.
 * Program that takes in a collection of strings as arguments.
 * It then detects how many "3-similar" strings match each string and
 * prints the amount of matching strings as a number for each string in order.
 *
 * "3 similar" - strings with exactly 3 shared unique characters
 */

public class Similarity3{
    public static void main(String args[]){
        for(int i=0; i<args.length; i++){
            int count = 0;
            for(int k=0; k<args.length; k++){

                //prevent comparison with itself
                if(i==k)
                    continue;

                //helper call to identify 3-sim
                if(sim3(args[i],args[k]))
                    count++;
            }
            //prints the amount of strings that are 3-sim to the target
            System.out.print(count);
        }
    }


    //helper function that detects 3-similarity using hashsets.

    public static boolean sim3(String one, String two){

        //create hashsets so we can expand it with each new unique character discovery
        char[] Array1 = one.toCharArray();
        char[] Array2 = two.toCharArray();
        Set<Character>w1Chars = new HashSet<Character>();
        Set<Character>w2Chars = new HashSet<Character>();
        for(char c : Array1){
            w1Chars.add(c);
        }
        for(char c : Array2){
            w2Chars.add(c);
        }

        //retain only the ones that appear in both sets and return true if only 3
        w1Chars.retainAll(w2Chars);
        return w1Chars.size()==3;
    }
}
