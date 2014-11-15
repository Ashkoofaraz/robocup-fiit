### planPm.rb ###
#	@author: Pavol Pidanic			#
# Upravene z planu timu Pavol Mestanik PlanPmKick #
#####################################

class PlanDynamicMove < Plan  
  def replan
    @plan.clear
    @agentInfo.whereIsBall();
    @agentInfo.isBallMine();
    @agentInfo.nearBall();    
    @agentInfo.ballControlPosition();
	 
	  @mathModel = Java::sk.fiit.robocup.library.geometry.Vector3D.clone
    #@target_position = @agentInfo.ballControlPosition()
    #@target_position_phi = @target_position.getPhi()
    
    # miesto kde ma kopnut
    #@kick_target = @mathModel.cartesian(5,0.0,0.0)
    #@leg = "right"
       
    if EnvironmentModel.beamablePlayMode and not EnvironmentModel.isKickOffLeftPlayMode  
        if(!@beamed)
          @plan << Beam.new(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(-1, 0.0, 0.4))  
        end
        @agentInfo.loguj('Beam')      
        if(@leg == "right")
          @plan << Beam.new(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(-0.2, 0.05, 0.4))
        else
          @plan << Beam.new(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(-0.2, -0.05, 0.4))
        end
        @beamed = true  
	#elsif (me.on_ground? or me.is_lying_on_back? or me.is_lying_on_belly?)
    #	@agentInfo.loguj('GetUp');
    # 	@plan << GetUp.new(Proc.new{me.on_ground?})		
	#elsif (not see_ball?) 
   	#	@agentInfo.loguj('Localize');
    #	@plan << Localize.new(Proc.new{(not see_ball?)})
	#elsif (is_ball_mine? and straight? and turned_to_goal?)
    #  @agentInfo.loguj('Kick');
    #	@plan << Kick.new(@kick_target)
    else
        @agentInfo.loguj('Dynamic Move')
        #ball_pos = @worldModel.getBall().getPosition             
        #puts "Ball X:"    
        #puts ball_pos.getX()
        #puts "Ball Y:"
        #puts ball_pos.getY()
        #@ball_distance = @agentInfo.calculateDistance(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(0, 0, 0), ball_pos)
        #puts "Ball distance from mid:"
        #puts @ball_distance
        
		
		puts "joint angles: #{Java::sk.fiit.jim.agent.models.AgentModel.getInstance().getJointAngles}"
		puts "parsed data: #{Java::sk.fiit.jim.agent.models.AgentModel.getInstance().getLastDataReceived}"
		puts "body abs positions: #{Java::sk.fiit.jim.agent.models.AgentModel.getInstance().getBodyPartAbsPositions}"
		puts "body rel positions: #{Java::sk.fiit.jim.agent.models.AgentModel.getInstance().getBodyPartRelPositions}"
				
			
        #if (ball_pos.getY().abs > 0.3 or ball_pos.getX().abs > 0.3)    
         # return nil
        #end           	
        puts "pre init RubyDynamicMove"
		@plan << RubyDynamicMove.new		
	
    end
  end
end