% member belongs inside list
isMemberOfList([], _) :- false.
isMemberOfList([E | _], E) :- true.
isMemberOfList([_ | T], E) :-
    isMemberOfList(T, E).


% occurrence ocurence count number of list
occurrences([], _, 0).
occurrences([X | T], X, Res) :-
    occurrences(T, X, ResRec),
    Res is ResRec + 1.
occurrences([_ | T], X, Res) :-
    occurrences(T, X, Res).

% delete remove rid erase
deleteOccurrences([], _, []).
deleteOccurrences([E | T], E, TR) :-
    deleteOccurrences(T, E, TR).
deleteOccurrences([H | T], E, [H | TR]) :-
    deleteOccurrences(T, E, TR).

% minimum list
getMin([], 9999999).
getMin([H | T], TR) :-
    getMin(T, R),
    TR is min(H, R).

% maximum list
getMax([], -9999999).
getMax([H | T], TR) :-
    getMax(T, R),
    TR is max(H, R).

gcd(X, 0, X).
gcd(X, Y, R):-
    Y > 0,
    X1 is X mod Y,
    gcd(Y, X1, R).
