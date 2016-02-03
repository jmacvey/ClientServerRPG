/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import Maps.GameMap;
import Team.TeamID;
import java.io.Serializable;


/**
 * This class represents a player on a team and provides functionality for
 * determining if a player is a primary or secondary player, their life,
 * @author jmacvey
 */
public abstract class Player implements Serializable
{
    
    /**
     *
     * @param name
     * @param teamID
     * @param health
     * @param isPrimary
     * @param hasRangedAttack
     * @param gameMap
     * @param position
     */
    public Player(String name, TeamID teamID, int health, boolean isPrimary,
            boolean hasRangedAttack, GameMap gameMap, String position)
    {
        this.name = name;
        this.teamID = teamID;
        this.health = health;
        this.isPrimary = isPrimary;
        this.hasRangedAttack = hasRangedAttack;
        this.gameMap = gameMap;
        this.position = position;
    }
    /**
     * Getter method for the name of this player.
     * @return the name of this player.
     */
    public String getName() { return this.name; };
    
    /**
     * Setter method for the name of this player.
     * @param name 
     */
    public void setName(String name) { this.name = name;};
    
    /**
     * Getter method for the team ID of this player.
     * @return 
     */
    public TeamID getTeamID() { return this.teamID; };
    
    /**
     * Setter method for the team ID of this player.
     * @param teamID the teamID
     */
    public void setTeamID(TeamID teamID) {this.teamID = teamID;};
    
    /**
     * Getter method for checking if this player is a primary player.
     * @return true if character is primary.  False otherwise.
     */
    public boolean isPrimary() { return this.isPrimary;};
    
    /**
     * Setter method for checking if this player is a primary player.
     * @param isPrimary whether or not this is a primary character.
     */
    public void setPrimary(boolean isPrimary) { this.isPrimary = isPrimary;};
    
    /**
     * Setter method for a player's health.
     * @param health health for this player
     */
    public void setHealth(int health)
    {
        this.health = health;
    }
    
    /**
     * Getter method for this player's health.
     * @return the player's health.
     */
    public int getHealth() {return this.health;}
    
    /**
     * Setter method for the position of this player object.
     * @param position the new position.
     */
    public void setPosition(String position){
        this.position = position;
    }
    
    /**
     * Getter method for the position of this player object.
     * @return the position of this player.
     */
    public String getPosition() {
        return this.position;
    }
    
    /**
     * Getter method for whether this player has ranged attack or not.
     * @return true if can attack from range.  Otherwise return false.
     */
    public boolean hasRangedAttack() {
        return this.hasRangedAttack;
    }
   
    private String name;
    private TeamID teamID;
    private int health;
    private boolean isPrimary;
    private boolean hasRangedAttack;
    private String position;
    private GameMap gameMap;
};
