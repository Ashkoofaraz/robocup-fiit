require 'high_skills/ruby_high_skill.rb'

class Beam < RubyHighSkill
  
  def initialize position
    super()
    @run_already = false
    @pos = position 
  end
  
  def pickLowSkill
     unless @run_already then
          current_skill = get_skill "rollback"
          @run_already = true
          return current_skill
     end
     ypos = @pos.getY()
     xpos = @pos.getX()
     Java::sk.fiit.jim.agent.communication.Communication.instance.addToMessage "(beam " + xpos.to_s + " " + ypos.to_s + " 0.0)"
#     ypos = 1.0 + Java::sk.fiit.jim.agent.AgentInfo.playerId;
#     Java::sk.fiit.jim.agent.communication.Communication.instance.addToMessage "(beam -5.0 " + ypos.to_s + " 0.0)"
     nil
  end
 
  def checkProgress
    #do nothing
  end
end