occurrences([], _, 0).
occurrences([X | T], X, Res) :-
    occurrences(T, X, ResRec),
    Res is ResRec + 1.
occurrences([_ | T], X, Res) :-
    occurrences(T, X, Res).


% a) Write a predicate to remove all occurrencesof a certain atom from a list.

deleteOccurrences([], _, []).
deleteOccurrences([E | T], E, TR) :-
    deleteOccurrences(T, E, TR).
deleteOccurrences([H | T], E, [H | TR]) :-
    deleteOccurrences(T, E, TR).

% b) Define a predicate to produce a list of pairs (atom n) from an
% initial list of atoms. In this initial list atom has n occurrences

numberAtoms([], []).
numberAtoms([H | T], [[H, Count] | TR]) :-
    occurrences([H | T], H, Count),
    deleteOccurrences([H | T], H, NewH),
    numberAtoms(NewH, TR).
