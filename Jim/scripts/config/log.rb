include Java
include_class Java::sk.fiit.jim.log.LogType
include_class Java::sk.fiit.jim.log.Log
include_class Java::sk.fiit.jim.log.LogLevel

Log.setLoggable LogType::INIT, true
Log.setLoggable LogType::PLANNING, false
Log.setLoggable LogType::AGENT_MODEL, false
Log.setLoggable LogType::WORLD_MODEL, false
Log.setLoggable LogType::INCOMING_MESSAGE, false
Log.setLoggable LogType::OUTCOMING_MESSAGE, false
Log.setLoggable LogType::INTERNALS, false
Log.setLoggable LogType::LOW_SKILL, false
Log.setLoggable LogType::HIGH_SKILL, false
Log.setLoggable LogType::OTHER, true

Log.output = "CONSOLE"
Log.pattern = "log.txt"
Log.log_level = LogLevel::LOG

def log message = ""
  Log.log LogType::HIGH_SKILL, message
end

def debug message = ""
  Log.debug LogType::HIGH_SKILL, message
end

def error message = ""
  Log.error LogType::HIGH_SKILL, message
end
