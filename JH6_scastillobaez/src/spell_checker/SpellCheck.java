package spell_checker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeSet;


public class SpellCheck {

    // We could have also used TreeSet for the dictionary
    private HashSet<String> dictionary = new HashSet<String>();

    private TreeSet<String> miss_spelled_words = new TreeSet<String>();

    public SpellCheck() throws IOException
    {
        FileReader fr = new FileReader("dictionary.txt");
        BufferedReader br = new BufferedReader(fr);

        String line;
        while((line=br.readLine())!=null){
            dictionary.add(line);
        }

    }
    public void checkSpelling(String fileName) throws IOException
    {
        Scanner ss = new Scanner(System.in);
        System.out.println("======== Spell checking " + fileName + " =========");

// Clear miss_spelled_words
        miss_spelled_words.clear();

// Read in each line from "fileName"
        int lineNumber = 1;
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);

        String line;
        while((line = br.readLine())!= null){
            // For each line, break the line into words using the following StringTokenizer
            StringTokenizer st = new StringTokenizer(line, " \t,.;:-%'\"");
            while(st.hasMoreTokens()){
                // lower case each word obtained from the StringTokenizer
                String str = st.nextToken().toLowerCase();
                // skip word if the first character is not a letter
                if(str.charAt(0)>='a' && str.charAt(0)<='z'){
                    // Skip over word if it can be found in either dictionary, or miss_spelled_words
                    if(!dictionary.contains(str) && !miss_spelled_words.contains(str) ){
                        // If word ends with 's', then try the singular version of the word in the dictionary and miss_spelled_words ... skip if found
                        if(str.endsWith("s")){
                            StringBuffer sb = new StringBuffer(str);
                            sb.deleteCharAt(str.length()-1);
                            if(dictionary.contains(sb.toString())){
                                if(miss_spelled_words.contains(sb.toString())){
                                    continue;
                                }else{
                                    miss_spelled_words.add(sb.toString());
                                }
                            }else{
                                if(!miss_spelled_words.contains(sb.toString())){
                                    // Ask the user if he wants the word added
                                    //to the dictionary or the miss-spelled words
                                    //and add word as specified by the user
                                    System.out.println(lineNumber + ": " + line);
                                    System.out.println(sb.toString() + " not in dictionary.  Add to dictionary? (y/n)");
                                    String w = ss.next();
                                    if(w.equalsIgnoreCase("y")){
                                        dictionary.add(str);
                                    }else{
                                        miss_spelled_words.add(str);
                                    }
                                }else{
                                    continue;
                                }
                            }
                        }else{
                            // Ask the user if he wants the word added
                            //to the dictionary or the miss-spelled words
                            //and add word as specified by the user
                            System.out.println(lineNumber + ": " + line);
                            System.out.println(str + " not in dictionary.  Add to dictionary? (y/n)");
                            String w = ss.next();
                            if(w.equalsIgnoreCase("y")){
                                dictionary.add(str);
                            }else{
                                miss_spelled_words.add(str);
                            }
                        }
                    }else if(dictionary.contains(str) && !miss_spelled_words.contains(str)){
                        continue;
                    }
                }else{
                    continue;
                }
            }
            lineNumber++;
        }
    }

    public void dump_miss_spelled_words()
    {
        System.out.println("****** Miss spelled words *******");
        for(String ms:miss_spelled_words){
            System.out.println(ms);
        }
    }


    public static void main(String[] args) throws IOException {

        try {
            SpellCheck spellCheck = new SpellCheck();

            for (int i=0; i < args.length; i++)
            {
                spellCheck.checkSpelling(args[i]);
                spellCheck.dump_miss_spelled_words();
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e);
        }


    }

}