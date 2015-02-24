package sk.fiit.jim.tests.highskill;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

import sk.fiit.jim.agent.highskill.Localize;
import sk.fiit.jim.agent.skills.HighSkill;

public class HighSkillRunnerTest {

	@Test
	public void memoryLeakTest() throws InterruptedException {
		if(true){
			return;
		}
		//need to run in cmd command jvisualvm (java implicit profiler) and open monitor view 
		Queue<HighSkill> highSkillQueue = new LinkedList<HighSkill>();

		for (int j = 0; j < 1000; j++) {
			for (int i = 0; i < 1000000; i++) {
				highSkillQueue.add(new Localize());
			}
			while (!highSkillQueue.isEmpty()) {
				highSkillQueue.poll();
			}

			for (int i = 0; i < 1000000; i++) {
				highSkillQueue.add(new Localize());
			}
			while (!highSkillQueue.isEmpty()) {
				highSkillQueue.poll();
			}
			for (int i = 0; i < 1000000; i++) {
				highSkillQueue.add(new Localize());
			}
			while (!highSkillQueue.isEmpty()) {
				highSkillQueue.poll();
			}
			for (int i = 0; i < 1000000; i++) {
				highSkillQueue.add(new Localize());
			}
			while (!highSkillQueue.isEmpty()) {
				highSkillQueue.poll();
			}
			for (int i = 0; i < 1000000; i++) {
				highSkillQueue.add(new Localize());
			}
			while (!highSkillQueue.isEmpty()) {
				highSkillQueue.poll();
			}

		}
	}
}
