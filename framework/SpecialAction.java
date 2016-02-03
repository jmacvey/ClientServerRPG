
package framework;

/**
 * This concrete class represents a special action.  A normal action ID is used,
 * but in addition there is a special flag that is set to differentiate it from
 * a normal action. 
 * 
 * All inheriting classes must
 * implement the performAction method, which serves to say what this action does.
 * @author Jmacvey
 */
public abstract class SpecialAction extends Action
{
    /**
     * Constructor
     * @param actor the actor to whom this action belongs.
     * @param actionID ID corresponding to this action
     */
    public SpecialAction(String actor, ActionID actionID)
    {
        super(actor, actionID, true, false);
    }
    
}
