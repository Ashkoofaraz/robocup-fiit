include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::sk.fiit.robocup.library.geometry.Vector3D
include_class Java::sk.fiit.jim.agent.models.AgentModel
include_class Java::sk.fiit.jim.agent.models.WorldModel
include_class Java::sk.fiit.jim.agent.models.EnvironmentModel
include_class Java::java.lang.Math
include_class Java::sk.fiit.robocup.library.geometry.Line2D
include_class Java::sk.fiit.robocup.library.geometry.Circle
include_class Java::sk.fiit.robocup.library.geometry.Vector2


class VedenieLopty < Java::sk.fiit.jim.agent.skills.ComplexHighSkill
  	
  def initialize
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    @radius = 1
  end
    
  def pickHighSkill
    case 
    when @agentInfo.ballUnseen > 5
      #puts 'finding ball'
      return Localize.new(Proc.new{(not see_ball? || me.on_ground?)})
    end
    #puts 'walking to'
    return pickWalk
  end
  
  def pickWalk
    #relative positions
    @ballRelPos = @agentInfo.ballControlPosition
    
    #absolute positions
    @ballAbsPos = WorldModel.getInstance.getBall.getPosition
    #@goalAbsPos = AgentInfo.getInstance.kickTarget
    @goalAbsPos = Vector3D.cartesian(11.5,0,0)
    @agentAbsPos = AgentModel.getInstance.getPosition
    
    #circle aroud ball in @radius radius 
    @circleAroundBall = Circle.new(Vector2.new(@ballAbsPos.getX(),@ballAbsPos.getY()),@radius)
    
    #line between ball and target
    @line = Line2D.new(@ballAbsPos.getX, @ballAbsPos.getY, @goalAbsPos.getX, @goalAbsPos.getY)
    #orthogonal line
    @ortLine = Line2D.new(@ballAbsPos.getX, @ballAbsPos.getY, @line.getNormalVector)
    
    #points of intersection between line (ball,target) and circle(ball,radius)
    #there have to be always two points as a result for each line 
    @pointsAroudBallOnSides = @ortLine.getCircleIntersection(@circleAroundBall) 
    @pointsAroudBallOnWay = @line.getCircleIntersection(@circleAroundBall)
    
    @leftPoint = Vector3D.cartesian(getHighestY(@pointsAroudBallOnSides),0)
    @rightPoint = Vector3D.cartesian(getLowestY(@pointsAroudBallOnSides),0)
    @frontPoint = Vector3D.cartesian(getLowestX(@pointsAroudBallOnWay),0)
         
    #puts @circleAroundBall
    #puts @line
    #puts @ortLine
    #puts @frontPoint
    #puts @leftPoint
    #puts @rightPoint
    
    if @ortLine.solveGeneralEqation(@agentAbsPos.getX,@agentAbsPos.getY)<2 then
      #puts 'ball is behind me'
      if @line.solveGeneralEqation(@agentAbsPos.getX,@agentAbsPos.getY)>0 then
        @targetToGo = @leftPoint 
        #puts 'I have to go left around ball'
      else        
        @targetToGo = @rightPoint
        #puts 'I have to go right around ball'
      end
    else
      #puts 'ball is in front of me, I have to go to front point'
      @targetToGo = @frontPoint
    end
    
    #@targetToGo = AgentModel.getInstance.relativize @targetToGo
    #puts 'BALL IS at'
    #puts @ballAbsPos
    #puts 'GOING TO'
    #puts @targetToGo
    #puts 'I am here>'
    #puts @agentAbsPos
    return WalkOld.new(@targetToGo)
    #return WalkOld.new(:ball)
  end  
  
  def getHighestY points
    points.sort_by {|p| p.getY}
    return points.last
  end
  
  def getLowestY points
    points.sort_by {|p| p.getY}
    return points.first
  end
  
  def getLowestX points
    points.sort_by {|p| p.getX}
    return points.first
  end
  
  def see_ball?
    ball.notSeenLongTime() < 5
  end
  
end
