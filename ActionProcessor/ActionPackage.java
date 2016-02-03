/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActionProcessor;

import Team.TeamID;
import framework.Action;
import java.io.Serializable;

/**
 *
 * @author Jmacvey
 */
public class ActionPackage implements Serializable
{

    /**
     *
     * @param action the action being performed
     * @param actorID the actor ID
     * @param actor the subject ID
     * @param subjectID the subject ID
     * @param subject the subject
     * @param aMessageID the actor's messageID
     * @param aMessageValue value associated with first message
     * @param asMessageID actor's secondary message
     * @param asMessageValue value associated with secondary message
     */
    public ActionPackage(
            Action action,
            TeamID actorID, String actor,
            TeamID subjectID, String subject,
            MessageID aMessageID, int aMessageValue,
            MessageID asMessageID, int asMessageValue
//            MessageID sMessageID, int sMessageValue,
//            MessageID ssMessageID, int ssMessageValue
            )
    {
        this.action = action;
        this.actorID = actorID;
        this.actor = actor;
        this.subjectID = subjectID;
        this.subject = subject;
        this.aMessageID = aMessageID;
        this.aMessageValue = aMessageValue;
        this.asMessageID = asMessageID;
        this.asMessageValue = asMessageValue;
//        this.sMessageID = sMessageID;
//        this.sMessageValue = sMessageValue;
//        this.ssMessageID = ssMessageID;
//        this.ssMessageValue = ssMessageValue;
    }


    public Action getAction()
    {
        return action;
    }
    
    public TeamID getActorID()
    {
        return actorID;
    }

    public String actor()
    {
        return actor;
    }

    public TeamID getSubjectID()
    {
        return subjectID;
    }

    public String subject()
    {
        return subject;
    }

    public MessageID getAMessageID()
    {
        return aMessageID;
    }

    public MessageID getASMessageID()
    {
        return asMessageID;
    }

//    public MessageID getSMessageID()
//    {
//        return sMessageID;
//    }
//
//    public MessageID getSSMessageID()
//    {
//        return ssMessageID;
//    }

    public int getAMessageValue()
    {
        return aMessageValue;
    }

    public int getASMessageValue()
    {
        return asMessageValue;
    }

//    public int getSMessageValue()
//    {
//        return sMessageValue;
//    }
//
//    public int getSSMessageValue()
//    {
//        return ssMessageValue;
//    }

    private Action action = null;
    private TeamID actorID = null;
    private String actor = null;
    private TeamID subjectID = null;
    private String subject = null;
    private MessageID aMessageID = null;
    private MessageID asMessageID = null;
//    private MessageID sMessageID = null;
//    private MessageID ssMessageID = null;
    private int aMessageValue = 0;
    private int asMessageValue = 0;
//    private int sMessageValue = 0;
//    private int ssMessageValue = 0;

}
