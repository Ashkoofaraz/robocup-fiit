class NilClass
  def method_missing name, *args
    puts "Method #{name} called on Nil. At: #{caller[0]}"
    raise "Method #{name} called on Nil. At: #{caller[0]}"
  end
end

class Object
  def method_missing name, *args
    puts "Method #{name} called on #{self.class.name}. At: #{caller[0]}"
    puts "Defined methods: #{self.class.instance_methods.join "\n"}"
    raise "Method #{name} called on #{self.class.name}. At: #{caller[0]}"
  end
end