package file_server;

import java.net.Socket;

public interface ConnectListener {
    public void handleConnect(Socket s);
}
