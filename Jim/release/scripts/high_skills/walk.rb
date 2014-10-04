require 'high_skills/ruby_high_skill.rb'
include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::java.lang.Math

class Walk < RubyHighSkill

	def Walk.angle_range from, to
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
  @@strafe_distance = 1.3
  @@very_close_distance = 0.4
  
  #270 - 330 degrees
  #@@right_range = (1.5*Math::PI)..((11.0/6.0)*Math::PI)
  #30 - 90 degrees
  #@@left_range = ((1.0/6.0)*Math::PI)..(0.5*Math::PI)
  #75 - 90 degrees
  #@@left_strafe_range = ((5.0/12.0)*Math::PI..(0.5*Math::PI))
  #270 - 285 degrees
  #@@right_strafe_range = (1.5*Math::PI..(19.0/12.0)*Math::PI)
  #@@strafe_distance = 1.5
  
  
  def initialize target, validity_proc
    super()
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    @target = target
    
    @validity_proc = validity_proc || Proc.new{return true}
    @ending = false
    
    @target_position = @agentInfo.ballControlPosition()

   # @target_phi = @target_position.getPhi() * 180.0 / Math::PI
  end
  
  def pickLowSkill
    #target_position = @agentInfo.ballControlPosition()
   # target_position = (ball relative position) if @target == :ball
  #  @target = @target.set_z(0.0) 
 #   @agentInfo.loguj("phi" + @target_phi.to_s)
 	is_still_valid = @validity_proc.call
    @ending = true if not is_still_valid
 #   @agentInfo.loguj("end: "+@ending.to_s)
    return nil if not is_still_valid or @ending
     @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
     @target_position = @agentInfo.ballControlPosition()
     @target_position_phi = @target_position.getPhi()
     
     if @target_position_phi != nil
     	@target_position_phi2 = @target_position_phi
     elsif @target_position_phi == nil and plan.see_ball?
     	@target_position_phi = @target_position_phi2
     end
     
 #    @agentInfo.loguj(@agentInfo.getIsBallMine().to_s)
 #    @agentInfo.loguj("straight: "+straight?.to_s)
 #    @agentInfo.loguj("isBallMine: "+@agentInfo.isBallMine().to_s)

    case
		when (me.on_ground? or me.lying_on_back? or me.lying_on_belly?) :
      		return nil
       # 	@plan << GetUp.new
        when (not see_ball?) :
	      #	@agentInfo.loguj("nevidim loptu dlhsie ako 5s")
	      #	return get_skill("turn_left_cont_20")
	      return nil
      
        when (close_enough? and straight?)
  			return nil
     # 	when back_and_very_close?:
     # 		return get_skill("turn_left_90")
        #	return choose_turning_skill;
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
     #   else
     #   	return get_skill("rollback")
    end   

    		
    #	when back?(target_position)
    #		#@agentInfo.setState('som chrbtom')
    #		return # get_skill("turn_right_cont_20")

  end
  
  def is_current_skill? possibilities
    current_skill_name = currentSkill.nil? ? "null" : currentSkill.name
    possibilities.member?(current_skill_name)
  end
  
  def close_enough?
    @agentInfo.isBallMine()
  end
  
 # def straight? target_position
 #   Angles.angle_diff(target_phi, 0.0) < Math::PI / 6.0 
 # end
  
  def see_ball?
   	ball.notSeenLongTime() < 5
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
  
 # def left_and_close? target_position
    #puts target_position
 #   (target_position.r <= @@strafe_distance) and @@left_strafe_range.include?(Angles.normalize(target_position.phi))
   # ball_left? and is_ball_mine?  
#  end
  
 # def right_and_close? target_position
 #   (target_position.r <= @@strafe_distance) and @@right_strafe_range.include?(Angles.normalize(target_position.phi))
  #	ball_right? and is_ball_mine?
 # end
  
#  def left_and_distant? target_position
 #   @@left_range.include?(Angles.normalize(target_position.phi))
   #	ball_left? and (not is_ball_mine?)
#  end
  
#  def right_and_distant? target_position
#    @@right_range.include?(Angles.normalize(target_position.phi))
    # ball_right? and (not is_ball_mine?)
#  end
  
#  def not_forward? targed_position
  #	@agentInfo.loguj(@agentInfo.whereIsTarget(targed_position))
 # 	not @agentInfo.whereIsTarget(targed_position).eql? 'front'	
 # end
  
  def is_ball_mine?
  	return @agentInfo.getIsBallMine
  end
  
  def checkProgress
#    raise "Fallen!" if me.on_ground?
  end
  
  def abs number
    Math.abs number
  end
end

#in order not to defer the startup cost
Walk.new nil, nil