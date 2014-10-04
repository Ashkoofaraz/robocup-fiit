########	plan.rb	 ########
#	@author: Team17		#
#############################

class PlanSch < Plan
  
  def initialize
    super
  end
  
  def replan
    
    @plan.clear
#    if !(EnvironmentModel.beamablePlayMode)
      @agentInfo.whereIsBall();
      @agentInfo.isBallMine();
      @agentInfo.nearBall();
      @agentInfo.ballControlPosition();
  	
      @target_position = @agentInfo.ballControlPosition()
      @target_position_phi = @target_position.getPhi()
      @kick_target = @agentInfo.kickTarget()
      @kick_target_phi =  @kick_target.getPhi()
      
      
      if (me.on_ground? or me.is_lying_on_back? or me.is_lying_on_belly?)
        @agentInfo.loguj('GetUp');
        @plan << GetUp.new(Proc.new{me.on_ground?})
      elsif (not see_ball?) 
        puts "Searching ball"
        @agentInfo.loguj('Localize');
        @plan << Localize.new(Proc.new{(not see_ball?)})
      else
        @targetPosition = Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(5, 5, 0)
        @agentInfo.loguj('GoToBall');
       	@plan << GotoBall.new(@targetPosition)
      end
#   	end
  end
  
end