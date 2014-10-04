require 'high_skills/ruby_high_skill.rb'
include_class Java::sk.fiit.jim.agent.trajectory.TrajectoryRealTime

class Trajectory < RubyHighSkill

	def initialize(x, y, z, phi)
	super()
	@tp = Java::sk.fiit.jim.agent.trajectory.TrajectoryRealTime.getInstance
	@tp.plan(x, y, z, phi)
	@a = Java::sk.fiit.jim.agent.AgentInfo.getInstance
	end

	def pickLowSkill	
		if me.on_ground?
			@a.loguj("Fallen !")\
			#provizorne riesenie
			#tu by sa mal skill ukoncit, pretoze pri pade sa moze agent vychylit z trajektorie
			return get_skill("stand_back") if me.lying_on_back?
			return get_skill("stand_front") if me.lying_on_belly?
			return nil
		else
			@a.loguj("abc :" + @tp.peekName().to_s)
			if(@tp.peekName().eql? "null")
				return nil 
			else
				return get_skill(@tp.getMoveName())
			end
		end
	end
	
	def checkProgress
	end

end