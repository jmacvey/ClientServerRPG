/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maps;

/**
 *
 * @author Jmacvey
 */
public interface MapFactory
{
    static GameMap createMap(MapID mapID)
    {
        switch (mapID)
        {
            case KAMINO_PLATFORM:
                GameMap map =  new KaminoPlatform();
                map.setMapID(MapID.KAMINO_PLATFORM);
                return map;
        }
        return null;
    }
}
