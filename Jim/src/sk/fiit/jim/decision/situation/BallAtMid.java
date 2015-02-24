package sk.fiit.jim.decision.situation;

import sk.fiit.jim.decision.factory.SituationFactory;
import sk.fiit.jim.decision.situation.octan.BallInMid1;
import sk.fiit.jim.decision.situation.octan.BallInMid2;
import sk.fiit.jim.decision.situation.octan.BallInMid3;
import sk.fiit.jim.decision.situation.octan.BallInMid4;
import sk.fiit.jim.decision.utils.DecisionUtils;

/**
 * @author Samuel Benkovic <sppred@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */

public class BallAtMid extends Situation {
	 @Override
	    public boolean checkSituation() {
	        SituationFactory factory = DecisionUtils.getSituationFactory();
	        if (factory.createInstance(BallInMid1.class.getName()).checkSituation()
	                || factory.createInstance(BallInMid2.class.getName()).checkSituation()
	                || factory.createInstance(BallInMid3.class.getName()).checkSituation()
	                || factory.createInstance(BallInMid4.class.getName()).checkSituation()) {
	            return true;
	        }
	        return false;
	    }
}
