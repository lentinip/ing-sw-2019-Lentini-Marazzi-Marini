package it.polimi.sw2019.network.client.socket;


import it.polimi.sw2019.network.client.ClientActions;
import it.polimi.sw2019.network.messages.Message;
import it.polimi.sw2019.network.server.socket.ServerInterface;

import java.io.*;
import java.net.Socket;

public class LineClient extends Thread implements ServerInterface {

    /**
     * Default constructor
     */
    public LineClient() {

    }

    public LineClient(int port, String host, ClientActions clientActions) {
        this.port = port;
        this.host = host;
        startLine(port, host,clientActions);
    }



    /* Attributes */

    private Socket socketClient;

    private int port;

    private String host;

    private ClientActions client;

    private ObjectInputStream ObjectIn;

    private ObjectOutputStream ObjectOut;

    /* Methods */

    //TODO verify if the connection has been established
    private void startLine(int port, String host, ClientActions clientActions) {

        try {
            this.client = clientActions;
            socketClient = new Socket();
            ObjectOut = new ObjectOutputStream(new BufferedOutputStream(socketClient.getOutputStream()));
            ObjectOut.flush();
            ObjectIn = new ObjectInputStream(new BufferedInputStream(socketClient.getInputStream()));
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
