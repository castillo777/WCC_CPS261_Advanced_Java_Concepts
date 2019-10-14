package file_operations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileOperations {

    StringTokenizer parseCommand;


    public void delete(File f)
    {
        if(!fileExists(f)) return;

        System.out.println("Deleting " + f);
        if (f.delete())
            System.out.println("Successful delete.");
        else
            System.out.println("Failed to delete " + f);
    }

    public void rename(File fOld, File fNew)
    {
        // code for handling the rename command
        // Make sure you check the return code from the
        // File rename method to print out success/failure status
        if(!fileExists(fOld)) return;

        System.out.println("Renaming " + fOld + " to " + fNew);
        if(fOld.renameTo(fNew))
            System.out.println("Successful rename.");
        else
            System.out.println("Failed to rename " + fOld);

    }

    public void list(File f)
    {
        // code for handling the list command
        if(!fileExists(f)) return;

        String[] files = f.list();
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i]);
        }
    }

    public void size(File f)
    {
        // code for handling the size command
        if(!fileExists(f)) return;

        System.out.println("Size for " + f + " is " + f.length());
    }

    public void lastModified(File f)
    {
        // code for handling the lastModified command
        if(!fileExists(f)) return;

        long time = f.lastModified();
        Date d = new Date(time);
        System.out.println("Last modified for " + f);
        System.out.println("Date: " + d);
    }

    public void mkdir(File f)
    {
        // code for handling the mkdir command
        // Make sure you check the return code from the
        // File mkdir method to print out success/failure status

        if (f.mkdir())
            System.out.println("mkdir successful: " + f);
        else
            System.out.println("mkdir failed: " + f);
    }

    public void createFile(File f)
    {
        // code for handling the createFile command

        PrintStream ps = null;

        try {
            if(f.createNewFile()){
                System.out.println("Created file " + f);
            }
            else{
                System.out.println("Could not create new file.");
            }
            ps = new PrintStream(new FileOutputStream(f));

            while(parseCommand.hasMoreTokens()){
                ps.println(parseCommand.nextToken());
            }
        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally{
            if(ps != null) ps.close();
        }
    }

    public void printFile(File f)
    {
        // code for handling the printFile command

        Scanner scan = null;

        if(!fileExists(f)) return;

        try {
            scan = new Scanner(new FileInputStream(f));

            while(scan.hasNextLine())
                System.out.println(scan.nextLine());
        }
        catch(FileNotFoundException e) {
            System.out.println("Command file does not exist:" + f);
        }
        finally {
            if(scan != null) scan.close();
        }
    }

    void printUsage()
    {
        // process the "?" command
        System.out.println();
        System.out.println("?");
        System.out.println("quit");
        System.out.println("delete filename");
        System.out.println("rename oldFilename newFilename");
        System.out.println("size filename");
        System.out.println("lastModified filename");
        System.out.println("list dir");
        System.out.println("printFile filename");
        System.out.println("createFile filename");
        System.out.println("mkdir dir");
        System.out.println("quit");
    }


    // useful private routine for getting next string on the command line
    private String getNextToken()
    {
        if (parseCommand.hasMoreTokens())
            return parseCommand.nextToken();
        else
            return null;
    }

    // useful private routine for getting a File class from the next string on the command line
    private File getFile()
    {
        File f = null;
        String fileName = getNextToken();
        if (fileName == null)
            System.out.println("Missing a File name");
        else
            f = new File(fileName);

        return f;
    }

    private boolean fileExists(File f){
        if(!f.exists()){
            System.out.println("File does not exist: " + f);
            return false;
        }
        return true;
    }

    public boolean processCommandLine(String line)
    {


        if (line != null) {
            parseCommand = new StringTokenizer(line);

            String command = getNextToken();

            if(command.equals("?")){
                System.out.println("Processing: " + command);
                printUsage();
                System.out.println("*****************************");
                return true;
            }
            else if(command.equals("quit")){
                System.out.println("Processing: " + command);
                return false;
            }

            File f = getFile();

            System.out.println("====>");
            System.out.println("Processing: " + command + " " + f);

            switch (command) {
                case "delete":
                    delete(f);
                    break;
                case "rename":
                    File fNew = getFile();
                    rename(f, fNew);
                    break;
                case "list":
                    list(f);
                    break;
                case "size":
                    size(f);
                    break;
                case "lastModified":
                    lastModified(f);
                    break;
                case "mkdir":
                    mkdir(f);
                    break;
                case "createFile":
                    createFile(f);
                    break;
                case "printFile":
                    printFile(f);
                    break;
            }
        }
        else {
            System.out.println("Bad input command.");
            return false;
        }

        System.out.println("*****************************");
        return true;
    }

    void processCommandFile(String commandFile)
    {

        Scanner inputStream = null;

        try {
            inputStream = new Scanner(new FileInputStream(commandFile));

            while(inputStream.hasNextLine() && processCommandLine(inputStream.nextLine()));

        }
        catch(FileNotFoundException e){
            System.out.println(e);
        }
        finally {
            if (inputStream != null)
                inputStream.close();
        }
    }

    public static void main(String[] args)
    {
        FileOperations fo= new FileOperations();
        for (int i=0; i < args.length; i++)
        {
            System.out.println("\n\n============ Processing " + args[i] +" =======================\n");
            fo.processCommandFile(args[i]);
        }

        System.out.println("Done with FileOperations");
    }
}