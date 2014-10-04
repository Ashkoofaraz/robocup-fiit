require 'high_skills/ruby_high_skill.rb'

class LowSkillTest < RubyHighSkill
  
  def initialize skill_name
    super()
    @skill = Java::sk.fiit.jim.agent.moves.LowSkills.get skill_name
  end
  
  def pickLowSkill
    log "Low skill"
    @skill
  end
  
  def checkProgress
    #do nothing
  end
end

#in order not to defer startup cost
LowSkillTest.new ""