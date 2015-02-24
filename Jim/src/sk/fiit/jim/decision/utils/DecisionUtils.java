package sk.fiit.jim.decision.utils;

import sk.fiit.jim.decision.factory.SituationFactory;
import sk.fiit.jim.decision.factory.TacticsFactory;
import sk.fiit.jim.decision.factory.StrategyFactory;

/**
 * utils class that provides basic utils for package decision
 *
 * @author Matej Badal <matejbadal@gmail.com>
 * @version 1.0.0
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class DecisionUtils {
    private static TacticsFactory tacticsFactory = null;
    private static StrategyFactory strategyFactory = null;
    private static SituationFactory situationFactory = null;

    public static StrategyFactory getStrategyFactory() {
        if (strategyFactory == null) {
            strategyFactory = new StrategyFactory();
        }
        return strategyFactory;
    }

    public static TacticsFactory getTacticsFactory() {
        if (tacticsFactory == null) {
            tacticsFactory = new TacticsFactory();
        }
        return tacticsFactory;
    }

    public static SituationFactory getSituationFactory() {
        if (situationFactory == null) {
            situationFactory = new SituationFactory();
        }
        return situationFactory;
    }

}
