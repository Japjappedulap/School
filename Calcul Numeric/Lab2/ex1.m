clf; hold on;
subplot(2, 2, 1)
f1 = @(x) x
fplot(f1, [0, 1])

subplot(2, 2, 2)
f2 = @(x) (3 / 2) * x .^ 2 - (1 / 2)
fplot(f2, [0, 1])

subplot(2, 2, 3)
f3 = @(x) (5 / 2) * x .^ 3 - (3 / 2) * x
fplot(f3, [0, 1])

subplot(2, 2, 4)
f4 = @(x) (35 / 8) * x .^ 4 - (15 / 4) * x .^ 2 + (3 / 8)
fplot(f4, [0, 1])


