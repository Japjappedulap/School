deleteOccurrences([], _, []).
deleteOccurrences([E | T], E, TR) :-
    deleteOccurrences(T, E, TR).
deleteOccurrences([H | T], E, [H | TR]) :-
    deleteOccurrences(T, E, TR).

% a)

listToSet([], []).
listToSet([H | T], [H | TR]) :-
    deleteOccurrences(T, H, NewH),
    listToSet(NewH, TR).

% b)

gcd(X, 0, X).
gcd(X, Y, R):-
    Y > 0,
    X1 is X mod Y,
    gcd(Y, X1, R).

gcdList([], 0).
gcdList([H | T], TR) :-
    gcdList(T, R),
    gcd(H, R, TR).
