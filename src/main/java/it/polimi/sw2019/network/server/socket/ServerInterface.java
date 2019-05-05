package it.polimi.sw2019.network.server.socket;

import it.polimi.sw2019.network.messages.Message;

/**
 * used for communications to server in socket
 */
public interface ServerInterface {

    public void send(Message message);
}


