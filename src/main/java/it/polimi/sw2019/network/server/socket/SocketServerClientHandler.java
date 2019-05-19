package it.polimi.sw2019.network.server.socket;

import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class manages the communication with the client matched with the socket assigned.
 * It uses the ClientAction methods to communicate with the client
 */
public class SocketServerClientHandler extends Thread implements ClientInterface {

    /**
     * Default constructor
     */

    public SocketServerClientHandler() {

    }

    public SocketServerClientHandler(Socket socket, SocketServer socketServer) {
        this.connection = socket;
        this.socketServer = socketServer;
        try{
            this.objectIn = new ObjectInputStream(this.connection.getInputStream());
            this.objectOut = new ObjectOutputStream(this.connection.getOutputStream());
        } catch (IOException e) {
            //TODO manage exception
        }
    }


    /* Attributes */

    private SocketServer socketServer;

    private Socket connection;

    private ObjectInputStream objectIn;

    private ObjectOutputStream objectOut;

    /* Methods */

    /**
     * this method is used to receive a message from the client
     */
    @Override
    public void run() {

        boolean go = true;
        try{
            while(go && !connection.isClosed()) {

                Message message = (Message) objectIn.readObject();
                if(message == null) {
                    go = false;
                } else {
                    //TODO implement how to use this message
                }
            }
        } catch (IOException e) {
            //TODO manage exception, DISCONNECTION
        }
        catch(ClassNotFoundException e) {
            //TODO manage exception, DISCONNECTION
        }

    }

    /**
     * this method is used to notify something to the client
     * @param message is the message sent by the server to the client
     */
    @Override
    public void notify(Message message) {
        try{
            objectOut.reset();
            objectOut.writeObject(message);
            objectOut.flush();
        } catch (IOException e) {
            //TODO manage exception
        }
    }

}
