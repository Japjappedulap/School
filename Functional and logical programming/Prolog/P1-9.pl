% a)

insert([], 0, E, [E]).
insert([], _, _, []).
insert([H | T], 0, E, R) :-
    append([[E], [H], T], R).
insert([H | T], P, E, [H | R]) :-
    NewP is P - 1,
    insert(T, NewP, E, R).

% b) Define a predicate to determine the greatest common divisor of all numbers from a list.

gcd(X, 0, X).
gcd(X, Y, R):-
    Y > 0,
    X1 is X mod Y,
    gcd(Y, X1, R).

gcdlist([], 0).
gcdlist([H | T], TR) :-
    gcdlist(T, R),
    gcd(H, R, TR).
