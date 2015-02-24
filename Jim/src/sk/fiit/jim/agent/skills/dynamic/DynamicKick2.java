package sk.fiit.jim.agent.skills.dynamic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.moves.Phase;
import sk.fiit.jim.agent.moves.kinematics.Orientation;
import sk.fiit.jim.agent.skills.dynamic.DynamicKick.Tuple;
import sk.fiit.robocup.library.geometry.Point3D;

public class DynamicKick2 extends DynamicMove{

	@Override
	public LowSkill pickLowSkill() {
		// TODO Auto-generated method stub
		return createDynamicKick("left");
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
		
		LowSkill ls = addSkill("dynamic_kick" + ui);

	    alterKickPhases(phases, side);
			
		addPhases(phases, ls.name);
		return ls;
	}
	
	private List<Phase> getBaseSkillPhases(String side)
	{
		LowSkill baseSkill = null;
		if(side.equals("right"))
		{
			baseSkill = LowSkills.get("kick_right_normal_stand");
		}
		else
		{
			baseSkill = LowSkills.get("kick_left_normal_stand");
		}
		
		return getPhasesForSkill(baseSkill);		
	}
	
	/*
	 * Vytvara kop
	 * Poradie faz je dolezite, budu na seba nadvazovat v poradi ako su pridane do zoznamu
	 */
	private void alterKickPhases(List<Phase> phases, String side) 
	{
		Tuple t = createSequenceRightArm();
//		List<Phase> alteredPhases = getPhasesForSkill(createDynamicMove("rightArm", t.points, t.orientations));

		List<Phase> alteredPhases = new ArrayList<>();
		Map<Joint, Double> result = new HashMap<>();
		result.put(Joint.LLE1, -50.0);
        alteredPhases.add(createPhase(300, result ));
		phases.remove(3);
		phases.addAll(3, alteredPhases);			
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
	    
	    Point3D point6 = new Point3D(0, 150.0, -200.0);
      Orientation orientation6 = Orientation.fromRadians(0.0, 0.0, 0.0);

		List<Point3D> points = new ArrayList<>();
		List<Orientation> orientations = new ArrayList<>();
//		points.add(point1);
//		points.add(point2);
//		points.add(point3);
//		points.add(point4);
//		points.add(point5);
		points.add(point6);

//		orientations.add(orientation1);
//		orientations.add(orientation2);
//		orientations.add(orientation3);
//		orientations.add(orientation4);
//		orientations.add(orientation5);
		orientations.add(orientation6);

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
	
	// TODO tu mam upraveny Mestanikov pohyb tak, ze dvihne nohu.
//	private void alterKickPhases(List<Phase> phases, String side) 
//    {
//        Tuple t = createSequenceRightArm();
////      List<Phase> alteredPhases = getPhasesForSkill(createDynamicMove("rightArm", t.points, t.orientations));
//
//        List<Phase> alteredPhases = new ArrayList<>();
//        Map<Joint, Double> result = new HashMap<>();
//        result.put(Joint.LLE1, -50.0);
//        alteredPhases.add(createPhase(300, result ));
//        phases.remove(3);
//        phases.addAll(3, alteredPhases);            
//    }
}
