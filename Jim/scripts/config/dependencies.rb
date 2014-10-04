#sets up dependencies in a specified order
include_class Java::sk.fiit.jim.agent.parsing.Parser
include_class Java::sk.fiit.jim.agent.models.KalmanAdjuster
include_class Java::sk.fiit.jim.agent.models.EnvironmentModel
include_class Java::sk.fiit.jim.agent.models.AgentModel
include_class Java::sk.fiit.jim.agent.models.WorldModel
include_class Java::sk.fiit.jim.agent.models.prediction.Prophet

Parser.clear_observers

#Environment model must go first - it holds global reference to simulation time
Parser.subscribe EnvironmentModel.instance
#register Kalman's adjuster as the second one! All other listeners will listen to already adjusted data
Parser.subscribe KalmanAdjuster.new
#AgentModel comes as the third one - it needs to calculate rotations and positions, used later by WorldModel
Parser.subscribe AgentModel.instance

Parser.subscribe WorldModel.instance

Parser.subscribe Prophet.instance