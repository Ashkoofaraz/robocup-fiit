package sk.fiit.jim.tests.highskill;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import sk.fiit.jim.Settings;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill;
import sk.fiit.jim.agent.highskill.move.MovementHighSkill.MovementSpeedEnum;
import sk.fiit.jim.annotation.data.AnnotationManager;
import sk.fiit.robocup.library.geometry.Vector3D;

public class MovementHighSkillTest {

	@BeforeClass
	public static void initTest(){
		try {
			AnnotationManager.getInstance().loadLowSkills("." + Settings.getString("Jim_root_path") + "/moves");
			AnnotationManager.getInstance().loadAnnotations("." + Settings.getString("Jim_root_path") + "/moves/annotations");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void move(){
		MovementHighSkill mhs = new MovementHighSkill();
		String s =mhs.move(Vector3D.cartesian(5,12,0), MovementSpeedEnum.WALK_FAST);
		System.out.println(s);
		assertTrue(s.length() > 0);
	}
}
