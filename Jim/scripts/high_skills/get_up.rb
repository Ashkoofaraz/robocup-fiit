require 'high_skills/ruby_high_skill.rb'

class GetUp < RubyHighSkill
  
  def initialize validity_proc
    super()
    @validity_proc = validity_proc
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    @agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance
  end
  
  def pickLowSkill
    @agentModel.gotUp
    #return nil if not @validity_proc.call
    
    if me.lying_on_back? 
      puts "on back"
    	return get_skill("stand_back")
    elsif me.lying_on_belly?
      puts "on belly"
    	return get_skill("stand_front")
    elsif(!me.on_ground?)
      puts "standing"
      return nil
    else
      puts "dont know"
    	return get_skill("stand_back")
    end
    

  end
  
  def checkProgress
    #do nothing
  end
end

#in order not to defer the startup cost
#GetUp.new nil
GetUp.new(Proc.new{me.on_ground?})