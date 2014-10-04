require 'high_skills/ruby_high_skill.rb'
include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::java.lang.Math

class Walk2Ball < RubyHighSkill

	@@y1 = 0.35
	@@y2 = 0.2
	@@x1 = 0.04
	@@x2 = 0.12
	
	@@medium_distance = 4
  	@@close_distance = 0.7
  	
	def Walk2Ball.angle_range from, to
    	(from/180.0*Math::PI)..(to/180.0*Math::PI)
  	end

  	@@straight_range1 = angle_range(0.0, 15.0)
  	@@straight_range2 = angle_range(345.0, 360.0)
  	
        @@right_range3 = angle_range(340.0, 346.0)
  	@@right_range2 = angle_range(325.0, 340.0)
        @@right_range = angle_range(180.0, 325.0)
        @@left_range3 = angle_range(14.0, 20.0)
  	@@left_range2 = angle_range(20.0, 35.0)
        @@left_range = angle_range(35.0, 180.0)
  	  
  def initialize target, validity_proc
    super()
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    #@target = target
   
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
     
     	if @target_position_phi != nil
     		@target_position_phi2 = @target_position_phi
     	elsif @target_position_phi == nil and plan.see_ball?
     		@target_position_phi = @target_position_phi2
     	end
     
    	#@agentInfo.loguj(@agentInfo.getIsBallMine().to_s)
     	#@agentInfo.loguj("straight: "+straight?.to_s)
     	#@agentInfo.loguj("isBallMine: "+@agentInfo.isBallMine().to_s)

		if (me.on_ground? or me.lying_on_back? or me.lying_on_belly?) :
      		return nil
        elsif (not see_ball?)
	      	return nil
		else # ak stoji a vie kde je lopta
			#@agentInfo.loguj(@target.getR().to_s)
			if(@target.getR() < @@close_distance) # je blizko lopty
				#@agentInfo.loguj("som pri lopte ")
				
				
				if(@target.getY > @@y1)
			      	if(@target.getX < -@@x2)
			        	#@agentInfo.loguj("ZONA 3")
			        	return get_skill("step_left")
			      	elsif(@target.getX > @@x2)
			        	#@agentInfo.loguj("ZONA 2")
			       		return get_skill("step_right")
			      	else
			        	#@agentInfo.loguj("ZONA 1")
			        	return get_skill("walk_slow")
			      	end
			    elsif(@target.getY > @@y2)
			      	if(@target.getX < -@@x2)
			        	#@agentInfo.loguj("ZONA 3")
			        	return get_skill("step_left")
			      	elsif(@target.getX > @@x2)
			        	#@agentInfo.loguj("ZONA 2")
			        	return get_skill("step_right")
			      	else
			        #	log @target
			        	#@agentInfo.loguj("mozem kopat")
			        	return nil #get_skill("kick_right_faster")
			      	end
			    elsif(@target.getY < 0)
			      	if(@target.getX < -@@x2)
			         	#@agentInfo.loguj("ZONA 4")
			        	return get_skill("walk_back")
			      	elsif(@target.getX > @@x2)
			         	#@agentInfo.loguj("ZONA 5")
			        	return get_skill("walk_back")
			      	elsif(@target.getX > 0)
			         	#@agentInfo.loguj("ZONA 7")
			        	return get_skill("step_right")
			      	else
			         	#@agentInfo.loguj("ZONA 8")
			        	return get_skill("step_left")
			      	end
			    else
			        #@agentInfo.loguj("ZONA 6")
			        return get_skill("walk_back")
			      
			    end
			
			else	# je dalej od lopty
				#@agentInfo.loguj("som daleako2 od lopty ")
				if(straight?)
					#@agentInfo.loguj("rovno")
		        	return get_skill("walk_forward") #_optimized2
                                elsif(right_and_distant?)
                                        #@agentInfo.loguj("vpravo")
                                        #return get_skill("turn_right_cont_4.5")
                                        return get_skill("turn_right_45")	     
                                elsif(right_and_distant_less?)
                                        #@agentInfo.loguj("vpravo MENEJ")
                                        return get_skill("turn_right_cont_20")
                                elsif(right_and_distant_bit?)
                                        #@agentInfo.loguj("vpravo Malicko")
                                        return get_skill("turn_right_cont_20")
                                elsif(left_and_distant?)
                                        #@agentInfo.loguj("vlavo")
                                        #return get_skill("turn_left_cont_4.5")
                                        return get_skill("turn_left_45")	     	
                                elsif(left_and_distant_less?)
                                        #@agentInfo.loguj("vlavo MENEJ")
                                        #return get_skill("turn_left_cont_4.5")
                                        return get_skill("turn_left_cont_20")
                                elsif(left_and_distant_bit?)
                                        #@agentInfo.loguj("vlavo Malicko")
                                        #return get_skill("turn_left_cont_4.5")
                                        return get_skill("turn_left_cont_20")
                                else
                                        @agentInfo.loguj("olalaa")
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
    @@straight_range1.include?(@target_position_phi) or @@straight_range2.include?(@target_position_phi)
  end

  def left_and_distant?
    @@left_range.include? Angles.normalize(@target.getPhi())
  end

  def left_and_distant_less?
    @@left_range2.include? Angles.normalize(@target.getPhi())
  end

  def left_and_distant_bit?
    @@left_range3.include? Angles.normalize(@target.getPhi())
  end

  def right_and_distant?
    @@right_range.include? Angles.normalize(@target.getPhi())
  end

  def right_and_distant_less?
    @@right_range2.include? Angles.normalize(@target.getPhi())
  end
  def right_and_distant_bit?
    @@right_range3.include? Angles.normalize(@target.getPhi())
  end
  def is_ball_mine?
  	return @agentInfo.getIsBallMine
  end
  
  def checkProgress
    #raise "Fallen!" if me.on_ground?
  end
  
  def abs number
    Math.abs number
  end
end

#in order not to defer the startup cost
Walk2Ball.new nil, nil