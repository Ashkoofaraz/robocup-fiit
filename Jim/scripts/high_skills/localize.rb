require 'high_skills/ruby_high_skill.rb'
include_class Java::sk.fiit.robocup.library.geometry.Angles
include_class Java::java.lang.Math

class Localize < RubyHighSkill

  @@right = 180..360
  @@left = 0..180
  @@front1 = 0..90
  @@front2 = 270..360
  @@back = 90..270
  
  @@go1 = 0..30
  @@go2 = 330..360
  
  @leftLook = false;
  @rightLook = false;
  @downLook = false
  
  def initialize validity_proc
      super()
      @validity_proc = validity_proc
      @agentInfo = Java::sk.fiit.jim.agent.AgentInfo.getInstance
      @agentModel = Java::sk.fiit.jim.agent.models.AgentModel.getInstance
      
      @leftLook = false;
      @rightLook = false;
	  @downLook = false
  end
  
  def pickLowSkill
    @ball = WorldModel.getInstance.getBall.getRelativePosition
    case
          when (not @validity_proc.call) : 
            puts "validity"
            return nil
          when EnvironmentModel.beamablePlayMode :
            puts "beam" 
            return nil
          when @agentModel.falled :
            puts "fall" 
            return nil
      
         when !@leftLook :
            puts "left look"
            @leftLook = true
            return get_skill("head_left_120")
		when !@rightLook :
            puts "right look"
            @rightLook = true
            return get_skill("head_right_129´0")
		  when !@downLook :
            puts "down look"
            @downLook = true
            return get_skill("head_down_pp")
              
                 
         when (ballInRange? @@left and (ballInRange? @@front1 or ballInRange? @@front2)) :
           puts "vlavo vpredu" 
           return get_skill("turn_left_cont_20")
         when (ballInRange? @@right and (ballInRange? @@front1 or ballInRange? @@front2)) :
           puts "vpravo vpredu"  
           return get_skill("turn_right_cont_20")
         
         when (ballInRange? @@left and ballInRange? @@back) :
           puts "vlavo vzadu"  
           return get_skill("turn_left_90")
         when (ballInRange? @@right and ballInRange? @@back) :
           puts "vpravo vzadu"  
           return get_skill("turn_right_90")    
       end 
      return get_skill("turn_right_90")    
  end
 
  
  def ballInRange? range
    range.include?(Math.toDegrees(@ball.phi))
  end  
  
  def checkProgress
    #do nothing
  end
    
end	
#in order not to defer the startup cost
Localize.new nil