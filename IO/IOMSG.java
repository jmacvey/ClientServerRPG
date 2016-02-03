/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

/**
 *
 * @author Jmacvey
 */
public enum IOMSG
{

    // Message (client)                 -> Response (Server)
    QUERY_INITIAL_CONTEXT,              UPDATE_INITIAL_CONTEXT,
    QUERY_CONTEXT,                      UPDATE_CONTEXT, 
    RELAY_CONTEXT,
    REQUEST_STOP,                       CLOSE_SOCKET,
    ATTACK,                             DEFEND,
    MOVE,
    ROLL,                               DRAW,
    
    // Message (server)                 -> Response(Client)
    

}
