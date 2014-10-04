### planTournamentGetUpFromBack.rb ###
#	@author: A55Kickers				 #
######################################

class PlanTournamentGetUpFromBack < Plan

  def initialize
  	@beamed = false
  	@falled = false
  	@in_falled_pose = false
  	@up = false
  	super()
  end
  
  def replan
    @plan.clear
    @agentInfo.whereIsBall();
    @agentInfo.isBallMine();
    @agentInfo.nearBall();
    @agentInfo.ballControlPosition();
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    @agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance
	
    if not @beamed and not @falled and not @in_falled_pose and not @up and EnvironmentModel.beamablePlayMode 
		@agentInfo.loguj('Beam, wait for Fall');
		@beamed = true
		@plan << Beam.new(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(-3, 0, 0.4))
	elsif @beamed and not @falled and not @in_falled_pose and not @up and EnvironmentModel.beamablePlayMode 
		@agentInfo.loguj('Fall, wait for To falled pose');
		@falled = true
		@plan << LowSkill.new("fall_back")
	elsif @beamed and @falled and not @in_falled_pose and not @up and EnvironmentModel.beamablePlayMode 
		@agentInfo.loguj('To falled pose, after that I am ready for GetUp');
		@in_falled_pose = true
		@plan << LowSkill.new("rollback2")
    elsif @beamed and @falled and @in_falled_pose and not @up and not EnvironmentModel.beamablePlayMode 
    	@agentInfo.loguj('GetUp, wait for Stand');
    	@up = true
     	@plan << GetUp.new(Proc.new{me.on_ground?})
    elsif @beamed and @falled and @in_falled_pose and @up and not EnvironmentModel.beamablePlayMode
     	@agentInfo.loguj('Stand');
     	@plan << LowSkill.new("rollback")
    elsif @beamed and @falled and @in_falled_pose and @up and EnvironmentModel.beamablePlayMode
    	@beamed = false
  		@falled = false
  		@in_falled_pose = false
  		@up = false
    end
  end
end