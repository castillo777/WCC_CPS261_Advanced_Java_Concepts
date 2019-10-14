package reading_with_exceptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Reading_With_Exceptions {

    void process(String inputFilename)
    {
        // Here is where your work goes ... Steps that you will need to do:
        // 1.) Create a Scanner from the inputFilename.  Catch exceptions from errors.
        //
        // 2.) Read the first String from the file and use it to create a PrintStream
        //          catching appropriate exceptions
        // 3.) Using hasNextInt and nextInt, carefully read the count integer.
        //          I recommend -1 for a count value if it is bad to indicate reading ALL
        // 4.) Use copyNumbers method described below to complete the job
        // 5.) Close Scanner and PrintStream objects
        // 6.) Call printToScreen to copy the output file to the screen
        Scanner scan = null;
        String fileName = "";
        try {
            FileInputStream fis = new FileInputStream(inputFilename);
            scan = new Scanner(fis);
            int numIntsToRead = 0;
            if (scan.hasNextLine())
            {
                String line = scan.nextLine();
                String[] parts = line.split("\\s+");
                fileName = parts[0];
                try{
                    numIntsToRead = Integer.parseInt(parts[1]);
                }
                catch (Exception e){
                    numIntsToRead = -1;
                }
            }
            if (numIntsToRead < 0)
                numIntsToRead = -1;
            PrintStream ps = new PrintStream(
                    new FileOutputStream(fileName));

            copyNumbers(scan, ps, numIntsToRead);
            System.out.println("File : "+ fileName + " is created");
            ps.close();
            printToScreen(fileName);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("can't open: " + inputFilename);
        }
        finally
        {
            if (scan != null)
                scan.close();

        }
    }

// The following routine is called to complete the job of copying integers to
// the output file:
// scan - a Scanner object to copy integers from
// ps - A PrintStream to write the integers to
// numIntsToRead - number of integers to read.  A value of -1 ==> read all integers

    void copyNumbers(Scanner scan, PrintStream ps, int numIntsToRead)
    {
        // hasNext() can be used to see if the scan object still has data
        // Note that hasNextInt() can be used to see if an integer is present
        // nextInt() will read an integer
        // next() can be used to skip over bad integers
        int numReadSoFar = 0;
        int numbersInLine = 0;
        while(scan.hasNext())
        {
            if (numIntsToRead > 0 && numReadSoFar >= numIntsToRead)
            {
                break;
            }
            if (scan.hasNextInt())
            {
                if (numbersInLine == 10)
                {
                    ps.print("\n");
                    numbersInLine = 0;
                }
                numbersInLine++;
                ps.print(scan.nextInt() + " ");
            }
            else {
                scan.next();
            }
        }
    }


    public static void main(String[] args) {
        Reading_With_Exceptions rwe = new Reading_With_Exceptions();
        for (int i=0; i < args.length; i++)
        {
            System.out.println("\n\n=========== Processing "+ args[i] + " ==========\n");
            rwe.process(args[i]);
        }

    }

    // For the last step, we Copy the contents of the file to the screen
    private void printToScreen(String filename)
    {
        Scanner scan = null;
        try {
            FileInputStream fis = new FileInputStream(filename);
            scan = new Scanner(fis);
            System.out.println("Data in output file is: \n");

            while (scan.hasNextLine())
                System.out.println(scan.nextLine());
        }
        catch (FileNotFoundException e)
        {
            System.out.println("printToScreen: can't open: " + filename);
        }
        finally
        {
            if (scan != null)
                scan.close();
        }
    } // end of printToScreen
} // end of class
