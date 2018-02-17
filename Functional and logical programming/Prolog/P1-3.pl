occurrences([], _, 0).
occurrences([X | T], X, Res) :-
    occurrences(T, X, ResRec),
    Res is ResRec + 1.
occurrences([_ | T], X, Res) :-
    occurrences(T, X, Res).


% a) Define a predicate to remove from a list all repetitive elements.

deleteOccurrences([], _, []).
deleteOccurrences([E | T], E, TR) :-
    deleteOccurrences(T, E, TR).
deleteOccurrences([H | T], E, [H | TR]) :-
    deleteOccurrences(T, E, TR).


deleteRepetitive([], []).
deleteRepetitive([H | T], TR) :-
    occurrences([H | T], H, Count),
    Count > 1,
    deleteOccurrences([H | T], H, NewH),
    deleteRepetitive(NewH, TR).
deleteRepetitive([H | T], [H | TR]) :-
    occurrences([H | T], H, Count),
    Count = 1,
    deleteRepetitive(T, TR).

% b) Remove all occurrence of a maximum value from a list on integer numbers

getMax([], -9999999).
getMax([H | T], TR) :-
    getMax(T, R),
    TR is max(H, R).

deleteAllOccurrencesOfMaximum(L, R) :-
    getMax(L, Max),
    deleteOccurrences(L, Max, R).
