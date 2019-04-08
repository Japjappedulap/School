clear all;

syms x;
% func is a SYMBOLIC function
func = x * log(x);
a_given = 1;
b_given = 2;

%% RepeatedTrapezium: 
%% 		calculates the repeated trapezium determined by f(a) and f(b)
%% 		with the origin
function Area = repeatedTrapezium(f, a, b, n)
	x = linspace(a, b, n + 1);
	rest = ((b - a) ^ 3) / (12 * n^2);
	Area = ((b - a) / (2 * n)) * (f(a) + f(b) + 2 * sum(f(x(2:n))));
end

%% getSmallestN: returns the smallest value N for which
%%    		the function satisfies inequation at page 13
function n = getSmallestN(f, a, b, EPS)
	secondDiff = diff(f, 2);
	secondDiffMatlabFunction = matlabFunction(secondDiff);
	M2f = fminbnd(secondDiffMatlabFunction, a, b);
	ezplot(secondDiff);
	N = 1;
	while N < 65536
		coeff = (b - a) ^ 3 / (12 * N);
		if coeff * M2f < EPS
			break
		end
		N += 1;
	end		
	n = N;
end

ezplot(func); hold on;
n_computed = getSmallestN(func, a_given, b_given, 0.00001);
A = repeatedTrapezium(matlabFunction(func), a_given, b_given, n_computed);

fprintf(" area: %.16f with repeated trapezium formula\n", A);
fprintf(" area: %.16f with classic numerical integration\n", quad(matlabFunction(func), a_given, b_given));

