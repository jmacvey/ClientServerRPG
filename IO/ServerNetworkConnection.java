/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Jmacvey
 */
public abstract class ServerNetworkConnection implements NetworkConnection
{

    public ServerNetworkConnection(int port, Consumer<Serializable> onReceiveCallback)
    {
        this.onReceiveCallback = onReceiveCallback;
        this.serverPort = port;
        serverThread = new ServerThread(serverPort, onReceiveCallback);
        serverThread.setDaemon(true);
    }

    @Override
    public void startConnection() throws Exception
    {
        serverThread.start();
    }

    /**
     * Takes data to be sent to a client and sends it
     * @param data the data for the client
     * @param clientNum the client number associated in the list
     * Note: the n-th client is associated with the (n-1) item in the list.
     * @throws Exception 
     */
    @Override
    public void send(Serializable data, int clientNum) throws Exception
    {
        serverThread.getClientThreads().get(clientNum).getOOS().reset();
        serverThread.getClientThreads().get(clientNum).getOOS().writeUnshared(data);
    }

    @Override
    public void closeConnection() throws Exception
    {
        serverThread.close();
    }

    private ServerThread serverThread;

    //----------------------------------------------------------------------
    // Private and Protected Fields
    //----------------------------------------------------------------------
    protected int getPort()
    {
        return serverPort;
    }

    public String getStatus()
    {
        return serverThread.getStatus();
    }

    private int serverPort;
    private List<Thread> clientThreads = new LinkedList<>();
    private Consumer<Serializable> onReceiveCallback;

}
