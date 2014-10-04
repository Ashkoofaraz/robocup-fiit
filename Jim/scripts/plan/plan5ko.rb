########	plan.rb	 ########
#	@author: Team17		#
#############################

class Plan5ko < Plan
  
 
  def replan
    @plan.clear
    @agentInfo.whereIsBall();
    @agentInfo.isBallMine();
    @agentInfo.nearBall();
    @agentInfo.ballControlPosition();
	
    @target_position = @agentInfo.ballControlPosition()
    @target_position_phi = @target_position.getPhi()
    @kick_target = @agentInfo.kickTarget()
    @kick_target_phi =  @kick_target.getPhi()
    
    if EnvironmentModel.beamablePlayMode
    	@agentInfo.loguj('Beam');
    	#@plan << Beam.new(@formationPosition)
    	#@beamed = true
    	return
    elsif (me.on_ground? or me.is_lying_on_back? or me.is_lying_on_belly?)
    	@agentInfo.loguj('GetUp');
     	@plan << GetUp.new(Proc.new{me.on_ground?})
    elsif (not see_ball?) 
   		@agentInfo.loguj('Localize');
    	@plan << Localize.new(Proc.new{(not see_ball?)})
    elsif (not is_ball_mine? or not straight?)
    	@agentInfo.loguj('Walk');
    	@plan << Walk2Ball.new(:ball, Proc.new{not is_ball_mine? or not straight?})# or (not see_ball?)})
    elsif not turned_to_goal?
    	@agentInfo.loguj('Turn');
     	@plan << Turn.new(@kick_target, Proc.new{not turned_to_goal?})
    elsif (is_ball_mine? and straight? and turned_to_goal?)
      @agentInfo.loguj('Kick');
    	@plan << Kick.new(@kick_target)
    else
    	@agentInfo.loguj('???');
    	@plan << GetUp.new(Proc.new{me.on_ground?})
    end
  end
end