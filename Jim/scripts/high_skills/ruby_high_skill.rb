class RubyHighSkill < Java::sk.fiit.jim.agent.skills.HighSkill
  
  def initialize
    super
  end
  
  def pickLowSkill
    raise "Abstract pickLowSkill called"
  end
  
  def checkProgress
    raise "Abstract checkProgress called"
  end
  
  def get_skill skill_name
    log "Chosen skill: #{skill_name}"
    Java::sk.fiit.jim.agent.moves.LowSkills.get skill_name
  end
  
  def ball_unseen
    EnvironmentModel.SIMULATION_TIME - WorldModel.getInstance.getBall.getLastTimeSeen()
  end  
end