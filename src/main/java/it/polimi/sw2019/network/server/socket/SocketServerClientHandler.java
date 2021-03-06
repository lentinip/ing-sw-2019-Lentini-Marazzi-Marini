package it.polimi.sw2019.network.server.socket;

import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.commons.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Mi97ch
 * This class manages the communication with the client matched with the socket assigned.
 * It uses the ClientAction methods to communicate with the client
 */
public class SocketServerClientHandler extends Thread implements ClientInterface {

    /**
     * Constructor
     * @param socket reference to socket
     * @param socketServer reference to socket server
     */
    public SocketServerClientHandler(Socket socket, SocketServer socketServer) {
        this.connection = socket;
        this.socketServer = socketServer;
        try{
            this.objectIn = new ObjectInputStream(this.connection.getInputStream());
            this.objectOut = new ObjectOutputStream(this.connection.getOutputStream());
        } catch (IOException e) {

            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }


    /* Attributes */

    private SocketServer socketServer;

    private Socket connection;

    private ObjectInputStream objectIn;

    private ObjectOutputStream objectOut;

    private String sender;

    private static Logger LOGGER = Logger.getLogger("SocketServerClientHandler");

    /* Methods */

    /**
     * this method is used to receive a message from the client
     */
    @Override
    public void run() {


        try{
            boolean go = true;
            while(go) {

                LOGGER.log(Level.INFO, "SocketServer is ready to receive messages");
                Message message = null;
                try {
                    message = (Message) objectIn.readObject(); //message received
                }
                catch (NullPointerException e){
                    go = false;
                }
                if(message == null) {
                    go = false;
                } else {

                    sender = message.getUsername();
                    socketServer.receive(message);
                }
            }
        } catch (IOException e) {

            if(socketServer.getServer().getCurrentWaitingRoom().getWaitingPlayers().containsKey(sender))
            {
                socketServer.getServer().removeWaitingPlayer(sender);
            }
            else {
                socketServer.disconnect(sender);
            }
        }
        catch(ClassNotFoundException e) {

            if(socketServer.getServer().getCurrentWaitingRoom().getWaitingPlayers().containsKey(sender))
            {
                socketServer.getServer().removeWaitingPlayer(sender);
            }
            else {
                socketServer.disconnect(sender);
            }
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

            LOGGER.log(Level.WARNING, e.getMessage());
        }
        catch (NullPointerException e){
            //DO nothing
        }
    }

}
