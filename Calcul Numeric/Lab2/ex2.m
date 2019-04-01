clf; hold on;
%Tn = @(n, t) cos(n * acos(t))
%
%t = -1:0.001:1;
%
%for n = 1:10
%    plot(t, Tn(n, t))
%end

T0 = @(x) ones(size(x)); 
T1 = @(x) x; 
fplot(T0, [-1, 1]);
fplot(T1, [-1, 1]);

for i = 2 : 10
  aux = T1;
  T1 = @(x) 2 * x .* aux(x) - T0(x);
  T0 = aux;
  fplot(T1, [-1, 1]);
end
