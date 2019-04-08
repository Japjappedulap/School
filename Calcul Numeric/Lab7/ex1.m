clear all;

func = @(x) 2 ./ (1 + x.^2);
a_given = 0;
b_given = 1;

%% trapezium: 
%%   	calculates the trapezium determined by f(a) and f(b)
%% 		with the origin
function Area = trapezium(f, a, b)
	Area = (f(a) + f(b)) * (b - a) / 2;
end

x = trapezium(func, a_given, b_given);
fplot(func, [a_given, b_given]); hold on;
plot([a_given, a_given], [0, func(a_given)]); hold on;
plot([b_given, b_given], [0, func(b_given)]); hold on;
axis([a_given - 0.2, b_given + 0.2, 0, 2.5]);
disp(x);
