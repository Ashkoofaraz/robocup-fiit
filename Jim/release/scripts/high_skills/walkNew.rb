require 'high_skills/ruby_high_skill.rb'
require 'plan/plan.rb'
include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::java.lang.Math
include_class Java::sk.fiit.jim.agent.models.AgentModel
include_class Java::sk.fiit.jim.agent.models.EnvironmentModel
include_class Java::sk.fiit.jim.agent.models.WorldModel

class WalkNew < RubyHighSkill
  
  @@right = 180..360
  @@left = 0..180
  @@front1 = 0..90
  @@front2 = 270..360
  @@back = 90..270
  
  @@go1 = 0..30
  @@go2 = 330..360
  
  @@range1 = Walk.angle_range(0.0, 25.0)
  @@range2 = Walk.angle_range(335.0, 360.0)
  
  
  
  def initialize haveToBeTurnedToTarget,radius_ball,radius_speed,validity_proc
    super()
    @haveToBeTurnedToTarget = haveToBeTurnedToTarget
    @radius_ball = radius_ball
    @radius_speed = radius_speed
    @validity_proc = validity_proc
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    @agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance  
    
    if(@haveToBeTurnedToTarget)
      @@go1 = 0..15
      @@go2 = 335..360
    else
      @@go1 = 0..30
      @@go2 = 330..360
    end
    
  end
  
  def pickLowSkill
    @ball = WorldModel.getInstance.getBall.getRelativePosition
    @kickTarget = @agentInfo.kickTarget()
    
    case
      when (not @validity_proc.call) : 
        puts "validity"
        return nil
      when @ball.r < @radius_ball :
        puts "ball radius" 
        return nil
      when EnvironmentModel.beamablePlayMode :
        puts "beam" 
        return nil
      when @agentModel.fallen? :
        puts "fall" 
        return nil
      when ball_unseen > 3 :
        puts "ball unseen" 
        return nil
      when (@haveToBeTurnedToTarget and not turned_to_goal?) 
        puts "not turned to goal"
        return nil
      when ((ballInRange? @@go1) or (ballInRange? @@go2)) :
        if close_enough?(@radius_speed)
          puts "go and blizko"
          return get_skill("walk_forward")
        else
          puts "go and daleko"
          return get_skill("walk_forward")
        end
        
    when (ballInRange? @@left and (ballInRange? @@front1 or ballInRange? @@front2)) :
        puts "vlavo vpredu" 
        if(@ball.r<0.5 and @haveToBeTurnedToTarget)
          return get_skill("walk_forward")
        else
          return get_skill("turn_left_cont_20")
        end
      when (ballInRange? @@right and (ballInRange? @@front1 or ballInRange? @@front2)) :
        puts "vpravo vpredu"
        if (@ball.r<0.5 and @haveToBeTurnedToTarget)
          return get_skill("walk_forward")  
        else
          return get_skill("turn_right_cont_20")
        end
      when (ballInRange? @@left and ballInRange? @@back) :
        puts "vlavo vzadu"  
        return get_skill("turn_left_90")
      when (ballInRange? @@right and ballInRange? @@back) :
        puts "vpravo vzadu"  
        return get_skill("turn_right_90")    
    end    
    return nil
    
  end
  
  def ballInRange? range
    range.include? (Math.toDegrees(@ball.phi))
  end
  
  def close_enough? radius
    @ball.r < radius
  end
  
  def turned_to_goal?
    @@range1.include?(@kickTarget.phi) or @@range2.include?(@kickTarget.phi)
  end
  
  def straight? target_position
    Angles.angle_diff(target_position.phi, 0.0) < Math::PI / 6.0 
  end
  
  def left_and_distant? target_position
    @@left_range.include?(Angles.normalize(target_position.phi))
  end
  
  def right_and_distant? target_position
    @@right_range.include?(Angles.normalize(target_position.phi))
  end
  
  def checkProgress
#    raise "Fallen!" if me.on_ground?
  end
end

#in order not to defer the startup cost
#WalkNew.new :ball