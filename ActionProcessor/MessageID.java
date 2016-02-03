/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActionProcessor;

/**
 * The messageID is returned by the logic set.  The GUI decides what to do
 * with it.
 * @author Jmacvey
 */
public enum MessageID
{
// primary message ID
    ATTACK,
    DEFEND,
    MOVE,
    MOVE_ON_CARD,
    DRAW,
    DRAW_ON_CARD,
    DISCARD,
    // everyone discard their hand and draw
    ALL_DISCARD_ALL_DRAW,
    AUTO_DMG, // automatic damage to subject
    // heals the subject
    HEAL,
    // peeks at another players cards
    PEEK,
    // lifts a player
    LIFT,
    // 
    INSIGHT,
    // pushing
    PUSH,
    
// secondary message IDs
    ALL_ON_TEAM,
    ALL_ON_BOARD,
    ANY_IN_DISCARD,
    SHUFFLE,
    RANDOM, 
    CHOICE,
    NULL, REBOUND,
    
    FINISH
}
