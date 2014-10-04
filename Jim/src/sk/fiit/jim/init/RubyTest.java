package sk.fiit.jim.init;

import sk.fiit.robocup.library.init.Script;

/** 
 *  RubyTest.java
 *  
 *@Title	Jim
 *@author	Androids
 */
public class RubyTest {
	/**
	 * TODO: Replace with purpose of method. Start with verb. 
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("require \"java\"\n");
		sb.append("include_class \"sk.fiit.jim.init.ScriptBoot\"\n");
		sb.append("ScriptBoot.boot");
		
		Script script = Script.createScript(sb.toString());
		script.execute();
	}
}