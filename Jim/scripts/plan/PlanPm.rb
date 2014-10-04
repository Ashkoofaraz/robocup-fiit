### planPm.rb ###
#	@author: Pavol Mestanik			#
# Upravene z planu timu A55Kickers #
#####################################

class PlanPm < Plan 
 
  def replan
    @plan.clear
    @agentInfo.whereIsBall();
    @agentInfo.isBallMine();
    @agentInfo.nearBall();    
    @agentInfo.ballControlPosition();
	 
	  @mathModel = Java::sk.fiit.robocup.library.geometry.Vector3D.clone
    @target_position = @agentInfo.ballControlPosition()
    @target_position_phi = @target_position.getPhi()
    
    # miesto kde ma kopnut
    @kick_target = @mathModel.cartesian(1.5,1.0,0.0) 
    
    # vypocet uhla pre nastavenie sa na poziciu
    me_flattened = my.position.set_z(0.0)
    target_vector = @kick_target - me_flattened
    target_angle = target_vector.phi
    diff_against_current = Angles.normalize(target_angle - my.rotation_z)    
    #uhol natocenia
    @kick_target_phi = diff_against_current
    
    if EnvironmentModel.beamablePlayMode and not EnvironmentModel.isKickOffLeftPlayMode  
        @agentInfo.loguj('Beam');
        @plan << Beam.new(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(-1, 0.0, 0.4))
        @beamed = true      
    elsif(me.on_ground? or me.is_lying_on_back? or me.is_lying_on_belly?)
    	@agentInfo.loguj('GetUp');
     	@plan << GetUp.new(Proc.new{me.on_ground?})
      elsif (not see_ball?) 
     		@agentInfo.loguj('Localize');
      	@plan << Localize.new(Proc.new{(not see_ball?)})
      elsif (not is_ball_mine? or not straight?)
      	@agentInfo.loguj('Walk');
      	@plan << Walk2Ball.new(:ball, Proc.new{(not is_ball_mine? or not straight?) and not (EnvironmentModel.beamablePlayMode and not EnvironmentModel.isKickOffLeftPlayMode)})
      elsif not turned_to_goal?
      	@agentInfo.loguj('Turn');
       	@plan << TurnToPosition.new(@kick_target, Proc.new{not turned_to_goal?})
      elsif (is_ball_mine? and straight? and turned_to_goal?)
        @agentInfo.loguj('Kick');
      	@plan << RubyKickDynamicStraight.new(@kick_target)
      else
      	@agentInfo.loguj('???');
      	@plan << GetUp.new(Proc.new{me.on_ground?})
      end
  end
end