package sk.fiit.jim.decision.factory;

import sk.fiit.jim.decision.strategy.Strategy;

/**
 * Factory class for strategies
 * 
 * @author Matej Badal <matejbadal@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 * @version 1.0.0
 * 
 */
public final class StrategyFactory implements IFactory {
	public Strategy createInstance(String strategyName) {
		Strategy strategy = null;
		try {
			strategy = (Strategy) Class.forName(strategyName).newInstance();
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
		return strategy;
	}
}
