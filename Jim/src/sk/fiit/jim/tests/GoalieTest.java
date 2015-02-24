package sk.fiit.jim.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import sk.fiit.jim.decision.situation.octan.MeIn1L;
import sk.fiit.jim.decision.situation.octan.MeIn2R;
import sk.fiit.jim.decision.tactic.Goalie;

/**
 *
 * @author roman
 */
public class GoalieTest {
    
    /**
     * Test of initCondition method, of class Goalie.
     */
    @Test
    public void testInitCondition() {
        System.out.println("initCondition");
        List<String> currentSituations = new ArrayList<String>(Arrays.asList(MeIn2R.class.getName()));
        Goalie instance = new Goalie();
        boolean expResult = false;
        boolean result = instance.getInitCondition(currentSituations);
        assertEquals(expResult, result);
        
        currentSituations.clear();
        currentSituations.add(MeIn1L.class.getName());
        expResult = true;
        result = instance.getInitCondition(currentSituations);
        assertEquals(expResult, result);
    }
}