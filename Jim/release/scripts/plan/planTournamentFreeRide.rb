### planTournamentFreeRide.rb ###
#	@author: A55Kickers			#
#################################

class PlanTournamentFreeRide < Plan
  
  def replan
    @plan.clear
    @agentInfo.whereIsBall();
    @agentInfo.isBallMine();
    @agentInfo.nearBall();
    @agentInfo.ballControlPosition();
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
	
    if EnvironmentModel.beamablePlayMode
    	@agentInfo.loguj('Beam');
    	@plan << Beam.new(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(-2, 0, 0.4))
    	@beamed = true
    elsif (me.on_ground? or me.is_lying_on_back? or me.is_lying_on_belly?)
    	@agentInfo.loguj('GetUp');
     	@plan << GetUp.new(Proc.new{me.on_ground?})
   	else
    	@agentInfo.loguj('Free Ride');
    	#@plan << LowSkill.new("free_ride") #TODO: sem doplnit low skill na turnajovu volnu jazdu
    end
  end
end