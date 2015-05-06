package sk.fiit.jim.decision.tactic;

import java.util.ArrayList;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import sk.fiit.jim.decision.utils.DecisionUtils;

public class TacticList
{

    private static final String TACTIC_ROOT_PACKAGE = "sk.fiit.jim.decision.tactic";

    static ArrayList<Tactic> tactics = new ArrayList<Tactic>();

    public static ArrayList<Tactic> getTactics()
    {
        if(tactics.isEmpty())
        {
            initializeAllTactics();
        }
        return tactics;
    }

    public static void initializeAllTactics()
    {

        Reflections reflections = new Reflections(TACTIC_ROOT_PACKAGE, new SubTypesScanner());
        Set<String> subTypes = reflections.getStore().getSubTypesOf(Tactic.class.getName());

        for (String tacticString : subTypes)
        {
            Tactic tacticClass = null;
            tacticClass = DecisionUtils.getTacticsFactory().createInstance(tacticString);
            if(tacticClass != null)
            {
                tactics.add(tacticClass);
            }
        }
    }
}
