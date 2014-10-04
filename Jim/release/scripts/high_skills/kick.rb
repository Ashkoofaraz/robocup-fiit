require 'high_skills/ruby_high_skill.rb'

include_class Java::sk.fiit.robocup.library.geometry.Vector3D

class Kick< RubyHighSkill

	
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
        puts "beam" 
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
                  
	  when (ball_pos.getY() < 0.7 and ball_pos.getY() > 0.25) 
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
        puts "wtf????"
        return nil
      end

  end

  def kick_dist leg, ball_pos
  	
 	
 	 @kick_target
 	 ball_pos = @worldModel.getBall().getPosition
    
   	@kick_target_dist = @agentInfo.calculateDistance(ball_pos,@kick_target)
 	
 	
  	if(@kick_target_dist > 4 and leg == "right")
  		puts "faster"
  		get_skill("kick_step_strong_right")
  		#get_skill("kick_right_faster")
	elsif (@kick_target_dist > 4 and leg=="left")
 		puts "faster"
 		get_skill("kick_step_strong_left")
 		#get_skill("kick_left_faster")
 	
 	elsif(@kick_target_dist <= 4 and @kick_target_dist > 1.5  and leg=="right")
  		puts "fast"
  		get_skill("kick_right_faster")
  		#get_skill("kick_right_normal")
	elsif(@kick_target_dist <= 2.5 and @kick_target_dist > 1.5  and leg=="left")
		puts "fast"
 		get_skill("kick_left_faster")
 		#get_skill("kick_left_normal")
 	elsif(@kick_target_dist <= 1.5 and @kick_target_dist > 0.5 and leg=="right")
  		puts "normal"
  		get_skill("kick_right_normal")
  		#get_skill("kick_right_slow")
	elsif (@kick_target_dist <= 1.5 and @kick_target_dist > 0.5 and leg=="left")
 		puts "normal"
 		get_skill("kick_left_normal")
 		#get_skill("kick_left_slow")
 	else
 		return nil
  	end
  
  end 

  
  def checkProgress
  #  raise "Fallen" if me.on_ground?
  end
end
#Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(0, 0, 0)
#in order not to defer the startup cost
Kick.new(@kick_target)
