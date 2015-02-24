package sk.fiit.jim.agent.highskill.runner;

import sk.fiit.jim.Settings;
import sk.fiit.jim.agent.models.EnvironmentModel;
import sk.fiit.jim.log.Log;
import sk.fiit.jim.log.LogType;

/**
 * 
 *  GarbageCollecting.java
 *  
 *  This class is responsible for explicit garbage collecting between each incoming message. This process
 *  is started if there is enough time for collecting. This specific time  is represented by constant <b>ENOUGH_TIME</b>.
 *  
 *@Title        Jim
 *@author       Markech
 *@author  		Gitmen
 */
public class GarbageCollecting {
	private static final double ENOUGH_TIME_FOR_PHASE = 200.0;
	private static final int ENOUGH_TIME = 5*1000*1000; //5 millisecond = 5,000,000 nanoseconds	
	public static double nextTimePhaseEnds;
	public static long loopStart;
	
	/**
	 * Method which runs garbage collecting only if there is enough time between incoming messages. 
	 */
	public static void runGarbageCollectIfEnoughTime(){
		if (!Settings.getBoolean("runGcOnPhaseStart"))
			return;
		long now = System.nanoTime();
		double serverTime = EnvironmentModel.SIMULATION_TIME;
		Log.debug(LogType.PLANNING, "Loop took %d ms", (now - loopStart));
		if ((now - loopStart) < ENOUGH_TIME && (nextTimePhaseEnds - serverTime) > ENOUGH_TIME_FOR_PHASE){
			Log.debug(LogType.PLANNING, "We have enough time, running GC and finalization");
			runGc();
		}
	}

	/**
	 * Method for calling system garbage collecting.
	 */
	private static void runGc(){
		new Thread(new Runnable(){
			public void run(){
				System.runFinalization();
				System.gc();
			}
		}).start();
	}
}
