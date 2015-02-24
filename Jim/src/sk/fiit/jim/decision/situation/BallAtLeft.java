package sk.fiit.jim.decision.situation;

import sk.fiit.jim.decision.factory.SituationFactory;
import sk.fiit.jim.decision.situation.octan.BallIn1L;
import sk.fiit.jim.decision.situation.octan.BallIn2L;
import sk.fiit.jim.decision.situation.octan.BallIn3L;
import sk.fiit.jim.decision.situation.octan.BallIn4L;
import sk.fiit.jim.decision.utils.DecisionUtils;

/**
 * @author Matej Badal <matejbadal@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class BallAtLeft extends Situation {
    @Override
    public boolean checkSituation() {
        SituationFactory factory = DecisionUtils.getSituationFactory();
        if (factory.createInstance(BallIn1L.class.getName()).checkSituation()
                || factory.createInstance(BallIn2L.class.getName()).checkSituation()
                || factory.createInstance(BallIn3L.class.getName()).checkSituation()
                || factory.createInstance(BallIn4L.class.getName()).checkSituation()) {
            return true;
        }
        return false;
    }
}
