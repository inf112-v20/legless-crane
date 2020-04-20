package roborally.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {


    /**
     *
     * cases to handle in this listener:
     * chose cards
     * power down?
     * stay in powerdown?
     *
     * @param connection the connection between server and client (?)
     * @param object the object being received
     */
    @Override
    public void received(Connection connection, Object object) {
        super.received(connection, object);
    }
}
