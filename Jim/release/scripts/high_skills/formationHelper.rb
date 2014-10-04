##### formationHelper.rb ######
#	Helper class (not high skill) with method getHighSkillToGoToFormation,
#	which returns high skill providing to move player to his position in formation.
#	@author: xsuchac
#	@author: A55-Kickers
#	
#	Example of planning in planner:
#	@plan << FormationHelper.getHighSkillToGoToFormation(Proc.new{see_ball?})
###############################

class FormationHelper

	# @param validity_proc - process for calling method from plan to check
	# 	if GoToPosition is still valid or not
	def FormationHelper.getHighSkillToGoToFormation validity_proc
		tacticalInfo = Java::sk.fiit.jim.agent.models.TacticalInfo.getInstance
    	target_position = tacticalInfo.getFormPositionGlobal()
    	return GoToPosition.new(target_position, validity_proc)
	end
end