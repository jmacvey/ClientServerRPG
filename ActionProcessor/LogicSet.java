/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActionProcessor;

import Maps.GameMap;
import Team.Team;
import Team.TeamID;
import framework.ADAction;
import framework.Action;
import framework.ModifiedAction;
import framework.Player;
import java.io.Serializable;

/**
 * This abstract class is an ActionProcessor for a specific team. All inheriting
 * classes need to implement the processAction method.
 *
 * @author Jmacvey
 */
public abstract class LogicSet implements ActionProcessor, Serializable
{

    /**
     * Constructor.
     *
     * @param team the team whose logic this set represents.
     * @param map the map associated with this logic set
     */
    public LogicSet(Team team, GameMap map)
    {
        this.map = map;
        this.team = team;
        this.teamID = team.getTeamID();
    }

    @Override
    public abstract ActionPackage processAction(ActionPackage ap,
            String position, boolean isAttack);

//    public abstract ActionPackage processAction(
//            TeamID actorTeamID,
//            Action actorAction, String actor, TeamID subjectTeamID,
//            String subject, String position, boolean isAttack);
    /**
     * This attackDefend method works for all attack and defend actions taken in
     * play.
     *
     * @param ap the action package associated with this attack
     * @param action the action.
     * @param isAttack true if this is being played as an attack, false
     * otherwise.
     * @return the actionPackage associated with this attack.
     */
    public ActionPackage attackDefend(ActionPackage ap, Action action, boolean isAttack)
    {
        if (isAttack)
        {
            int attackVal = ap.getAction().isModified() ? ((ModifiedAction) ap.getAction()).getAttackValue()
                    : ((ADAction) ap.getAction()).getAttackValue();
            // return the action modified with the attack value
            ActionPackage app = new ActionPackage(ap.getAction(), ap.getActorID(),
                    ap.actor(), ap.getSubjectID(), ap.subject(), MessageID.ATTACK,
                    attackVal, null, 0
            );
            return this.currentAction = app;
        } else
        {
            Player p = this.getTeam().getPlayerOnTeam(ap.actor());
            p.setHealth(Math.max(0, p.getHealth()
                    - Math.max(0, ap.getASMessageValue() - ap.getAMessageValue())));
            if (p.getHealth() == 0)
            {
                map.getOccupiedPositions().remove(p.getPosition());
                p.setPosition("DEAD");
            }
            return this.currentResponse = ap;
        }
    }

    /**
     * Move function for a player
     *
     * @param action the action associated with this
     * @param teamID the ID of the team
     * @param playerName the player to move
     * @param position the new position of the player
     * @return the action package produced from a player movement
     */
    public ActionPackage movePlayer(Action action, TeamID teamID,
            String playerName, String position)
    {
        System.out.println("MOVING PLAYER.");
        // set the position as true
        Player p = team.getPlayerOnTeam(playerName);
        // set the current position as unoccupied
        map.setUnoccupiedPosition(p.getPosition());
        // change position of the player.
        p.setPosition(position);
        map.setOccupiedPosition(position, playerName);
        // communicate the change to the GUI
        return new ActionPackage(action, teamID, playerName, null, null,
                MessageID.MOVE, 0, null, 0);
    }

    /**
     * All inheriting classes must implement the drawCard() method.
     *
     * @param override whether or not to override the maximum allowed cards
     * @return returns true if the card is drawn. False otherwise.
     */
    public ActionPackage drawCard(boolean override)
    {
        if (this.getTeam().getNextCard(override))
        {
            ActionPackage ap = new ActionPackage(null, null, "Team "
                    + getTeam().getTeamID().toString(),
                    null, null, MessageID.DRAW,
                    1, null, 0);
            System.out.println("Draw Confirmed");
            return ap;
        } else
        {
            return null;
        }
    }

    /**
     * Getter function for the teamID
     *
     * @return the teamID associated with this logic set
     */
    public TeamID getTeamID()
    {
        return this.teamID;
    }

    /**
     * Getter function for the current action being played.
     *
     * @return the current action in play.
     */
    public ActionPackage getCurrentAction()
    {
        return this.currentAction;
    }

    /**
     * Getter function for the current response in play.
     *
     * @return the current response.
     */
    public ActionPackage getCurrentResponse()
    {
        return this.currentResponse;
    }

    /**
     * Setter function for the current response in play.
     *
     * @param currentResponse the currentResponse.
     */
    public void setCurrentResponse(ActionPackage currentResponse)
    {
        this.currentResponse = currentResponse;
    }

    /**
     * Setter function for the current action in play.
     *
     * @param currentAction the current action.
     */
    public void setCurrentAction(ActionPackage currentAction)
    {
        this.currentAction = currentAction;
    }

    /**
     * Getter method for the team.
     *
     * @return the team
     */
    public Team getTeam()
    {
        return team;
    }
    
    public void setMap(GameMap map)
    {
        this.map = map;
    }
    

    private Team team;
    private TeamID teamID;
    private GameMap map;

    private ActionPackage currentResponse;
    private ActionPackage currentAction;

}
