class Something
  def initialize x, y
    @something = [x, y]
  end
  
  def method_missing name, *args
    return @something[0] if name == :x
    return @something[1] if name == :y
    raise "Unknown method #{name}"
  end
end



something = Something.new 4, 5

if something.x == 6
  value = something.x ** 4
else
  value = something.y ** 5 if something.y == 5
end