require 'high_skills/ruby_high_skill.rb'
include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::java.lang.Math

class Walk2 < RubyHighSkill

	@@y1 = 0.35
	@@y2 = 0.25
	@@x1 = 0.04
	@@x2 = 0.12
	
	@@medium_distance = 4
  	@@close_distance = 0.7
  	
	def Walk2.angle_range from, to
    	(from/180.0*Math::PI)..(to/180.0*Math::PI)
  	end

  	@@straight_range1 = angle_range(0.0, 30.0)
  	@@straight_range2 = angle_range(330.0, 360.0)
  	
  	@@right_range = angle_range(180.0, 330.0)
  	@@left_range = angle_range(30.0, 180.0)
  	  
  def initialize target, kick_target, validity_proc 
    super()
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    #@target = target
    @kick_target = kick_target
    @validity_proc = validity_proc || Proc.new{return true}
    @ending = false
    
    @target = @agentInfo.ballControlPosition()

   # @target_phi = @target_position.getPhi() * 180.0 / Math::PI
  end
  
 	def pickLowSkill
	 	is_still_valid = @validity_proc.call
	    @ending = true if not is_still_valid
	    return nil if not is_still_valid or @ending
	    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
	    @target = @agentInfo.ballControlPosition()
	    @target_position_phi = @target.getPhi()
	    
	    @kick_target_phi = @kick_target.getPhi()
	    
     
     	if @target_position_phi != nil
     		@target_position_phi2 = @target_position_phi
     	elsif @target_position_phi == nil and plan.see_ball?
     		@target_position_phi = @target_position_phi2
     	end
     
 #   	@agentInfo.loguj(@agentInfo.getIsBallMine().to_s)
 #    	@agentInfo.loguj("straight: "+straight?.to_s)
 #    	@agentInfo.loguj("isBallMine: "+@agentInfo.isBallMine().to_s)

		if (me.on_ground? or me.lying_on_back? or me.lying_on_belly?) :
      		return nil
        elsif (not see_ball?)
	      	return nil
		else # ak stoji a vie kde je lopta
		#	@agentInfo.loguj(@target.getR().to_s)
			if(@target.getR() < @@close_distance) # je blizko lopty
			#	@agentInfo.loguj("som pri lopte ")
				
				if(@target.getY > @@y1)
			      	if(@target.getX < -@@x2)
			        	@agentInfo.loguj("ZONA 3")
			        	@agentInfo.loguj("step_left")
			        	return get_skill("step_left")
			      	elsif(@target.getX > @@x2)
			        	@agentInfo.loguj("ZONA 2")
			        	@agentInfo.loguj("step_right")
			       		return get_skill("step_right")
			      	else
			        	@agentInfo.loguj("ZONA 1")
			        	@agentInfo.loguj("walk_slow")
			        	#return get_skill("ball_control3")#fast2")
			        	#return get_skill("walk_slow")
			        	return get_skill("walk_slow2")
			      	end
			    elsif(@target.getY > @@y2)
			      	if(@target.getX < -@@x2)
			        	@agentInfo.loguj("ZONA 3")
			        	@agentInfo.loguj("step_left")
			        	return get_skill("step_left")
			      	elsif(@target.getX > @@x2)
			        	@agentInfo.loguj("ZONA 2")
			        	@agentInfo.loguj("step_right")
			        	return get_skill("step_right")
			      	else 
			        #	log @target
			        #	@agentInfo.loguj("mozem kopat")			        	
			        	return nil
			      	end
			    elsif(@target.getY < 0)
			      	if(@target.getX < -@@x2)
			         	@agentInfo.loguj("ZONA 4")
			         	@agentInfo.loguj("walk_back")
			        	return get_skill("walk_back")
			      	elsif(@target.getX > @@x2)
			         	@agentInfo.loguj("ZONA 5")
			         	@agentInfo.loguj("walk_back")
			        	return get_skill("walk_back")
			      	elsif(@target.getX > 0)
			         	@agentInfo.loguj("ZONA 7")
			         	@agentInfo.loguj("step_right")
			        	return get_skill("step_right")
			      	else
			         	@agentInfo.loguj("ZONA 8")
			         	@agentInfo.loguj("step_left")
			        	return get_skill("step_left")
			      	end
			    else
			        @agentInfo.loguj("ZONA 6")
			        @agentInfo.loguj("walk_back")
			        return get_skill("walk_back")
			      
			    end
=begin		    
			#---------------------------------------------------------------------------------    
			elsif (@target.getR() < @@medium_distance and @target.getR() > @@close_distance)
				@agentInfo.loguj("medium distance")
				if(straight?)
					@agentInfo.loguj("rovno")
		        	get_skill("walk_forward") 
		        	#_optimized2
		        	#return get_skill("walk_fine_fast3")
		        elsif(right_and_distant?)
		     		@agentInfo.loguj("vpravo")
		        	return get_skill("step_circ_right_small")
		        	#get_skill("turn_right_cont_10")
		     	elsif(left_and_distant?)
		     		@agentInfo.loguj("vlavo")
					return get_skill("step_circ_left_small")	     	
		        	#get_skill("turn_left_cont_10")
		        else
			        	@agentInfo.loguj("olalaa")
			        	get_skill("rollback")
				end    
			    
			#------------------------------------------------------------------------------------    
=end
			else	# je dalej od lopty
			#	@agentInfo.loguj("som daleko od lopty ")
				if(straight?)
			#		@agentInfo.loguj("rovno")
					@agentInfo.loguj("walk_fast")		        	
		        	#get_skill("walk_fine_fast2_optimized3")
		        	get_skill("walk_forward")
		        	 
		        elsif(right_and_distant?)
		     		@agentInfo.loguj("vpravo_daleko ")
		        	return get_skill("step_circ_right_small")
		        	#return get_skill("turn_right_cont_20")		        	
		     	elsif(left_and_distant?)
		     		@agentInfo.loguj("vlavo_daleko ")
		        	return get_skill("step_circ_left_small")
		        	#return get_skill("turn_left_cont_20")		        	   	
		        else
		    #    	@agentInfo.loguj("olalaa")
		        	return get_skill("rollback")
				end
			end 
		end

  end
  
  def close_enough?
    @agentInfo.isBallMine()
  end
  
  def near_ball?
    @agentInfo.nearBall()
  end
  
  def see_ball?
   	ball.notSeenLongTime() < 5
  end
  
  def straight?
    (@@straight_range1.include?(@target_position_phi) or @@straight_range2.include?(@target_position_phi)) #and (@@straight_range1.include?(@kick_target_phi) or @@straight_range2.include?(@kick_target_phi))
  end

  def left_and_distant?
    @@left_range.include? Angles.normalize(@target.getPhi())
  end

  def right_and_distant?
    @@right_range.include? Angles.normalize(@target.getPhi())
  end
  
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
Walk2.new (:ball, @kick_target, Proc.new{not is_ball_mine? or not straight?})