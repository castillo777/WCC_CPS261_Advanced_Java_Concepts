package file_server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class FileServer extends EchoServer
{
    StringTokenizer parseCommand;
    PrintStream outputPS = null;

    public void delete()
    {
        File f = getFile();

        if (f != null)
        {
            outputPS.println("deleting "+ f.getAbsolutePath());
            if (f.delete())
                outputPS.println(" Successful delete");
            else
                outputPS.println(" unSuccessful delete");
        }
    }

    public void rename()
    {
        File f = getFile();

        if (f != null)
        {
            outputPS.println("renaming "+ f.getAbsolutePath());
            File f2 = getFile();

            if (f.renameTo(f2))
                outputPS.println(" Successful rename");
            else
                outputPS.println(" unSuccessful rename");
        }
    }

    public void list()
    {
        File f = getFile();

        if (f != null)
        {
            outputPS.println("Listing files for "+ f.getAbsolutePath());

            if (f.exists())
            {
                String[] files = f.list();

                if (files != null)
                {
                    for (int i=0; i < files.length; i++)
                        outputPS.println(files[i]);
                }
            }
            else
                outputPS.println("list error - Non-existent:" + f.getAbsolutePath());
        }
    }

    public void size()
    {
        File f = getFile();

        if (f != null)
        {
            if (f.exists())
            {
                outputPS.print("size for " + f.getAbsolutePath());

                long len = f.length();

                outputPS.println(" is = " + len);
            }
            else
                outputPS.println("size error - Non-existent:" + f.getAbsolutePath());
        }
    }

    public void lastModified()
    {
        File f = getFile();

        if (f != null)
        {
            if (f.exists())
            {
                outputPS.println("lastModified for "+ f.getAbsolutePath());

                long date = f.lastModified();

                outputPS.println(" date="+new Date(date));
            }
            else
                outputPS.println("lastModified error Non-existent:" + f.getAbsolutePath());
        }
    }

    public void mkdir()
    {
        File f = getFile();

        if (f != null)
        {
            if (f.mkdir())
                outputPS.println("mkdir successful: " + f.getAbsolutePath());
            else
                outputPS.println("mkdir unsuccessful: " + f.getAbsolutePath());
        }
    }

    public void createFile()
    {
        File f = getFile();

        if (f!= null)
        {
            try {
                PrintWriter pw = new PrintWriter(f);

                String token = getNextToken();

                while (token != null)
                {
                    pw.println(token);

                    token = getNextToken();
                }

                pw.close();

                outputPS.println("created file for " + f.getAbsolutePath());

            } catch (FileNotFoundException e) {
                outputPS.println("createFile can't create: " + f.getAbsolutePath());
            }
        }
    }

    public void printFile()
    {
        File f = getFile();

        if (f != null)
        {
            try {
                Scanner scan = new Scanner(f);

                while (scan.hasNextLine())
                {
                    outputPS.println(scan.nextLine());
                }

                scan.close();

                outputPS.println("printed file for " + f.getAbsolutePath());

            } catch (FileNotFoundException e) {
                outputPS.println("printFile can't open: "+ f.getAbsolutePath());
            }
        }
    }

    void printUsage()
    {
        outputPS.println("?");

        outputPS.println("quit");

        outputPS.println("delete filename");

        outputPS.println("rename oldFilename newFilename");

        outputPS.println("size filename");

        outputPS.println("lastModified filename");

        outputPS.println("list dir");

        outputPS.println("printFile filename");

        outputPS.println("createFile filename <remaining tokens written to file>");

        outputPS.println("mkdir dir");
    }

    private String getNextToken()
    {
        if (parseCommand.hasMoreTokens())
            return parseCommand.nextToken();
        else
            return null;
    }

    public File getFile()
    {
        File f = null;

        String fileName = getNextToken();

        if (fileName == null)
            outputPS.println("Missing a File name");
        else
            f = new File(fileName);

        return f;
    }

    public boolean processCommandLine(String line)
    {
        if (line == null)
            return false;

        boolean retval = true;

        System.out.println("FileServer processing: " + line);

        parseCommand = new StringTokenizer(line);

        String cmd = getNextToken();

        if (cmd == null)
        {
            outputPS.println("No command specified");
        }
        else
        {
            switch(cmd)
            {
                case "?":
                    printUsage();
                    break;
                case "quit":
                    retval = false;
                    break;
                case "delete":
                    delete();
                    break;
                case "rename":
                    rename();
                    break;
                case "size":
                    size();
                    break;
                case "lastModified":
                    lastModified();
                    break;
                case "list":
                    list();
                    break;
                case "printFile":
                    printFile();
                    break;
                case "createFile":
                    createFile();
                    break;
                case "mkdir":
                    mkdir();
                    break;
                default:
                    outputPS.println("Unrecognized command: "+cmd);
                    break;
            }
        }

        outputPS.println("====================================");
        outputPS.println();

        return retval;

    }

    public void processStream(InputStream is, OutputStream os) {
        System.out.println("FileServer.processStream begins");
        Scanner input = new Scanner(is);
        outputPS = new PrintStream(os);
        boolean continueFlag = true;

        while(continueFlag && input.hasNextLine())
        {
            String line = input.nextLine();

            outputPS.println("***************************************");

            outputPS.println(line);

            continueFlag = processCommandLine(line);
        }

        input.close();
        outputPS.close();

        System.out.println("Exiting processStream");
    }

    public static void main(String[] args) {
        FileServer fs = new FileServer();
        fs.monitorServer();

        System.out.println("Exiting FileServer");
    }
}