require 'high_skills/ruby_high_skill.rb'

include_class Java::sk.fiit.robocup.library.geometry.Vector3D

# dynamic move prototype
# pidanic
class RubyDynamicMove < Java::sk.fiit.jim.agent.skills.dynamic.DynamicMove
  
  def initialize
    super()
    puts "initialized dynamic move"
    #@agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    #@agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance
    #@mathModel = Java::sk.fiit.robocup.library.geometry.Vector3D.clone 
    #@worldModel = Java::sk.fiit.jim.agent.models.WorldModel.getInstance   
    
    #@kick_target = kick_target
    #@leg = leg
    @moved = false
  end

  def pickLowSkill
	puts "RubyDynamicMove pickLowSkill"
    #@agentInfo.loguj('Vyber skillu')                           
    #ball_pos = @worldModel.getBall().getPosition
     
    #@kick_target_dist = @agentInfo.calculateDistance(ball_pos, @kick_target)
    #puts "dist:" 
    #puts @kick_target_dist
    #puts "Dynamic kick"
    
    if(!@moved)
      @moved = true
	  puts "Creating dynamic move"
      createDynamicMove();
    else
      return nil
    end
  end
  
  def checkProgress
  end
end

#in order not to defer the startup cost
RubyDynamicMove.new