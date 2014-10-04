package sk.fiit.jim;

import java.util.HashMap;
import java.util.Map;

import sk.fiit.robocup.library.review.ReviewOk;

/**
 *  Settings.java
 *  
 *  Encapsulates global settings that alter the behaviour of the code throughout
 *  the entire project. Can change default settings for the game. Usually is
 *  meant to be set in ./scripts/config/settings.rb.
 *
 *@Title	Jim
 *@author	marosurbanec
 *@author	Androids
 */
@ReviewOk
public final class Settings{

	private Settings(){}

	private static Map<String, Object> settings = new HashMap<String, Object>();
	
	/* indicates which settings were overridden by command line arguments
	 * has to be done this way because the ruby script is called very indirectly
	 * with little control over it
	 */
	private static Map<String, Object> override = new HashMap<String, Object>();

	static{
		setDefaults();
	}

	/*
	 * Here are the default settings for the game. If not set differently in
	 * ./scripts/config/settings.rb, they will be read from here.
	 */
	private static void setDefaults(){
		settings.put("kalmanUseFilter", true);
		settings.put("kalmanDefaultQ", .475);
		settings.put("kalmanDefaultR", .375);
		settings.put("runGcOnPhaseStart", true);
		settings.put("runGui", true);
		settings.put("gravityAcceleration", 9.81);
		settings.put("maximumAngularChangePerQuantum", 7.0);
		settings.put("ignoreAccelerometer", false);
		settings.put("Tftp_enable", false);
	}
	
	/**
	 * Sets Object value of Map element with specified key
	 *
	 * @param key
	 * @param value
	 */	
	public static void setValue(String key, Object value){
		settings.put(key, value);
	}
	/**
	 * Sets int value of Map element with specified key 
	 *
	 * @param key
	 * @param value
	 */
	public static void setIntValue(String key, int value){
		settings.put(key, value);
	}
	
	/* stores any settings from the command line into the override map
	 * these settings can then be used by calling setCommandLineOverrides,
	 * which is meant to be called from the ruby script
	 */
	/**
	 * Stores any settings from the command line into the override map.
	 * These settings can then be used by calling setCommandLineOverrides,
	 * which is meant to be called from the ruby script
	 *
	 * @param args
	 */
	public static void parseCommandLine(String[] args) {
		int argPtr = 0;
		while (argPtr < args.length) {
			String key = args[argPtr++];
			
			if (key.startsWith("-")) {
				key = key.substring(1);
				String value;
				if (key.contains("=")) {
					value = key.substring(key.indexOf("=") + 1);
					key = key.substring(0, key.indexOf("="));
				} else {
					value = args[argPtr++];
				}
				if (value.startsWith("\"") || value.startsWith("'")) {
					value = value.substring(1, value.length()-1);
				}
				System.out.println("Setting key " + key + " = " + value);
				override.put(key, parseValue(value));
			}	
		}
	}
	
	//guesses the type of a value
	private static Object parseValue(String str) {
		if ("true".equals(str) || "false".equals(str)) {
			return Boolean.parseBoolean(str);
		}
		try {
			return Integer.parseInt(str);
		}catch (NumberFormatException e){
			return str;
		}
	}
	
	/* copies the values from the override map (command line arguments)
	 * into the main settings map
	 */
	/**
	 * Copies the values from the override map (command line arguments)
	 * into the main settings map. 
	 *
	 */
	public static void setCommandLineOverrides() {
		for (String key : override.keySet()) {
			settings.put(key, override.get(key));
		}
	}
	
	/**
	 * Checks if Collection contains element with specified key 
	 *
	 * @param key
	 * @return
	 */
	public static boolean hasKey(String key) {
		return (settings.get(key) != null);
	}
	
	/**
	 * Returns Double value of specified key 
	 *
	 * @param key
	 * @return
	 */
	public static double getDouble(String key){
		return (Double)get(key);
	}

	/**
	 * Returns boolean value of specified key 
	 *
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key){
		return (Boolean)get(key);
	}

	/**
	 * Returns String value of specified key 
	 *
	 * @param key
	 * @return
	 */
	public static String getString(String key){
		return (String)get(key);
	}

	/**
	 * Returns int value of specified key 
	 *
	 * @param key
	 * @return
	 */
	public static int getInt(String key){
		return (Integer)get(key);
	}

	private static Object get(String key){
		Object setting = settings.get(key);
		if (setting == null)
			throw new IllegalStateException("Setting: " + key + " not found");
		return setting;
	}
}