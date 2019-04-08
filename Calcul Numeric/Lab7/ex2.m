clear all;

func = @(x, y) log(x + 2 * y);
a_given = 1.4;
b_given = 2;
c_given = 1;
d_given = 1.5;

%% doubleTrapezium: calculates the double trapezium determined by 
%% 			f(x, y) with the origin
function Area = doubleTrapezium(f, a, b, c, d)
	coeff1 = ((b - a) * (d - c)) / 16;
	coeff2 = ...
		f(a, c) + f(a, d) + f(b, c) + f(b, d) + ...
		2 * f((a + b) / 2, c) + ...
		2 * f((a + b) / 2, d) + ...
		2 * f(a, (c + d) / 2) + ...
		2 * f(b, (c + d) / 2) + ...
		4 * f((a + b) / 2, (c + d) / 2);
	Area = coeff1 * coeff2;
end


A = doubleTrapezium(func, a_given, b_given, c_given, d_given);

fprintf(" %.6f\n", A);
