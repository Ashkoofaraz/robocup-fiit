package sk.fiit.testframework.ui;

import java.awt.BorderLayout;
import java.awt.CheckboxGroup;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import sk.fiit.robocup.library.geometry.Circle;
import sk.fiit.robocup.library.geometry.Point3D;
import sk.fiit.robocup.library.geometry.Vector2;
import sk.fiit.testframework.agenttrainer.AgentMoveReader;
import sk.fiit.testframework.agenttrainer.AgentMoveWriter;
import sk.fiit.testframework.agenttrainer.models.AgentMove;
import sk.fiit.testframework.annotator.Annotator;
import sk.fiit.testframework.communication.agent.AgentJim;
import sk.fiit.testframework.communication.agent.AgentManager;
import sk.fiit.testframework.communication.agent.IAgentManagerListener;
import sk.fiit.testframework.communication.robocupserver.RobocupServer;
import sk.fiit.testframework.communication.robocupserver.RobocupServerAddress;
import sk.fiit.testframework.ga.CrossoverType;
import sk.fiit.testframework.ga.GeneticAlgorithm;
import sk.fiit.testframework.ga.SelectionType;
import sk.fiit.testframework.ga.parameters.ParameterCrossover;
import sk.fiit.testframework.ga.parameters.ParameterGA;
import sk.fiit.testframework.ga.parameters.ParameterMutation;
import sk.fiit.testframework.ga.parameters.ParameterSelection;
import sk.fiit.testframework.ga.simulation.AlgorithmType;
import sk.fiit.testframework.ga.simulation.EnvironmentSetting;
import sk.fiit.testframework.ga.simulation.TestTypeParameter;
import sk.fiit.testframework.init.ImplementationFactory;
import sk.fiit.testframework.monitor.AgentMonitor;
import sk.fiit.testframework.monitor.AgentMonitorMessage;
import sk.fiit.testframework.monitor.IAgentMonitorListener;
import sk.fiit.testframework.monitor.RobocupMonitor;
import sk.fiit.testframework.parsing.models.PlayMode;
import sk.fiit.testframework.trainer.testsuite.TestCaseResult;
import sk.fiit.testframework.ui.TestCaseList.TestHolder;
import sk.fiit.testframework.worldrepresentation.ISimulationStateObserver;
import sk.fiit.testframework.worldrepresentation.models.SimulationState;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */

/**
 * <p>Main GUI window of the test framework.</p>
 * <p>Most of the elements are generated automatically by the Jigloo GUI builder in
 * the createGUI() method.</p>
 * <p>Note: Currently, most of the listeners are added manually in the registerGUIListeners()
 * method. This makes the code easier to navigate in my opinion, because they're all in
 * one place. It could be changed to add them automatically in the GUI editor, but the events should
 * not be handled inline (they should still just call a handler method in this class)</p>
 */
public class MainFrame extends JFrame implements ISimulationStateObserver, UserInterface, IAgentMonitorListener, IAgentManagerListener {
	private static final long serialVersionUID = -995645483680121615L;

	{
		// Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
					.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// nobody cares, it'll work anyway...
		}
	}
	
	private RobocupMonitor monitor;
	private RobocupServer trainer = null;
	private RobocupServerAddress serverAddress;
	private MeasurableInformation atStart;
	private boolean firstUpdate = true;
	private boolean isTestRunning = false;
	private Logger logger;
	private boolean monitorState = false;
	private boolean waitingForAgent = false;
	private String awaitedAgent;

	private final ButtonGroup logLevelGroup = new ButtonGroup();
	private JButton btnClearLog;
	private JButton btnApplyGraphical;
	private JTextField txtUpdateInterval;
	private JComboBox comboWmAgent;
	private JComboBox comboWmData;
	private JLabel jLabel10;
	private JLabel jLabel9;
	private JPanel tabGraphicalSettings;
	private GameView gameView;
	private JPanel tabGraphical;
	private JButton btnAgentMonitor;
	private JComboBox comboTestCase;
	private JTextField txtPlanName;
	private JLabel jLabelPlanName;
	private JButton btnRemoveAgent;
	private JButton btnReloadXml;
	private JButton btnReplan;
	private JComboBox comboAgentControl;
	private JPanel panelAgentControl;
	private JScrollPane scrollAgentOut;
	private JTextArea txtAgentOut;
	private JComboBox comboAgentOut;
	private JPanel panelAgentOut;
	private JComboBox comboAgentPos;
	private JComboBox comboTeam;
	private JButton btnAddAgent;
	private JButton btnSetBallPos;
	private JPanel panelControls;
	private JPanel monitoringSidebar;
	private JPanel panelAgent;
	private JPanel tabManageAgents;
	
	
	private JPanel panelBall;
	private JScrollPane scrollLogArea;
	private JScrollPane scrollLogLevel;
	private JScrollPane scrollJimInfo;
	private JButton btnTestCase;
	private JTextField txtAnnotX;
	private JPanel jPanel2;
	private JPanel jPanel1;
	private JTextArea txtJimInfo;
	private JTextArea txtLogArea;
	private JTextField txtBallPos;
	private JButton btnSetAgentPos;
	private JTextField txtAgentPos;
	private JButton btnConnect;
	private JTextField txtAnnotRadius;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JTextField txtAnnotLoops;
	private JLabel jLabel6;
	private JTextField txtAnnotDest;
	private JTextField txtAnnotMove;
	private JButton btnAnnotate;
	private JLabel jLabel1;
	private JTextField txtAnnotY;
	private JLabel jLabel7;
	private JPanel panelAddAgent;
	
	//pridane na GA
	private JPanel tabEvolutionAlgorithm;
	private JPanel panelGA;
	private JLabel jLabelPopulation;
	private JTextField jTFPopulation;
	private JTextField jTFGeneration;
	private JLabel jLabelGeneration;
	private JPanel panelSimulation;
	private JLabel jLabelPlayerPosition;
	private JTextField jTFPlayerPosition;
	private JLabel jLabelBallPosition;
	private JTextField jTFBallPosition;
	private JRadioButton btnKick;
	private JRadioButton btnAuto;
	private JRadioButton btnWalk;
	private JLabel jLabelPenalize;
	private JRadioButton btnYes;
	private JRadioButton btnNo;
	private JLabel jLabelMinDistanceKick;
	private JTextField jTFDistance;
	private JLabel jLabelMaxAngleDiversion;
	private JTextField jTFMaxAngleDiversion;
	private JLabel jLabelLenghtWalk;
	private JTextField jTFLenghtWalk;
	private JLabel jLabelPickMove;
	private JTextField jTFPickMove;
	private JButton btnPickMove;
	private JLabel jLabelSaveMove;
	private JTextField jTFSaveMove;
	private JButton btnStart;
	private JButton btnStop;
	private String moveName;
	private JLabel jLabelMaxTimeOnMove;
	private JTextField jTFTimeOnMove;

	private JLabel jLabelPMut;
	private JSpinner spPMut;
	private JLabel jLabelNumberTests;
	private JSpinner spNumberOfTest;
	private JLabel jLabelRangeMutation;
	private JSpinner spRangeMut;
	private JCheckBox chBHE1;
	private JCheckBox chBHE2;
	private JCheckBox chBRLE1;
	private JCheckBox chBRLE2;
	private JCheckBox chBRLE3;
	private JCheckBox chBRLE5;
	private JCheckBox chBRLE4;
	private JCheckBox chBRLE6;
	private JCheckBox chBLLE1;
	private JCheckBox chBLLE2;
	private JCheckBox chBLLE3;
	private JCheckBox chBLLE4;
	private JCheckBox chBLLE5;
	private JCheckBox chBLLE6;
	private JCheckBox chBRAE1;
	private JCheckBox chBRAE2;
	private JCheckBox chBRAE3;
	private JCheckBox chBRAE4;
	private JCheckBox chBLAE1;
	private JCheckBox chBLAE2;
	private JCheckBox chBLAE3;
	private JCheckBox chBLAE4;
	private CheckboxGroup chBGroupJoint;
	private JRadioButton btnOnePointCrossover;
	private JRadioButton btnTwoPointCrossover;
	private JRadioButton btnMore;
	private JSpinner spMore;
	private JRadioButton btnTournamentSelection;
	private JRadioButton btnRuletteSelection;
	private JRadioButton btnGeneticAlgorithm;
	private JRadioButton btnGeneticProgramming;
	private JRadioButton btnBoth;
	

	public MainFrame(RobocupServerAddress robocupAddress) {
		this.serverAddress = robocupAddress;

		try {
			monitor = RobocupMonitor.getMonitorInstance(serverAddress);
			trainer = RobocupServer.getServerInstance(serverAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}

		createGUI();
		registerGUIListeners();
		
		//register the handler for closing the window, allows clean exit
		addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent arg0) { }
			public void windowClosed(WindowEvent arg0) { }
			public void windowClosing(WindowEvent arg0) { onWindowClosing(); }
			public void windowDeactivated(WindowEvent arg0) { }
			public void windowDeiconified(WindowEvent arg0) { }
			public void windowIconified(WindowEvent arg0) { }
			public void windowOpened(WindowEvent arg0) { }
		});

		//put the logged messages into the text area
		logger = Logger.getLogger("sk.fiit.testframework");
		logger.addHandler(new Handler() {
			@Override
			public void publish(LogRecord rec) {		
				txtLogArea.append(rec.getLevel().toString() + ": " + rec.getMessage() + "\n");
				//scroll the text area to the end
				txtLogArea.setCaretPosition(txtLogArea.getText().length() - 1);
			}
			public void flush() { }
			public void close() throws SecurityException { }
		});
		
		registerMonitor();
		
		AgentManager.getManager().setAgentWaitTime(1);
		AgentManager.getManager().addAgentManagerListener(this);
		
		// zatial len ako demo -> model sa do danej metody receivedMessage este neposle
		AgentMonitor.setMessageListener(1, "ANDROIDS", this, AgentMonitorMessage.TYPE_WORLD_MODEL);
	}
	
	//clean exit when the window closes
	protected void onWindowClosing() {
		this.dispose();
		ImplementationFactory.getImplementationInstance().exit();
		
		// odstranenie listenera na world model objekty od hracov
		AgentMonitor.removeMessageListener(this);
	}

	/**
	 * Registers listeners for all of the window's elements
	 */
	private void registerGUIListeners() {
		//===== Log section =====
		btnClearLog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnClearLogClicked(evt);
			}
		});
		
		//all the log level radio buttons
		Enumeration<AbstractButton> e = logLevelGroup.getElements();
		while (e.hasMoreElements()) {
			ActionListener listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					logLevelChanged(evt);
				}
			};
			e.nextElement().addActionListener(listener);
		}
		
		//===== Server monitoring tab =====
		//Start/stop monitoring button
		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnConnectClicked(evt);
			}
		});

		btnSetAgentPos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnSetAgentPosClicked(evt);
			}
		});
		
		btnSetBallPos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnSetBallPosClicked(evt);
			}
		});
		
		btnTestCase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnTestCaseClicked(evt);
			}
		});
		
		btnApplyGraphical.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnApplyGraphicalClicked(evt);
			}
		});
		
		//===== Manage agents tab =====		
		btnAddAgent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnAddAgentClicked(evt);
			}
		});
		
		comboAgentOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				comboAgentOutSelected(evt);
			}
		});
		
		btnRemoveAgent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnRemoveAgentClicked(evt);
			}
		});
		
		btnReplan.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnReplanClicked(evt);
			}
		});
		
		btnReloadXml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnReloadXmlClicked(evt);
			}
		});
		
		btnAgentMonitor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnAgentMonitorClicked(evt);
			}
		});
		
				
		//listens for additions to the agents' output and updates the text area
		//as necessary
		AgentManager.getManager().addAgentManagerListener(new IAgentManagerListener() {
			public void agentAdded(AgentJim agent) {}
			public void agentRemoved(AgentJim agent) {}
			
			@Override
			public void agentOutputLine(AgentJim agent, String line) {
				if (comboAgentOut.getSelectedItem() == agent) {
					txtAgentOut.setText(agent.getStdout().toString());
					txtAgentOut.setCaretPosition(txtAgentOut.getText().length() - 1);
				}
			}
		});

		//===== Evolution Algorithm tab =====
		btnPickMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnPickMoveClicked(evt);				
			}
		});
		
		btnStart.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent evt) {			
				btnStartClicked(evt);
			}
		});
		btnMore.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnCrossClicked(evt);
				
			}
		});
		btnOnePointCrossover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnCrossClicked(evt);
				
			}
		});
		btnTwoPointCrossover.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnCrossClicked(evt);
				
			}
		});
		btnGeneticProgramming.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnStabilizationClicked(evt);
				
			}
		});
		btnGeneticAlgorithm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnCreateMoveClicked(evt);
				
			}
		});
		
		btnStop.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent evt) {			
				btnStopClicked(evt);
			}
		});						
		
		//===== Annotations tab =====
		btnAnnotate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btnAnnotateClicked(evt);
			}
		});
	}
	//================= GA tab ============
	protected void btnPickMoveClicked(ActionEvent evt){
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(this);		
		File file = fc.getSelectedFile();		
		jTFPickMove.setText(file.getAbsolutePath());//getName());//		
		jTFPickMove.disable();
		Date date = new Date();
    	DateFormat df = new SimpleDateFormat("HH_mm_ss");
		String dir = file.getName().substring(0,file.getName().indexOf("."));
		jTFSaveMove.setText(file.getParentFile().toString()+"fromGA" + "\\" + dir + "_" + df.format(date));
		jTFSaveMove.disable();
		moveName = dir;//file.getName(); 
		
		
	}
	protected void btnCrossClicked(ActionEvent evt){
		if(btnMore.isSelected()){
			spMore.setEnabled(true);
		}
		else{
			spMore.setEnabled(false);
		}
	}
	
	protected void btnStabilizationClicked(ActionEvent evt){
		if(btnGeneticProgramming.isSelected()){
			btnTwoPointCrossover.setEnabled(false);
			btnMore.setEnabled(false);
			spMore.setEnabled(false);
			spRangeMut.setEnabled(false);
			spPMut.setEnabled(false);
		}
	}
	protected void btnCreateMoveClicked(ActionEvent evt){
		if(btnGeneticAlgorithm.isSelected()){
			btnTwoPointCrossover.setEnabled(true);
			btnMore.setEnabled(true);
			spRangeMut.setEnabled(true);
			spPMut.setEnabled(true);
		}
	}
	
	protected void btnStartClicked(ActionEvent evt){
		/*String team = "Jimmer";//comboTeam.getSelectedItem().toString();
		int uniform = AgentManager.getManager().getFreeUniform(team);
		waitingForAgent = true;
		awaitedAgent = uniform + team;*/
		addAgent();
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		AgentJim agent = (AgentJim)comboAgentControl.getSelectedItem();
		
		//set param for GA and simulation
		ParameterGA paramGA = new ParameterGA();		
		paramGA.setGeneration(Integer.parseInt(jTFGeneration.getText()));
		paramGA.setPopulation(Integer.parseInt(jTFPopulation.getText()));
		paramGA.setSource(jTFPickMove.getText());
		paramGA.setMoveName(moveName);
		paramGA.setDestination(jTFSaveMove.getText());
		
		ParameterMutation paramMut = new ParameterMutation();
		paramMut.setPercentOfMutation(Integer.parseInt(spPMut.getValue().toString()));
		paramMut.setRangeMutation(Integer.parseInt(spRangeMut.getValue().toString()));
		if(chBHE1.isSelected()){
			paramMut.addJoint(chBHE1.getLabel());
		}
		if(chBHE2.isSelected()){
			paramMut.addJoint(chBHE2.getLabel());
		}
		if(chBLAE1.isSelected()){
			paramMut.addJoint(chBLAE1.getLabel());
		}
		if(chBLAE2.isSelected()){
			paramMut.addJoint(chBLAE2.getLabel());
		}
		if(chBLAE3.isSelected()){
			paramMut.addJoint(chBLAE3.getLabel());
		}
		if(chBLAE4.isSelected()){
			paramMut.addJoint(chBLAE4.getLabel());
		}
		if(chBRAE1.isSelected()){
			paramMut.addJoint(chBRAE1.getLabel());
		}
		if(chBRAE2.isSelected()){
			paramMut.addJoint(chBRAE2.getLabel());
		}
		if(chBRAE3.isSelected()){
			paramMut.addJoint(chBRAE3.getLabel());
		}
		if(chBRAE4.isSelected()){
			paramMut.addJoint(chBRAE4.getLabel());
		}
		if(chBRLE1.isSelected()){
			paramMut.addJoint(chBRLE1.getLabel());
		}
		if(chBRLE2.isSelected()){
			paramMut.addJoint(chBRLE2.getLabel());
		}
		if(chBRLE3.isSelected()){
			paramMut.addJoint(chBRLE3.getLabel());
		}
		if(chBRLE4.isSelected()){
			paramMut.addJoint(chBRLE4.getLabel());
		}
		if(chBRLE5.isSelected()){
			paramMut.addJoint(chBRLE5.getLabel());
		}
		if(chBRLE6.isSelected()){
			paramMut.addJoint(chBRLE6.getLabel());
		}
		if(chBLLE1.isSelected()){
			paramMut.addJoint(chBLLE1.getLabel());
		}
		if(chBLLE2.isSelected()){
			paramMut.addJoint(chBLLE2.getLabel());
		}
		if(chBLLE3.isSelected()){
			paramMut.addJoint(chBLLE3.getLabel());
		}
		if(chBLLE4.isSelected()){
			paramMut.addJoint(chBLLE4.getLabel());
		}
		if(chBLLE5.isSelected()){
			paramMut.addJoint(chBLLE5.getLabel());
		}
		if(chBLLE6.isSelected()){
			paramMut.addJoint(chBLLE6.getLabel());
		}
		paramGA.setParamMut(paramMut);
		CrossoverType type=CrossoverType.OnePoint;
		int crossPoint=1;
		if(btnOnePointCrossover.isSelected()){			
			type = CrossoverType.OnePoint;
			crossPoint=1;
		}else if(btnTwoPointCrossover.isSelected()){			
			type=CrossoverType.TwoPoint;
			crossPoint=2;
		}else if(btnMore.isSelected()){
			
			type = CrossoverType.MorePoint;
			crossPoint = Integer.parseInt(spMore.getValue().toString());
		}
		ParameterCrossover paramCross = new ParameterCrossover(type, crossPoint);
		paramGA.setParamCross(paramCross);		
		SelectionType selection=null;
		if(btnTournamentSelection.isSelected()){
			selection = SelectionType.Tournament;
		}
		if(btnRuletteSelection.isSelected()){
			selection = SelectionType.Rulette;
		}
		ParameterSelection paramSelection = new ParameterSelection(selection);
		paramGA.setParamSel(paramSelection);
		EnvironmentSetting envSetting = new EnvironmentSetting();
		envSetting.setBallPosition(jTFBallPosition.getText());
		envSetting.setPlayerPosition(jTFPlayerPosition.getText());
		
		AlgorithmType algType = new AlgorithmType();
		if(btnGeneticAlgorithm.isSelected()){
			algType.setGeneticAlgorithm(true);
			algType.setGeneticPrograming(false);
		}else if(btnGeneticProgramming.isSelected()){
			algType.setGeneticAlgorithm(false);
			algType.setGeneticPrograming(true);
		}else if(btnBoth.isSelected()){
			algType.setGeneticAlgorithm(true);
			algType.setGeneticPrograming(true);
		}
		
		TestTypeParameter ttParam = new TestTypeParameter();
		ttParam.setLenghtWalk(Double.parseDouble(jTFLenghtWalk.getText()));
		ttParam.setMaxAngleDiversion(Integer.parseInt(jTFMaxAngleDiversion.getText()));
		ttParam.setMinKickDistance(Double.parseDouble(jTFDistance.getText()));
		ttParam.setMaxTimeOnMove(Double.parseDouble(jTFTimeOnMove.getText()));
		ttParam.setNumberTestOnMove(Integer.parseInt(spNumberOfTest.getValue().toString()));
		if(btnYes.isSelected()){
			ttParam.setPenalize(true);
		}
		else{
			ttParam.setPenalize(false);
		}
		if(btnKick.isSelected()){
			ttParam.setMoveType("kick");
		}
		if(btnWalk.isSelected()){
			ttParam.setMoveType("walk");
		}
		if(btnAuto.isSelected()){
			ttParam.setMoveType("automatic");
		}		
		
		
		
		AgentMoveReader read = new AgentMoveReader();
		AgentMoveWriter write = new AgentMoveWriter();
		try {
			AgentMove move = read.read(jTFPickMove.getText());		
			GeneticAlgorithm ga = GeneticAlgorithm.getInstance(move,paramGA,agent,envSetting,ttParam, algType);
			ga.doGA();
			
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	protected void btnStopClicked(ActionEvent evt){
						
		try {
			RobocupServer.getServerInstance().setPlayMode(PlayMode.BeforeKickOff);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			btnStart.enable();
			btnStop.disable();
		}
		
	}
	
	
	protected void btnApplyGraphicalClicked(ActionEvent evt) {
		int interval = Integer.parseInt(txtUpdateInterval.getText());
		gameView.setUpdateInterval(interval);
		
		int comboWMIndex = comboWmData.getSelectedIndex();
		if (comboWMIndex == 0) {
			gameView.setWorldModelDrawing(GameView.WorldModelDrawing.ALL);
		}else if (comboWMIndex == 1) {
			gameView.setWorldModelDrawing(GameView.WorldModelDrawing.NONE);
		}else{
			gameView.setWorldModelDrawing(GameView.WorldModelDrawing.SELECTED);
			gameView.setWMDrawingAgent((AgentJim)comboWmAgent.getSelectedItem());
		}
	}

	protected void btnAgentMonitorClicked(ActionEvent evt) {
		AgentJim agent = (AgentJim)comboAgentControl.getSelectedItem();
		if (agent != null)
			new AgentMonitorFrame(agent, atStart, monitor).setVisible(true);
	}

	protected void btnReloadXmlClicked(ActionEvent evt) {
		try {
			AgentJim agent = (AgentJim)comboAgentControl.getSelectedItem();
			if (agent != null)
				agent.invokeXMLReload();
		}catch (Exception e) {
			logger.warning("Replan: " + e.getMessage());
		}
	}

	protected void btnReplanClicked(ActionEvent evt) {
		try {
			AgentJim agent = (AgentJim)comboAgentControl.getSelectedItem();
			if (agent != null) {
				if (txtPlanName.getText().isEmpty())
					agent.invokeReplan();
				else
					agent.invokePlanChange(txtPlanName.getText());
			}
		}catch (Exception e) {
			logger.warning("Replan: " + e.getMessage());
		}
	}

	protected void btnRemoveAgentClicked(ActionEvent evt) {
		AgentJim agent = (AgentJim)comboAgentControl.getSelectedItem();
		if (agent != null)
			AgentManager.getManager().removeAgent(agent);
	}

	protected void comboAgentOutSelected(ActionEvent evt) {
		AgentJim agent = (AgentJim)comboAgentOut.getSelectedItem();
		if (agent != null)
			txtAgentOut.setText(agent.getStdout().toString());
	}

	protected void btnAddAgentClicked(ActionEvent evt) {
		addAgent();
	}
	private void addAgent(){
		String team = comboTeam.getSelectedItem().toString();
		int uniform = AgentManager.getManager().getFreeUniform(team);
		waitingForAgent = true;
		awaitedAgent = uniform + team;
		btnAddAgent.setEnabled(false);
		btnAddAgent.setText("wait...");
		AgentManager.getManager().getAgent(uniform, team, true);
	}

	protected void btnClearLogClicked(ActionEvent evt) {
		txtLogArea.setText("");
	}

	protected void btnTestCaseClicked(ActionEvent evt) {
		if (isTestRunning) {
			btnTestCase.requestFocusInWindow();
			btnTestCase.setText("Start test case");
			isTestRunning = false;
		}else{
			firstUpdate = true;
			btnTestCase.requestFocusInWindow();
			btnTestCase.setText("Stop test case");
			isTestRunning = true;

			new Thread(((TestHolder)comboTestCase.getSelectedItem()).runnable).start();
		}
	}

	protected void btnSetBallPosClicked(ActionEvent evt) {
		try {
			trainer.setBallPosition(getPoint3D(txtBallPos.getText()));
		} catch (Exception ex) {
			logger.severe("btnSetBallClicked: " + ex.getMessage());
		}
	}

	protected void btnAnnotateClicked(ActionEvent evt) {
		int loops;
		Circle circle;
		try {
			loops = Integer.parseInt(txtAnnotLoops.getText());
			double x = Double.parseDouble(txtAnnotX.getText());
			double y = Double.parseDouble(txtAnnotY.getText());
			double radius = Double.parseDouble(txtAnnotRadius.getText());
			Vector2 v = new Vector2(x, y);
			circle = new Circle(v, radius);
		} catch (NumberFormatException ex) {
			logger.severe("Invalid input. Only numeric values are accepted.");
			return;
		}
		new Annotator(loops, circle, txtAnnotMove.getText(), new File(txtAnnotDest.getText())).annotate();
	}

	protected void btnSetAgentPosClicked(ActionEvent evt) {
		if (comboAgentPos.getModel().getSize() == 0) {
			logger.warning("No agents exist, add some first");
			return;
		}
		
		AgentJim aj = (AgentJim)comboAgentPos.getSelectedItem();

		try {
			trainer.setAgentPosition(aj.getAgentData(), getPoint3D(txtAgentPos.getText()));
		} catch (Exception e) {
			logger.severe(e.getMessage());
		}
	}

	protected void btnConnectClicked(ActionEvent evt) {
		registerMonitor();
	}

	protected void logLevelChanged(ActionEvent evt) {
		Level logLevel = Level.parse(evt.getActionCommand());
		logger.setLevel(logLevel);
		logger.info("Logging level changed to " + evt.getActionCommand());
	}

	/**
	 * Creates all the GUI elements. Most of the code in this method is generated
	 * automatically.
	 */
	private void createGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		BorderLayout thisLayout = new BorderLayout();
		thisLayout.setVgap(3);
		thisLayout.setHgap(3);
		getContentPane().setLayout(thisLayout);
		setTitle("Jim Control Interface");
		this.setSize(728, 723);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.35);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		getContentPane().add(splitPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		{
			scrollLogArea = new JScrollPane();
			panel.add(scrollLogArea, BorderLayout.CENTER);
			scrollLogArea.setPreferredSize(new java.awt.Dimension(575, 200));
			{
				txtLogArea = new JTextArea();
				scrollLogArea.setViewportView(txtLogArea);
				txtLogArea.setFont(new java.awt.Font("Monospaced", 0, 12));
			}
		}
		{
			scrollLogLevel = new JScrollPane();
			panel.add(scrollLogLevel, BorderLayout.EAST);
			scrollLogLevel.setPreferredSize(new java.awt.Dimension(149, 222));
			{
				JPanel panel_1 = new JPanel();
				scrollLogLevel.setViewportView(panel_1);
				panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
				panel_1.setBorder(new TitledBorder(null, "Log level",
						TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_1.setPreferredSize(new java.awt.Dimension(131, 218));
				panel_1.setSize(133, 200);
				{
					JRadioButton rdbtnLog = new JRadioButton("Finest");
					panel_1.add(rdbtnLog);
					logLevelGroup.add(rdbtnLog);
					rdbtnLog.setActionCommand("FINEST");
				}
				{
					JRadioButton rdbtnFiner = new JRadioButton("Finer");
					panel_1.add(rdbtnFiner);
					logLevelGroup.add(rdbtnFiner);
					rdbtnFiner.setActionCommand("FINER");
				}
				{
					JRadioButton rdbtnFine = new JRadioButton("Fine");
					panel_1.add(rdbtnFine);
					logLevelGroup.add(rdbtnFine);
					rdbtnFine.setActionCommand("FINE");
				}
				{
					JRadioButton rdbtnConfig = new JRadioButton("Config");
					panel_1.add(rdbtnConfig);
					logLevelGroup.add(rdbtnConfig);
					rdbtnConfig.setActionCommand("CONFIG");
				}
				{
					JRadioButton rdbtnInfo = new JRadioButton("Info");
					panel_1.add(rdbtnInfo);
					rdbtnInfo.setSelected(true);
					logLevelGroup.add(rdbtnInfo);
					rdbtnInfo.setActionCommand("INFO");
				}
				{
					JRadioButton rdbtnWarning = new JRadioButton("Warning");
					panel_1.add(rdbtnWarning);
					logLevelGroup.add(rdbtnWarning);
					rdbtnWarning.setActionCommand("WARNING");
				}
				{
					JRadioButton rdbtnSevere = new JRadioButton("Severe");
					panel_1.add(rdbtnSevere);
					logLevelGroup.add(rdbtnSevere);
					rdbtnSevere.setActionCommand("SEVERE");
				}
				{
					btnClearLog = new JButton();
					panel_1.add(btnClearLog);
					btnClearLog.setText("Clear");
					btnClearLog
							.setPreferredSize(new java.awt.Dimension(95, 25));
				}
			}
		}

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPane);
		{
			JPanel tabServerMonitoring = new JPanel();
			tabbedPane.addTab("Server monitoring", null, tabServerMonitoring,
					null);
			BorderLayout bl_tabServerMonitoring = new BorderLayout();
			tabServerMonitoring.setLayout(bl_tabServerMonitoring);
			{
				JTabbedPane monitoringPane = new JTabbedPane(JTabbedPane.TOP);
				tabServerMonitoring.add(monitoringPane, BorderLayout.CENTER);
				{
					JPanel tabMonitorData = new JPanel();
					monitoringPane.addTab("Monitor data", null, tabMonitorData,
							null);
					BorderLayout bl_tabMonitorData = new BorderLayout();
					tabMonitorData.setLayout(bl_tabMonitorData);
					{
						scrollJimInfo = new JScrollPane();
						tabMonitorData.add(scrollJimInfo, BorderLayout.CENTER);
						scrollJimInfo.setPreferredSize(new java.awt.Dimension(
								482, 279));
						{
							txtJimInfo = new JTextArea();
							scrollJimInfo.setViewportView(txtJimInfo);
							txtJimInfo.setText("Ready");
							txtJimInfo.setFont(new java.awt.Font("Monospaced",
									0, 12));
							txtJimInfo.setBorder(BorderFactory
									.createEtchedBorder(BevelBorder.LOWERED));
							((DefaultCaret)txtJimInfo.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
						}
					}
				}
				{
					tabGraphical = new JPanel();
					BorderLayout tabGraphicalLayout = new BorderLayout();
					tabGraphical.setLayout(tabGraphicalLayout);
					monitoringPane.addTab("Graphical view", null, tabGraphical, null);
					{
						gameView = new GameView();
						tabGraphical.add(gameView, BorderLayout.CENTER);
					}
				}
				{
					tabGraphicalSettings = new JPanel();
					monitoringPane.addTab("Graphical view settings", null, tabGraphicalSettings, null);
					tabGraphicalSettings.setLayout(null);
					{
						jLabel9 = new JLabel();
						tabGraphicalSettings.add(jLabel9);
						jLabel9.setText("Update interval (0 = unlimited)");
						jLabel9.setBounds(12, 13, 176, 16);
					}
					{
						jLabel10 = new JLabel();
						tabGraphicalSettings.add(jLabel10);
						jLabel10.setText("Show world model data for");
						jLabel10.setBounds(12, 42, 156, 16);
					}
					{
						ComboBoxModel comboWmDataModel = 
								new DefaultComboBoxModel(
										new String[] { "All agents", "No agents", "Selected agent" });
						comboWmDataModel.setSelectedItem("All agents");
						comboWmData = new JComboBox();
						tabGraphicalSettings.add(comboWmData);
						comboWmData.setModel(comboWmDataModel);
						comboWmData.setBounds(193, 39, 132, 22);
					}
					{
						ComboBoxModel comboWmAgentModel = new AgentComboModel();
						comboWmAgent = new JComboBox();
						tabGraphicalSettings.add(comboWmAgent);
						comboWmAgent.setModel(comboWmAgentModel);
						comboWmAgent.setBounds(193, 68, 132, 22);
					}
					{
						txtUpdateInterval = new JTextField();
						tabGraphicalSettings.add(txtUpdateInterval);
						txtUpdateInterval.setText("0");
						txtUpdateInterval.setBounds(193, 10, 132, 22);
					}
					{
						btnApplyGraphical = new JButton();
						tabGraphicalSettings.add(btnApplyGraphical);
						btnApplyGraphical.setText("Apply");
						btnApplyGraphical.setBounds(12, 97, 79, 25);
					}
				}
			}
			{
				monitoringSidebar = new JPanel();
				GroupLayout monitoringSidebarLayout = new GroupLayout((JComponent)monitoringSidebar);
				monitoringSidebar.setLayout(monitoringSidebarLayout);
				tabServerMonitoring.add(monitoringSidebar, BorderLayout.EAST);
				monitoringSidebar.setPreferredSize(new java.awt.Dimension(197, 419));
				{
					panelControls = new JPanel();
					GridLayout panelControlsLayout = new GridLayout(3, 1);
					panelControlsLayout.setHgap(5);
					panelControlsLayout.setVgap(5);
					panelControlsLayout.setColumns(1);
					panelControlsLayout.setRows(3);
					panelControls.setLayout(panelControlsLayout);
					panelControls.setBorder(BorderFactory.createTitledBorder("Controls"));
					{
						btnConnect = new JButton();
						panelControls.add(btnConnect);
						btnConnect.setText("Start monitoring");
					}
					{
						btnTestCase = new JButton();
						panelControls.add(btnTestCase);
						btnTestCase.setText("Start test case");
					}
					{
						ComboBoxModel comboTestCaseModel = 
							new TestCaseComboModel();
						comboTestCase = new JComboBox();
						panelControls.add(comboTestCase);
						comboTestCase.setModel(comboTestCaseModel);
					}
				}
				{
					panelAgent = new JPanel();
					GridLayout panelAgentLayout = new GridLayout(3, 1);
					panelAgentLayout.setHgap(5);
					panelAgentLayout.setVgap(5);
					panelAgentLayout.setColumns(1);
					panelAgentLayout.setRows(3);
					panelAgent.setLayout(panelAgentLayout);
					panelAgent.setBorder(BorderFactory.createTitledBorder("Agent"));
					{
						ComboBoxModel comboAgentPosModel = new AgentComboModel();
						comboAgentPos = new JComboBox();
						panelAgent.add(comboAgentPos);
						comboAgentPos.setModel(comboAgentPosModel);
					}
					{
						txtAgentPos = new JTextField();
						panelAgent.add(txtAgentPos);
						txtAgentPos.setText("0:2:0.4");
					}
					{
						btnSetAgentPos = new JButton();
						panelAgent.add(btnSetAgentPos);
						btnSetAgentPos.setText("Set position");
					}
				}
				{
					panelBall = new JPanel();
					GridLayout panelBallLayout = new GridLayout(2, 1);
					panelBallLayout.setHgap(5);
					panelBallLayout.setVgap(5);
					panelBallLayout.setColumns(1);
					panelBallLayout.setRows(2);
					panelBall.setLayout(panelBallLayout);
					panelBall.setBorder(BorderFactory.createTitledBorder("Ball"));
					{
						txtBallPos = new JTextField();
						panelBall.add(txtBallPos);
						txtBallPos.setText("0:0:0.4");
					}
					{
						btnSetBallPos = new JButton();
						panelBall.add(btnSetBallPos);
						btnSetBallPos.setText("Set position");
					}
				}
			monitoringSidebarLayout.setHorizontalGroup(monitoringSidebarLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(monitoringSidebarLayout.createParallelGroup()
					    .addComponent(panelControls, GroupLayout.Alignment.LEADING, 0, 173, Short.MAX_VALUE)
					    .addComponent(panelAgent, GroupLayout.Alignment.LEADING, 0, 173, Short.MAX_VALUE)
					    .addComponent(panelBall, GroupLayout.Alignment.LEADING, 0, 173, Short.MAX_VALUE))
					.addContainerGap());
			monitoringSidebarLayout.setVerticalGroup(monitoringSidebarLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelControls, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(panelAgent, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(panelBall, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(63, Short.MAX_VALUE));
			}
		}
		
		// panel manage agents
		
		{
			tabManageAgents = new JPanel();
			GroupLayout tabManageAgentsLayout = new GroupLayout((JComponent)tabManageAgents);
			tabManageAgents.setLayout(tabManageAgentsLayout);
			tabbedPane.addTab("Manage agents", null, tabManageAgents, null);
			{
				panelAddAgent = new JPanel();
				FlowLayout panelAddAgentLayout = new FlowLayout();
				panelAddAgent.setBorder(BorderFactory.createTitledBorder("Add new agent"));
				panelAddAgent.setLayout(panelAddAgentLayout);
				{
					jLabel7 = new JLabel();
					panelAddAgent.add(jLabel7);
					jLabel7.setText("Team");
				}
				{
					ComboBoxModel comboTeamModel = 
						new DefaultComboBoxModel(
								new String[] { "ANDROIDS", "Team2", "JA" });
					comboTeamModel.setSelectedItem("JA");
					comboTeam = new JComboBox();
					panelAddAgent.add(comboTeam);
					comboTeam.setModel(comboTeamModel);
					comboTeam.setPreferredSize(new java.awt.Dimension(114, 22));
				}
				{
					btnAddAgent = new JButton();
					panelAddAgent.add(btnAddAgent);
					btnAddAgent.setText("Add");
				}
			}
			{
				panelAgentOut = new JPanel();
				BorderLayout panelAgentOutLayout = new BorderLayout();
				panelAgentOutLayout.setVgap(5);
				panelAgentOut.setLayout(panelAgentOutLayout);
				panelAgentOut.setBorder(BorderFactory.createTitledBorder("Agent output"));
				{
					ComboBoxModel comboAgentOutModel = new AgentComboModel();
					comboAgentOut = new JComboBox();
					panelAgentOut.add(comboAgentOut, BorderLayout.NORTH);
					comboAgentOut.setModel(comboAgentOutModel);
				}
				{
					scrollAgentOut = new JScrollPane();
					panelAgentOut.add(scrollAgentOut, BorderLayout.CENTER);
					scrollAgentOut.setPreferredSize(new java.awt.Dimension(667, 143));
					{
						txtAgentOut = new JTextArea();
						scrollAgentOut.setViewportView(txtAgentOut);
						txtAgentOut.setFont(new java.awt.Font("Monospaced",0,12));
					}
				}
			}
			{
				panelAgentControl = new JPanel();
				GroupLayout panelAgentControlLayout = new GroupLayout((JComponent)panelAgentControl);
				panelAgentControl.setLayout(panelAgentControlLayout);
				panelAgentControl.setBorder(BorderFactory.createTitledBorder("Control agent"));
				{
					ComboBoxModel comboAgentControlModel = new AgentComboModel();
					comboAgentControl = new JComboBox();
					comboAgentControl.setModel(comboAgentControlModel);
				}
				{
					btnAgentMonitor = new JButton();
					btnAgentMonitor.setText("Monitor");
				}
				{
					jLabelPlanName = new JLabel();
					jLabelPlanName.setText("Plan name");
				}
				{
					txtPlanName = new JTextField();
				}
				{
					btnReplan = new JButton();
					btnReplan.setText("Replan");
				}
				{
					btnReloadXml = new JButton();
					btnReloadXml.setText("Reload XMLs");
				}
				{
					btnRemoveAgent = new JButton();
					btnRemoveAgent.setText("Remove");
				}
			panelAgentControlLayout.setHorizontalGroup(panelAgentControlLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(panelAgentControlLayout.createParallelGroup()
					    .addGroup(GroupLayout.Alignment.LEADING, panelAgentControlLayout.createSequentialGroup()
					        .addGroup(panelAgentControlLayout.createParallelGroup()
					            .addComponent(btnReplan, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					            .addComponent(btnAgentMonitor, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
					        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 0, Short.MAX_VALUE)
					        .addComponent(btnReloadXml, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					        .addComponent(btnRemoveAgent, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
					    .addGroup(GroupLayout.Alignment.LEADING, panelAgentControlLayout.createSequentialGroup()
					        .addComponent(jLabelPlanName, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
					        .addComponent(txtPlanName, 0, 274, Short.MAX_VALUE))
					    .addComponent(comboAgentControl, GroupLayout.Alignment.LEADING, 0, 339, Short.MAX_VALUE))
					.addContainerGap());
			panelAgentControlLayout.setVerticalGroup(panelAgentControlLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboAgentControl, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(panelAgentControlLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(btnReplan, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(btnReloadXml, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(btnRemoveAgent, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(panelAgentControlLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					    .addComponent(txtPlanName, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
					    .addComponent(jLabelPlanName, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addComponent(btnAgentMonitor, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(0, 8, Short.MAX_VALUE));
			}
			tabManageAgentsLayout.setHorizontalGroup(tabManageAgentsLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(tabManageAgentsLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, tabManageAgentsLayout.createSequentialGroup()
				        .addComponent(panelAddAgent, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				        .addComponent(panelAgentControl, 0, 375, Short.MAX_VALUE))
				    .addComponent(panelAgentOut, GroupLayout.Alignment.LEADING, 0, 679, Short.MAX_VALUE))
				.addContainerGap());
			tabManageAgentsLayout.setVerticalGroup(tabManageAgentsLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(tabManageAgentsLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, tabManageAgentsLayout.createSequentialGroup()
				        .addComponent(panelAddAgent, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
				        .addGap(58))
				    .addComponent(panelAgentControl, GroupLayout.Alignment.LEADING, 0, 169, Short.MAX_VALUE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(panelAgentOut, 0, 217, Short.MAX_VALUE)
				.addContainerGap());
		}
		
		//evolucny algoritmus priprava prostredia
		{
			tabEvolutionAlgorithm = new JPanel();
			GroupLayout tabEvolutionAlgorithmLayout = new GroupLayout((JComponent)tabEvolutionAlgorithm);
			tabEvolutionAlgorithm.setLayout(tabEvolutionAlgorithmLayout);
			tabbedPane.addTab("Evolution Algorithm", null, tabEvolutionAlgorithm,null);
			{	
				// UI for setting GA
				panelGA = new JPanel();
				GroupLayout panelGALayout = new GroupLayout((JComponent)panelGA);
				panelGA.setBorder(BorderFactory.createTitledBorder("Genethic Algorithm"));
				panelGA.setLayout(panelGALayout);
				{
					jLabelPopulation = new JLabel();
					panelGA.add(jLabelPopulation);
					jLabelPopulation.setText("Size of population");
					{
						 jTFPopulation = new JTextField();
						 jTFPopulation.setText("50");
					}
				
					jLabelGeneration = new JLabel();
					panelGA.add(jLabelGeneration);
					jLabelGeneration.setText("Number of generation");
					{
						jTFGeneration = new JTextField();
						jTFGeneration.setText("50");
					}
					//select move for GA
					jLabelPickMove = new JLabel();
					panelGA.add(jLabelPickMove);
					jLabelPickMove.setText("Pick move");
					{
						jTFPickMove = new JTextField();					
						btnPickMove = new JButton();
						btnPickMove.setText("Pick Move");
						
					}
					//select folder for save move
					jLabelSaveMove = new JLabel();
					panelGA.add(jLabelSaveMove);
					jLabelSaveMove.setText("Save move");
					{
						jTFSaveMove = new JTextField();						
					}
					
					jLabelPMut = new JLabel();
					panelGA.add(jLabelPMut);
					jLabelPMut.setText("Percent of mutation");
					{
						SpinnerModel model = new SpinnerNumberModel(20,0,100,10);						
						spPMut = new JSpinner(model);
					}
					jLabelRangeMutation = new JLabel();
					panelGA.add(jLabelRangeMutation);
					jLabelRangeMutation.setText("Mutation join about[%]");
					{
						SpinnerModel model = new SpinnerNumberModel(20,1,50,1);						
						spRangeMut = new JSpinner(model);
					}
					{						
						btnOnePointCrossover = new JRadioButton("One Point Crossover");
						btnTwoPointCrossover = new JRadioButton("Two Point Crossover");
						btnMore = new JRadioButton("More Point Crossover");
						{
							SpinnerModel model = new SpinnerNumberModel(2,2,10,1);						
							spMore = new JSpinner(model);
							spMore.setEnabled(false);
						}						
						ButtonGroup testGroup = new ButtonGroup();
						testGroup.add(btnOnePointCrossover);
						testGroup.add(btnTwoPointCrossover);
						testGroup.add(btnMore);
						btnOnePointCrossover.setSelected(true);
					}
					{
						btnTournamentSelection = new JRadioButton("Tournament");
						btnRuletteSelection = new JRadioButton("Rulette");
						{
							ButtonGroup testGroup = new ButtonGroup();
							testGroup.add(btnTournamentSelection);
							testGroup.add(btnRuletteSelection);
							btnTournamentSelection.setSelected(true);
						}
					}
					
					//select joint 
					{						
						chBHE1 = new JCheckBox("HE1", true);
						chBHE2 = new JCheckBox("HE2", true);
						
						chBRLE1 = new JCheckBox("RLE1", true);
						chBRLE2 = new JCheckBox("RLE2", true);
						chBRLE3 = new JCheckBox("RLE3", true);
						chBRLE4 = new JCheckBox("RLE4", true);
						chBRLE5 = new JCheckBox("RLE5", true);
						chBRLE6 = new JCheckBox("RLE6", true);
						
						chBLLE1 = new JCheckBox("LLE1", true);
						chBLLE2 = new JCheckBox("LLE2", true);
						chBLLE3 = new JCheckBox("LLE3", true);
						chBLLE4 = new JCheckBox("LLE4", true);
						chBLLE5 = new JCheckBox("LLE5", true);
						chBLLE6 = new JCheckBox("LLE6", true);
						
						chBRAE1 = new JCheckBox("RAE1", true);
						chBRAE2 = new JCheckBox("RAE2", true);
						chBRAE3 = new JCheckBox("RAE3", true);
						chBRAE4 = new JCheckBox("RAE4", true);
						
						chBLAE1 = new JCheckBox("LAE1", true);
						chBLAE2 = new JCheckBox("LAE2", true);
						chBLAE3 = new JCheckBox("LAE3", true);
						chBLAE4 = new JCheckBox("LAE4", true);
					}
				}
				/*panelGALayout.setHorizontalGroup(panelGALayout.createSequentialGroup()
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(jLabelPopulation)
								.addComponent(jLabelGeneration)
								.addComponent(jLabelPickMove)
								.addComponent(jLabelSaveMove)
								.addComponent(jLabelMutate))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(jTFPopulation)
								.addComponent(jTFGeneration)
								.addGroup(panelGALayout.createSequentialGroup()
									.addComponent(jTFPickMove)
									.addComponent(btnPickMove))
								.addGroup(panelGALayout.createSequentialGroup()
									.addComponent(jTFSaveMove)
									)
								));*/
				panelGALayout.setHorizontalGroup(panelGALayout.createSequentialGroup()
						.addGroup(panelGALayout.createSequentialGroup()
								.addGroup(panelGALayout.createParallelGroup()
								.addComponent(jLabelPopulation)
								.addComponent(jLabelGeneration)
								.addComponent(jLabelPickMove)
								.addComponent(jLabelSaveMove)
								.addComponent(jLabelPMut)
								.addComponent(jLabelRangeMutation)
								.addComponent(btnOnePointCrossover)								
								.addComponent(btnMore)
								.addComponent(btnTournamentSelection)
								.addComponent(chBHE1)								
								.addComponent(chBLAE1)
								.addComponent(chBLAE2)
								.addComponent(chBLAE3)
								.addComponent(chBLAE4)								
								.addComponent(chBLLE1)
								.addComponent(chBLLE2)
								.addComponent(chBLLE3)
								.addComponent(chBLLE4)
								.addComponent(chBLLE5)
								.addComponent(chBLLE6))
								)
						.addGroup(panelGALayout.createSequentialGroup()
								.addGroup(panelGALayout.createParallelGroup()										
									.addComponent(jTFPopulation)
									.addComponent(jTFGeneration)
									.addGroup(panelGALayout.createSequentialGroup()
											.addComponent(jTFPickMove)
											.addComponent(btnPickMove))
									.addComponent(jTFSaveMove)									
									.addComponent(spPMut)
									.addComponent(spRangeMut)
									.addComponent(btnTwoPointCrossover)
									.addComponent(spMore)
									.addComponent(btnRuletteSelection)
									.addComponent(chBHE2)
									.addComponent(chBRAE1)
									.addComponent(chBRAE2)
									.addComponent(chBRAE3)
									.addComponent(chBRAE4)									
									.addComponent(chBRLE1)
									.addComponent(chBRLE2)
									.addComponent(chBRLE3)
									.addComponent(chBRLE4)
									.addComponent(chBRLE5)
									.addComponent(chBRLE6)))
						);
				
				panelGALayout.setVerticalGroup(panelGALayout.createSequentialGroup()
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelPopulation)
								.addComponent(jTFPopulation))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelGeneration)
								.addComponent(jTFGeneration))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelPickMove)
								.addComponent(jTFPickMove)
								.addComponent(btnPickMove))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelSaveMove)
								.addComponent(jTFSaveMove))						
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelPMut)
								.addComponent(spPMut))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(jLabelRangeMutation)
								.addComponent(spRangeMut))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(btnOnePointCrossover)
								.addComponent(btnTwoPointCrossover))						
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(btnMore)
								.addComponent(spMore))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(btnTournamentSelection)
								.addComponent(btnRuletteSelection))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBHE1)
								.addComponent(chBHE2))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBLAE1)
								.addComponent(chBRAE1))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBLAE2)
								.addComponent(chBRAE2))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBLAE3)
								.addComponent(chBRAE3))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBLAE4)
								.addComponent(chBRAE4))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBLLE1)
								.addComponent(chBRLE1))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBLLE2)
								.addComponent(chBRLE2))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBLLE3)
								.addComponent(chBRLE3))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBLLE4)
								.addComponent(chBRLE4))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBLLE5)
								.addComponent(chBRLE5))
						.addGroup(panelGALayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(chBLLE6)
								.addComponent(chBRLE6))
								);
				
				
				{
					//UI for setting test
					panelSimulation = new JPanel();
					GroupLayout panelSimLayout = new GroupLayout((JComponent)panelSimulation);
					panelSimulation.setBorder(BorderFactory.createTitledBorder("Simulation"));
					panelSimulation.setLayout(panelSimLayout);
					JPanel panelEnvironmentSetting = new JPanel();
					JPanel panelTestType = new JPanel();
					JPanel panelTTParam = new JPanel(); // panel for test type parameter's
					JPanel panelAlgorithm = new JPanel();
					{
						GroupLayout panelEnviromentSettingLayout = new GroupLayout((JComponent)panelEnvironmentSetting);
						panelEnvironmentSetting.setBorder(BorderFactory.createTitledBorder("Environment Setting"));
						panelEnvironmentSetting.setLayout(panelEnviromentSettingLayout);
						{
							jLabelPlayerPosition = new JLabel();
							panelEnvironmentSetting.add(jLabelPlayerPosition);
							jLabelPlayerPosition.setText("Player start position [x:y:z]");
							{
								jTFPlayerPosition = new JTextField();
								jTFPlayerPosition.setText("-5:0:0.4");
							}
							jLabelBallPosition = new JLabel();
							panelEnvironmentSetting.add(jLabelBallPosition);
							jLabelBallPosition.setText("Ball position [x:y:z]");
							{
								jTFBallPosition = new JTextField();
								jTFBallPosition.setText("0:2:0.4");
							}
						}
						//add to Environment setting
						panelEnviromentSettingLayout.setHorizontalGroup(panelEnviromentSettingLayout.createSequentialGroup()
								.addGroup(panelEnviromentSettingLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(jLabelPlayerPosition)
										.addComponent(jLabelBallPosition))
								.addGroup(panelEnviromentSettingLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(jTFPlayerPosition)
										.addComponent(jTFBallPosition)));
						
						panelEnviromentSettingLayout.setVerticalGroup(panelEnviromentSettingLayout.createSequentialGroup()
								.addGroup(panelEnviromentSettingLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(jLabelPlayerPosition)
										.addComponent(jTFPlayerPosition))
								.addGroup(panelEnviromentSettingLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(jLabelBallPosition)
										.addComponent(jTFBallPosition)));
						
						GroupLayout panelTestTypeLayout = new GroupLayout((JComponent)panelTestType);
						panelTestType.setBorder(BorderFactory.createTitledBorder("Test Type"));
						panelTestType.setLayout(panelTestTypeLayout);
						{
							btnKick = new JRadioButton("Kick test");
							btnWalk = new JRadioButton("Walk test");
							btnAuto = new JRadioButton("AutoSelect test");
							ButtonGroup testGroup = new ButtonGroup();
							testGroup.add(btnKick);
							testGroup.add(btnWalk);
							testGroup.add(btnAuto);
							btnAuto.setSelected(true);
						}
						
						//add to test type
						panelTestTypeLayout.setHorizontalGroup(panelTestTypeLayout.createSequentialGroup()
								.addGroup(panelTestTypeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(btnKick)
										.addComponent(btnWalk)
										.addComponent(btnAuto)));
						
						panelTestTypeLayout.setVerticalGroup(panelTestTypeLayout.createSequentialGroup()								
										.addComponent(btnKick)					
										.addComponent(btnWalk)
										.addComponent(btnAuto));
						
						//algorithm type
						GroupLayout panelAlgorithmLayout = new GroupLayout((JComponent)panelAlgorithm);
						panelAlgorithm.setBorder(BorderFactory.createTitledBorder("Algorithm type"));
						panelAlgorithm.setLayout(panelAlgorithmLayout);
						{
							btnGeneticAlgorithm = new JRadioButton("Create new move");
							btnGeneticProgramming = new JRadioButton("Create stabilization equation");
//							btnBoth = new JRadioButton("Both");
							ButtonGroup algorithmGroup = new ButtonGroup();
							algorithmGroup.add(btnGeneticAlgorithm);
							algorithmGroup.add(btnGeneticProgramming);
//							algorithmGroup.add(btnBoth);
							btnGeneticAlgorithm.setSelected(true);
						}
						
						//add to test type
						panelAlgorithmLayout.setHorizontalGroup(panelAlgorithmLayout.createSequentialGroup()
								.addGroup(panelAlgorithmLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(btnGeneticAlgorithm)
										.addComponent(btnGeneticProgramming)
//										.addComponent(btnBoth)
										));
						
						panelAlgorithmLayout.setVerticalGroup(panelAlgorithmLayout.createSequentialGroup()								
										.addComponent(btnGeneticAlgorithm)					
										.addComponent(btnGeneticProgramming)
//										.addComponent(btnBoth)
										);
						
						//test parameter
						GroupLayout panelTTParameterLayput = new GroupLayout((JComponent)panelTTParam);
						panelTTParam.setBorder(BorderFactory.createTitledBorder("Test type parameter's"));
						panelTTParam.setLayout(panelTTParameterLayput);
						{
							jLabelPenalize = new JLabel();
							panelTTParam.add(jLabelPenalize);
							jLabelPenalize.setText("Penalize");
							{
								btnYes = new JRadioButton("yes");
								btnNo = new JRadioButton("no");
								ButtonGroup penalizeGroup = new ButtonGroup();
								penalizeGroup.add(btnYes);
								penalizeGroup.add(btnNo);
								btnYes.setSelected(true);
							}
							jLabelMinDistanceKick = new JLabel();
							panelTTParam.add(jLabelMinDistanceKick);
							jLabelMinDistanceKick.setText("Minimum kick distance [m]");
							{
								jTFDistance = new JTextField();
								jTFDistance.setText("5");
							}
							jLabelMaxAngleDiversion = new JLabel();
							panelTTParam.add(jLabelMaxAngleDiversion);
							jLabelMaxAngleDiversion.setText("Maximum of angle diversion [°]");
							{
								jTFMaxAngleDiversion = new JTextField();
								jTFMaxAngleDiversion.setText("5");
							}
							jLabelLenghtWalk = new JLabel();
							panelTTParam.add(jLabelLenghtWalk);
							jLabelLenghtWalk.setText("Lenght of the walking [m]");
							{
								jTFLenghtWalk = new JTextField();
								jTFLenghtWalk.setText("3");
							}
							jLabelMaxTimeOnMove = new JLabel();
							panelTTParam.add(jLabelMaxTimeOnMove);
							jLabelMaxTimeOnMove.setText("Max time on move [s]");
							{
								jTFTimeOnMove = new JTextField();
								jTFTimeOnMove.setText("20");
							}						
							jLabelNumberTests = new JLabel();
							panelTTParam.add(jLabelNumberTests);
							jLabelNumberTests.setText("Number of test on move");
							{
								SpinnerModel model = new SpinnerNumberModel(2,1,3,1);						
								spNumberOfTest = new JSpinner(model);
							}
							
						}
						//add to test type param										
						panelTTParameterLayput.setHorizontalGroup(panelTTParameterLayput.createSequentialGroup()
								.addGroup(panelTTParameterLayput.createSequentialGroup()
										.addGroup(panelTTParameterLayput.createParallelGroup()
										.addComponent(jLabelPenalize)
										.addComponent(jLabelMinDistanceKick)
										.addComponent(jLabelMaxAngleDiversion)
										.addComponent(jLabelLenghtWalk)
										.addComponent(jLabelMaxTimeOnMove)
										.addComponent(jLabelNumberTests))
										)
								.addGroup(panelTTParameterLayput.createSequentialGroup()
										.addGroup(panelTTParameterLayput.createParallelGroup()
												.addGroup(panelTTParameterLayput.createSequentialGroup()
												.addComponent(btnYes)
												.addComponent(btnNo))
									.addComponent(jTFDistance)
									.addComponent(jTFMaxAngleDiversion)
									.addComponent(jTFLenghtWalk)
									.addComponent(jTFTimeOnMove)
									.addComponent(spNumberOfTest)))
								);
						
						panelTTParameterLayput.setVerticalGroup(panelTTParameterLayput.createSequentialGroup()
								.addGroup(panelTTParameterLayput.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(jLabelPenalize)
										.addComponent(btnYes)
										.addComponent(btnNo))
								.addGroup(panelTTParameterLayput.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(jLabelMinDistanceKick)
										.addComponent(jTFDistance))
								.addGroup(panelTTParameterLayput.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(jLabelMaxAngleDiversion)
										.addComponent(jTFMaxAngleDiversion))
								.addGroup(panelTTParameterLayput.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(jLabelLenghtWalk)
										.addComponent(jTFLenghtWalk))
								.addGroup(panelTTParameterLayput.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(jLabelMaxTimeOnMove)
										.addComponent(jTFTimeOnMove))
								.addGroup(panelTTParameterLayput.createParallelGroup(GroupLayout.Alignment.BASELINE)
										.addComponent(jLabelNumberTests)
										.addComponent(spNumberOfTest))
								);
						
					}
					//add to simulation card
					panelSimLayout.setHorizontalGroup(panelSimLayout.createSequentialGroup()
							.addGroup(panelSimLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(panelEnvironmentSetting)
								.addComponent(panelAlgorithm))
								//.addGroup(panelSimLayout.createSequentialGroup()
										.addGroup(panelSimLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(panelTestType)
								.addComponent(panelTTParam)));
					
					panelSimLayout.setVerticalGroup(panelSimLayout.createSequentialGroup()
							.addGroup(panelSimLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addGroup(panelSimLayout.createSequentialGroup()
									.addComponent(panelEnvironmentSetting)
									.addComponent(panelAlgorithm))
							.addGroup(panelSimLayout.createSequentialGroup()
								.addComponent(panelTestType)
								.addComponent(panelTTParam))));
				}
			
				btnStart = new JButton();
				btnStart.setText("Start simulation");
				btnStop = new JButton();
				btnStop.setText("Stop simulation");
			}
			//pridanie na velku kartu
			tabEvolutionAlgorithmLayout.setHorizontalGroup(tabEvolutionAlgorithmLayout.createSequentialGroup()					
					.addGroup(tabEvolutionAlgorithmLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addGroup(tabEvolutionAlgorithmLayout.createSequentialGroup()
									.addComponent(panelGA)
									.addComponent(panelSimulation))
					
							.addComponent(btnStart)
							.addComponent(btnStop)));
			
			tabEvolutionAlgorithmLayout.setVerticalGroup(tabEvolutionAlgorithmLayout.createSequentialGroup()
					.addGroup(tabEvolutionAlgorithmLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(panelGA)
							.addComponent(panelSimulation))
							.addComponent(btnStart)
							.addComponent(btnStop)
							);
			
			
			
		}
				
				

		JPanel tabAnnotations = new JPanel();
		GroupLayout gl_tabAnnotations = new GroupLayout(
				(JComponent) tabAnnotations);
		tabbedPane.addTab("Annotations", null, tabAnnotations, null);
		tabAnnotations.setLayout(gl_tabAnnotations);
		{
			jPanel2 = new JPanel();
			GroupLayout jPanel2Layout = new GroupLayout((JComponent) jPanel2);
			jPanel2.setLayout(jPanel2Layout);
			jPanel2.setBounds(237, 80, 10, 10);
			jPanel2.setBorder(BorderFactory.createTitledBorder("Settings"));
			{
				jLabel4 = new JLabel();
				jLabel4.setText("Move name");
				jLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
			}
			{
				txtAnnotMove = new JTextField();
				txtAnnotMove.setText("kick_right_fast");
			}
			{
				jLabel5 = new JLabel();
				jLabel5.setText("Destination folder");
				jLabel5.setHorizontalAlignment(SwingConstants.RIGHT);
			}
			{
				txtAnnotDest = new JTextField();
				txtAnnotDest.setText("./annotation/");
			}
			{
				jLabel6 = new JLabel();
				jLabel6.setText("Loops");
				jLabel6.setHorizontalAlignment(SwingConstants.RIGHT);
			}
			{
				txtAnnotLoops = new JTextField();
				txtAnnotLoops.setText("1");
			}
			jPanel2Layout
					.setHorizontalGroup(jPanel2Layout
							.createSequentialGroup()
							.addGroup(
									jPanel2Layout
											.createParallelGroup()
											.addComponent(
													jLabel4,
													GroupLayout.Alignment.LEADING,
													GroupLayout.PREFERRED_SIZE,
													147,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(
													jLabel5,
													GroupLayout.Alignment.LEADING,
													GroupLayout.PREFERRED_SIZE,
													147,
													GroupLayout.PREFERRED_SIZE)
											.addComponent(
													jLabel6,
													GroupLayout.Alignment.LEADING,
													GroupLayout.PREFERRED_SIZE,
													147,
													GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(
									LayoutStyle.ComponentPlacement.RELATED)
							.addGroup(
									jPanel2Layout
											.createParallelGroup()
											.addComponent(
													txtAnnotLoops,
													GroupLayout.Alignment.LEADING,
													0, 492, Short.MAX_VALUE)
											.addGroup(
													GroupLayout.Alignment.LEADING,
													jPanel2Layout
															.createSequentialGroup()
															.addComponent(
																	txtAnnotDest,
																	0,
																	492,
																	Short.MAX_VALUE)
															.addPreferredGap(
																	LayoutStyle.ComponentPlacement.RELATED))
											.addComponent(
													txtAnnotMove,
													GroupLayout.Alignment.LEADING,
													0, 492, Short.MAX_VALUE))
							.addGap(7));
			jPanel2Layout.setVerticalGroup(jPanel2Layout
					.createSequentialGroup()
					.addGroup(
							jPanel2Layout
									.createParallelGroup(
											GroupLayout.Alignment.BASELINE)
									.addComponent(txtAnnotMove,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jLabel4,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jPanel2Layout
									.createParallelGroup(
											GroupLayout.Alignment.BASELINE)
									.addComponent(txtAnnotDest,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jLabel5,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jPanel2Layout
									.createParallelGroup(
											GroupLayout.Alignment.BASELINE)
									.addComponent(txtAnnotLoops,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jLabel6,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 28,
											GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED));
		}
		{
			jPanel1 = new JPanel();
			GroupLayout jPanel1Layout = new GroupLayout((JComponent) jPanel1);
			jPanel1.setLayout(jPanel1Layout);
			jPanel1.setBounds(30, 115, 198, 160);
			jPanel1.setBorder(BorderFactory.createTitledBorder("Ball"));
			{
				jLabel1 = new JLabel();
				jLabel1.setText("X");
				jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
			}
			{
				txtAnnotX = new JTextField();
				txtAnnotX.setText("0.17");
			}
			{
				jLabel2 = new JLabel();
				jLabel2.setText("Y");
				jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
			}
			{
				txtAnnotY = new JTextField();
				txtAnnotY.setText("0.0");
			}
			{
				jLabel3 = new JLabel();
				jLabel3.setText("Radius");
				jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
			}
			{
				txtAnnotRadius = new JTextField();
				txtAnnotRadius.setText("0.02");
			}
			jPanel1Layout.setHorizontalGroup(jPanel1Layout
					.createSequentialGroup()
					.addGroup(
							jPanel1Layout
									.createParallelGroup()
									.addComponent(jLabel1,
											GroupLayout.Alignment.LEADING,
											GroupLayout.PREFERRED_SIZE, 143,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jLabel2,
											GroupLayout.Alignment.LEADING,
											GroupLayout.PREFERRED_SIZE, 143,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jLabel3,
											GroupLayout.Alignment.LEADING,
											GroupLayout.PREFERRED_SIZE, 143,
											GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jPanel1Layout
									.createParallelGroup()
									.addComponent(txtAnnotRadius,
											GroupLayout.Alignment.LEADING, 0,
											491, Short.MAX_VALUE)
									.addComponent(txtAnnotY,
											GroupLayout.Alignment.LEADING, 0,
											491, Short.MAX_VALUE)
									.addComponent(txtAnnotX,
											GroupLayout.Alignment.LEADING, 0,
											491, Short.MAX_VALUE))
					.addContainerGap());
			jPanel1Layout.setVerticalGroup(jPanel1Layout
					.createSequentialGroup()
					.addGroup(
							jPanel1Layout
									.createParallelGroup(
											GroupLayout.Alignment.BASELINE)
									.addComponent(txtAnnotX,
											GroupLayout.Alignment.BASELINE, 0,
											GroupLayout.DEFAULT_SIZE,
											Short.MAX_VALUE)
									.addComponent(jLabel1,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 23,
											GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jPanel1Layout
									.createParallelGroup(
											GroupLayout.Alignment.BASELINE)
									.addComponent(txtAnnotY,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 23,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jLabel2,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 23,
											GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
					.addGroup(
							jPanel1Layout
									.createParallelGroup(
											GroupLayout.Alignment.BASELINE)
									.addComponent(txtAnnotRadius,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 23,
											GroupLayout.PREFERRED_SIZE)
									.addComponent(jLabel3,
											GroupLayout.Alignment.BASELINE,
											GroupLayout.PREFERRED_SIZE, 23,
											GroupLayout.PREFERRED_SIZE))
					.addGap(8));
		}
		{
			btnAnnotate = new JButton();
			btnAnnotate.setText("Annotate");
		}
		gl_tabAnnotations.setVerticalGroup(gl_tabAnnotations
				.createSequentialGroup()
				.addContainerGap()
				.addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 128,
						GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, 116,
						GroupLayout.PREFERRED_SIZE)
				.addComponent(btnAnnotate, GroupLayout.PREFERRED_SIZE,
						GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				.addContainerGap(20, Short.MAX_VALUE));
		gl_tabAnnotations
				.setHorizontalGroup(gl_tabAnnotations
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_tabAnnotations
										.createParallelGroup()
										.addComponent(jPanel2,
												GroupLayout.Alignment.LEADING,
												0, 663, Short.MAX_VALUE)
										.addComponent(jPanel1,
												GroupLayout.Alignment.LEADING,
												0, 663, Short.MAX_VALUE)
										.addGroup(
												GroupLayout.Alignment.LEADING,
												gl_tabAnnotations
														.createSequentialGroup()
														.addGap(0, 562,
																Short.MAX_VALUE)
														.addComponent(
																btnAnnotate,
																GroupLayout.PREFERRED_SIZE,
																101,
																GroupLayout.PREFERRED_SIZE)))
						.addContainerGap());
	}

	/**
	 * Upon finishing a test case, resets the state and makes it possible to run a new one
	 */
	@Override
	public void testFinished(TestCaseResult result) {
		logger.info("Test finished with result " + result.toString());
		isTestRunning = false;
		btnTestCase.setText("Start test case");
	}

	@Override
	public void start() {
		new Thread() {
			public void run() {
				setVisible(true);
			}
		}.start();
	}

	@Override
	public boolean shoudExitOnEmptyQueue() {
		return false;
	}

	/**
	 * Updates the displayed simulation data.
	 */
	@Override
	public void update() {
		if (firstUpdate) {
			atStart = new MeasurableInformation(monitor.getSimulationState());
			firstUpdate = false;
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				SimulationState ss = monitor.getSimulationState();
				MeasurableInformation now = new MeasurableInformation(ss);
				MeasurableInformation dist = MeasurableInformation.createDistanceInformation(now, atStart);
				
				gameView.setSimulationState(ss);
				gameView.requestUpdate();

				StringBuilder sbInfo = new StringBuilder();
				sbInfo.append("General\n=======\n");
				sbInfo.append("Time start: ").append(format(atStart.time)).append("\n");
				sbInfo.append("Time now: ").append(format(now.time)).append("\n");
				sbInfo.append("Time passed: ").append(format(dist.time)).append("\n\n");

				sbInfo.append("Ball\n======\n");
				sbInfo.append("At start: ").append(atStart.ballPosition.toString()).append("\n");
				sbInfo.append("Now: ").append(now.ballPosition.toString()).append("\n");
				sbInfo.append("Dist: ").append(dist.ballPosition.toString()).append("\n");
				sbInfo.append("2D Dist: ").append(atStart.ballPosition.getXYDistanceFrom(now.ballPosition)).append("\n\n");

				sbInfo.append("First existing agent\n======\n");
				sbInfo.append("At start: ").append(atStart.agentPosition.toString()).append("\n");
				sbInfo.append("Now: ").append(now.agentPosition.toString()).append("\n");
				sbInfo.append("Dist: ").append(dist.agentPosition.toString()).append("\n");
				sbInfo.append("2D Dist: ").append(atStart.agentPosition.getXYDistanceFrom(now.agentPosition)).append("\n");

				sbInfo.append("Rotation\n======\n");
				sbInfo.append("At start: ").append(atStart.agentRotation.toString()).append("\n");
				sbInfo.append("Now: ").append(now.agentRotation.toString()).append("\n");
				sbInfo.append("Dist: ").append(dist.agentRotation.toString()).append("\n");

				txtJimInfo.setText(sbInfo.toString());
			}
		});
	}

	/**
	 * Convenience method. Formats a number to a string with 3 decimal points
	 */
	private String format(double d) {
		return String.format("%.3f", d);
	}

	/**
	 * Starts/stops the monitoring of the server by the test framework
	 */
	public void registerMonitor() {
		if (!monitorState) {
			monitor.getSimulationState().registerObserver(this);
			btnConnect.setText("Stop monitoring");
			monitorState = true;
		}else{
			monitor.getSimulationState().unregisterObserver(this);
			btnConnect.setText("Start monitoring");
			monitorState = false;
		}
	}

	/**
	 * 
	 * Parses string in x:y:z format to a 3D point
	 * 
	 * @param text the input string
	 * @return 3d point
	 * @throws IllegalArgumentException if the number of items isn't 3 or if they're not valid numbers
	 */
	private Point3D getPoint3D(String text) throws IllegalArgumentException {
		String[] split = text.split(":");
		if (split.length != 3) {
			throw new IllegalArgumentException(
					"String to split doesn't have 3 items: " + text);
		}
		try {
			double x = new Double(split[0]).doubleValue();
			double y = new Double(split[1]).doubleValue();
			double z = new Double(split[2]).doubleValue();
			return new Point3D(x, y, z);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"String isn't a valid sequence of numbers: " + text);
		}
	}

	/* (non-Javadoc)
	 * @see sk.fiit.testframework.monitor.IAgentMonitorListener#receivedMessage(int, java.lang.String, sk.fiit.testframework.monitor.AgentMonitorMessage)
	 * poslany model sveta od hraca (vykonat update UI)
	 */
	@Override
	public void receivedMessage(int uniform, String team, AgentMonitorMessage message) {
//		AgentMonitorMessage.WorldModel modelMessage = (WorldModel) message;
//		sk.fiit.jim.agent.models.WorldModel model = modelMessage.model;
	}

	/* (non-Javadoc)
	 * @see sk.fiit.testframework.communication.agent.IAgentManagerListener#agentAdded(sk.fiit.testframework.communication.agent.AgentJim)
	 */
	@Override
	public void agentAdded(AgentJim agent) {
		if (waitingForAgent) {
			if (agent.toString().equals(awaitedAgent)) {
				waitingForAgent = false;
				btnAddAgent.setEnabled(true);
				btnAddAgent.setText("Add");
			}
		}
	}

	//unused interface methods
	public void agentRemoved(AgentJim agent) {}
	public void agentOutputLine(AgentJim agent, String line) {}
}
