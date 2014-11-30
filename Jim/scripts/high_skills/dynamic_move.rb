require 'high_skills/ruby_high_skill.rb'

include_class Java::sk.fiit.robocup.library.geometry.Vector3D

# dynamic move prototype
# pidanic
class RubyDynamicMove < Java::sk.fiit.jim.agent.skills.dynamic.DynamicMove
  
  def initialize
    super()
    puts "initialized dynamic move"
    #@agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
    #@agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance
    #@mathModel = Java::sk.fiit.robocup.library.geometry.Vector3D.clone 
    #@worldModel = Java::sk.fiit.jim.agent.models.WorldModel.getInstance   
    
    #@kick_target = kick_target
    #@leg = leg
    @moved = false
  end

  def pickLowSkill
	puts "RubyDynamicMove pickLowSkill"
    #@agentInfo.loguj('Vyber skillu')                           
    #ball_pos = @worldModel.getBall().getPosition
     
    #@kick_target_dist = @agentInfo.calculateDistance(ball_pos, @kick_target)
    #puts "dist:" 
    #puts @kick_target_dist
    #puts "Dynamic kick"
    
    if(!@moved)
      @moved = true
	  puts "Creating dynamic move"
                  
		#(points, orientations) = createSequenceLeftArm
		#createDynamicMove("leftArm", points, orientations)
		
		(points, orientations) = createSequenceLeftLeg
		createDynamicMove("leftLeg", points, orientations);
    else
      return nil
    end
			
	#get_skill("fall_back")
	
  end
  
  def createSequenceLeftArm
    point1 = Java::sk.fiit.robocup.library.geometry.Point3D.new(195, 98, 75)
	orientation1 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromRadians(0.0, 0.0, 0.0)
	
	point2 = Java::sk.fiit.robocup.library.geometry.Point3D.new(0, 293, 75)
	orientation2 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromRadians(0.0, 0.0, 0.0)
	
	point3 = Java::sk.fiit.robocup.library.geometry.Point3D.new(0, 98, -120)
	orientation3 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromDegrees(45, 90, 45)
	
	point4 = Java::sk.fiit.robocup.library.geometry.Point3D.new(0, 98, 270)
	orientation4 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromRadians(0.0, 0.0, 0.0)
	
	point5 = Java::sk.fiit.robocup.library.geometry.Point3D.new(170, 98, 100)
	orientation5 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromRadians(0.0, 0.0, 0.0)
	
	point6 = Java::sk.fiit.robocup.library.geometry.Point3D.new(123.00, 203.86, -26.25)
	orientation6 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromRadians(0.93, 0.42, 0.84)
	
	points = Java::java.util.ArrayList.new
	orientations = Java::java.util.ArrayList.new
	points.add(point1)
	points.add(point2)
	points.add(point3)
	points.add(point4)
	points.add(point5)
	points.add(point6)
	
	orientations.add(orientation1)
	orientations.add(orientation2)
	orientations.add(orientation3)
	orientations.add(orientation4)
	orientations.add(orientation5)
	orientations.add(orientation6)
	return [points, orientations]
  end
  
  def createSequenceLeftLeg
    point1 = Java::sk.fiit.robocup.library.geometry.Point3D.new(0.0, 55.0, -385.0)
	orientation1 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromRadians(0.0, 0.0, 0.0)
	
	point2 = Java::sk.fiit.robocup.library.geometry.Point3D.new(0, 245, -305)
	orientation2 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromRadians(0.0, 0.0, 0.0)
	
	point3 = Java::sk.fiit.robocup.library.geometry.Point3D.new(190, 55, -305)
	orientation3 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromDegrees(0, 90, 45)
	
	point4 = Java::sk.fiit.robocup.library.geometry.Point3D.new(188, 74, -303)
	orientation4 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromRadians(0.39, -0.79, 0)
	
	point5 = Java::sk.fiit.robocup.library.geometry.Point3D.new(234.85, 55.00, -199.85)
	orientation5 = Java::sk.fiit.jim.agent.moves.ik.Orientation.fromRadians(-0.18, -1.57, 0.18)
	
	
	points = Java::java.util.ArrayList.new
	orientations = Java::java.util.ArrayList.new
	points.add(point1)
	points.add(point2)
	points.add(point3)
	points.add(point4)
	points.add(point5)
	
	orientations.add(orientation1)
	orientations.add(orientation2)
	orientations.add(orientation3)
	orientations.add(orientation4)
	orientations.add(orientation5)
	return [points, orientations]
  end
  
  def checkProgress
  end
end

#in order not to defer the startup cost
RubyDynamicMove.new