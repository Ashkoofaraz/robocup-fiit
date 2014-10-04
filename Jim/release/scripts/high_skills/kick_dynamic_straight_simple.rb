require 'high_skills/ruby_high_skill.rb'

include_class Java::sk.fiit.robocup.library.geometry.Vector3D

class RubyKickDynamicStraightSimple < Java::sk.fiit.jim.agent.skills.dynamic.DynamicKickStraight
  
  def initialize (kick_target, leg)
    super()
    puts "Test"
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    @agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance
    @mathModel = Java::sk.fiit.robocup.library.geometry.Vector3D.clone 
    @worldModel = Java::sk.fiit.jim.agent.models.WorldModel.getInstance   
    
    @kick_target = kick_target
    @leg = leg
    @kicked = false
  end

  def pickLowSkill
    @agentInfo.loguj('Vyber skillu')                           
    ball_pos = @worldModel.getBall().getPosition
     
    @kick_target_dist = @agentInfo.calculateDistance(ball_pos, @kick_target)
    puts "dist:" 
    puts @kick_target_dist
    puts "Dynamic kick"
    
    if(!@kicked)
      @kicked = true
      createDynamicKick(@leg, @kick_target_dist);
    else
      return nil
    end
  end
  
  def checkProgress
  end
end

#in order not to defer the startup cost
RubyKickDynamicStraightSimple.new(@kick_target, @leg)