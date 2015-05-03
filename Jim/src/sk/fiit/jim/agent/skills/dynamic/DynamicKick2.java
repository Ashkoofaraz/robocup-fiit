package sk.fiit.jim.agent.skills.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import sk.fiit.jim.agent.models.AgentModel;
import sk.fiit.jim.agent.models.BodyPart;
import sk.fiit.jim.agent.models.EnvironmentModel;
import sk.fiit.jim.agent.models.WorldModel;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.moves.Phase;
import sk.fiit.jim.agent.moves.kinematics.Orientation;
import sk.fiit.robocup.library.geometry.Point3D;

public class DynamicKick2 extends DynamicMove{

    static final class Tuple {
        List<Point3D> points;
        List<Orientation> orientations;
    }
    
    private AgentModel agentModel = AgentModel.getInstance();
    
    
    private static boolean kicked = false;
    
    private static int steps = 0;
    
    private static boolean stepped = false;
	@Override
	public LowSkill pickLowSkill() {
        if(!kicked) {
            kicked = true;
            return createDynamicKick("left");
        }
        return null;
	}

	@Override
	public void checkProgress() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Vytvara dynamicky kop
	 * Na zaciatku sa vypocitaju parametre kopu a nasledne sa nastavia fazy
	 * Kop je zalozeny na kick_right_normal_stand a kick_left_normal_stand ma 6 faz pricom dynamicky sa nastavuju fazy 2-3
	 * Je mozne bud vsetky fazy zadat priamo, alebo je mozne nacitat existujuci kop a nahradit niektore jeho fazy alebo len hodnoty vo fazach
	 */
	public LowSkill createDynamicKick(String side)
	{			
		List<Phase> phases = getBaseSkillPhases(side);
	    
		String ui = UUID.randomUUID().toString();

		ArrayList<String> types = new ArrayList<String>();
		types.add("kick");	 
		
		LowSkill ls = addSkill("dynamic_kick_" + ui);

//	    alterKickPhases(phases, side);
			
		addPhases(phases, ls.name);
		return ls;
	}
	
	private List<Phase> getBaseSkillPhases(String side)
	{
		LowSkill baseSkill = null;
		if(side.equals("right"))
		{
			baseSkill = LowSkills.get("kick_right_normal_stand");
//			baseSkill = LowSkills.get("kick_step_strong_right");
		}
		else
		{
			baseSkill = LowSkills.get("kick_left_normal_stand");
//			baseSkill = LowSkills.get("step_left_very_small");
//			baseSkill = LowSkills.get("kick_step_strong_left");
		}
		
		return getPhasesForSkill(baseSkill);		
	}
	
	private Tuple createSequenceLeftLeg() {
//		Point3D point1 = new Point3D(0.0, 55.0, -385.0);
//		Orientation orientation1 = Orientation.fromRadians(0.0, 0.0, 0.0);
//
//		Point3D point2 = new Point3D(0, 245, -305);
//		Orientation orientation2 = Orientation.fromRadians(0.0, 0.0, 0.0);
//
//		Point3D point3 = new Point3D(190, 55, -305);
//		Orientation orientation3 = Orientation.fromDegrees(0, 90, 45);
//
//		Point3D point4 = new Point3D(188, 74, -303);
//		Orientation orientation4 = Orientation.fromRadians(0.39, -0.79, 0);

//		Point3D point5 = new Point3D(234.85, 55.00, -199.85);
//		Orientation orientation5 = Orientation.fromRadians(-0.18, -1.57, 0.18);
		
//		Point3D point5 = new Point3D(-40, 150.0, -200.0);
//		Orientation orientation5 = Orientation.fromRadians(0.0, 0.0, 0.0);
//		
//		Point3D point6 = new Point3D(0, 150.0, -200.0);
//		Orientation orientation6 = Orientation.fromRadians(0.0, 0.0, 0.0);
	    
//	  Point3D point6 = new Point3D(0.0, 55.0, -385.0);
//      Orientation orientation6 = Orientation.fromRadians(0.0, 0.0, 0.0);

   // prednozenie s pokrcenym kolenom
//      ForwardKinematicResult frk = new ForwardKinematicResult(Kinematics.getInstance().getForwardLeftLeg(0, Math.PI/4, Math.PI/2, -Math.PI/2, 0, 0));
//      System.out.println(frk.getEndPoint() + " " + frk.getOrientation());
//      Point3D point6 = frk.getEndPoint();
//      Orientation orientation6 = frk.getOrientation();
//      
//      ForwardKinematicResult frk2 = new ForwardKinematicResult(Kinematics.getInstance().getForwardLeftLeg(0, Math.PI/4, Math.PI/2, -Math.PI/4, 0, 0));
//      System.out.println(frk2.getEndPoint() + " " + frk.getOrientation());
//      Point3D point7 = frk2.getEndPoint();
//      Orientation orientation7 = frk2.getOrientation();
	    
//	    Point3D point6 = new Point3D(-107, 145, -250);
//        Orientation orientation6 = Orientation.fromRadians(0, 0, 0);
        
	    Point3D point7 = new Point3D(-193.6619, 74.1639, -197.6524);
        Orientation orientation7 = Orientation.fromRadians(0.1263, 0.7268, 0.3295);
        
//        Point3D point8 = new Point3D(-203.6619, 94.1639, -217.6524);
//        Orientation orientation8 = Orientation.fromRadians(0.1263, 0.7268, 0.3295);
        
		List<Point3D> points = new ArrayList<>();
		List<Orientation> orientations = new ArrayList<>();
//		points.add(point1);
//		points.add(point2);
//		points.add(point3);
//		points.add(point4);
//		points.add(point5);
//		points.add(point6);
		points.add(point7);
//		points.add(point8);

//		orientations.add(orientation1);
//		orientations.add(orientation2);
//		orientations.add(orientation3);
//		orientations.add(orientation4);
//		orientations.add(orientation5);
//		orientations.add(orientation6);
		orientations.add(orientation7);
//		orientations.add(orientation8);

		Tuple retVal = new Tuple();
		retVal.points = points;
		retVal.orientations = orientations;
		return retVal;
	}
	
	private Tuple createSequenceRightArm() {
//        Point3D point1 = new Point3D(195, 98, 75);
//        Orientation orientation1 = Orientation.fromRadians(0.0, 0.0, 0.0);

        Point3D point2 = new Point3D(195, -98, 75);
        Orientation orientation2 = Orientation.fromRadians(0.0, 0.0, 0.0);

//        Point3D point3 = new Point3D(0, 98, -120);
//        Orientation orientation3 = Orientation.fromDegrees(45, 90, 45);
//
//        Point3D point4 = new Point3D(0, 98, 270);
//        Orientation orientation4 = Orientation.fromRadians(0.0, 0.0, 0.0);
//
//        Point3D point5 = new Point3D(170, 98, 100);
//        Orientation orientation5 = Orientation.fromRadians(0.0, 0.0, 0.0);
//
//        Point3D point6 = new Point3D(123.00, 203.86, -26.25);
//        Orientation orientation6 = Orientation.fromRadians(0.93, 0.42, 0.84);

        List<Point3D> points = new ArrayList<>();
        List<Orientation> orientations = new ArrayList<>();
//        points.add(point1);
        points.add(point2);
//        points.add(point3);
//        points.add(point4);
//        points.add(point5);
//        points.add(point6);

//        orientations.add(orientation1);
        orientations.add(orientation2);
//        orientations.add(orientation3);
//        orientations.add(orientation4);
//        orientations.add(orientation5);
//        orientations.add(orientation6);
        
        Tuple retVal = new Tuple();
        retVal.points = points;
        retVal.orientations = orientations;
        return retVal;
    }
	
	private void alterKickPhases(List<Phase> phases, String side) 
    {
        Tuple t = createSequenceLeftLeg();
      List<Phase> newPhases = createPhasesDynamicMove("leftLeg", t.points, t.orientations);

        List<Phase> alteredPhases = new ArrayList<>();
//        Map<Joint, Double> result = new HashMap<>();
//        result.put(Joint.LLE2, 45.0);
//        result.put(Joint.LLE3, 80.0);
//        result.put(Joint.LLE5, 40.0);
//        alteredPhases.add(createPhase(300, result ));
        
        alteredPhases.addAll(newPhases);
        
        phases.remove(4);
//        phases.remove(4);
//        phases.remove(4);
        phases.addAll(4, alteredPhases);            
    }
}
