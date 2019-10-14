package file_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer implements Runnable, Cloneable{

    Socket clientSocket = null;

    Thread myThread = null;

    public void handleConnect(Socket s) {

        clientSocket = s;

        try {
            Runnable protocol_handler = (Runnable)clone();

            Thread myThread = new Thread(protocol_handler);

            System.out.println("Launching a new Child Thread");

            myThread.start();
        }

        catch (CloneNotSupportedException e) {
            System.out.println("EchoServer.handleConnect Clone error: " + e);
        }

    }

    public void run()
    {
        InputStream is = null;
        OutputStream os = null;

        try {
            is = clientSocket.getInputStream();

            os = clientSocket.getOutputStream();

            processStream(is, os);
        }

        catch (IOException e) {
            System.out.println("Server Exception: " + e);
        }

        finally
        {
            try
            {
                if (is != null) is.close();
                if (os != null) os.close();
            }

            catch (IOException e)
            {
                System.out.println("run: Stupid exception that shouldn't happen");
            }

        }

        System.out.println("Exiting Child Server Thread");

    }

    public void processStream(InputStream is, OutputStream os)
    {
        System.out.println("FileServer.processStream begins");
        Scanner input = new Scanner(is);
        PrintStream ps = new PrintStream(os);

        while (input.hasNextLine())
        {
            processNextLine(input, ps);
        }

        input.close();
        ps.close();

        System.out.println("Exiting processStream");
    }

    public void processNextLine(Scanner input, PrintStream ps)
    {
        String s = input.nextLine();
        ps.println(s);
        System.out.println("FileServer echoed: " + s);
    }

    public void monitorServer()
    {
        MotherServer myServer = new MotherServer();

        System.out.println(MotherServer.getHostInfo());

        myServer.startServer(4444, this);

        Scanner keyboard = new Scanner(System.in);

        String s = "";

        while (!s.equals("quit"))
        {
            System.out.println("Whenever you want to stop the server, enter \"quit\" ");
            s = keyboard.next();
        }

        keyboard.close();
        myServer.stopServer();
    }

    public static void main(String[] args) {
        EchoServer es = new EchoServer();

        es.monitorServer();
        System.out.println("Exitting FileServer");
    }
}