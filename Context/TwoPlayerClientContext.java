/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Context;

import BoardManager.TPClientBoardManager;
import Die.Die;
import IO.ClientID;
import IO.IOMSG;
import Maps.GameMap;
import Team.Team;
import Team.TeamID;
import framework.Action;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * This provides the contextual data for all two-player client contexts.
 *
 * @author Jmacvey
 */
public class TwoPlayerClientContext extends ClientContext
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
        this.teamOneActionButtons = new LinkedList<>();
        this.teamTwoActionButtons = new LinkedList<>();
        this.rectPanes = new HashMap<>();
        die.updatePaint();
    }

    /**
     * Default constructor. This constructor constructs a gameMap and teams. Its
     * use is primarily for the server in constructing new teams.
     *
     * @param map the map associated with this context.
     * @param ioMSG the ioMsg associated with the context.
     * @param allTeams list of all the teams
     * @param teamOne the first team in this context
     * @param teamTwo the second team in this context
     * @param bm the two player client board manager associated with this client
     * @param clientID the client ID associated with this context
     */
    public TwoPlayerClientContext(GameMap map, IOMSG ioMSG, List<Team> allTeams,
            Team teamOne, Team teamTwo, TPClientBoardManager bm, ClientID clientID)
    {
        super(map, ioMSG, bm, allTeams, clientID);
        this.currentActions = bm.getCurrentTeamActions();
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        buttonDisabled = new LinkedList<>();
        buttonDisabled.add(bm.isTurn());
        buttonDisabled.add(bm.isTurn());
    }
    
    /**
     *
     * @param other the context to compare
     * @return
     */
    @Override
    public boolean equals(Object other)
    {
        return getClientID().equals(
                ((TwoPlayerClientContext) other).getClientID());
    }

    /**
     * This constructor is used for I/O on initialization and exit
     *
     * @param ioMSG
     */
    public TwoPlayerClientContext(IOMSG ioMSG)
    {
        super(null, ioMSG, null, null, null);
    }

    public TwoPlayerClientContext copyContext()
    {
        TwoPlayerClientContext copy = new TwoPlayerClientContext(getMap(), null, getAllTeams(),
                teamOne, teamTwo, (TPClientBoardManager) getBoardManager(), getClientID());
        copy.setActionNum(actionNum);
        copy.setQueueNum(queueNum);
        copy.setIsDefending(isDefending);
        copy.setTeamOneActionButtons(teamOneActionButtons);
        copy.setTeamTwoActionButtons(teamTwoActionButtons);
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
        copy.setMoveableSpaces(moveableSpaces);
        copy.setMessage(message);
        copy.setGameMap(getMap());
        return copy;
    }


    public void initializeAvailableActions()
    {
        teamOneAvailableActions.clear();
        teamOneAvailableActions.addAll(teamOne.getCardsInHand());
        teamTwoAvailableActions.clear();
        teamTwoAvailableActions.addAll(teamTwo.getCardsInHand());
    }

    /**
     * Getter for the list of available actions.
     *
     * @return list of all available actions on this team.
     */
    public List<Action> getTeamOneAvailableActions()
    {
        return teamOneAvailableActions;
    }

    /**
     * Setter for the list of available actions
     *
     * @param availableActions the list of available actions
     */
    public void setTeamOneAvailableActions(List<Action> availableActions)
    {
        this.teamOneAvailableActions = (LinkedList<Action>) availableActions;
    }

    /**
     * Getter for the list of available actions.
     *
     * @return list of all available actions on this team.
     */
    public List<Action> getTeamTwoAvailableActions()
    {
        return teamTwoAvailableActions;
    }

    /**
     * Setter for the list of available actions
     *
     * @param availableActions the list of available actions
     */
    public void setTeamTwoAvailableActions(List<Action> availableActions)
    {
        this.teamTwoAvailableActions = (LinkedList<Action>) availableActions;
    }

    public void setButtonDisabled(LinkedList<Boolean> buttonDisabled)
    {
        this.buttonDisabled = buttonDisabled;
    }

    /**
     * Returns a master list of all action buttons associated with this team.
     *
     * @return
     */
    public List<Button> getActionButtons()
    {
        List<Button> listActions = new LinkedList<>();
        listActions.addAll(teamOneActionButtons);
        listActions.addAll(teamTwoActionButtons);
        return listActions;
    }

    /**
     * Setter for action buttons
     *
     * @param teamOneActionButtons the action buttons
     */
    public void setTeamOneActionButtons(List<Button> teamOneActionButtons)
    {
        this.teamOneActionButtons = teamOneActionButtons;
    }

    /**
     * Setter for action buttons
     *
     * @param teamTwoActionButtons the action buttons
     */
    public void setTeamTwoActionButtons(List<Button> teamTwoActionButtons)
    {
        this.teamTwoActionButtons = teamTwoActionButtons;
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
    }

    /**
     * Clears the actions associated with both the buttons and current actions.
     */
    public void clearActions()
    {
        teamOneActionButtons.clear();
        teamTwoActionButtons.clear();
    }

    /**
     * This method updates all fields with javaFX areas that are not
     * serializable. It should be called each time the object is updated after
     * serialization.
     */
    public void updateContext()
    {
        // update JavaFX objects after serializatoin
        getMap().generateCanvas();
        getBoardManager().setMap(getMap());
        die.updatePaint();
    }

    //--------------------------------------------------------------------------
    // Setter and Getter methods
    //--------------------------------------------------------------------------
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
     * Getter method for the list of action buttons for team one.
     *
     * @return the action buttons.
     */
    public List<Button> getTeamOneActionButtons()
    {
        return this.teamOneActionButtons;
    }

    /**
     * Getter method for the list of action buttons for team two.
     *
     * @return the action buttons.
     */
    public List<Button> getTeamTwoActionButtons()
    {
        return this.teamTwoActionButtons;
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
     * Gets the ally of the team associated with this ID
     *
     * @param teamID the ID
     * @param getAlly whether or not to return the ally of the team instead.
     * @return the ally of the team associated with the ID.
     */
    public Team getTeam(TeamID teamID, boolean getAlly)
    {
        if (teamOne.getTeamID().equals(teamID))
        {

            return getAlly ? teamTwo : teamOne;
        } else if (teamTwo.getTeamID().equals(teamID))
        {
            return getAlly ? teamOne : teamTwo;
        } else
        {
            return null;
        }
    }
    
    /**
     * Sets the moveable spaces after a roll.
     * @param moveableSpaces the moveable spaces.
     */
    public void setMoveableSpaces(HashSet<String> moveableSpaces) {
        this.moveableSpaces = moveableSpaces;
    }
    
    /**
     * Gets the moveable spaces.
     * @return the moveable spaces.
     */
    public HashSet<String> getMoveableSpaces() {return this.moveableSpaces;}

    //--------------------------------------------------------------------------
    // Private Data
    //--------------------------------------------------------------------------
    // Teams initialized on constructor
    private Team teamOne = null;
    private Team teamTwo = null;

    // buttonlists
    transient private List<Button> teamOneActionButtons = new LinkedList<>();
    transient private List<Button> teamTwoActionButtons = new LinkedList<>();

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
    private LinkedList<Action> teamOneAvailableActions = new LinkedList<>();
    private LinkedList<Action> teamTwoAvailableActions = new LinkedList<>();
    private HashSet<String> moveableSpaces = new HashSet<>();
}
