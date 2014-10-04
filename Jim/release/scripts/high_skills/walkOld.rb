require 'high_skills/ruby_high_skill.rb'
require 'plan/plan.rb'
include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::java.lang.Math
include_class Java::sk.fiit.jim.agent.models.AgentModel
include_class Java::sk.fiit.jim.agent.models.EnvironmentModel

class WalkOld < RubyHighSkill
  
  #270 - 330 degrees
  @@right_range = (1.5*Math::PI)..((11.0/6.0)*Math::PI)
  #30 - 90 degrees
  @@left_range = ((1.0/6.0)*Math::PI)..(0.5*Math::PI)
  #75 - 90 degrees
  @@left_strafe_range = ((5.0/12.0)*Math::PI..(0.5*Math::PI))
  #270 - 285 degrees
  @@right_strafe_range = (1.5*Math::PI..(19.0/12.0)*Math::PI)
  @@strafe_distance = 1.5
  
  
  def initialize target
    super()
    @target = target
  end
  
  def pickLowSkill
    target_position = @target
    if @target == :ball then
    	target_position = (ball relative position)
    else
    	target_position = AgentModel.getInstance.relativize @target
    end
    
    #puts 'moviing this>'
    #puts target_position
    
    target_position = target_position.set_z(0.0)
    #puts 'going to'
    #puts target_position
    #puts 'going to'
    return nil if close_enough?(target_position)
    return nil if EnvironmentModel.beamablePlayMode
    #puts AgentModel.getInstance.position.getZ
	#return get_skill("stand_back") if me.on_ground?
	return nil if me.on_ground?
	
	return nil if ball_unseen > 3
    return get_skill("walk_forward") if straight?(target_position)
    #return get_skill("turn_left_90") if back_and_left?(target_position)
    #return get_skill("turn_right_90") if back_and_right?(target_position)
    #return get_skill("step_left") if left_and_close?(target_position)
    #puts '4'
    #return get_skill("step_right") if right_and_close?(target_position)
    #puts '5'
    return get_skill("turn_left_cont_20") if left_and_distant?(target_position)
    return get_skill("turn_right_cont_20") if right_and_distant?(target_position)
  end
  
  def close_enough? target_position
    target_position.r < 0.0
  end
  
  def straight? target_position
    Angles.angle_diff(target_position.phi, 0.0) < Math::PI / 6.0 
  end
  
  #def on_ground?
  #  AgentModel.getInstance.position.getZ < 0.5 
  #end
  
  def back? target_position
    Angles.angle_diff(target_position.phi, Math::PI) < Math::PI / 6.0
  end
  
  def back_and_right? target_position
    Angles.angle_diff(target_position.phi, Math::PI) < Math::PI / 6.0 and @@right_range.include?(Angles.normalize(target_position.phi))
  end
  
  def back_and_left? target_position
    Angles.angle_diff(target_position.phi, Math::PI) < Math::PI / 6.0 and @@right_range.include?(Angles.normalize(target_position.phi))
  end
  
  def left_and_close? target_position
    puts target_position
    (target_position.r <= @@strafe_distance) and @@left_strafe_range.include?(Angles.normalize(target_position.phi))
  end
  
  def right_and_close? target_position
    (target_position.r <= @@strafe_distance) and @@right_strafe_range.include?(Angles.normalize(target_position.phi))
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
  
  def abs number
    Math.abs number
  end
end

#in order not to defer the startup cost
WalkOld.new :ball