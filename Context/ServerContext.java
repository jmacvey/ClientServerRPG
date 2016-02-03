/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Context;

import ActionProcessor.LogicFactory;
import BoardManager.BoardManager;
import BoardManager.TPClientBoardManager;
import Die.Die;
import IO.ClientID;
import IO.IOMSG;
import Maps.GameMap;
import Maps.MapFactory;
import Maps.MapID;
import Team.Team;
import Team.TeamFactory;
import Team.TeamID;
import framework.Action;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * This class represents the overarching server context. It is responsible for
 * holding the information of all client contexts as well as updating whose turn
 * the client is. It stores team and map information.
 *
 * @author Jmacvey
 */
public class ServerContext implements Context, Serializable
{

    /**
     * Serializable overrides for transient objects.
     *
     * @param in the object input stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        this.actionButtons = new LinkedList<>();
        this.rectPanes = new HashMap<>();
    }

    /**
     * Default constructor. This constructor constructs a gameMap and teams. Its
     * use is primarily for the server in constructing new teams.
     */
    public ServerContext()
    {
        createMapAndTeams();
        this.currentActions = bm.getCurrentTeamActions();
    }

    /**
     * Update the clients.
     */
    public void updateClients() {
        
    }
        
    /**
     * Constructor for use by network communication modules.
     *
     * @param ioMsg the Input/Output message
     */
    public ServerContext(IOMSG ioMsg)
    {
        this.ioMsg = ioMsg;
    }

    public ServerContext(String message)
    {
        this.message = message;
    }

    public ClientContext createClientContext(boolean isTwoPlayerGame)
    {
        TwoPlayerClientContext cc = null;

        if (isTwoPlayerGame)
        {
            switch (numClients)
            {
                // first client connecting
                case 0:
                    cc = initTPCC(teamOne, teamTwo, 
                            isTeamTurn(teamOne) || isTeamTurn(teamTwo), 
                            ClientID.CLIENT_ONE);
                    break;
                case 1:
                    cc = initTPCC(teamThree, teamFour, 
                            isTeamTurn(teamThree) || isTeamTurn(teamFour),
                            ClientID.CLIENT_TWO);
                    break;
                default:
            }
            contextList.add(cc);
            return cc;
        } else // need to implement 4-player client based game
        {
            return null;
        }
    }
    
    /**
     * Accessor method for the context list.
     * @return 
     */
    public List<TwoPlayerClientContext> getContextList() {
        return this.contextList;
    }

    /**
     * Method meaning: initialize two player client context.
     *
     * @param firstTeam
     * @param secondTeam
     * @return
     */
    private TwoPlayerClientContext initTPCC(Team firstTeam, Team secondTeam,
            boolean isTurn, ClientID clientID)
    {

        // create a client board manager
        TPClientBoardManager cBM = new TPClientBoardManager(this.map, isTurn,
                bm.getCurrentTeam(),
                bm.getTeamLogic(firstTeam.getTeamID()),
                bm.getTeamLogic(secondTeam.getTeamID()));

        // create the context
        TwoPlayerClientContext tPCC
                = new TwoPlayerClientContext(map,
                        IOMSG.UPDATE_INITIAL_CONTEXT, teamList,
                        firstTeam, secondTeam, cBM,
                clientID);
        tPCC.setMessage("ClientView Initialized...");
        tPCC.updateDrawRollDisable(true, !isTurn);

        // we're adding another client here.
        numClients++;

        return tPCC;
    }

    private boolean isTeamTurn(Team team)
    {
        return bm.getCurrentTeam().equals(team);
    }

    public ServerContext copyContext()
    {
        ServerContext copy = new ServerContext("COPY");
        copy.setActionNum(actionNum);
        copy.setQueueNum(queueNum);
        copy.setIsDefending(isDefending);
        copy.setMap(map);
        copy.setBoardManager(bm);
        copy.setTeams(teamOne, teamTwo, teamThree, teamFour);
        copy.setActionButtons(actionButtons);
        copy.setCurrentActions(currentActions);
        copy.setSelectedActor(selectedActor);
        copy.setSelectedSubject(selectedSubject);
        copy.setSubjectID(subjectID);
        copy.setCurrValidMoves(currValidMoves);
        copy.setIsDefending(isDefending);
        copy.setIsMoving(isMoving);
        copy.setDie(die);
        copy.setCurrentLabel(currentLabel);
        copy.setButtonDisabled(buttonDisabled);
        return copy;
    }

    public void setButtonDisabled(LinkedList<Boolean> buttonDisabled)
    {
        this.buttonDisabled = buttonDisabled;
    }

    /**
     * Setter for action buttons
     *
     * @param actionButtons the action buttons
     */
    public void setActionButtons(List<Button> actionButtons)
    {
        this.actionButtons = actionButtons;
    }

    /**
     * Setter for current valid moves.
     *
     * @param currValidMoves the current valid moves
     */
    public void setCurrValidMoves(List<String> currValidMoves)
    {
        this.currValidMoves = currValidMoves;
    }

    /**
     * Setter for the die.
     *
     * @param die the die
     */
    public void setDie(Die die)
    {
        this.die = die;
    }

    /**
     * Setter for the current Actions
     *
     * @param currentActions the current actions.
     */
    public void setCurrentActions(List<Action> currentActions)
    {
        this.currentActions = currentActions;
    }

    public void setIOMSG(IOMSG ioMsg)
    {
        this.ioMsg = ioMsg;
    }

    public IOMSG getIOMSG()
    {
        return this.ioMsg;
    }

    /**
     * Setter for the teams.
     *
     * @param teamOne the first team
     * @param teamTwo the second team
     * @param teamThree the third team
     * @param teamFour the fourth team
     */
    public void setTeams(Team teamOne, Team teamTwo, Team teamThree, Team teamFour)
    {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.teamThree = teamThree;
        this.teamFour = teamFour;
    }

    /**
     * Game over test
     *
     * @return true if the game is over, false otherwise.
     */
    public boolean gameOver()
    {
        return (teamOne.isPrimaryDead() && teamTwo.isPrimaryDead())
                || (teamThree.isPrimaryDead() && teamFour.isPrimaryDead());
    }

    /**
     * Clears the actions associated with both the buttons and current actions.
     */
    public void clearActions()
    {
        actionButtons.clear();
    }

    /**
     * This method updates all fields with javaFX areas that are not
     * serializable. It should be called each time the object is updated after
     * serialization.
     */
    public void updateContext()
    {
        // update JavaFX objects after serializatoin
        map.generateCanvas();
        die.updatePaint();
    }

    //--------------------------------------------------------------------------
    // Setter and Getter methods
    //--------------------------------------------------------------------------
    /**
     * Getter method for the game map
     *
     * @return the game map
     */
    public GameMap getMap()
    {
        return map;
    }

    /**
     * Setter method for the game map
     *
     * @param map the map
     */
    public void setMap(GameMap map)
    {
        this.map = map;
    }

    /**
     * Getter method for the board manager.
     *
     * @return the board manager
     */
    public BoardManager getBoardManager()
    {
        return bm;
    }

    /**
     * Setter method for the board manager
     *
     * @param bm the board manager
     */
    public void setBoardManager(BoardManager bm)
    {
        this.bm = bm;
    }

    /**
     * Getter method for team one
     *
     * @return team one.
     */
    public Team getTeamOne()
    {
        return this.teamOne;
    }

    /**
     * Getter method for team two
     *
     * @return team two.
     */
    public Team getTeamTwo()
    {
        return this.teamTwo;
    }

    /**
     * Getter method for team Three
     *
     * @return team three
     */
    public Team getTeamThree()
    {
        return this.teamThree;
    }

    /**
     * Getter method for team Four
     *
     * @return team four
     */
    public Team getTeamFour()
    {
        return this.teamFour;
    }

    /**
     * Getter method for the list of action buttons
     *
     * @return the action buttons.
     */
    public List<Button> getActionButtons()
    {
        return this.actionButtons;
    }

    /**
     * Getter method for the list of current actions
     *
     * @return the list of current actions
     */
    public List<Action> getCurrentActions()
    {
        return this.currentActions;
    }

    /**
     * Getter method for the selected actor
     *
     * @return the selected actor
     */
    public String getSelectedActor()
    {
        return this.selectedActor;
    }

    /**
     * Setter method for selected actor
     *
     * @param actor the actor
     */
    public void setSelectedActor(String actor)
    {
        this.selectedActor = actor;
    }

    /**
     * Getter method for the selected subject
     *
     * @return the selected subject.
     */
    public String getSelectedSubject()
    {
        return this.selectedSubject;
    }

    /**
     * Setter method for the selected subject
     *
     * @param subject the subject
     */
    public void setSelectedSubject(String subject)
    {
        this.selectedSubject = subject;
    }

    /**
     * Getter method for the subject ID
     *
     * @return the subject ID
     */
    public TeamID getSubjectID()
    {
        return this.subjectID;
    }

    /**
     * Setter method for the subject ID
     *
     * @param subjectID the subject ID
     */
    public void setSubjectID(TeamID subjectID)
    {
        this.subjectID = subjectID;
    }

    /**
     * Getter method for the isDefending flag
     *
     * @return true if the subject is defending. false otherwise
     */
    public boolean isDefending()
    {
        return this.isDefending;
    }

    /**
     * Setter method for the isDefending flag
     *
     * @param isDefending the isDefending flag
     */
    public void setIsDefending(boolean isDefending)
    {
        this.isDefending = isDefending;
    }

    /**
     * Getter method for the current attack.
     *
     * @return the current attack
     */
    public Action getCurrentAttack()
    {
        return this.currentAttack;
    }

    /**
     * Setter method for the current attack
     *
     * @param currentAttack the current attack
     */
    public void setCurrentAttack(Action currentAttack)
    {
        this.currentAttack = currentAttack;
    }

    /**
     * Getter method for the die.
     *
     * @return the die
     */
    public Die getDie()
    {
        return this.die;
    }

    /**
     * Getter method for map from rectangle to stack panes.
     *
     * @return the hashmap
     */
    public HashMap<Rectangle, StackPane> getRectPanes()
    {
        return this.rectPanes;
    }

    /**
     * Getter method for the list of valid moves.
     *
     * @return the list of valid moves
     */
    public List<String> getCurrValidMoves()
    {
        return this.currValidMoves;
    }

    /**
     * Getter method for the queueNum
     *
     * @return the queueNum
     */
    public int getQueueNum()
    {
        return this.queueNum;
    }

    /**
     * Setter method for the queueNum
     *
     * @param queueNum the queueNum
     */
    public void setQueueNum(int queueNum)
    {
        this.queueNum = queueNum;
    }

    /**
     * Getter method for the the current number of actions remaining.
     *
     * @return the number of actions remaining.
     */
    public int getActionNum()
    {
        return this.actionNum;
    }

    /**
     * Setter method for the actionNum
     *
     * @param actionNum
     */
    public void setActionNum(int actionNum)
    {
        this.actionNum = actionNum;
    }

    /**
     * Getter method for the isMoving flag.
     *
     * @return true if player is currently moving. false otherwise.
     */
    public boolean isMoving()
    {
        return this.isMoving;
    }

    /**
     * Setter method for the isMoving flag
     *
     * @param isMoving the flag
     */
    public void setIsMoving(boolean isMoving)
    {
        this.isMoving = isMoving;
    }

    public void setMessage(String msg)
    {
        this.message = msg;
    }

    public String getMessage()
    {
        return message;
    }

    /**
     * Setter method for the current label.
     *
     * @param currentLabel the current label
     */
    public void setCurrentLabel(String currentLabel)
    {
        this.currentLabel = currentLabel;
    }

    /**
     * Getter method for the current label
     *
     * @return the current label
     */
    public String getCurrentLabel()
    {
        return this.currentLabel;
    }

    @Override
    public String toString()
    {
        return Integer.toString(currentActions.size());
    }

    //--------------------------------------------------------------------------
    // Utility methods
    //--------------------------------------------------------------------------
    /**
     * Converts a string-converted id to a grammatically-correct label.
     *
     * @param id the id to to convert
     * @return the converted string
     */
    public String convertIDToString(String id)
    {
        String[] s = id.split("_");
        String converted = "";
        for (String item : s)
        {
            converted += item.substring(0, 1)
                    + item.substring(1, item.length()).toLowerCase()
                    + " ";
        }
        return converted;
    }

    //--------------------------------------------------------------------------
    // Private Initialization Methods
    //--------------------------------------------------------------------------
    private void createMapAndTeams()
    {
        // create teams (the team factory maps positions into the zone).
        teamOne = TeamFactory.createTeam(TeamID.OBIWAN, map);
        teamTwo = TeamFactory.createTeam(TeamID.ANAKIN, map);
        makeAllies(teamOne, teamTwo);
        teamThree = TeamFactory.createTeam(TeamID.VADER, map);
        teamFour = TeamFactory.createTeam(TeamID.YODA, map);
        makeAllies(teamThree, teamFour);
        map.setTeamIDList(Arrays.asList(teamOne.getTeamID(), teamTwo.getTeamID(),
                teamThree.getTeamID(), teamFour.getTeamID()));

        // create the board manager
        createBoardManager(teamOne, teamTwo, teamThree, teamFour);
        teamList.add(teamOne);
        teamList.add(teamTwo);
        teamList.add(teamThree);
        teamList.add(teamFour);
    }

    /**
     * Creates the board manager given the teams.
     *
     * @param teamOne first team
     * @param teamTwo second team
     * @param teamThree third team
     * @param teamFour fourth team
     */
    private void createBoardManager(Team teamOne, Team teamTwo, Team teamThree,
            Team teamFour)
    {
        bm = new BoardManager(map,
                LogicFactory.createLogicSet(teamOne, map),
                LogicFactory.createLogicSet(teamTwo, map),
                LogicFactory.createLogicSet(teamThree, map),
                LogicFactory.createLogicSet(teamFour, map));
    }

    /**
     * Makes two teams allies.
     *
     * @param teamOne first team.
     * @param teamTwo second team.
     */
    private void makeAllies(Team teamA, Team teamB)
    {
        teamA.setAllyID(teamB.getTeamID());
        teamB.setAllyID(teamA.getTeamID());
    }

    public void updateDrawRollDisable(boolean drawButton, boolean rollButton)
    {
        buttonDisabled.clear();
        buttonDisabled.add(drawButton);
        buttonDisabled.add(rollButton);
    }

    public List<Boolean> getButtonDisabled()
    {
        return buttonDisabled;
    }

    /**
     * Sets the number of clients.
     *
     * @param numClients the number of clients.
     */
    public void setNumClients(int numClients)
    {
        this.numClients = numClients;
    }

    /**
     * Getter for the number of clients.
     *
     * @return num clients.
     */
    public int getNumClients()
    {
        return this.numClients;
    }

    //--------------------------------------------------------------------------
    // Private Data
    //--------------------------------------------------------------------------
    private GameMap map = MapFactory.createMap(MapID.KAMINO_PLATFORM);

    // Manager initialized on constructor
    private BoardManager bm;

    // Teams initialized on constructor
    private Team teamOne = null;
    private Team teamTwo = null;
    private Team teamThree = null;
    private Team teamFour = null;

    // buttonlists
    transient private List<Button> actionButtons = new LinkedList<>();

    // actions
    private List<Action> currentActions = new LinkedList<>();

    // selected actors/subjects - these values are updated based on toggles
    private String selectedActor = null;
    private String selectedSubject = null;
    private TeamID subjectID = null;

    private boolean isDefending = false;
    private boolean isMoving = false;

    private int actionNum = 2;
    private Action currentAttack = null;

    // game Die
    private Die die = new Die();

    private String currentLabel;
    private int queueNum = 0;
    private List<String> currValidMoves = new LinkedList<>();
    private transient HashMap<Rectangle, StackPane> rectPanes = new HashMap<>();
    private String message = "UNINITIALIZED";
    private IOMSG ioMsg;
    private LinkedList<Boolean> buttonDisabled = new LinkedList<>();

    private int numClients = 0;
    private List<Team> teamList = new LinkedList<>();
    
    private List<TwoPlayerClientContext> contextList = new LinkedList<>();
}
