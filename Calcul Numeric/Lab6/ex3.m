clear all; clf;

temperature = [0, 10, 20, 30, 40, 60, 80, 100];
pressure = [0.0061, 0.0123, 0.0234, 0.0424, 0.0738, 0.1992, 0.4736, 1.0133];
req = [45];

plot(temperature, pressure, 'x'); hold on;

pp1 = polyval(polyfit(temperature, pressure, 2), 0:10:100);
pp2 = polyval(polyfit(temperature, pressure, 4), 0:10:100);

vals = polyval(polyfit(temperature, pressure, 4), req);

plot(0:10:100, pp1); hold on;
plot(0:10:100, pp2); hold on;
plot(req, vals, 'o'); hold on;

