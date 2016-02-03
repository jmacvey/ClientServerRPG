/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActionProcessor;

import Maps.GameMap;
import Team.Team;
import framework.Action;
import framework.ActionID;

/**
 *
 * @author Jmacvey
 */
public class YodaLogicSet extends LogicSet
{

    public YodaLogicSet(Team team, GameMap map)
    {
        super(team, map);
    }

    @Override
    public ActionPackage processAction(
            ActionPackage ap,
            //            TeamID actorTeamID, Action actorAction,
            //            String actor, TeamID subjectTeamID, String subject,
            String position, boolean isAttack)
    {
        if (ap != null)
        {
            Action action = ap.getAction();
            if (action != null)
            {
                switch (action.getActionID())
                {
                    case DRAW:
                        return drawCard(false);
                    case MOVE:
                        return movePlayer(action, ap.getActorID(), ap.actor(), position);
                    case SERENITY:
                        return serenity(ap);
                    case FORCE_REBOUND:
                        return forceRebound(ap);
                    case FORCE_LIFT:
                        return forceLift(ap);
                    case FORCE_PUSH_YODA:
                        return forcePushYoda(ap, position);
                    case INSIGHT:
                        return insight(ap);
                    case FORCE_STRIKE:
                        return forceStrike(ap);
                    case NORMAL:
                        return attackDefend(ap, action, isAttack);
                    default:
                        return null;
                }
            }
        }
        return null;
    }

    private ActionPackage serenity(ActionPackage ap)
    {
        // defend first then draw
        return new ActionPackage(ap.getAction(), ap.getActorID(), ap.actor(),
                ap.getSubjectID(), ap.subject(), MessageID.DEFEND, 15,
                MessageID.DRAW, 1);
    }

    private ActionPackage forceRebound(ActionPackage ap)
    {
        // rebound all of th attack
        return new ActionPackage(ap.getAction(), ap.getActorID(), ap.actor(),
                ap.getSubjectID(), ap.subject(), MessageID.DEFEND, 100,
                MessageID.REBOUND, 0);
    }

    private ActionPackage forceLift(ActionPackage ap)
    {
        return null;
    }

    private ActionPackage forcePushYoda(ActionPackage ap, String pos)
    {
        // give the character automatic dmg and then push them anywhere
        return new ActionPackage(ap.getAction(), ap.getActorID(), ap.actor(),
                ap.getSubjectID(), ap.subject(), MessageID.AUTO_DMG, 3, MessageID.PUSH, 0);
    }

    private ActionPackage insight(ActionPackage ap)
    {
        // peek at the subject's hand and discard one.
        return new ActionPackage(ap.getAction(), ap.getActorID(), ap.actor(),
                ap.getSubjectID(), ap.subject(), MessageID.PEEK, 10, MessageID.DISCARD, 1);
    }

    private ActionPackage forceStrike(ActionPackage ap)
    {
        // attack then draw
       return new ActionPackage(ap.getAction(), ap.getActorID(), ap.actor(),
            ap.getSubjectID(), ap.subject(), MessageID.ATTACK, 6, 
               MessageID.DRAW_ON_CARD, 1);
    }
}
