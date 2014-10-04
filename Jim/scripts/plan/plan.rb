########	plan.rb	 ########
#	@author: Androids		#
#############################
import Java::sk.fiit.jim.agent.models.EnvironmentModel

require 'singleton'
require 'thread'

lock = Mutex.new
lock.synchronize do 

class Plan
  include Singleton
  
  def change_skill move_name 
     @queue.clear
     @queue << LowSkill.new(move_name)
  end
 
  @@straight_range1 = Walk.angle_range(0.0, 15.0)
  @@straight_range2 = Walk.angle_range(345.0, 360.0)
  
  def initialize
    puts "Initializing!"
    @agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance
  	@agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
  	@tacticalInfo = Java::sk.fiit.jim.agent.models.TacticalInfo.getInstance
    @worldModel = Java::sk.fiit.jim.agent.models.WorldModel.getInstance  
  	@Angles = Java::sk.fiit.robocup.library.geometry.Angles
    @formationPosition    = @tacticalInfo.getFormPositionGlobal()
    @plan = []
    @queue = []
    @names = []
    @turned = false
    @beamed = false
  end
  
  def control
  	if @plan.empty? or @plan[0].isEnded()
  		replan
    end
    
	  unless @plan.empty?
	    @plan[0].execute
    end
  end
  
  def replan
  end
  
  def reset
  	@plan.clear
  	@beamed = false
  end
  
  def turned_to_goal?
  	@@straight_range1.include?( @kick_target_phi) or @@straight_range2.include?( @kick_target_phi)
  end

  def see_ball?
   	ball.notSeenLongTime() < 5
  end
  
  def ball_front?
  	@agentInfo.getWhereIsBall.eql? 'front'	
  end
  
  def ball_back?
  	@agentInfo.getWhereIsBall.eql? 'back'
  end
  
  def is_ball_mine?
  	return @agentInfo.isBallMine
  end
 
  def straight?
    @@straight_range1.include?(@target_position_phi) or @@straight_range2.include?(@target_position_phi)
  end
  
  def near_ball?
  	return @agentInfo.getNearBall
  end

  def closest_to_ball?
    return true
  end
  
  def ball_unseen
    EnvironmentModel.SIMULATION_TIME - WorldModel.getInstance.getBall.getLastTimeSeen()
  end

  #def fillQueue

	#return
  #end
  
end

  #------------override Ruby's global logging => log PLANNING instead--------------
  
  def log message = ""
    Log.log LogType::PLANNING, message
  end

  def debug message = ""
    Log.debug LogType::PLANNING, message
  end

  def error message = ""
    Log.error LogType::PLANNING, message
  end
end

#reset on 2nd load => causing replan
Plan.instance.reset