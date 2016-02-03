package ActionProcessor;

import Maps.GameMap;
import Team.Team;
import Team.TeamID;
import framework.ADAction;
import framework.Action;

/**
 * This class implements the logic set for teamObiWan
 *
 * @author Jmacvey
 */
public class ObiWanLogicSet extends LogicSet
{

    public ObiWanLogicSet(Team team, GameMap map)
    {
        super(team, map);
    }

    @Override
    public ActionPackage processAction(ActionPackage ap, String position, boolean isAttack
    //            TeamID actorTeamID,
    //            Action actorAction, String actor, TeamID subjectTeamID,
    //            String subject, String position, boolean isAttack
    )
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
                    case FORCE_QUICKNESS:
                        return forceQuickness(ap, position);
                    case JEDI_BLOCK:
                        return jediBlock(ap.getActorID(), ap.actor(), action);
                    case FORCE_CONTROL:
                        return forceControl(ap.getActorID(), ap.actor(),
                                ap.getSubjectID(), ap.subject(),
                                action);
                    case JEDI_MIND_TRICK:
                        return jediMindTrick(ap.getActorID(), ap.actor(), action);
                    case FORCE_BALANCE:
                        return forceBalance(ap.getActorID(), ap.actor(), action);
                    case JEDI_ATTACK:
                        return jediAttack(ap.getActorID(), ap.actor(),
                                ap.getSubjectID(), ap.subject(),
                                action);
                    case NORMAL:
                        return attackDefend(ap, action, isAttack);
                    default:
                        return null;
                }
            }
        }
        return null;
    }

    private ActionPackage forceQuickness(ActionPackage ap, String pos)
    {
        // move and draw
        return new ActionPackage(ap.getAction(), ap.getActorID(),
                ap.actor(), null, null, MessageID.MOVE_ON_CARD, 8, 
                MessageID.DRAW_ON_CARD, 1);
    }

    private ActionPackage jediBlock(TeamID actorTeamID, String actor,
            Action action)
    {
        ADAction a = (ADAction) action;
        return new ActionPackage(action, actorTeamID, actor, null, null,
                MessageID.DEFEND, a.getDefendValue(), MessageID.DRAW_ON_CARD, 1);
    }

    private ActionPackage forceControl(TeamID actorTeamID, String actor,
            TeamID subjectTeamID, String subject, Action action)
    {
        ADAction a = (ADAction) action;
        return new ActionPackage(action, actorTeamID, actor, subjectTeamID, subject,
                MessageID.ATTACK, a.getAttackValue(), MessageID.ALL_ON_BOARD, 3);
    }

    private ActionPackage jediAttack(TeamID actorTeamID, String actor, TeamID subjectTeamID,
            String subject, Action action)
    {
        ADAction a = (ADAction) action;
        return new ActionPackage(action, actorTeamID, actor, subjectTeamID, subject,
                MessageID.ATTACK, a.getAttackValue(), MessageID.MOVE_ON_CARD, 6);
    }

    private ActionPackage jediMindTrick(TeamID actorTeamID, String actor, Action action)
    {
        return new ActionPackage(action, actorTeamID, actor, null, null,
                MessageID.DRAW, 1, MessageID.ANY_IN_DISCARD, 0);
    }

    private ActionPackage forceBalance(TeamID actorTeamID, String actor, Action action)
    {
        return new ActionPackage(action, actorTeamID, actor, null, null,
                MessageID.ALL_DISCARD_ALL_DRAW, 3, null, 0);
    }

}
