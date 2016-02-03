/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

/**
 * This abstract class represents a modified action. All inheriting classes must
 * implement the performAction method, which serves to say what this action does.
 * @author Jmacvey
 */
public abstract class ModifiedAction extends ADAction
{
    /**
     * Contructor
     * @param actor the actor to whom this action belongs
     * @param actionID the actionID associated with this action
     * @param attackValue the attack Value of this action
     * @param defendValue the defend value of this action
     */
    public ModifiedAction(String actor, ActionID actionID, 
            int attackValue, int defendValue)
    {
        super(actor, actionID, attackValue, defendValue);
        this.setIsModified(true);
        this.isModifiedAttack = attackValue > 0;
        this.isModifiedDefense = defendValue > 0;
        this.actionIDString = "";
        String[] s = actionID.toString().split("_");
        for (String item : s)
        {
            actionIDString += item.substring(0, 1) + 
                    item.substring(1, item.length()).toLowerCase() + " ";
        }
    }
    
    /**
     * Getter method for checking if this card is a modified defense card
     * @return true if modified defense, false otherwise.
     */
    public boolean isModifiedDefense() {
        return isModifiedDefense;
    }
    
    /**
     * Getter method for checking if this card is a modified defense card
     * @return true if modified defense, false otherwise.
     */
    public boolean isModifiedAttack() {
        return isModifiedAttack;
    }
    
    public String toString() {
        return actionIDString + "\n" + super.toString();
    }
    
    private String actionIDString;
    private boolean isModifiedDefense;
    private boolean isModifiedAttack;
}
