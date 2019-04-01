clear all;
figure('units','normalized','outerposition',[0 0 1 1])

X = [0, pi / 2, pi, 3*pi / 2, 2*pi];
Y = [0, 1, 0, -1, 0];
disp(X);
disp(Y);

ppval(spline(X, [1, Y, 1]), pi/4)
clf; hold on;
plot(X, Y, 'r*');
S = @(x) ppval(spline(X, [1, Y, 1]), x);
fplot(S, [0, 2*pi]);
fplot(@sin, [0, 2*pi], '--m');

