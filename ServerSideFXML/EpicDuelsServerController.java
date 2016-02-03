/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSideFXML;

import Context.ClientContext;
import Context.ServerContext;
import Context.TwoPlayerClientContext;
import IO.IOMSG;
import epicduelsfxml.FXMLEpicDuelsController;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * This class represents the controller portion of the MVC design pattern for
 * the server side implementation of the Epic Duels turn-based RPG. The class is
 * largely responsible for the server-side response and data processing side of
 * the client-network protocol.
 *
 * @author Jmacvey
 */
public class EpicDuelsServerController extends FXMLEpicDuelsController
{

    /**
     * This method sets the server context for the game. Though this is already
     * set in the abstract class
     *
     * @param serverContext
     */
    public void setServerContext(ServerContext serverContext)
    {
        this.serverContext = serverContext;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        messageQueue.appendText("SERVER MESSAGE QUEUE: EPIC DUELS\n");
    }

    /**
     * This is the primary handler for incoming client data. The method
     * primarily maps data associated with an I/O message to a processing
     * function. This processing function eventually updates contexts and
     * transmits data back to the client for appropriate UI and game logic
     * updating.
     *
     * @param data the incoming data.
     */
    @Override
    public void handleData(Serializable data)
    {
        try
        {
            // get the client context
            clientContext = (TwoPlayerClientContext) data;
            messageQueue.appendText(
                    "Received IO Message: " + clientContext.getIOMSG().toString());
            messageQueue.appendText(clientContext.getIOMSG()
                    .equals(IOMSG.QUERY_INITIAL_CONTEXT) ? "\n"
                            : " from " + clientContext.getClientID() + "\n");
            messageQueue.appendText("Server received actions: "
                    + clientContext.toString() + "\n\n");
            switch (clientContext.getIOMSG())
            {
                case QUERY_INITIAL_CONTEXT:
                    transmitData(IOMSG.UPDATE_INITIAL_CONTEXT,
                            serverContext.createClientContext(true),
                            serverContext.getNumClients() - 1);
                    break;
                case RELAY_CONTEXT:
                    processAction(clientContext, "Updated client context.");
                    break;
                case ROLL:
                    processAction(clientContext, clientContext.getSelectedActor() + " rolled a "
                        + clientContext.getDie().toString() + "!");
                    break;
                case MOVE:
                    processAction(clientContext, clientContext.getSelectedActor() + " moved!");
                    break;
                default:
                    break;
            }
        } catch (Exception ex)
        {
            Logger.getLogger(EpicDuelsServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Processes the roll action for a specified context.
     *
     * @param context the context associated with the roller.
     * @param relayMsg the message associated with the roller.
     * @throws Exception
     */
    private void processAction(ClientContext context, String relayMsg) throws Exception
    {
        TwoPlayerClientContext tPCC = (TwoPlayerClientContext) context;
        updateContext(tPCC, tPCC.getIOMSG(), tPCC.getMessage());
        // update the list
        for (TwoPlayerClientContext tp : serverContext.getContextList())
        {
            if (!tp.equals(tPCC))
            {
                messageQueue.appendText("Processing Action for " + 
                        tp.getClientID().toString() + "\n");
                // copy the data over to other clients
                copyData(tPCC, tp);
                updateContext(tp, tPCC.getIOMSG(), relayMsg);
            }
        }
    }

    /**
     * Copies data from one context to another. This method is used to update
     * universal, passive information in the game across all clients.
     *
     * @param contextOne the first context.
     * @param contextTwo the second context.
     */
    private void copyData(ClientContext contextOne, ClientContext contextTwo)
    {
        TwoPlayerClientContext tpOne = (TwoPlayerClientContext) contextOne;
        TwoPlayerClientContext tpTwo = (TwoPlayerClientContext) contextTwo;

        // client needs updates on the action number, the die, 
        // and the moveable spaces
        tpTwo.setActionNum(tpOne.getActionNum());
        tpTwo.setDie(tpOne.getDie());
        tpTwo.setMoveableSpaces(tpOne.getMoveableSpaces());
        tpTwo.setGameMap(tpOne.getMap());
        // copy the map
//        System.out.println(tpTwo.getClientID() + " has "
//                + Integer.toString(tpTwo.getMoveableSpaces().size())
//                + " moveable spaces...");
//        System.out.println(tpOne.getClientID() + " has "
//                + Integer.toString(tpOne.getMoveableSpaces().size())
//                + " moveable spaces...");
    }

    /**
     * This private method is responsible for updating the contexts and
     * transmitting them to their respective destination. The update also
     * corresponds to both a string message to display to the user as well as an
     * IOMsg for I/O processing.
     *
     * @param context the context
     * @param message the message
     * @param ioMsg the IO Message
     * @throws Exception
     */
    private void updateContext(ClientContext context,
            IOMSG ioMsg, String message) throws Exception
    {
        int index = 0;

        for (TwoPlayerClientContext tp : serverContext.getContextList())
        {
            if (tp.equals(context))
            {
                messageQueue.appendText("Updating context for " + tp.getClientID() + "\n");
                tp.setMessage(message);
                tp = (TwoPlayerClientContext) context;
                transmitData(ioMsg, context, index);
            }
            index++;
        }
    }

    /**
     * Transmits data across the network from server to client. This is achieved
     * through calling the send(...) method associated with this client's
     * connection.
     *
     * @param msg the I/O message associated with this transmission.
     * @param clientContext the context associated with the destination client.
     * @param clientNum the client number used by the send message to access its
     * list of client.
     * @throws Exception
     */
    @Override
    public void transmitData(IOMSG msg,
            ClientContext clientContext, int clientNum) throws Exception
    {
        // get context handled by server
        ServerContext sContext = (ServerContext) getContext();

        if (clientContext != null)
        {
            clientContext.setIOMSG(msg);
        }
        // update the clients
        messageQueue.appendText("Updating client contexts...\n");
        sContext.updateClients();
        // update server message queue
        messageQueue.appendText("Transmitting IO Message: "
                + msg.toString() + "\n");
        switch (msg)
        {
            case UPDATE_INITIAL_CONTEXT:
                getConnection().send(
                        clientContext, clientNum);
                break;
            case ROLL:
            case MOVE:
            case RELAY_CONTEXT:
                getConnection().send(
                        clientContext, clientNum);
                break;
        }
    }

    /**
     * Adds a message to the server-side message queue.
     *
     * @param message the message.
     */
    public void addToMessageQueue(String message)
    {
        messageQueue.appendText(message + "..." + "\n");
    }

    //--------------------------------------------------------------------------
    // FXML Epic Duels
    //--------------------------------------------------------------------------
    @FXML
    private TextArea messageQueue;

    private TwoPlayerClientContext clientContext;

    private ServerContext serverContext;
}
