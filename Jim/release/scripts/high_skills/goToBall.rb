require 'high_skills/ruby_high_skill.rb'
include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::java.lang.Math

class GotoBall < RubyHighSkill

	def GotoBall.angle_range from, to
    	(from/180.0*Math::PI)..(to/180.0*Math::PI)
  end
  
  def distance(x,y)
    return Math.sqrt(x*x +  y*y)
  end

  @@rovno1 = angle_range(0.0, 25.0)
  @@rovno2 = angle_range(335.0, 360.0)
  @@vpravo = angle_range(270.0, 335.0)
  @@vlavo = angle_range(25.0, 90.0)
  @@vpravoZad = angle_range(180.0, 270.0)
  @@vlavoZad = angle_range(90.0, 180.0)
  
  @@daleko = 4
  @@blizko = 1.0
  @@tesne = 0.2
  @@odLopty = 0.7
  @@kLopte = 0.1

  
  def initialize(targetPosition)
    super()
    @tacticalInfo = Java::sk.fiit.jim.agent.models.TacticalInfo.getInstance
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    @agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance
    
    @targetPosition = targetPosition
    @closeMove = false
    @indirectMove = false
    @finished = false
  end
  
  def pickLowSkill
    if @finished == true
      return get_skill("rollback")
    end
    
    @ballPosition = ball.position
    @agentPosition = @agentModel.getPosition()
    
    if @closeMove == false
      @movePosition = computeMidMovePoint()
    else
      @movePosition = computeFinalMovePoint()
    end
    @movePosition_phi = @movePosition.getPhi()
    
    
 #   puts "RM: " + @movePosition.getX().to_s+", "+@movePosition.getY().to_s
 #   puts "GM: " + @agentModel.globalize(@movePosition).getX().to_s+", "+@agentModel.globalize(@movePosition).getY().to_s
 #   puts "close: "+@closeMove.to_s+", indirect: "+@indirectMove.to_s+", finished: "+@finished.to_s
 #   puts "MOVE DISTANCE: "+Math.sqrt(@movePosition.getX()*@movePosition.getX() + @movePosition.getY()*@movePosition.getY()).to_s
 #   phi_degrees = @movePosition_phi * 180 / Math::PI
 #   puts "degrees: "+phi_degrees.to_s
 #   puts ""

    case
      when me.on_ground?
        puts "PADNUTY"
        @closeMove = false
        @indirectMove = false
        return
#       when @agentInfo.ballUnseen() > 10
#        puts "NEVIDIM"
#        return
      when distance(@movePosition.getX(), @movePosition.getY()) > @@daleko
        puts "DALEKO"
        @closeMove == false
        case
          when rovno?
            puts "ROVNO"
            return get_skill("walk_forward")
          when vlavo?
            puts "VLAVO"
            return get_skill("turn_left_36")
          when vpravo?
            puts "VPRAVO"
            return get_skill("turn_right_36")
          when vlavoZad?
            puts "VLAVOZAD"
          return get_skill("turn_left_36")
          when vpravoZad?
            puts "VPRAVOZAD"
            return get_skill("turn_right_36")
          else return get_skill("rollback")
        end
      when distance(@movePosition.getX(), @movePosition.getY()) > @@blizko
        puts "BLIZKO"
        case
          when rovno?
            puts "ROVNO"
            return get_skill("walk_forward")
          when vlavo?
            puts "VLAVO"
            return get_skill("turn_left_cont_20")
          when vpravo?
            puts "VPRAVO"
            return get_skill("turn_right_cont_20")
          when vlavoZad?
            puts "VLAVOZAD"
            return get_skill("turn_left_90")
          when vpravoZad?
            puts "VPRAVOZAD"
            return get_skill("turn_right_90")
          else return get_skill("rollback")
        end
      when (distance(@movePosition.getX(), @movePosition.getY()) > (@@tesne) && @indirectMove == true)
        puts "TESNE1"
        case
          when rovno?
            puts "ROVNO"
            return get_skill("walk_slow")
          when vlavo?
            puts "VLAVO"
            return get_skill("step_left")
          when vpravo?
            puts "VPRAVO"
            return get_skill("step_right")
          when vlavoZad?
            puts "VLAVOZAD"
            return get_skill("turn_left_90")
          when vpravoZad?
            puts "VPRAVOZAD"
            return get_skill("turn_right_90")
          else return get_skill("stojRovno")
        end
      when (distance(@movePosition.getX(), @movePosition.getY()) > (@@tesne) && @closeMove == false)
        puts "TESNE2"
        case
          when rovno?
            puts "ROVNO"
            return get_skill("walk_slow")
          when vlavo?
            puts "VLAVO"
            return get_skill("turn_left_cont_20")
          when vpravo?
            puts "VPRAVO"
            return get_skill("turn_right_cont_20")
          when vlavoZad?
            puts "VLAVOZAD"
            return get_skill("turn_left_90")
          when vpravoZad?
            puts "VPRAVOZAD"
            return get_skill("turn_right_90")
          else return get_skill("stojRovno")
        end
      when (distance(@movePosition.getX(), @movePosition.getY()) > @@tesne && @closeMove == true)
        puts "TESNE3"
        case
          when rovno?
            puts "ROVNO"
            return get_skill("walk_slow")
          when vlavo?
            puts "VLAVO"
            return get_skill("turn_left_cont_10")
          when vpravo?
            puts "VPRAVO"
            return get_skill("turn_right_cont_10")
          when vlavoZad?
            puts "VLAVOZAD"
            return get_skill("turn_left_90")
          when vpravoZad?
            puts "VPRAVOZAD"
            return get_skill("turn_right_90")
          else return get_skill("rollback")
        end
      when distance(@movePosition.getX(), @movePosition.getY()) <= @@tesne
      if @closeMove == true
 #       puts "NA MIESTE CLOSE"
 #       puts "A1: "+Angles.normalize(@agentPosition.getPhi()).to_s
 #       puts "A2: "+Angles.normalize(@targetPosition.getPhi()).to_s
 #       if (Angles.normalize(@agentPosition.getPhi()) - Angles.normalize(@targetPosition.getPhi())) > 0.3
 #         puts "ZLEEEE"
 #       end
        @finished = true
        return get_skill("step_left")
      elsif @closeMove == false
        puts "NA MIESTE"
        @closeMove = true;
        return get_skill("rollback")
 #     elsif @indirectMove == true
 #       puts "NA MIESTE INDIRECT"
 #       @indirectMove = false
 #       return get_skill("rollback")
      end
    else
      puts "FINAL ELSE"
      return get_skill("rollback")
    end
  end
  

  
   
  def rovno?
    @@rovno1.include? Angles.normalize(@movePosition_phi) or @@rovno2.include? Angles.normalize(@movePosition_phi)
  end
  
  def vlavo?
    @@vlavo.include? Angles.normalize(@movePosition_phi)
  end
 
  def vpravo?
    @@vpravo.include? Angles.normalize(@movePosition_phi)
  end
  
  def vlavoZad?
    @@vlavoZad.include? Angles.normalize(@movePosition_phi)
  end
  
  def vpravoZad?
    @@vpravoZad.include? Angles.normalize(@movePosition_phi)
  end
  

  
  
  
  
#  def back? target_position
#    Angles.angle_diff(target_phi, Math::PI) < Math::PI / 6.0
#  end
    
  
  
  
  

  def computeMidMovePoint()
#    dist = computeDistance(@targetPosition, @ballPosition)
    dist = (@targetPosition.getX() - @ballPosition.getX()).abs() + (@targetPosition.getY() - @ballPosition.getY()).abs()
    
    dX = (@targetPosition.getX() - @ballPosition.getX()) / dist
    dY = (@targetPosition.getY() - @ballPosition.getY()) / dist
    moveX = @ballPosition.getX() - dX * @@odLopty
    moveY = @ballPosition.getY() - dY * @@odLopty
    
    movePositionTmp = Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(moveX, moveY, 0)

    agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance()
    movePosition = agentModel.relativize(movePositionTmp)
    
    moveDist = movePositionTmp.getXYDistanceFrom(@agentPosition)
    ballDist = @ballPosition.getXYDistanceFrom(@agentPosition)
    
#    if ((@ballPosition.getPhi() - movePositionTmp.getPhi()).abs() < 0.1 && moveDist > ballDist)
#      puts "BP: "+@ballPosition.getX().to_s+", "+@ballPosition.getY().to_s
#      puts "MID: "+movePositionTmp.getX().to_s+", "+movePositionTmp.getY().to_s
#      if dX > dY
#        newMP = Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(
#                movePositionTmp.getX() + 1.5 * dY,
#                movePositionTmp.getY() - 1.5 * dX,
#                0)
#      else
#        newMP = Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(
#                movePositionTmp.getX() - 1.5 * dY,
#                movePositionTmp.getY() + 1.5 * dX,
#                0)
#      end
#      
#      movePosition = agentModel.relativize(newMP)
#      
#      @indirectMove = true
#    end
    
    return movePosition
  end
  
  def computeFinalMovePoint()
    dist = computeDistance(@targetPosition, @ballPosition)
        
    dX = (@targetPosition.getX() - @ballPosition.getX()) / dist
    dY = (@targetPosition.getY() - @ballPosition.getY()) / dist
    moveX = @ballPosition.getX() - dX * @@kLopte
    moveY = @ballPosition.getY() - dY * @@kLopte
    
    movePositionTmp = Java::sk.fiit.robocup.library.geometry.Vector3D.cartesian(moveX, moveY, 0)
    
    agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance()
    movePosition = agentModel.relativize(movePositionTmp)

    return movePosition
  end

  def computeDistance(pA, pB)
    distX = pA.getX() - pB.getX()
    distY = pA.getY() - pB.getY()
    return Math.sqrt(distX * distX + distY * distY)
  end

  def checkProgress
#    raise "Fallen!" if me.on_ground?
  end    
end

#in order not to defer the startup cost
GotoBall.new(@targetPosition)

