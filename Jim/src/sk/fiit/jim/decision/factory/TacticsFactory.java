package sk.fiit.jim.decision.factory;


import sk.fiit.jim.decision.tactic.Tactic;

/**
 * Factory class for Tactics
 *
 * @author 	Matej Badal <matejbadal@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 * @version 1.0.0
 *
 */
public class TacticsFactory implements IFactory{
    public Tactic createInstance(String tacticName) {
        Tactic tactic = null;
        try {
			tactic = (Tactic)Class.forName(tacticName).newInstance();
		} catch (InstantiationException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
        return tactic;

    }
}
