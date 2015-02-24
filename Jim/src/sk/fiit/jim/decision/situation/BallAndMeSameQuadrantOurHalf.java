package sk.fiit.jim.decision.situation;

import sk.fiit.jim.decision.factory.SituationFactory;
import sk.fiit.jim.decision.situation.octan.BallIn1L;
import sk.fiit.jim.decision.situation.octan.BallIn1R;
import sk.fiit.jim.decision.situation.octan.BallIn2L;
import sk.fiit.jim.decision.situation.octan.BallIn2R;
import sk.fiit.jim.decision.situation.octan.BallInMid1;
import sk.fiit.jim.decision.situation.octan.BallInMid2;
import sk.fiit.jim.decision.situation.octan.MeIn1L;
import sk.fiit.jim.decision.situation.octan.MeIn1R;
import sk.fiit.jim.decision.situation.octan.MeIn2L;
import sk.fiit.jim.decision.situation.octan.MeIn2R;
import sk.fiit.jim.decision.situation.octan.MeInMid1;
import sk.fiit.jim.decision.situation.octan.MeInMid2;
import sk.fiit.jim.decision.utils.DecisionUtils;

public class BallAndMeSameQuadrantOurHalf extends Situation {
	 @Override
	    public boolean checkSituation() {
	        SituationFactory factory = DecisionUtils.getSituationFactory();
	        if (factory.createInstance(BallInMid1.class.getName()).checkSituation()
	                && factory.createInstance(MeInMid1.class.getName()).checkSituation()) {
				return true;
			}else if (factory.createInstance(BallIn1L.class.getName()).checkSituation()
	                && factory.createInstance(MeIn1L.class.getName()).checkSituation()) {
				return true;
			}else if (factory.createInstance(BallIn1R.class.getName()).checkSituation()
	                && factory.createInstance(MeIn1R.class.getName()).checkSituation()) {
				return true;
			}else if (factory.createInstance(BallInMid2.class.getName()).checkSituation()
	                && factory.createInstance(MeInMid2.class.getName()).checkSituation()) {
				return true;
			}else if (factory.createInstance(BallIn2L.class.getName()).checkSituation()
	                && factory.createInstance(MeIn2L.class.getName()).checkSituation()) {
				return true;
			}else if (factory.createInstance(BallIn2R.class.getName()).checkSituation()
	                && factory.createInstance(MeIn2R.class.getName()).checkSituation()) {
				return true;
			}else
				return false;
	    }
}
