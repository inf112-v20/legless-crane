package roborally.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * code from https://github.com/EsotericSoftware/kryonet
 *
 * investigating how to implement this with our system.
 */
public class Prototying {

    // The SomeRequest and SomeResponse classes are defined like this:
    public class SomeRequest {
        public String text;
    }
    public class SomeResponse {
        public String text;
    }

    void startServer() throws IOException {
        // This code starts a server on TCP port 54555 and UDP port 54777:
        Server server = new Server();
        server.start();
        server.bind(54555, 54777);
        // The start method starts a thread to handle incoming connections, reading/writing to the socket,
        // and notifying listeners.


        // This code adds a listener to handle receiving objects:
        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof SomeRequest) {
                    SomeRequest request = (SomeRequest)object;
                    System.out.println(request.text);

                    SomeResponse response = new SomeResponse();
                    response.text = "Thanks";
                    connection.sendTCP(response);
                }
            }
        });
        /*
        Note the Listener class has other notification methods that can be overridden.
        Typically a listener has a series of instanceof checks to decide what to do with the object received.
        In this example, it prints out a string and sends a response over TCP.
         */


        /*
        For the above examples to work, the classes that are going to be sent over the network must be registered with the following code:

        This must be done on both the client and server, before any network communication occurs.
        It is very important that the exact same classes are registered on both the client and server,
        and that they are registered in the exact same order. Because of this, typically the code that registers
        classes is placed in a method on a class available to both the client and server.
        */
        Kryo kryo = server.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);

    }

    void connectingAClient() throws IOException {
        // This code connects to a server running on TCP port 54555 and UDP port 54777:
        Client client = new Client();
        client.start();
        client.connect(5000, "192.168.0.4", 54555, 54777);

        SomeRequest request = new SomeRequest();
        request.text = "Here is the request";
        client.sendTCP(request);

        /*
        The start method starts a thread to handle the outgoing connection, reading/writing to the socket,and notifying
        listeners. Note that the thread must be started before connect is called, else the outgoing connection will fail.

        In this example, the connect method blocks for a maximum of 5000 milliseconds.
        If it times out or connecting otherwise fails, an exception is thrown (handling not shown).
        After the connection is made, the example sends a "SomeRequest" object to the server over TCP.
         */


        // This code adds a listener to print out the response:
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof SomeResponse) {
                    SomeResponse response = (SomeResponse)object;
                    System.out.println(response.text);
                }
            }
        });

        /*
        For the above examples to work, the classes that are going to be sent over the network must be registered with the following code:

        This must be done on both the client and server, before any network communication occurs.
        It is very important that the exact same classes are registered on both the client and server,
        and that they are registered in the exact same order. Because of this, typically the code that registers
        classes is placed in a method on a class available to both the client and server.
        */
        Kryo kryo = client.getKryo();
        kryo.register(SomeRequest.class);
        kryo.register(SomeResponse.class);
    }







}
