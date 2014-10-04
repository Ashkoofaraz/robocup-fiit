include_class Java::sk.fiit.robocup.library.geometry.Vector3D

#operator definition for Ruby usage
class Vector3D
  def + another_vector
    add another_vector
  end
  
  def - another_vector
    subtract another_vector
  end
  
  def -@
    negate
  end
  
  def * scale
    return cross_product(scale) if scale.respond_to?(:cross_product)
    multiply scale
  end
  
  def / scale
    divide scale
  end
end