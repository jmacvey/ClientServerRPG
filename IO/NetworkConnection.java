/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

import java.io.Serializable;

/**
 *
 * @author Jmacvey
 */
public interface NetworkConnection 
{
    public void startConnection() throws Exception;

    default public void send(Serializable data) throws Exception {}
    
    default public void send(Serializable data, int clientNum) throws Exception {}
 

    public void closeConnection() throws Exception;
}
