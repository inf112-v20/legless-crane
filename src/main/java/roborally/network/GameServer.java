package roborally.network;


import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer {
    private Server server;
    private ServerListener listener;

    public GameServer() throws IOException {
        server = new Server();
        // This code starts a server on TCP port 54555 and UDP port 54777:
        server.start();
        server.bind(54555, 54777);
        // The start method starts a thread to handle incoming connections, reading/writing to the socket,
        // and notifying listeners.

        listener = new ServerListener(); // singleton here?
        server.addListener(listener); // think this should work as the ServerListener extends Listener
    }



}
