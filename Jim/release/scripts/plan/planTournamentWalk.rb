### planTournamentWalk.rb ###
#	@author: A55Kickers		#
#############################

class PlanTournamentWalk < Plan
  
  def replan
    @plan.clear
    @agentInfo.whereIsBall();
    @agentInfo.isBallMine();
    @agentInfo.nearBall();
    @agentInfo.ballControlPosition();
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
	
    if EnvironmentModel.beamablePlayMode
    	@agentInfo.loguj('Beam');
    	@plan << Beam.new(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(-5, 1, 0.4))
    	@beamed = true
    elsif (me.on_ground? or me.is_lying_on_back? or me.is_lying_on_belly?)
    	@agentInfo.loguj('GetUp');
     	@plan << GetUp.new(Proc.new{me.on_ground?})
   	else
    	@agentInfo.loguj('Go');
    	@plan << GoToPosition.new(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(10, 1, 0.4), Proc.new{not EnvironmentModel.beamablePlayMode})
    end
  end
end