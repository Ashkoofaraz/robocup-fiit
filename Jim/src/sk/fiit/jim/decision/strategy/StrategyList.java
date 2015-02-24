package sk.fiit.jim.decision.strategy;

import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import sk.fiit.jim.decision.utils.DecisionUtils;

/**
 * Team 04 - RFC Megatroll List of strategy which exist
 * 
 * @author michal petras TODO - better ADD?
 */
public class StrategyList {

	private static final String STRATEGY_ROOT_PACKAGE = "sk.fiit.jim.decision.strategy";

	private static ArrayList<Strategy> strategies = new ArrayList<Strategy>();

	public static ArrayList<Strategy> getStrategies() {
		if (strategies.isEmpty()) {
			initializeAllStrategies();
		}
		return strategies;
	}

	public static void initializeAllStrategies() {

		Reflections reflections = new Reflections(STRATEGY_ROOT_PACKAGE, new SubTypesScanner());
		Set<String> subTypes = reflections.getStore().getSubTypesOf(Strategy.class.getName());

		for (String strategyString : subTypes) {
			Strategy strategyClass = null;
			strategyClass = DecisionUtils.getStrategyFactory().createInstance(strategyString);
			if (strategyClass != null) {
				strategies.add(strategyClass);
			}
		}
	}
}
