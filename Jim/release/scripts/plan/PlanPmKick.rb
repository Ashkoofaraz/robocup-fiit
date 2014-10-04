### planPm.rb ###
#	@author: Pavol Mestanik			#
# Upravene z planu timu A55Kickers #
#####################################

class PlanPmKick < Plan  
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
    @kick_target = @mathModel.cartesian(1.8,0.0,0.0)
    @leg = "right"
       
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
    else
        @agentInfo.loguj('Kick')
        ball_pos = @worldModel.getBall().getPosition             
        puts "Ball X:"    
        puts ball_pos.getX()
        puts "Ball Y:"
        puts ball_pos.getY()
        @ball_distance = @agentInfo.calculateDistance(Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(0, 0, 0), ball_pos)
        puts "Ball distance from mid:"
        puts @ball_distance
        
        if (ball_pos.getY().abs > 0.3 or ball_pos.getX().abs > 0.3)    
          return nil
        end           	
        
        @plan << RubyKickDynamicStraightSimple.new(@kick_target, @leg)        
    end
  end
end