/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

/**
 * This class extends action to an attack and defend value. The only action to
 * perform here is to either attack or defend. The game state will use event
 * signals and accessor methods to determine which value to use when this action
 * is played.
 *
 * @author Jmacvey
 */
public class ADAction extends Action
{

    /**
     * Constructor
     *
     * @param actor The actor associated with this action.
     * @param actionID The ID value associated with this action.
     * @param attackValue The attack value
     * @param defendValue the defend value
     */
    public ADAction(String actor, ActionID actionID, int attackValue, int defendValue)
    {
        super(actor, actionID, false, false);
        this.attackValue = attackValue;
        this.defendValue = defendValue;
    }

    @Override
    public String toString()
    {
        String s = "Actor: " + this.getActor() + "\n";
        s += this.getAttackValue() > 0 ? "Attack: "
                + Integer.toString(attackValue) +"\n" : "";
        s += this.getDefendValue() > 0 ? "Defense: "
                + Integer.toString(defendValue) + "\n" : "";
        return s;
    }

    /**
     * Gets the attack value for this action.
     *
     * @return the attack value of this action.
     */
    public int getAttackValue()
    {
        return attackValue;
    }

    /**
     * Gets the defense value for this action.
     *
     * @return the defend value of this action.
     */
    public int getDefendValue()
    {
        return defendValue;
    }
    ;
    
    private int attackValue;
    private int defendValue;
}
