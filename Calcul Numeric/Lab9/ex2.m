clear('all');

A = [10 7 8 7; 7 5 6 5; 8 6 10 9; 7 5 9 10];
b = [32; 23; 33; 31];

A_per = [10 7 8.1 7.2; 7.08 5.04 6 5; 8 5.98 9.89 9; 6.99 4.99 9 9.98];
b_per = [32.1; 22.9; 33.1; 30.9];

cond(A)

% a)
fprintf('Solution for a)\n');
x = A \ b

% b)
fprintf('Solution for b)\n');
xp = A \ b_per
er_rel_b = norm(b - b_per) / norm(b);
er_rel_x = norm(x - xp) / norm(x);

rel = er_rel_x / er_rel_b

% c)
fprintf('Solution for c)\n');
xpp = A_per \ b
er_rel_bA = norm(b - b_per) / norm(b);
er_rel_xA = norm(x - xpp) / norm(x);

relA = er_rel_xA / er_rel_bA
