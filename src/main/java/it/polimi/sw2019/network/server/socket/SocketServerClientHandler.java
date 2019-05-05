package it.polimi.sw2019.network.server.socket;

import it.polimi.sw2019.network.client.ClientActions;
import it.polimi.sw2019.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class manages the communication with the client mathed with the socket assigned
 */
public class SocketServerClientHandler extends Thread implements ClientActions {

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
            //TODO manage exception
        }
        catch(ClassNotFoundException e) {
            //TODO manage exception
        }

    }

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
