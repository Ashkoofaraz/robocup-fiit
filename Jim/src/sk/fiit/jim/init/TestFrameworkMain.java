package sk.fiit.jim.init;

import java.io.File;
import java.util.logging.Logger;

import sk.fiit.jim.Settings;
import sk.fiit.jim.agent.AgentInfo;
import sk.fiit.jim.agent.communication.Communication;
import sk.fiit.jim.agent.server.TFTPServer;
import sk.fiit.jim.agent.server.TFTPServer.ServerMode;
import sk.fiit.jim.annotation.data.AnnotationManager;
import sk.fiit.jim.garbage.code_review.UnderConstruction;
import sk.fiit.jim.gui.OldReplanWindow;

/**
 * TestFrameworkMain.java
 * 
 * [-testframework host port] [-uniform number] [-team team_name] [-tftp [port]]
 * 
 * Possible arguments
 * -testframework host port - zapnutie odosielania spatnej vazby na adresu host:port
 * -test [port]				- zapnutie lokalneho tftp servera - mozne dodatocne urcenie portu (default 3070)
 * -uniform number			- nastavenie atributov agenta na pripojenie k serveru
 * -team team_name			- nastavenie atributov agenta na pripojenie k serveru
 * 
 * @Title	Jim
 * @author	Androids
 */
@UnderConstruction(todo = { "wrap communication into thread to achieve restart functionality" })
public class TestFrameworkMain {

	private static Logger logger = Logger.getLogger(TestFrameworkMain.class.getName());

	/**
	 * <code>TestFrameworkMain</code> is entry point which is called by Test
	 * Framework. This is especially in need when using machine learning for
	 * specifying
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {		
		
		// LOAD ALL (INCLUDING DEFAULT SETTINGS)
		new SkillsFromXmlLoader(new File("./moves")).load();
		AnnotationManager.getInstance().loadAnnotations("./moves/annotations");

		Settings.parseCommandLine(args);
		Settings.setCommandLineOverrides();
		
		if (Settings.getBoolean("runGui"))
			new OldReplanWindow().makeVisible();
		
		
		// PARSE ARGUMENTS
		
		int argument_pointer = 0;
		
		while (argument_pointer < args.length) {
			String arg = args[argument_pointer++];

			if (arg.equals("-tftp")) {
				Settings.setValue("Tftp_enable", true);

				try {
					String next = args[argument_pointer];
					if (!next.startsWith("-")) {
						int port = Integer.parseInt(next);
						Settings.setValue("Tftp_port", port);
						argument_pointer++;
					}
				} catch (NumberFormatException e) {

				} catch (ArrayIndexOutOfBoundsException e) {

				}

			} else if (arg.equals("-uniform")) {
				try {
					String next = args[argument_pointer++];
					int uniformNumber = Integer.parseInt(next);
					AgentInfo.playerId = uniformNumber;
				} catch (NumberFormatException e) {
					logger.severe("Invalid argument - expected uniform number");
					return;
				} catch (ArrayIndexOutOfBoundsException e) {
					logger.severe("Invalid argument - expected uniform number");
				}

			} else if (arg.equals("-team")) {
				try {
					String next = args[argument_pointer++];
					AgentInfo.team = next;
				} catch (NumberFormatException e) {
					logger.severe("Invalid argument - expected uniform number");
					return;
				} catch (ArrayIndexOutOfBoundsException e) {
					logger.severe("Invalid argument - expected uniform number");
				}

			} else if (arg.equals("-testframework")) {
				Settings.setValue("TestFramework_monitor_enable", true);
				try {
					String next = args[argument_pointer++];
					Settings.setValue("TestFramework_monitor_address", next);

				} catch (ArrayIndexOutOfBoundsException e) {
					logger.severe("Invalid argument - expected testframework port");
				}

				try {
					String next = args[argument_pointer++];
					int port = Integer.parseInt(next);
					Settings.setValue("TestFramework_monitor_port", port);
				} catch (NumberFormatException e) {
					logger.severe("Invalid argument - expected testframework port");
					return;
				} catch (ArrayIndexOutOfBoundsException e) {
					logger.severe("Invalid argument - expected testframework port");
				}

			}

		}

		// RUN AGENT

		// Run TFTP server
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
		// dependency injection is set in dependencies.rb => no need to do them
		// manually
		System.runFinalization();
		System.gc();

		Communication.getInstance().start();
	}
}