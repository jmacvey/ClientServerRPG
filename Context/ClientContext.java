/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Context;

import BoardManager.ClientBoardManager;
import IO.ClientID;
import IO.IOMSG;
import Maps.GameMap;
import Team.Team;
import framework.Player;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jmacvey
 */
public abstract class ClientContext implements Context, Serializable
{

    /**
     * Constructor the the client context.
     *
     * @param map the game map associated with this client
     * @param ioMsg the ioMsg associated with the client.
     * @param bm the board manager.
     * @param teamList the list of all teams on the board.
     * @param clientID the client id associated with this context.
     */
    public ClientContext(GameMap map, IOMSG ioMsg, ClientBoardManager bm,
            List<Team> teamList, ClientID clientID)
    {
        this.map = map;
        this.ioMsg = ioMsg;
        this.bm = bm;
        this.teamList = teamList;
        this.clientID = clientID;
    }

    /**
     * Constructor containing just the I/O message to query server
     *
     * @param ioMsg the i/o message
     */
    public ClientContext(IOMSG ioMsg)
    {
        this.ioMsg = ioMsg;
    }

    /**
     * Getter method for the gameMap.
     *
     * @return the game map
     */
    public final GameMap getMap()
    {
        return this.map;
    }
    
    /**
     * Gets the client iD
     * @return the client id
     */
    public final ClientID getClientID() {
        return this.clientID;
    }
    /**
     * Setter for the game map.
     *
     * @param map the map.
     */
    public final void setGameMap(GameMap map)
    {
        this.map = map;
    }

    /**
     * Gets the current IOMSG
     *
     * @return the I/O message.
     */
    public final IOMSG getIOMSG()
    {
        return this.ioMsg;
    }

    /**
     * Setter for the IOMSG
     *
     * @param ioMsg the I/O message
     */
    public final void setIOMSG(IOMSG ioMsg)
    {
        this.ioMsg = ioMsg;
    }

    /**
     * Getter method for the board manager.
     *
     * @return the board manager
     */
    public final ClientBoardManager getBoardManager()
    {
        return this.bm;
    }

    /**
     * Setter for the board manager
     *
     * @param bm the board manager.
     */
    public final void setBoardManager(ClientBoardManager bm)
    {
        this.bm = bm;
    }

    /**
     * Sets the teams for the game.
     *
     * @param teamList
     */
    public void setAllTeams(List<Team> teamList)
    {
        this.teamList = teamList;
    }

    /**
     * Gets the list of all teams in this game.
     *
     * @return the opponents
     */
    public List<Team> getAllTeams()
    {
        return teamList;
    }

    /**
     * Gets the list of all players in the game.
     * @return the master player list.
     */
    public List<Player> getAllPlayers()
    {
        List<Player> masterPlayerList = new LinkedList<>();
        teamList.stream().forEach(team ->
        {
           masterPlayerList.addAll(team.getPlayers());
        });
        return masterPlayerList;
    }

    //--------------------------------------------------------------------------
    // Private Fields
    //--------------------------------------------------------------------------
    private GameMap map;
    private IOMSG ioMsg;
    private ClientBoardManager bm;

    private boolean isDefending = false;
    private boolean isMoving = false;

    private int actionNum = 2;

    private String currentLabel;
    private int queueNum = 0;
    private List<Team> teamList = new LinkedList<>();
    private ClientID clientID;
}
