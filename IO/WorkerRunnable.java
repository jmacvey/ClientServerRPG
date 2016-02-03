package IO;

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
 */
public class WorkerRunnable implements Runnable
{

    protected Socket clientSocket = null;
    protected String serverText = null;
    protected Consumer<Serializable> onReceiveCallback;

    public WorkerRunnable(Socket clientSocket, String serverText,
            Consumer<Serializable> onReceiveCallback)
    {
        this.clientSocket = clientSocket;
        this.serverText = serverText;
        this.onReceiveCallback = onReceiveCallback;
    }

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

    private synchronized void processClientRequest(Socket clientSocket,
            Consumer<Serializable> onReceiveCallback)
            throws IOException, ClassNotFoundException
    {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        clientSocket.setTcpNoDelay(true);
        while (!clientSocket.isClosed())
        {

            Serializable data = (Serializable) in.readUnshared();
            onReceiveCallback.accept(data);
        }
        System.out.println("Socket closed.");
    }
}
