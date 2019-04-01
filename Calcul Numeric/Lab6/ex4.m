% clear all; clf; hold on; axis equal;
% axis([0 2, 0 1]);

% x_input = 0; y_input = 0;
% X = [];
% Y = [];

% while 1 
% 	[x_input, y_input] = ginput(1);
% 	if x_input < 0 || x_input > 2 || y_input < 0 || y_input > 1
% 		break;
% 	end
% 	X = [X, x_input];
% 	Y = [Y, y_input];
% 	plot(x_input, y_input, 'x', 'MarkerSize', 5)

% end

% plot(0:0.01:2, polyval(polyfit(X, Y, 4), 0:0.01:2));
% plot(0:0.01:2, polyval(polyfit(X, Y, 8), 0:0.01:2));
% plot(0:0.01:2, polyval(polyfit(X, Y, 16), 0:0.01:2));

clear all; clf; hold on; axis equal;
axis([0 2, 0 1]);

x_input = 0; y_input = 0;
X = [];
Y = [];

while 1 
	[x_input, y_input] = ginput(1);
	if x_input < 0 || x_input > 2 || y_input < 0 || y_input > 1
		break;
	end
	X = [X, x_input];
	Y = [Y, y_input];
	plot(x_input, y_input, 'x', 'MarkerSize', 5)
end

t = linspace(0, 1, length(X));
tt = linspace(0, 1, 1000);
% plot(polyval(polyfit(t, X, 12), tt), polyval(polyfit(t, Y, 12), tt));
plot(ppval(spline(t, [1, X, 1]), tt), ppval(spline(t, [1, Y, 1]), tt));