package roborally.network;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * Testing out Kryonet as suggested by Siv in the announcement 03.02 as the libGDX, suggested import in pom.xml
 * didn't work out with copy and paste, likely something I'm not understanding.
 * Anyhow I copied and pasted the one from https://mvnrepository.com/artifact/com.esotericsoftware/kryonet/2.22.0-RC1
 * Seems to be working
 *
 * Assume we'll need a listener that gets updates from clients
 *
 *
 * Prompts we might handle here:
 * Powerdown
 * rotation upon respawn
 * continue powerdown (after being powered down or just respawning)
 * cards chosen by player?
 */
public class ServerListener extends Listener {

    /**
     * Taken from https://github.com/EsotericSoftware/kryonet
     * just gather all listeners we'll need in this class? Do the same for clients?
     *
     *
     *         This code adds a listener to handle receiving objects:
     *
     *         server.addListener(new Listener() {
     *             public void received (Connection connection, Object object) {
     *                 if (object instanceof SomeRequest) {
     *                     SomeRequest request = (SomeRequest)object;
     *                     System.out.println(request.text);
     *
     *                     SomeResponse response = new SomeResponse();
     *                     response.text = "Thanks";
     *                     connection.sendTCP(response);
     *                 }
     *             }
     *         });
     *
     *         Note the Listener class has other notification methods that can be overridden.
     *         Typically a listener has a series of instanceof checks to decide what to do with the object received.
     *         In this example, it prints out a string and sends a response over TCP.
     *
     * @param connection
     */

    @Override
    public void connected(Connection connection) {
        // limit amount of players
        // set host
        // assign player ID?
        // player name?
    }

    @Override
    public void disconnected(Connection connection) {
        // Needed at all?
    }

    @Override
    public void received(Connection connection, Object object) {
        /*
        if (object instanceof x) {
            Handle initiating powerdown
        } else if (object instanceof y) {
            Handle continuing to be powered down
        } else if (object instanceof z) {
            Handle rotation selection when respawning ???
        } else if (object instanceof XYZ){
            Handle cards chosen by players?
        } else {
            etc...
        }
         */
    }

}
