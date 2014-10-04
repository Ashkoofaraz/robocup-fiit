##### planTactic.rb	 ########
#	@author: A55Kickers		#
#############################

class PlanTactic < Plan
  
  def replan
    @plan.clear
    @agentInfo.whereIsBall();
    @agentInfo.isBallMine();
    @agentInfo.nearBall();
    @agentInfo.ballControlPosition();
      	
    @target_position      = @agentInfo.ballControlPosition()     #pozicia lopty
    @target_position_phi  = @target_position.getPhi()        #pozicia lopty phi
    @kick_target          = @agentInfo.kickTarget()                  #ciel kopu
    @kick_target_phi      = @kick_target.getPhi()               #ciel kopu phi
    
    @is_ball_nearest_to_me_in_my_team = @tacticalInfo.bIsBallNearestToMeInMyTeam()
   	@is_ball_owned_by_them            = @tacticalInfo.isBallOwnedByThem()
    @is_ball_owned_by_us              = @tacticalInfo.isBallOwnedByUs()
    
    @chance_to_get_ball = @tacticalInfo.chanceToGetBall()
    
    @agentInfo          = Java::sk.fiit.jim.agent.AgentInfo.getInstance
   	@mathModel          = Java::sk.fiit.robocup.library.geometry.Vector3D.clone
    #@fixedObject       = Java::sk.fiit.jim.agent.models.FixedObject.????
   	#@their_goal        = @fixedObject.theirPostMiddle()            				#pozicia superovej brany
   	@ball_pos           = @agentInfo.ballControlPosition()
   	@their_goal         = @mathModel.cartesian(10,0.0,0.0)
   	@ball_to_their_goal = @agentInfo.calculateDistance(@ball_pos, @their_goal) #vzdialenost lopty a superovej brany
   	#puts @ball_to_their_goal 

    if EnvironmentModel.beamablePlayMode
    	@agentInfo.loguj('Beam');
    	@plan << Beam.new(@formationPosition)
    	@beamed = true
    elsif (me.on_ground? or me.is_lying_on_back? or me.is_lying_on_belly?)
    	@agentInfo.loguj('GetUp');
     	@plan << GetUp.new(Proc.new{me.on_ground?})
    elsif (not see_ball?) 
   		@agentInfo.loguj('Localize');
    	@plan << Localize.new(Proc.new{(not see_ball?)})
	
   	### 1. Ak nikto nevlastni loptu ###

    ### ak nema nikto loptu + som najblizsie k lopte 
    elsif (not is_ball_mine? and not @is_ball_owned_by_us and not @is_ball_owned_by_them and @is_ball_nearest_to_me_in_my_team)          
        @agentInfo.loguj('1/Walk to ball');
    	@plan << Walk2Ball.new(:ball, Proc.new{not @is_ball_owned_by_us and not @is_ball_owned_by_them and @is_ball_nearest_to_me_in_my_team})

   	### ak nema nikto loptu + nie som najblizsie k lopte
    elsif (not is_ball_mine? and not @is_ball_owned_by_us and not @is_ball_owned_by_them and not @is_ball_nearest_to_me_in_my_team)          
    	@tacticalInfo.setMyFormPosition
    	if (@tacticalInfo.isInPositionArea)
    		@agentInfo.loguj('1/On position');
  			@plan << LowSkill.new("rollback")
  		else
    		@agentInfo.loguj('1/Go to formation');
    		@plan << FormationHelper.getHighSkillToGoToFormation(Proc.new{see_ball? and not is_ball_mine? and not @is_ball_owned_by_us and not @is_ball_owned_by_them and not @is_ball_nearest_to_me_in_my_team})
    	end
    	
=begin
    ### ak nema nikto loptu + mam sancu dostat sa k lopte >= 80% 
    elsif (not is_ball_mine? and not @is_ball_owned_by_us and not @is_ball_owned_by_them and @chance_to_get_ball >= 0.8)          
    	@tacticalInfo.chanceToGetBallString();
        @agentInfo.loguj('1/Walk to ball');
    	@plan << Walk2Ball.new(:ball, Proc.new{not @is_ball_owned_by_us and not @is_ball_owned_by_them})

   	### ak nema nikto loptu + mam sancu dostat sa k lopte < 80%
    elsif (not is_ball_mine? and not @is_ball_owned_by_us and not @is_ball_owned_by_them and @chance_to_get_ball < 0.8)          
    	@tacticalInfo.setMyFormPosition
    	if (@tacticalInfo.isInPositionArea)
    		@agentInfo.loguj('1/On position');
  			@plan << LowSkill.new("rollback")
  		else
	    	@tacticalInfo.chanceToGetBallString();
	    	@agentInfo.loguj('1/Go to formation');
	    	@plan << FormationHelper.getHighSkillToGoToFormation(Proc.new{see_ball? and not is_ball_mine? and not @is_ball_owned_by_us and not @is_ball_owned_by_them and @chance_to_get_ball < 0.8})
	    end
=end
    	
   	### 2. Ak super vlastni loptu ###
   	
   	### ak ma loptu super + som najblizsie k lopte 
    elsif (not is_ball_mine? and @is_ball_owned_by_them and @is_ball_nearest_to_me_in_my_team)         
        @agentInfo.loguj('2/Walk to ball');
    	@plan << Walk2Ball.new(:ball, Proc.new{@is_ball_owned_by_them and @is_ball_nearest_to_me_in_my_team}) 

    ### ak ma loptu super + nie som najblizsie k lopte
    elsif (not is_ball_mine? and @is_ball_owned_by_them and not @is_ball_nearest_to_me_in_my_team)          
    	@tacticalInfo.setMyFormPosition
    	if (@tacticalInfo.isInPositionArea)
    		@agentInfo.loguj('2/On position');
  			@plan << LowSkill.new("rollback")
  		else
    		@agentInfo.loguj('2/Go to formation');
    		@plan << FormationHelper.getHighSkillToGoToFormation(Proc.new{see_ball? and not is_ball_mine? and @is_ball_owned_by_them and not @is_ball_nearest_to_me_in_my_team})
    	end
  	
    ### 3. Ak my vlastnime loptu ###
    
   	### ak mame loptu my + nemam loptu ja
    elsif (not is_ball_mine? and @is_ball_owned_by_us)   
    	@tacticalInfo.setMyFormPosition
    	if (@tacticalInfo.isInPositionArea)
    		@agentInfo.loguj('3/On position');
  			@plan << LowSkill.new("rollback")
  		else    
	    	@agentInfo.loguj('3/Go to formation');
	    	@plan << FormationHelper.getHighSkillToGoToFormation(Proc.new{see_ball? and not is_ball_mine? and @is_ball_owned_by_us})
	    end
	    
    ### ak mam loptu ja + nie som natoceny na loptu
    elsif (is_ball_mine? and not straight?)         
    	@agentInfo.loguj('3/Walk to straight');
		@plan << Walk2Ball.new(:ball, Proc.new{is_ball_mine? and not straight?}) 

	### ak mam loptu ja + nie som natoceny na branu
    elsif (is_ball_mine? and not turned_to_goal?)         
    	@agentInfo.loguj('3/Turn to goal');
		@plan << Turn.new(@kick_target, Proc.new{is_ball_mine? and not turned_to_goal?})
		
	### ak mam loptu ja + som natoceny na loptu + som natoceny na branu
    elsif (is_ball_mine? and straight? and turned_to_goal?)         
    	@agentInfo.loguj('3/Kick');
    	@plan << Kick.new(@kick_target)
  
=begin		
    ### ak mam loptu ja + som natoceny na loptu + som natoceny na branu
    elsif ((is_ball_mine? and straight? and turned_to_goal? and @ball_to_their_goal > 3))         
    	@agentInfo.loguj('3/Walk to ball');
		@plan << Walk2Ball.new(:ball, Proc.new{is_ball_mine? and straight? and turned_to_goal?}) 

    ### ak mam loptu ja + som natoceny na loptu + som natoceny na branu
    elsif ((is_ball_mine? and straight? and turned_to_goal? and @ball_to_their_goal <= 3))         
    	@agentInfo.loguj('3/Kick');
    	@plan << Kick.new(@kick_target)
=end
    
    ### Ak nastane neosetreny pripad ###
    else
    	@agentInfo.loguj('???');
    	@plan << GetUp.new(Proc.new{me.on_ground?})

    end
  end
end