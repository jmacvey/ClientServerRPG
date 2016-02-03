/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Jmacvey
 */
public class ServerThread extends Thread
{

    public ServerThread(int port, Consumer<Serializable> onReceiveCallback)
    {
        this.serverPort = port;
        this.onReceiveCallback = onReceiveCallback;
    }

    @Override
    public void run()
    {
        synchronized (this)
        {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped())
        {
            Socket socket = null;
            try
            {
                // wait for client to communicate 
                this.status = "Waiting for client to connect to port "
                        + Integer.toString(serverPort);
                socket = this.serverSocket.accept();
            } catch (IOException e)
            {
                if (isStopped())
                {
                    System.out.println("Server stopped");
                    break;
                } else
                {
                    throw new RuntimeException("Error accepting client connection", e);
                }
            }
            this.status = "Server connected to client@:"
                    + socket.getRemoteSocketAddress();

            ClientThread clientThread = new ClientThread(
                    socket, "Duels Server", onReceiveCallback);
            clientThread.start();
            // create a new thread to handle response
            clientThreads.add(clientThread);
        }
    }

    /**
     * Determines if the server is stopped.
     *
     * @return true if stopped. false otherwise.
     */
    private boolean isStopped()
    {
        return this.isStopped;
    }

    /**
     * Returns the status of the server.
     *
     * @return string containing status.
     */
    public String getStatus()
    {
        return this.status;
    }

    private void openServerSocket()
    {
        try
        {
            this.serverSocket = new ServerSocket(serverPort);
        } catch (IOException e)
        {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

    public synchronized List<ClientThread> getClientThreads()
    {
        return clientThreads;
    }

    public synchronized void close()
    {
        this.isStopped = true;
        try
        {
            // request to break.
            this.serverSocket.close();
        } catch (IOException e)
        {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private String status;
    private ServerSocket serverSocket;
    protected boolean isStopped = false;
    protected Thread runningThread = null;
    private int serverPort;
    private List<ClientThread> clientThreads = new LinkedList<>();
    private Consumer<Serializable> onReceiveCallback;
}
