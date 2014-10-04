require 'high_skills/ruby_high_skill.rb'
include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::java.lang.Math

class Turn < RubyHighSkill

  def Turn.angle_range from, to
    	(from/180.0*Math::PI)..(to/180.0*Math::PI)
  end
  	
  @@turn_right_range = angle_range(180.0, 360.0) #Math::PI...(Math::PI*2.0)
  @@turn_left_range = angle_range(0.0, 180.0) #0...Math::PI
  @@in_place_range1 = angle_range(0.0, 15.0) #0...(Math::PI*0.1)
  @@in_place_range2 = angle_range(345.0, 360.0) #(Math::PI*1.9)...(Math::PI*2.0)

  def initialize target, validity_proc
    super()
    @target = target

    @validity_proc = validity_proc || Proc.new{return true}
    @ending = false
    
    @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
  end

def pickLowSkill
 	  is_still_valid = @validity_proc.call
    @ending = true if not is_still_valid   
    return nil if not is_still_valid or @ending

    @target = @agentInfo.kickTarget()
    @target_phi = @target.getPhi()
    
    
    case
      when me.on_ground? : nil
      when @@in_place_range1.include?(Angles.normalize(@target_phi)) : nil
      when @@in_place_range2.include?(Angles.normalize(@target_phi)) : nil
      when @@turn_left_range.include?(Angles.normalize(@target_phi)) : get_skill("test_turn_left")
      when @@turn_right_range.include?(Angles.normalize(@target_phi)) : get_skill("test_turn_right")
      else get_skill("test_turn_right")
    end
  end

 # def Turn.is_towards? point
 #   me_flattened = my.position.set_z(0.0)
 #   target_vector = point - me_flattened
 #   target_angle = target_vector.phi
 #   diff_against_current = Angles.normalize(target_angle - my.rotation_z)
 #   @@in_place_range1.include?(diff_against_current) or @@in_place_range2.include?(diff_against_current)
 # end

  def checkProgress
  #  raise "Fallen" if me.on_ground?
  end
end

#in order not to defer the startup cost
Turn.new 0.0, nil