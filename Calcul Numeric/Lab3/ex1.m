function Lagrange = polyinterp(x, y, X)
  m = length(x)
  A = ones(1, m)
  
  for i = 1 : m
    A(i) = 1 / prod(x(i) - x([1 : i-1, i+1 : end]))
  end  
  
A
