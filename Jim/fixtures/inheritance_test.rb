include_class Java::sk.fiit.jim.init.ScriptTest

class Inherited < ScriptTest
  
  def initialize
    super
  end
  
  def set_both_variables
    self.inherited2 = self.inherited1
  end
end

inherited = Inherited.new
inherited.set_both_variables

$script.toBeSetByScript = inherited