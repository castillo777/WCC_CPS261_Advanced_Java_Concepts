package file_client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class echoClient {

    Socket socket = null;

    void communicate(InputStream is, OutputStream os)

    {

        Scanner scanSocket = new Scanner(is);

        PrintStream psSocket = new PrintStream(os, true);

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Start entering commands for FileClient ....");

        String fromKeyboard= keyboard.nextLine();

        while (!fromKeyboard.equals("quit"))

        {

            psSocket.println(fromKeyboard); // write to socket

            String sFromSocket = scanSocket.nextLine(); // read from socket

            System.out.println("received from server: "+sFromSocket);

            fromKeyboard = keyboard.nextLine();

        }

        scanSocket.close();

        psSocket.close();

        keyboard.close();

        System.out.println("Exiting communicate");

    }

    public void startup(String[] args){

        String hostName="localhost";

        int portNum=4444;

        InputStream is = null;

        OutputStream os = null;

        if (args.length > 0)

            hostName = args[0];

        if (args.length > 1)

        {

            portNum = Integer.parseInt(args[1]);

        }

        try {

            socket = new Socket(hostName, portNum);

            is = socket.getInputStream();

            os = socket.getOutputStream();

            communicate(is,os);

            is.close();

            os.close();

        }

        catch (UnknownHostException e) {

            System.out.println("startup.UnknownHostException: "+e);

        }

        catch (IOException e) {

            System.out.println("startup.IOException: "+e);

        }

        System.out.println("Exitting startup");

    }

    public static void main(String[] args) {

        echoClient echoClient= new echoClient();

        echoClient.startup(args);

        System.out.println("Exiting main");

    }

}