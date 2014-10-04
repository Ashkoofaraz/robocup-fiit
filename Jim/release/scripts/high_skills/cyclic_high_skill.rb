require 'high_skills/ruby_high_skill.rb'

class CyclicHighSkill < RubyHighSkill

  def name
    return @skill.name
  end
  
  def initialize skill_name
	@skill = Java::sk.fiit.jim.agent.moves.LowSkills.get(skill_name)
    super()
  end
  
  def pickLowSkill
    return @skill
  end
  
  def checkProgress
    #do nothing
  end
end