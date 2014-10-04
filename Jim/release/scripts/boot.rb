dir = File.expand_path File.dirname(__FILE__)
$LOAD_PATH.unshift File.dirname(__FILE__)
include Java

Dir["#{dir}/scripts/**/*.rb"].each do |ruby_file|
  puts "Loading #{ruby_file}"
  load ruby_file unless File.identical? ruby_file, __FILE__
end