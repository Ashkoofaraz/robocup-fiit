include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::sk.fiit.robocup.library.geometry.Vector3D
include_class Java::sk.fiit.jim.agent.models.AgentModel
include_class Java::sk.fiit.jim.agent.models.WorldModel
include_class Java::sk.fiit.jim.agent.models.EnvironmentModel
include_class Java::java.lang.Math
include_class Java::sk.fiit.robocup.library.geometry.Line2D
include_class Java::sk.fiit.robocup.library.geometry.Circle
include_class Java::sk.fiit.robocup.library.geometry.Vector2


class WalkBehindBallRelative < Java::sk.fiit.jim.agent.skills.ComplexHighSkill
  	
  def initialize
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    @radius = 1
  end
    
  def pickHighSkill
    case 
    when @agentInfo.ballUnseen > 3
      #puts 'finding ball'
      return Localize.new(Proc.new{(not see_ball? || me.on_ground?)})
    end
    #puts 'walking to'
    return pickWalk
  end
  
  def pickWalk
    #relative positions
    @ballRelPos = @agentInfo.ballControlPosition
    @goalRelPos = @agentInfo.kickTarget
    
    @leftPoint = @ballRelPos.add(Vector3D.cartesian(0,@radius,0))
    @frontPoint = @ballRelPos.add(Vector3D.cartesian(-@radius,0,0))      
     
    if(@agentInfo.whereIsTarget(@ballRelPos) == "back")
      puts "baaaaaaaaaaaaaaaaaaaaaaack"
      @targetToGo = @leftPoint
    else
      @targetToGo = @frontPoint
    end

    return WalkOld.new(@targetToGo)

  end  
  
end
