clear all;

func = @(x) (1 ./ (4 + sin(20 * x)));
a_given = 0;
b_given = pi;
n1_given = 10;
n2_given = 30;


%% repeatedSimpsonQuadrature: 
%% 		calculates the area using repeated simpson's formula
function Area = repeatedSimpsonQuadrature(f, a, b, n)
	x = linspace(a, b, n + 1);
	Area =  ((b - a) / (6 * n)) * ...
			(f(a) + ...
			 f(b) + ...
			 4 * sum(f((x(1:end-1) + x(2:end)) / 2)) + ...
			 2 * sum(f(x(2:end-1))) ...
			);
end

A1 = repeatedSimpsonQuadrature(func, a_given, b_given, n1_given);
A2 = repeatedSimpsonQuadrature(func, a_given, b_given, n2_given);

fprintf(" N = %d, area: %.6f\n", n1_given, A1);
fprintf(" N = %d, area: %.6f\n", n2_given, A2);

