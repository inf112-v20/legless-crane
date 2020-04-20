package roborally.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class GameClient {
    private Client client;

    public GameClient() throws IOException {

        // pull IP address out and add as parameter?

        client = new Client();
        client.start();
        // This code connects to a server running on TCP port 54555 and UDP port 54777:
        client.connect(5000, "192.168.0.4", 54555, 54777);

        /*
         * The start method starts a thread to handle the outgoing connection, reading/writing to the socket,and notifying
         * listeners. Note that the thread must be started before connect is called, else the outgoing connection will fail.
         *
         * In this example, the connect method blocks for a maximum of 5000 milliseconds.
         * If it times out or connecting otherwise fails, an exception is thrown (handling not shown).
         * After the connection is made, the example sends a "SomeRequest" object to the server over TCP.
         */

        client.addListener(new ClientListener());
    }

    public void sendRequest(Object object) {
        client.sendTCP(object);
    }
}
