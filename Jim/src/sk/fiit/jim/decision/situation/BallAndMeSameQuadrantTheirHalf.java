package sk.fiit.jim.decision.situation;

import sk.fiit.jim.decision.factory.SituationFactory;
import sk.fiit.jim.decision.situation.octan.BallIn1L;
import sk.fiit.jim.decision.situation.octan.BallIn1R;
import sk.fiit.jim.decision.situation.octan.BallIn2L;
import sk.fiit.jim.decision.situation.octan.BallIn2R;
import sk.fiit.jim.decision.situation.octan.BallIn3L;
import sk.fiit.jim.decision.situation.octan.BallIn3R;
import sk.fiit.jim.decision.situation.octan.BallIn4L;
import sk.fiit.jim.decision.situation.octan.BallIn4R;
import sk.fiit.jim.decision.situation.octan.BallInMid1;
import sk.fiit.jim.decision.situation.octan.BallInMid2;
import sk.fiit.jim.decision.situation.octan.BallInMid3;
import sk.fiit.jim.decision.situation.octan.BallInMid4;
import sk.fiit.jim.decision.situation.octan.MeIn3L;
import sk.fiit.jim.decision.situation.octan.MeIn3R;
import sk.fiit.jim.decision.situation.octan.MeIn4L;
import sk.fiit.jim.decision.situation.octan.MeIn4R;
import sk.fiit.jim.decision.situation.octan.MeInMid3;
import sk.fiit.jim.decision.situation.octan.MeInMid4;
import sk.fiit.jim.decision.tactic.defense.DefendPosition;
import sk.fiit.jim.decision.utils.DecisionUtils;

public class BallAndMeSameQuadrantTheirHalf extends Situation {
	 @Override
	    public boolean checkSituation() {
	        SituationFactory factory = DecisionUtils.getSituationFactory();

	        if (factory.createInstance(BallInMid3.class.getName()).checkSituation()
	                && factory.createInstance(MeInMid3.class.getName()).checkSituation()) {
				return true;
			}else if (factory.createInstance(BallIn3L.class.getName()).checkSituation()
	                && factory.createInstance(MeIn3L.class.getName()).checkSituation()) {
				return true;
			}else if (factory.createInstance(BallIn3R.class.getName()).checkSituation()
	                && factory.createInstance(MeIn3R.class.getName()).checkSituation()) {
				return true;
			}else if (factory.createInstance(BallInMid4.class.getName()).checkSituation()
	                && factory.createInstance(MeInMid4.class.getName()).checkSituation()) {
				return true;
			}else if (factory.createInstance(BallIn4L.class.getName()).checkSituation()
	                && factory.createInstance(MeIn4L.class.getName()).checkSituation()) {
				return true;
			}else if (factory.createInstance(BallIn4R.class.getName()).checkSituation()
	                && factory.createInstance(MeIn4R.class.getName()).checkSituation()) {
				return true;
			}else
				return false;
	    }

}
