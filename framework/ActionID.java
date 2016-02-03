/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

/**
 *
 * @author Jmacvey
 */
public enum ActionID
{
    QUERY_ANSWER,
    QUERY_CONTEXT,
    // MOVE (does not result in action being used).
    MOVE,
    // Draw
    DRAW,
    // all normal attack and defend cards fall under this category
    NORMAL,
    
    //--------------------------------------------------------------------
    // LIGHT SIDE
    //--------------------------------------------------------------------
    
    // Obi-Wan
    JEDI_MIND_TRICK,
    JEDI_BLOCK,
    JEDI_ATTACK,
    FORCE_QUICKNESS,
    FORCE_BALANCE,
    FORCE_CONTROL,
    
    // Anakin
    WRATH,
    CALM,
    COUNTERATTACK,
    ANGER,
    
    // Padme
    SHOT_ON_THE_RUN,
    PROTECTION,
    PRECISE_SHOT,
    
    // Yoda
    SERENITY,
    FORCE_REBOUND,
    FORCE_LIFT,
    FORCE_PUSH_YODA,
    INSIGHT,
    FORCE_STRIKE,
    
    // Han
    HEROIC_RETREAT,
    GAMBLERS_LUCK,
    NEVER_TELL_ME_THE_ODDS,
    
    
    // Chewbacca
    WOOKIE_INSTINCTS,
    WOOKIE_HEALING,
    ITS_NOT_WISE,
    BOWCASTER_ATTACK,
    
    // Luke
    CHILDREN_OF_THE_FORCE,
    JUSTICE,
    I_WILL_NOT_FIGHT_YOU,
    
    // Leia
    LATENT_FORCE_ABILITIES,
    LUKES_IN_TROUBLE,
    
    // Mace Windu
    BATTLEMIND,
    WHIRLWIND_ATTACK,
    WISDOM,
    MASTERFUL_FIGHTING,
    
    //--------------------------------------------------------------------
    // DARK SIDE
    //--------------------------------------------------------------------
    
    // Jango and Boba
    ROCKET_RETREAT,
    WRIST_CABLE,
    
    // Jango
    FLAME_THROWER,
    FIRE_UP_THE_JET_PACK,
    MISSILE_LAUNCH,
    
    // Zam
    SNIPER_SHOT,
    ASSASSINATION,
    
    // Boba
    DEADLY_AIM,
    KYBER_DART,
    THERMAL_DETONATOR,
    
    // Greedo
    SUDDEN_ARRIVAL,
    DESPERATE_SHOT,
    
    // Palpatine
    LET_GO_OF_YOUR_HATRED,
    FORCE_LIGHTNING,
    YOU_WILL_DIE,
    MEDITATION,
    ROYAL_COMMAND,
    FUTURE_FORESEEN,
    
    // Dooku
    GAIN_POWER,
    FORCE_PUSH_DOOKU,
    GIVE_ORDERS,
    FORCE_DRAIN,
    TAUNTING,
    
    // Vader
    ALL_TOO_EASY,
    THROW_DEBRIS,
    CHOKE,
    WRATH_VADER,
    DARK_SIDE_DRAIN,
    YOUR_SKILLS_ARE_NOT_COMPLETE,
    
    // Maul
    MARTIAL_DEFENSE,
    SITH_SPEED,
    BLINDING_SURGE,
    ATHLETIC_SURGE,
    SUPER_SITH_SPEED
}
