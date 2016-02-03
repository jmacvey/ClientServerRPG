/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 *
 * @author Jmacvey
 */
public class Client extends ClientNetworkConnection
{

    public Client(String ip, int port, Consumer<Serializable> onReceiveCallback)
    {
        super(onReceiveCallback);
        this.ip = ip;
        this.port = port;
    }

    @Override
    protected String getIP()
    {
        return ip;
    }

    @Override
    protected int getPort()
    {
        return port;
    }
    private int port;
    private String ip;

}
