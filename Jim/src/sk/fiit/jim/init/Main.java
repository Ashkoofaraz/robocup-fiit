package sk.fiit.jim.init;

import java.io.File;

import sk.fiit.jim.Settings;
import sk.fiit.jim.agent.communication.Communication;
import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.EnvironmentModel;
import sk.fiit.jim.agent.models.KalmanAdjuster;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.agent.models.prediction.Prophet;
import sk.fiit.jim.agent.parsing.Parser;
import sk.fiit.jim.agent.server.TFTPServer;
import sk.fiit.jim.agent.server.TFTPServer.ServerMode;
import sk.fiit.jim.annotation.data.AnnotationManager;
import sk.fiit.jim.decision.SelectorObserver;
import sk.fiit.jim.gui.ReplanWindow;
import sk.fiit.jim.log.Log;
import sk.fiit.jim.log.LogLevel;
import sk.fiit.jim.log.LogType;

/**
 * Main.java
 * <p/>
 * Entry point of the program. Launching this class will glue all the components
 * together, connect to the server and play.
 *
 * @Title Jim
 * @author marosurbanec
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Settings.parseCommandLine(args);
        Settings.setCommandLineOverrides();

        new SkillsFromXmlLoader(new File("." + Settings.getString("Jim_root_path") + "/moves")).load();
        AnnotationManager.getInstance().loadLowSkills("." + Settings.getString("Jim_root_path") + "/moves");
        AnnotationManager.getInstance()
                         .loadAnnotations("." + Settings.getString("Jim_root_path") + "/moves/annotations");

        if (Settings.getBoolean("Tftp_enable")) {
            int tftpPort = Settings.getInt("Tftp_port");
            new Thread(new TFTPServer(
                    new File("." + Settings.getString("Jim_root_path") + "/scripts/testframework"),
                    new File("." + Settings.getString("Jim_root_path") + "/scripts/testframework"),
                    tftpPort,
                    ServerMode.PUT_ONLY,
                    System.out,
                    System.err)).start();
        }

        if (Settings.getBoolean("runGui")) {
            ReplanWindow.getInstance().makeVisible();
        }

        //load Observer initialization
        //these lines are rewrited from ruby's dependencies.rb

        //TOTO extract to some another file?
        Parser.clearObservers();

        //Environment model must go first - it holds global reference to simulation time
        Parser.subscribe(EnvironmentModel.getInstance());

        //register Kalman's adjuster as the second one! All other listeners will listen to already adjusted data
        Parser.subscribe(new KalmanAdjuster());

        //AgentModel comes as the third one - it needs to calculate rotations and positions, used later by WorldModel
        Parser.subscribe(AgentModel.getInstance());
        Parser.subscribe(WorldModel.getInstance());
        Parser.subscribe(Prophet.getInstance());
        Parser.subscribe(SelectorObserver.getInstance());


        //moved from log.rb
        //TODO refactor
        Log.setLoggable(LogType.INIT, true);
        Log.setLoggable(LogType.PLANNING, false);
        Log.setLoggable(LogType.AGENT_MODEL, false);
        Log.setLoggable(LogType.WORLD_MODEL, false);
        Log.setLoggable(LogType.INCOMING_MESSAGE, false);
        Log.setLoggable(LogType.OUTCOMING_MESSAGE, false);
        Log.setLoggable(LogType.INTERNALS, false);
        Log.setLoggable(LogType.LOW_SKILL, false);
        Log.setLoggable(LogType.HIGH_SKILL, false);
        Log.setLoggable(LogType.OTHER, true);

        Log.setOutput("CONSOLE");
        Log.setPattern(Settings.getString("Jim_root_path") + "/log.txt");
        Log.setLogLevel(LogLevel.LOG);

        Settings.initDecisionObjects();

        //dependency injection is set in dependencies.rb => no need to do them manually
        System.runFinalization();
        System.gc();
        Communication.getInstance().start();
    }
}