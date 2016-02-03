/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import Context.ClientContext;
import Context.Context;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jmacvey
 */
public class ClientThread extends Thread
{

    protected Socket clientSocket = null;
    protected String serverText = null;
    protected Consumer<Serializable> onReceiveCallback;

    public ClientThread(Socket clientSocket, String serverText,
            Consumer<Serializable> onReceiveCallback)
    {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
        this.onReceiveCallback = onReceiveCallback;
    }

    @Override
    public void run()
    {
        try
        {
            processClientRequest(clientSocket,
                    onReceiveCallback);
        } catch (IOException ex)
        {
            Logger.getLogger(WorkerRunnable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(WorkerRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Request processed. ");
    }

    /**
     * This method processes clients request.  All data input is checked against
     * a REQUEST_STOP I/O message, which breaks from the read loop and allows the 
     * client to close all streams and the socket.
     * @param clientSocket the socket associated with this client
     * @param onReceiveCallback consumer of serializable
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    private synchronized void processClientRequest(Socket clientSocket,
            Consumer<Serializable> onReceiveCallback)
            throws IOException, ClassNotFoundException
    {
        out = new ObjectOutputStream(clientSocket.getOutputStream());
        in = new ObjectInputStream(clientSocket.getInputStream());
        clientSocket.setTcpNoDelay(true);
        while (!clientSocket.isClosed())
        {
            System.out.println("Read data in client thread...");
            Serializable data = (Serializable) in.readUnshared();
            if (((ClientContext) data).getIOMSG().equals(IOMSG.REQUEST_STOP))
            {
                System.out.println("Received stop request from client."
                        + "\nSending confirmation to close and Breaking connection...");
                // send a close socket packet.
                out.writeObject(new IOContext(IOMSG.CLOSE_SOCKET));
                // send message to close the thread
                break;
            }
            else
            {
                onReceiveCallback.accept(data);
            }
        }
        out.close();
        in.close();
        clientSocket.close();
        System.out.println("Client Socket closed by Server.");
    }

    public ObjectOutputStream getOOS()
    {
        return out;
    }

    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
}
