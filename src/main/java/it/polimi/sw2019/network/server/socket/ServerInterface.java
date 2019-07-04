package it.polimi.sw2019.network.server.socket;

import it.polimi.sw2019.network.messages.Message;

import java.io.IOException;

/**
 * @author Mi97ch
 * used for communications to server in socket connection
 */
public interface ServerInterface {

    /**
     * send message to server in Socket connection
     * @param message message to be sent
     * @exception IOException exception thrown
     */
    void send(Message message) throws IOException;
}


