% a)

gcd(X, 0, X).
gcd(X, Y, R):-
    Y > 0,
    X1 is X mod Y,
    gcd(Y, X1, R).

lcmlist([], 1).
lcmlist([H | T], NewR) :-
    lcmlist(T, R),
    Prod is H * R,
    gcd(H, R, Gcd),
    NewR is Prod div Gcd.

% b)

solver([], _, _ , _, []).
solver([H | T], E, Pow, 0, TR) :-
    NewPow is Pow * 2,
    NewStep is Pow * 2 - 1,
    solver(T, E, NewPow, NewStep, R),
    append([[H], [E], R], TR).
solver([H | T], E, Pow, Step, [H | R]) :-
    NewStep is Step - 1,
    solver(T, E, Pow, NewStep, R).

task(L, E, R) :- solver(L, E, 1, 0, R).
