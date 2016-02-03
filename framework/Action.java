
package framework;

import java.io.Serializable;

/**
 * This abstract class serves as the base for all actions in the game. All actions
 * must be associated with an actor, an actionID, and flags to say whether or not
 * the action is either (1) modified, or (2) a special.
 * @author Jmacvey
 */
public abstract class Action implements Serializable
{
    
    /**
     * Constructor that takes the name of the actor associated with this action as
     * well as the ID referencing this action.
     * @param actor the actor defining this id
     * @param actionID the ID associated with this action
     * @param isSpecial the special flag.
     * @param isModified the modified flag
     */
    public Action(String actor, ActionID actionID, boolean isSpecial, boolean isModified) {
        this.actionID = actionID;
        this.actor = actor;
        this.isSpecial = isSpecial;
        this.isModified = isModified;
        actionIDString = "";
        String[] s = actionID.toString().split("_");
        for (String item : s)
        {
            actionIDString += item.substring(0, 1) + item.substring(1, item.length()).toLowerCase()
                    + " ";
        }
    }
    
    /**
     * Sets the modified action flag.
     * @param isModified boolean value of the new flag.
     */
    public void setIsModified(boolean isModified) {
        this.isModified = true;
    }
    /**
     * Gets the ID associated with this action.
     * @return the action ID
     */
    public ActionID getActionID() { return this.actionID;};
    
    /**
     * Gets the actor associated with this action.
     * @return the string representing the actor. 
     */
    public String getActor() { return this.actor; };
    
    /**
     * Gets the flag discerning whether or not this action is a special move.
     * @return true if action is special. False otherwise.
     */
    public boolean isSpecial() { return this.isSpecial; };
    
    /**
     * Gets the flag discerning whether or not this action is a modified action
     * @return true if this is a modified action, false otherwise.
     */
    public boolean isModified() { return this.isModified; };
    
    public String toString() {
        return actionIDString + "\nActor: " + actor;
    }
    
    private String actionIDString;
    private ActionID actionID;
    private String actor;
    private boolean isSpecial;
    private boolean isModified;
};
