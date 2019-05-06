clear all;

func = @(x) 100 ./ (x .^ 2) .* sin(10 ./ x);
a_given = 1;
b_given = 3;
n_given = 5;

%% rectangleQuadrature: calculates the area using 
%%		the rectangle's quadrature
function Area = rectangleQuadrature(f, a, b)
	Area = (b - a) * f((a + b) / 2);
end

%% repeatedRectangleQuadrature: calculates the 
%% 		area using the repeated rectangle quadrature
function Area = repeatedRectangleQuadrature(f, a, b, n)
	x = linspace(a + (b - a) / (2 * n), b - (b - a) / (2 * n), n);
	Area = ((b - a) ./ n) * sum(f(x));
end

%% romberg: function description
function Area = romberg(f, a, b, n)
	Area = (f(a) + f(b)) * (b - a) / 2;
	h = b - a;
	for k = 1 : n
		j = 1 : 2 ^ (k - 1);
		Area = Area / 2 + (h / 2 ^ k) * sum(f(a + (2 * j - 1) * (h / (2 ^ k))));
	end
end

%% rombergMatrix: function description
function T = rombergMatrix(f, a, b, n)
	T = NaN(n + 1);
	h = b - a;
	T(1, 1) = (f(a) + f(b)) * (b - a) / 2;
	for k = 1 : n
		j = 1 : 2 ^ (k - 1);
		T(k + 1) = T(k) / 2 + (h / 2 ^ k) * sum(f(a + (2 * j - 1) * (h / (2 ^ k))));
	end

	for j = 2 : n + 1
		for i = j : n + 1
			T(i, j) = (4 ^ -(j - 1) * T(i-1, j-1) - T(i, j-1)) / (4 ^ -(j - 1) - 1);
		end
	end
end

rm = rombergMatrix(func, a_given, b_given, n_given);

disp(rm);
disp(quad(func, a_given, b_given));
% fplot(func, [a_given, b_given]);
