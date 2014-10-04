package sk.fiit.jim.init;

import java.io.File;

import sk.fiit.jim.Settings;
import sk.fiit.jim.agent.communication.Communication;
import sk.fiit.jim.agent.server.TFTPServer;
import sk.fiit.jim.agent.server.TFTPServer.ServerMode;
import sk.fiit.jim.annotation.data.AnnotationManager;
import sk.fiit.jim.gui.ReplanWindow;
import sk.fiit.robocup.library.geometry.Vector3D;
import static java.lang.Math.*;

/**
 *  Main.java
 *  
 *  Entry point of the program. Launching this class will glue all the components
 *  together, connect to the server and play.
 *
 *@Title	Jim
 *@author	marosurbanec
 */
public class Main{
	
	public static void main(String[] args) throws Exception{
		new SkillsFromXmlLoader(new File("./moves")).load();
		AnnotationManager.getInstance().loadAnnotations("./moves/annotations");
		
		Settings.parseCommandLine(args);
		Settings.setCommandLineOverrides();
		
		if (Settings.getBoolean("Tftp_enable")) {
			int tftpPort = Settings.getInt("Tftp_port");
			new Thread(new TFTPServer(
					new File("./scripts/testframework"),
					new File("./scripts/testframework"),
					tftpPort,
					ServerMode.PUT_ONLY,
					System.out,
					System.err)).start();
		}
		
		if (Settings.getBoolean("runGui"))
			new ReplanWindow().makeVisible();
		
		ScriptBoot.boot();
		//dependency injection is set in dependencies.rb => no need to do them manually
		System.runFinalization();
		System.gc();
		Communication.getInstance().start();
	}
}