package sk.fiit.jim.decision.factory;

import sk.fiit.jim.decision.situation.Situation;

/**
 * @author Matej Badal <matejbadal@gmail.com>
 * @year 2013/2014
 * @team RFC Megatroll
 */
public class SituationFactory implements IFactory {

    @Override
    public Situation createInstance(String className) {
        Situation situation = null;
        try {
            situation = (Situation) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
        return situation;
    }
}
