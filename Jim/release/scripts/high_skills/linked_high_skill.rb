require 'high_skills/ruby_high_skill.rb'

class LinkedHighSkill < RubyHighSkill

  def name
    return @currentSkill.name
  end
  
  def initialize
    @skills = []
    @skills << Java::sk.fiit.jim.agent.moves.LowSkills.get("step_circ_left_small")
    @skills << Java::sk.fiit.jim.agent.moves.LowSkills.get("step_circ_left_small")
    @skills << Java::sk.fiit.jim.agent.moves.LowSkills.get("step_circ_left_small")
    @skills << Java::sk.fiit.jim.agent.moves.LowSkills.get("step_circ_left_small")

	@currentSkill = @skills[0]
    super()
  end
  
  def pickLowSkill
    unless @skills.empty?
      @currentSkill = @skills.shift
      log "picking skill #{@currentSkill.name}"
      return @currentSkill
    end
    return nil
  end
  
  def checkProgress
    #do nothing
  end
end