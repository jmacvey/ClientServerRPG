/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActionProcessor;

import Maps.GameMap;
import Team.Team;

/**
 * Creates the logic for each team.
 *
 * @author Jmacvey
 */
public interface LogicFactory
{

    static LogicSet createLogicSet(Team team, GameMap map)
    {
        switch (team.getTeamID())
        {
            case OBIWAN:
                return new ObiWanLogicSet(team, map);
            case YODA:
                return new YodaLogicSet(team, map);
            case ANAKIN:
                return new YodaLogicSet(team, map);
            case VADER:
                return new YodaLogicSet(team, map);
            default:
                return null;
        }
    }
}
