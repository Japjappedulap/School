% a)
clear('all');

A = [400 -201; -800 401];
b = [200; -200];

fprintf('Solution for a)\n');
solution = A \ b;
disp(solution);

% b)

A_per = [401 -201; -800 401];
fprintf('Solution for b)\n');
solution_per = A_per \ b;
disp(solution_per);

% c)
clear('all');

A = [400 -201; 800 401];

fprintf('Solution for c)\n');
solution = cond(A);
disp(solution);
