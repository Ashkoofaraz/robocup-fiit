package sk.fiit.jim.agent.skills.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.Phase;
import sk.fiit.jim.agent.moves.ik.Orientation;
import sk.fiit.jim.agent.moves.ik.Kinematics;
import sk.fiit.jim.agent.skills.DynamicSkill;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * 
 * @author Pidanic
 *
 */
public abstract class DynamicMove extends DynamicSkill
{

    public LowSkill createDynamicMove()
    {
        Kinematics kin = Kinematics.getInstance();
        Point3D end1 = new Point3D(195, 98, 75);
        Orientation angle = Orientation.fromDegrees(0, 0, 0);
        Map<Joint, Double> result1 = kin.getInverseLeftArm(end1, angle);
        System.out.println("Kinematics result " + result1);
        
        Point3D end2 = new Point3D(0, 293, 75);
        Map<Joint, Double> result2 = kin.getInverseLeftArm(end2, angle);
        System.out.println("Kinematics result " + result2);
        
        Point3D end3 = new Point3D(0, 98, -120);
        Map<Joint, Double> result3 = kin.getInverseLeftArm(end3, angle);
        System.out.println("Kinematics result " + result3);
        
        Point3D end4 = new Point3D(0, 98, 270);
        Map<Joint, Double> result4 = kin.getInverseLeftArm(end4, angle);
        System.out.println("Kinematics result " + result4);
        
        Point3D end5 = new Point3D(170, 98, 100);
        Map<Joint, Double> result5 = kin.getInverseLeftArm(end5, angle);
        System.out.println("Kinematics result " + result5);
        
        Phase phase1 = createPhase(300, result1);
        Phase phase2 = createPhase(300, result2);
        Phase phase3 = createPhase(300, result3);
        Phase phase4 = createPhase(300, result4);
        Phase phase5 = createPhase(300, result5);
        List<Phase> phases = new ArrayList<Phase>();
        phases.add(phase1);
        phases.add(phase2);
        phases.add(phase3);
        phases.add(phase4);
        phases.add(phase5);
        //Phase finalPhase = new Phase();
        //phases.add(finalPhase);
        
        System.out.println("created phases " + phases);
        
        UUID uuid = UUID.randomUUID();
        String skillName = "dynamic_move" + uuid.toString(); 
        
        LowSkill ls = addSkill(skillName);
        
        addPhases(phases, skillName);
        System.out.println("addedPhases");
        return ls;
    }
}
