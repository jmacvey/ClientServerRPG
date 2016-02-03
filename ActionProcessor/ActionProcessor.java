/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActionProcessor;

import Team.TeamID;
import framework.Action;

/**
 * This interface provides functionality for processing actions.
 * @author Jmacvey
 */
public interface ActionProcessor
{

    /**
     * All classes must implement this function, which is responsible for the
     * processing of all actions.
     * @param actorTeamID the ID of the actor's team
     * @param actorAction the action performed by the actor.
     * @param actor the name of the actor.
     * @param subjectTeamID the ID of the subject's team
     * @param subject the name of the subject
     * @param position the position
     * @return the action package associated with this action.
     */
    public ActionPackage processAction(
            ActionPackage ap,
//            TeamID actorTeamID, Action actorAction,
//            String actor, TeamID subjectTeamID, String subject, 
            String position, boolean isAttack);
    
}
