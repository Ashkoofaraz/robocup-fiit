include Java
include_class Java::sk.fiit.jim.agent.models.EnvironmentModel
include_class Java::sk.fiit.jim.agent.AgentInfo
include_class Java::sk.fiit.jim.agent.communication.Communication
include_class Java::sk.fiit.jim.Settings

EnvironmentModel.version = EnvironmentModel::Version::VERSION_0_6_5

AgentInfo::team = "Pidanic"
AgentInfo::playerId = 1

Settings.setValue("TestFramework_monitor_enable", false)
Settings.setIntValue("TestFramework_monitor_port", 8000)
#Settings.setValue("TestFramework_monitor_address", "192.168.1.12")
Settings.setValue("TestFramework_monitor_address", "localhost")

Settings.setValue("Tftp_enable", false)
Settings.setIntValue("Tftp_port", 3071)

Settings.setCommandLineOverrides();
if Settings.hasKey("team") then AgentInfo::team = Settings.getString("team") end
if Settings.hasKey("uniform") then AgentInfo::playerId = Settings.getInt("uniform") end

#Communication.instance.server_ip = "192.168.1.13"
Communication.instance.server_ip = "localhost"
Communication.instance.port = 3100

#Settings.setValue("Planner", "PlanPm")
#Settings.setValue("Planner", "PlanPmKick")
Settings.setValue("Planner", "PlanDynamicMove")
#Settings.setValue("Planner", "PlanKopnutieNaMiesto")
#Settings.setValue("Planner", "Plan5ko")
#Settings.setValue("Planner", "TestPlan")
#Settings.setValue("Planner", "PlanTactic")
#Settings.setValue("Planner", "PlanTournamentKickDistance")
#Settings.setValue("Planner", "PlanCreateFormation")
#Settings.setValue("Planner", "PlanZakladny")

#Settings.set_value "ignoreAccelerometer", true
#Settings.set_value "kalmanUseFilter", true
#Settings.set_value "kalmanDefaultQ", 0.475
#Settings.set_value "kalmanDefaultE", 0.375
#Settings.set_value "runGcOnPhaseStart", true
#Settings.set_value "runGui", true
#Settings.set_value "gravityAcceleration", 9.81
#Settings.set_value "maximumAngularChangePerQuantum", 7.0
#Settings.set_value "runTFTPserver", true