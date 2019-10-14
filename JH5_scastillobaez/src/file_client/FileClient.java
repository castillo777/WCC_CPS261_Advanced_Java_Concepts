package file_client;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class FileClient extends echoClient {

    public void communicate (InputStream is, OutputStream os){

        Scanner scanSocket = new Scanner(is);

        PrintStream psSocket = new PrintStream(os, true);

        Scanner keyboard = new Scanner(System.in);

        System.out.println("Start entering text to FileServer ....");

        String fromKeyboard= keyboard.nextLine();

        do
        {
            psSocket.println(fromKeyboard); // write to socket

            while (scanSocket.hasNextLine())
            {
                String sFromSocket = scanSocket.nextLine(); // read from socket

                if (sFromSocket.length() == 0)

                    break;

                System.out.println("received from server: "+sFromSocket);
            }

            fromKeyboard = keyboard.nextLine();

        } while (!fromKeyboard.equals("quit"));

        scanSocket.close();

        psSocket.close();

        keyboard.close();

        System.out.println("Exiting communicate");
    }

    public static void main(String[] args) {

        FileClient fc= new FileClient();

        fc.startup(args);

        System.out.println("Exiting main");
    }
}