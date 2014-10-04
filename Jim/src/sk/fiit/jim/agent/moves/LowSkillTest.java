package sk.fiit.jim.agent.moves;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import sk.fiit.jim.agent.models.EnvironmentModel;
import sk.fiit.jim.init.SkillsFromXmlLoaderTest;

/**
 *  LowSkillTest.java
 *
 *@Title	Jim
 *@author	marosurbanec
 */
public class LowSkillTest{
	private LowSkill testLowSkill;

	@Before
	public void loadXml(){
		Phases.reset();
		SkipFlags.reset();
		LowSkills.reset();
		EnvironmentModel.SIMULATION_TIME = 0.0;
		
		SkillsFromXmlLoaderTest.loadXml();
		testLowSkill = LowSkills.get("walk_straight");
		testLowSkill.reset();
	}
	
	@Test
	public void shouldBeginWithInitialPhase(){
		testLowSkill.step();
		assertThat(testLowSkill.activePhase.name, is(equalTo(testLowSkill.initialPhase)));
	}
	
	@Test
	public void shouldSwitchToNextPhase(){
		testLowSkill.step();
		EnvironmentModel.SIMULATION_TIME += 200.0;
		testLowSkill.step();
		assertThat(testLowSkill.activePhase.name, is(equalTo("walk2")));
	}
	
	@Test
	public void shouldSetFlags(){
		assertThat(SkipFlags.isTrue(new SkipFlag("inBasicPosition")), is(false));
		testLowSkill.step();
		assertThat(SkipFlags.isTrue(new SkipFlag("inBasicPosition")), is(false));
		EnvironmentModel.SIMULATION_TIME += 200.0;
		testLowSkill.step();
		assertThat(SkipFlags.isTrue(new SkipFlag("inBasicPosition")), is(true));
		EnvironmentModel.SIMULATION_TIME += 200.0;
		testLowSkill.step();
		assertThat(SkipFlags.isTrue(new SkipFlag("inBasicPosition")), is(false));
	}
	
	@Test
	public void shouldSkipPhaseIfFlagIsSet(){
		SkipFlags.setTrue(new SkipFlag("inBasicPosition"));
		testLowSkill.step();
		assertThat(testLowSkill.activePhase.name, is(equalTo("walk2")));
	}
}