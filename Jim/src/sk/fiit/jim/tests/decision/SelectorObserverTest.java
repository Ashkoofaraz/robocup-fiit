package sk.fiit.jim.tests.decision;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import sk.fiit.jim.decision.*;

public class SelectorObserverTest {

	//SelectorObserver selectorObserver = new SelectorObserver();

	@Test
	public void getInstanceTest() {
		assertTrue(SelectorObserver.getInstance() instanceof SelectorObserver);
	}

	@Test
	public void processNewServerMessageTest() {
        SelectorObserver selectorObserver = SelectorObserver.getInstance();
        try {
            int state = -1;
            for (int i = 0; i <= 4; i++) {
                state = -1;
                selectorObserver.processNewServerMessage(null);
                state = selectorObserver.getState();
                if (i < 4) {
                    assertTrue(state == SelectorObserver.STATE_SLEEP);
                } else {
                    assertTrue(state == SelectorObserver.STATE_RUN);
                }
            }
        } catch (Exception e) {
            assertEquals("ERROR IN PROCESSING NEW SERVER MESSAGE.", e.getMessage());
		}
	}

}
