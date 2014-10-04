def agent_model argument = :agent
  model = Java::sk.fiit.jim.agent.models.AgentModel::instance
  return model.position if argument == :position
  return model.rotation_x if argument == :rotation_x
  return model.rotation_y if argument == :rotation_y
  return model.rotation_z if argument == :rotation_z
  model
end

alias :i :agent_model
alias :me :agent_model
alias :my :agent_model
alias :mine :agent_model
alias :agent :agent_model

def ball argument = :ball
  ball = Java::sk.fiit.jim.agent.models.WorldModel::instance.ball
  return ball.position if argument == :position
  return ball.relative_position if argument == :relative_position
  ball
end

def cartesian x, y, z
  Vector3D::cartesian x, y, z
end

def spherical r, phi, theta
  Vector3D::spherical r, phi, theta
end

def environment argument = :environment
  return Java::sk.fiit.jim.agent.models.EnvironmentModel.PLAY_MODE if argument == :play_mode
  return Java::sk.fiit.jim.agent.models.EnvironmentModel.SIMULATION_TIME if argument == :time
  Java::sk.fiit.jim.agent.models.EnvironmentModel::instance
end

def play_mode
  :play_mode
end

def time
  :time
end


#--------------------------DOMAIN SPECIFIC LANGUAGE SYNTAX SUGAR-----------------------

def position
  :position
end

# argument is here only to shut up errors like 1 argument get, expected 0
# if a call "my relative position" occurs 
def relative argument
  :relative_position
end

def to argument
  argument
end

alias :from :to
alias :next :to
alias :near :to