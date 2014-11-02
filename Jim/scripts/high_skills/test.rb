require 'high_skills/ruby_high_skill.rb'
include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::java.lang.Math

class Testt < RubyHighSkill
	def initialize 
      super()
      #@validity_proc = validity_proc
      @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
      @agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance
      
      @done = false;
      #@rightLook = false;
	  #@downLook = false
	end
  
	def pickLowSkill
		if(!@done)
			!@done = true
			return get_skill("aaa")
		else
			nil
		end
	end
	
	def checkProgress
    #do nothing
	end
end

Testt.new