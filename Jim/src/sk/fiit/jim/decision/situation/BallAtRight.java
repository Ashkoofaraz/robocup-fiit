package sk.fiit.jim.decision.situation;

import sk.fiit.jim.decision.factory.SituationFactory;
import sk.fiit.jim.decision.situation.octan.BallIn1R;
import sk.fiit.jim.decision.situation.octan.BallIn2R;
import sk.fiit.jim.decision.situation.octan.BallIn3R;
import sk.fiit.jim.decision.situation.octan.BallIn4R;
import sk.fiit.jim.decision.utils.DecisionUtils;

/**
 * @author Matej Badal <matejbadal@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class BallAtRight extends Situation {
    @Override
    public boolean checkSituation() {
        SituationFactory factory = DecisionUtils.getSituationFactory();
        if (factory.createInstance(BallIn1R.class.getName()).checkSituation()
                || factory.createInstance(BallIn2R.class.getName()).checkSituation()
                || factory.createInstance(BallIn3R.class.getName()).checkSituation()
                || factory.createInstance(BallIn4R.class.getName()).checkSituation()) {
            return true;
        }
        return false;
    }
}
