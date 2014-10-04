require 'high_skills/ruby_high_skill.rb'
include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::java.lang.Math

class Goto < RubyHighSkill

	def Goto.angle_range from, to
    	(from/180.0*Math::PI)..(to/180.0*Math::PI)
  	end

  #65 - 85 - the same as above
  @@left_strafe_crossing = angle_range(60.0, 80.0)
  #275..295
  @@right_strafe_crossing = angle_range(280.0, 300.0)
  ##same...
  @@left_back_crossing = angle_range(80.0, 100.0)
  @@right_back_crossing = angle_range(260.0, 280.0)

  #careful - do not put 330 instead of -30, will cause havoc in Ruby range, as expected (it doesn't know it is dealing with degrees)
  @@straight_range1 = angle_range(0.0, 30.0)
  @@straight_range2 = angle_range(330.0, 360.0)
  @@back_range = angle_range(90.0, 270.0)
  #270 - 330 degrees
  @@right_range = angle_range(180.0, 330.0)
  #30 - 90 degrees
  @@left_range = angle_range(30.0, 180.0)
  #75 - 90 degrees
  @@left_strafe_range = angle_range(45.0, 90.0)
  #270 - 285 degrees
  @@right_strafe_range = angle_range(270.0, 315.0)
  @@strafe_distance = 1.5
  @@very_close_distance = 0.4
 
  
  def initialize
    super()
    @tacticalInfo = Java::sk.fiit.jim.agent.models.TacticalInfo.getInstance
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    
    @target_position = @tacticalInfo.getFormPosition()

  end
  
  def pickLowSkill
    
     @tacticalInfo = Java::sk.fiit.jim.agent.models.TacticalInfo.getInstance
     @target_position = @tacticalInfo.getFormPosition()
     @target_position_phi = @target_position.getPhi()
     
     if @target_position_phi != nil
     	@target_position_phi2 = @target_position_phi
     elsif @target_position_phi == nil and plan.see_ball?
     	@target_position_phi = @target_position_phi2
     end
  
    case
		when me.on_ground?
      		return	
       	when @tacticalInfo.isOnPosition
       		@agentInfo.loguj("close ")
  			return nil
        when left_and_close?:
        	return get_skill("step_left")
      	when right_and_close?:
        	return get_skill("step_right")
      	when straight?:
        	return get_skill("walk_forward")
      	when back_and_close?:
        	return get_skill("walk_back")
     	when left_and_distant?:
        	return get_skill("turn_left_cont_20")
     	when right_and_distant?:
        	return get_skill("turn_right_cont_20")
        else
        	return nil
        
    end   
  end
  
  def is_current_skill? possibilities
    current_skill_name = currentSkill.nil? ? "null" : currentSkill.name
    possibilities.member?(current_skill_name)
  end
  
  def close_enough?
    @target_position.getR < 0.5
  end

  def straight?
    @@straight_range1.include?(@target_position_phi) or @@straight_range2.include?(@target_position_phi)
  end
  
  def back_and_close?
    @@back_range.include?(@target_position_phi) and (@target_position.getR <= @@strafe_distance)
  end

  def left_and_close?
    (@target_position.r <= @@strafe_distance) and @@left_strafe_range.include?(Angles.normalize(@target_position_phi))
  end

  def right_and_close?
    (@target_position.r <= @@strafe_distance) and @@right_strafe_range.include?(Angles.normalize(@target_position_phi))
  end

  def left_and_distant?
    @@left_range.include? Angles.normalize(@target_position_phi)
  end

  def right_and_distant?
    @@right_range.include? Angles.normalize(@target_position.getPhi())
  end
  
  def back? target_position
    Angles.angle_diff(target_phi, Math::PI) < Math::PI / 6.0
  end
  

  
  def back? target_position
    Angles.angle_diff(target_phi, Math::PI) < Math::PI / 6.0
  end
  
  
  def checkProgress
#    raise "Fallen!" if me.on_ground?
  end
  
  def abs number
    Math.abs number
  end
end

#in order not to defer the startup cost
Goto.new
