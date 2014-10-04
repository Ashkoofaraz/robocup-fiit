require 'high_skills/ruby_high_skill.rb'

include_class Java::sk.fiit.robocup.library.geometry.Vector3D

class RubyKickDynamicStraight < Java::sk.fiit.jim.agent.skills.dynamic.DynamicKickStraight
  
  def initialize (kick_target)
    super()
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    @agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance
    @mathModel = Java::sk.fiit.robocup.library.geometry.Vector3D.clone 
    @worldModel = Java::sk.fiit.jim.agent.models.WorldModel.getInstance   
    
    @kick_target = kick_target
  end

  def pickLowSkill
    ball_pos = @agentInfo.ballControlPosition()
        
    puts ball_pos.getY()
    puts ball_pos.getX()
    
    case
      when (EnvironmentModel.beamablePlayMode and not EnvironmentModel.isKickOffPlayMode) :
        return nil
      when @agentModel.fallen? :
        puts "fall"
        return nil
      when ball_unseen > 3 :
        puts "ball unseen"
        return nil

      when ball_pos.getY().abs > 0.7 
        puts "very big Y"
        return nil    
        when ball_pos.getX().abs > 0.7
              puts "very big X"
              return nil
                  
    when (ball_pos.getY() < 0.7 and ball_pos.getY() > 0.20) 
        puts "go a bit"
        return get_skill("walk_slow2")    
   
      when (ball_pos.getX() > 0.1 and ball_pos.getX() < 0.7)
        puts "big X"
        return get_skill("step_right")
      when (ball_pos.getX() < -0.1 and ball_pos.getX() > -0.7)
        puts "small X"
        return get_skill("step_left")
    
      when (ball_pos.getX() > 0.0) 
        puts "kick right"
        @ballR=ball_pos.getR()        
        kick_dist "right", ball_pos
      when (ball_pos.getX() < 0.0) 
        puts "kick left"        
        @ballR=ball_pos.getR()
        kick_dist "left", ball_pos
     
      else
        puts "???"
        return nil
      end

  end

  def kick_dist leg, ball_pos  
   @kick_target
   ball_pos = @worldModel.getBall().getPosition
    
   @kick_target_dist = @agentInfo.calculateDistance(ball_pos, @kick_target)
   puts "Dynamic"
   createDynamicKick(leg, @kick_target_dist); 
  end 
  
  def checkProgress
  end
  
  def ball_unseen
    EnvironmentModel.SIMULATION_TIME - WorldModel.getInstance.getBall.getLastTimeSeen()
  end
  
  def get_skill skill_name
      log "Chosen skill: #{skill_name}"
      Java::sk.fiit.jim.agent.moves.LowSkills.get skill_name
    end
end

#in order not to defer the startup cost
RubyKickDynamicStraight.new(@kick_target)
