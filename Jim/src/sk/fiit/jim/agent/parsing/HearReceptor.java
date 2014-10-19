package sk.fiit.jim.agent.parsing;

/**
 *  HearReceptor.java
 *  
 *@Title	Jim
 *@author	Androids
 */
public class HearReceptor {
	public Boolean isMessageForMe;
	public double time;
	public double destinationRelativeAngle;
	public String message;
	
	@Override
	public String toString()
	{
	    return "Hear receptor"
	            + "\n is mesage for me " + isMessageForMe
	            + "\n destinationRelativeAngle " + destinationRelativeAngle
	    + "\n time " + time
	    + "\n is mesage " + message;
	}
}
