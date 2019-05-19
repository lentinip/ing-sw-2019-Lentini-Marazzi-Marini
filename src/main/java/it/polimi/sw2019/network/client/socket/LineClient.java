package it.polimi.sw2019.network.client.socket;


import it.polimi.sw2019.network.client.ClientInterface;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.socket.ServerInterface;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * creates the connection between client and server and uses ServerInterface methods to communicate with the server
 */
public class LineClient extends Thread implements ServerInterface {

    /**
     * Default constructor
     */
    public LineClient() {

    }

    public LineClient(int port, String host, ClientInterface clientInterface) {
        this.port = port;
        this.host = host;
        startLine(port, host, clientInterface);
    }



    /* Attributes */

    private Socket socketClient;

    private int port;

    private String host;

    private ClientInterface client;

    private ObjectInputStream ObjectIn;

    private ObjectOutputStream ObjectOut;

    /* Methods */

    //TODO verify if the connection has been established
    private void startLine(int port, String host, ClientInterface clientInterface) {

        try {
            this.client = clientInterface;
            socketClient = new Socket(host, port);
            ObjectOut = new ObjectOutputStream(new BufferedOutputStream(socketClient.getOutputStream()));
            ObjectOut.flush();
            ObjectIn = new ObjectInputStream(new BufferedInputStream(socketClient.getInputStream()));
        } catch (UnknownHostException e) {
            //TODO manage exception
        } catch (IOException e) {
            //TODO manage exception
        }


    }

    @Override
    public void send(Message message) {

        try{
            ObjectOut.reset();
            ObjectOut.writeObject(message);
            ObjectOut.flush();
        } catch (IOException e) {
            //TODO manage exception
        }

    }

    @Override
    public void run() {

        boolean go = true;
        while (go && !socketClient.isClosed()) {

            try{
                Message message = (Message) ObjectIn.readObject();
                if(message == null) {
                    go = false;
                } else {
                    client.notify(message);
                }
            } catch (IOException e) {
                //TODO manage exception
            }
            catch (ClassNotFoundException e) {
                //TODO manage exception
            }

        }
    }



}
