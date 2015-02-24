package sk.fiit.jim.decision.situation;

import java.util.ArrayList;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

/**
 * Team RFC Megatroll Class for List of situation which exist
 * 
 * @author michal petras
 * 
 */
public class SituationList {

	private static final String SITUATION_ROOT_PACKAGE = "sk.fiit.jim.decision.situation";
	static ArrayList<Situation> situations = new ArrayList<Situation>();
	
	public static ArrayList<Situation> getSituations() {
        if (situations.isEmpty()) {
            initializeAllSituations();
        }
		return situations;
	}
	
	public static void initializeAllSituations() {

		Reflections reflections = new Reflections(SITUATION_ROOT_PACKAGE, new SubTypesScanner());
		Set<String> subTypes = reflections.getStore().getSubTypesOf(Situation.class.getName());

		for (String situationString : subTypes) {
			Situation situationClass = null;
			try {
				situationClass = (Situation) Class.forName(situationString).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (situationClass != null) {
				situations.add(situationClass);
			}
		}
	}
}