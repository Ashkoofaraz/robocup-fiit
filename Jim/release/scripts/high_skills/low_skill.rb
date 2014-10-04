require 'high_skills/ruby_high_skill.rb'

class LowSkill < RubyHighSkill
  
  def initialize skill_name
    @skill = Java::sk.fiit.jim.agent.moves.LowSkills.get skill_name
	@run_already = false
    super()
  end
  
  def pickLowSkill
	unless @run_already then
	  @run_already = true
	  return @skill
	end
  end
  
  def checkProgress
    #do nothing
  end
end