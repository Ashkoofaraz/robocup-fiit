package sk.fiit.jim.agent.skills.dynamic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.moves.LowSkill;
import sk.fiit.jim.agent.moves.Phase;
import sk.fiit.jim.agent.moves.kinematics.Kinematics;
import sk.fiit.jim.agent.moves.kinematics.Orientation;
import sk.fiit.robocup.library.geometry.Point3D;

/**
 * 
 * <p>
 * Class for creating dynamic moves for Nao based on sequences of end effector positions and orientations.
 * </p>
 * <p>
 * Created move is "pure" dynamic. It is not dependent on any of static defined moves in XML files.
 * </p>
 * @author Pidanic
 *
 */
public abstract class DynamicMove extends DynamicSkill
{

    /**
     * 
     * Creates {@link LowSkill} which consists of sequence of {@link Phase}s. Phase specifies joint angle values to move on based on
     * positions and orientation for end effector.
     * 
     * @param limb name of a limb to move.
     * @param points sequence of positions for end effector to move.
     * @param orientations sequence of orientatins for end effector to move.
     * @return newly created dynamic low skill
     * @throws IllegalArgumentException if {@code points} size not equal {@code orientations} size or
     *              if {@code limb} is other than {@code "leftArm", "leftLeg", "rightArm", "rightLeg"}}
     */
    public LowSkill createDynamicMove(String limb, List<Point3D> points, List<Orientation> orientations)
    {
        List<Phase> phases = this.createPhasesDynamicMove(limb, points, orientations);
        
        UUID uuid = UUID.randomUUID();
        String skillName = "dynamic_move" + uuid.toString(); 
        
        LowSkill ls = addSkill(skillName);
        addPhases(phases, skillName);
        System.out.println("addedPhases");
        return ls;
    }
    
    
    public List<Phase> createPhasesDynamicMove(String limb, List<Point3D> points, List<Orientation> orientations)
    {
        if(points.size() != orientations.size())
        {
            throw new IllegalArgumentException("points size must equal orientations size");
        }
        List<Phase> phases = new ArrayList<Phase>();
        for(int i = 0; i < points.size(); i++)
        {
            Kinematics kinematics = Kinematics.getInstance();
            Map<Joint, Double> result;
            if("leftLeg".equals(limb))
            {
                result = kinematics.getInverseLeftLeg(points.get(i), orientations.get(i));
            }
            else if("rightLeg".equals(limb))
            {
                result = kinematics.getInverseRightLeg(points.get(i), orientations.get(i));
            }
            else if("leftArm".equals(limb))
            {
                result = kinematics.getInverseLeftArm(points.get(i), orientations.get(i));
            }
            else if("rightArm".equals(limb))
            {
                result = kinematics.getInverseRightArm(points.get(i), orientations.get(i));
            }
            else
            {
                throw new IllegalArgumentException("unknown limb: " + limb);
            }
            Phase phase = createPhase(300, result);
            phases.add(phase);
        }
        System.out.println("phases created");
        return phases;
    }
    
}
