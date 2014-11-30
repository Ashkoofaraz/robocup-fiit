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
	elsif (not me.on_ground?)# or is_lying_on_back?)
		puts "falling"
		@plan << Fall.new	
    #	@agentInfo.loguj('GetUp');
    # 	@plan << GetUp.new(Proc.new{me.on_ground?})		
	#elsif (not see_ball?) 
   	#	@agentInfo.loguj('Localize');
    #	@plan << Localize.new(Proc.new{(not see_ball?)})
	#elsif (is_ball_mine? and straight? and turned_to_goal?)
    #  @agentInfo.loguj('Kick');
    #	@plan << Kick.new(@kick_target)
	elsif (me.is_lying_on_back?)
		puts "lying on back"
		@plan << RubyDynamicMove.new
		#@plan << Fall.new
    else
        @agentInfo.loguj('Dynamic Move')
              
		
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