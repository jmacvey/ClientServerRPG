/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;


import java.io.Serializable;
import java.util.function.Consumer;

/**
 * @author Jmacvey
 */
public class Server extends ServerNetworkConnection
{
    public Server(int port, Consumer<Serializable> onReceiveCallback)
    {
        super(port, onReceiveCallback);
    }
}
