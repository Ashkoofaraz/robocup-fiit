### planTournamentTurn.rb ###
#	@author: A55Kickers		#
#############################

class PlanTournamentTurn < Plan
  
  def replan
    @plan.clear
    @agentInfo.whereIsBall();
    @agentInfo.isBallMine();
    @agentInfo.nearBall();
    @agentInfo.ballControlPosition();
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    @agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance

    if EnvironmentModel.beamablePlayMode
    	@agentInfo.loguj('Beam');
    	@plan << Beam.new(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(-3, 0, 0.4))
    	@beamed = true
    elsif (me.on_ground? or me.is_lying_on_back? or me.is_lying_on_belly?)
    	@agentInfo.loguj('GetUp');
     	@plan << GetUp.new(Proc.new{me.on_ground?})
   	else
    	@agentInfo.loguj('Turn');
    	@plan << TurnToPosition.new(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(-10, 0, 0.4), Proc.new{not EnvironmentModel.beamablePlayMode})
    end
  end
end