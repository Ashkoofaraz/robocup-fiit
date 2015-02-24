package sk.fiit.jim.decision;

import sk.fiit.jim.agent.parsing.ParsedData;
import sk.fiit.jim.agent.parsing.ParsedDataObserver;


/**
 * Observer for selector, that controls and replans strategies and tactics each time server
 * sends message to agent
 *
 * @author  Vlado Bosiak <vladimir.bosiak@gmail.com>, Matej Badal <matejbadal@gmail.com>
 * @year	2013/2014
 * @team	RFC Megatroll
 * @version 1.0.0
 */
public class SelectorObserver implements ParsedDataObserver {

    public static final int SLEEP_PERIOD = 5;

    public static final int STATE_SLEEP = 0;
    public static final int STATE_RUN = 1;

    
	private SelectorController controller = new SelectorController();

    private static SelectorObserver instance = new SelectorObserver();

    private int counter = 1;

    /* testing purpose only */
    private int state = -1;
    
    public void setController(SelectorController controller) {
    	this.controller = controller;
    }
    
    public SelectorController getController() {
    	return this.controller;
    }

    /**
     * get SelectorObserver Instance
     *
     * @return instance of SelectorObserver
     */
    public static SelectorObserver getInstance() {
        return SelectorObserver.instance;
    }

    /**
     * New massage when server notify observer. 
     *
     */
    @Override
    public void processNewServerMessage(ParsedData data) {
        if (this.counter != SelectorObserver.SLEEP_PERIOD) {
            this.state = SelectorObserver.STATE_SLEEP;
            this.counter++;
            return;
        }

        this.counter = 1;
        this.state = SelectorObserver.STATE_RUN;

        try {
        	controller.controlStrategy();
        	controller.controlTactics();
        } catch (Exception e) {
            System.err.println("ERROR IN PROCESSING NEW SERVER MESSAGE.");
        }
    }

    public int getState() {
        return this.state;
    }
}
