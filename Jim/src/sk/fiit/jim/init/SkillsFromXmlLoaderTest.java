package sk.fiit.jim.init;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

import static org.hamcrest.Matchers.closeTo;

import sk.fiit.jim.agent.moves.Joint;
import sk.fiit.jim.agent.moves.LowSkills;
import sk.fiit.jim.agent.moves.Phase;
import sk.fiit.jim.agent.moves.Phases;
import sk.fiit.jim.agent.moves.SkipFlag;
import sk.fiit.jim.agent.moves.SkipFlags;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 *  SkillsFromXmlLoaderTest.java
 *  
 *  >>>> @see fixtures/test_moves.xml <<<<
 *
 *@Title	Jim
 *@author	marosurbanec
 */
public class SkillsFromXmlLoaderTest {
	@BeforeClass
	public static void loadXml(){
		try{
			Phases.reset();
			SkipFlags.reset();
			LowSkills.reset();
			new SkillsFromXmlLoader(new File("./fixtures")).load();
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	@Test
	public void shouldCreateLowSkills(){
		assertTrue(LowSkills.exists("walk_straight"));
		assertThat("walk_start", is(equalTo(LowSkills.get("walk_straight").initialPhase)));
	}
	
	@Test
	public void shouldCreateAndPopulatePhases(){
		assertTrue(Phases.exists("walk_start"));
		assertFalse(Phases.get("walk_start").isFinal);
		assertTrue(Phases.get("walk_final").isFinal);
		assertThat(Phases.get("walk_start").next, is("walk2"));
		assertTrue(Phases.get("walk_final").duration == 0.4);
		assertThat(Phases.get("stop_walking").duration, is(closeTo(0.22, 0.001)));
		assertTrue(Phases.get("walk2").duration == 0.4);
		assertThat(Phases.get("walk_final").finalizationPhase, is(equalTo("stop_walking")));
	}
	
	@Test
	public void shouldCreateAndPopulateEffectorTags(){
		assertTrue(Phases.get("walk_start").effectors.size() == 2);
		assertThat(Phases.get("walk_start").effectors.get(0).effector, is(equalTo(Joint.RAE1)));
		assertTrue(Phases.get("walk_start").effectors.get(1).endAngle == -90.0);
	}
	
	@Test
	public void shouldCalculateValuesWithConstants(){
		Phase phaseWithConstants = Phases.get("walk2");
		double endAngle = phaseWithConstants.effectors.get(0).endAngle;
		assertThat(endAngle, is(closeTo(35.0, .1)));
		double endAngle2 = phaseWithConstants.effectors.get(1).endAngle;
		assertThat(endAngle2, is(closeTo(30, .01)));
	}
	
	@Test
	public void shouldPopulateSkipFlags(){
		Phase basicStancePosition = Phases.get("walk_start");
		assertThat(basicStancePosition.skipIfFlag, is(new SkipFlag("inBasicPosition")));
		assertThat(basicStancePosition.setFlagFalse, is(nullValue()));
		assertThat(basicStancePosition.setFlagTrue, is(new SkipFlag("inBasicPosition")));
	}
}