/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import Context.ClientContext;
import Context.Context;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

/**
 *
 * @author Jmacvey
 */
public abstract class ClientNetworkConnection implements NetworkConnection
{

    public ClientNetworkConnection(Consumer<Serializable> onReceiveCallback)
    {
        this.onReceiveCallback = onReceiveCallback;
        connThread.setDaemon(true);
    }

    @Override
    public void startConnection() throws Exception
    {
        // spawns new thread and executes run
        connThread.start();
    }

    @Override
    public void send(Serializable data) throws Exception
    {
        if (connThread.out == null)
        {
            System.out.println("connThread.out is null!");
        }
        connThread.out.reset();
        connThread.out.writeUnshared(data);
    }

    @Override
    public void closeConnection() throws Exception
    {
        if (connThread.socket != null)
        {
            connThread.socket.close();
        }
    }

    private class ConnectionThread extends Thread
    {

        @Override
        public void run()
        {
            try

            {
                Socket s = new Socket(getIP(), getPort());
                this.socket = s;
                this.out = new ObjectOutputStream(socket.getOutputStream());
                this.in = new ObjectInputStream(socket.getInputStream());
                socket.setTcpNoDelay(true);
                System.out.println("Client connected to host@:"
                        + socket.getRemoteSocketAddress());
                boolean initial = true;
                while (!socket.isClosed())
                {
                    System.out.println("Waiting for read...");
                    Serializable data = (Serializable) in.readUnshared();
                    System.out.println("Read data...");
                    if (data != null && ((ClientContext) data).
                            getIOMSG().equals(IOMSG.CLOSE_SOCKET))
                    {
                        System.out.println("CLOSE_SOCKET packet received...");
                        out.close();
                        in.close();
                        socket.close();
                        System.out.println("Socket closed by client.");
                        break;
                    } else
                    {
                        // calls the consumer function
                        onReceiveCallback.accept(data);
                    }
                }
            } catch (Exception e)
            {
                System.out.println("Exception Caught: " + e.toString());
                e.printStackTrace();
            }
        }

        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
    }

    //----------------------------------------------------------------------
    // Private and Protected Fields
    //----------------------------------------------------------------------
    protected abstract String getIP();

    protected abstract int getPort();

    private Consumer<Serializable> onReceiveCallback;
    private ConnectionThread connThread = new ConnectionThread();
    private boolean isStopped = false;

}
