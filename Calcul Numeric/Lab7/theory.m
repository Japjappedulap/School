clear all;

func = @(x) 2 ./ (1 + x.^2);
a_given = 0;
b_given = 1;
n_given = 150;

%% trapezium: 
%%   	calculates the trapezium determined by f(a) and f(b)
%% 		with the origin
function Area = trapezium(f, a, b)
	Area = (f(a) + f(b)) * (b - a) / 2;
end

%% RepeatedTrapezium: 
%% 		calculates the repeated trapezium determined by f(a) and f(b)
%% 		with the origin
function Area = repeatedTrapezium(f, a, b, n)
	x = linspace(a, b, n + 1);
	rest = ((b - a) ^ 3) / (12 * n^2);
	Area = ((b - a) / (2 * n)) * (f(a) + f(b) + 2 * sum(f(x(2:n))));
end

%% simpsonQuadrature: 
%% 		calculates the area using simpson's quadrature
function Area = simpsonQuadrature(f, a, b)
	Area = ((b - a) / 6) * (f(a) + 4 * f((a + b) / 2) + f(b));
end

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


trap1 = trapezium(func, a_given, b_given);
trap2 = repeatedTrapezium(func, a_given, b_given, n_given);
simp1 = simpsonQuadrature(func, a_given, b_given);
simp2 = repeatedSimpsonQuadrature(func, a_given, b_given, n_given);

fprintf(" %.16f\n", trap1);
fprintf(" %.16f\n", trap2);
fprintf(" %.16f\n", simp1);
fprintf(" %.16f\n", simp2);
