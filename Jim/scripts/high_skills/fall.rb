class Fall < RubyHighSkill
  
  def initialize
    super()
    puts "initialized Fall"
    
	@fallen = false
  end

  def pickLowSkill
	if( not @fallen)		
		@fallen =true
		get_skill("fall_back")
	end
	
  end
  
  def checkProgress
  end
end

#in order not to defer the startup cost
Fall.new