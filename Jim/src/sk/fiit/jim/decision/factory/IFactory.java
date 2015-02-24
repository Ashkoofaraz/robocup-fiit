package sk.fiit.jim.decision.factory;

/**
 * Interface for factory
 *
 * @author 	Matej Badal <matejbadal@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 * @version 1.0.0
 */
public interface IFactory {
    public Object createInstance(String className)  throws  ClassNotFoundException, IllegalAccessException, InstantiationException;
}
