% Multiply elements of a list with a constant value
multiply([], _, []).

multiply([H | T], K, [HR | TR]) :-
    HR is H * K,
    multiply(T, K, TR).


% Add an element at the end of a list.
append([], X, [X]).

append([H | T], X, [H | TR]) :-
    append(T, X, TR).


% Compute number of occurrences of an element in a list.
occurrences([], _, 0).

occurrences([X | T], X, Res) :-
    occurrences(T, X, ResRec),
    Res is ResRec + 1.

occurrences([_ | T], X, Res) :-
    occurrences(T, X, Res).


% Eliminate all elements with just one occurrence from a list.
eliminate([], _, []).

eliminate([H | T], InitialCopy, [H | R]) :-
    occurrences(InitialCopy, H, N),
    N > 1,
    eliminate(T, InitialCopy, R).

eliminate([H | T], InitialCopy, R) :-
    occurrences(InitialCopy, H, N),
    N = 1,
    eliminate(T, InitialCopy, R).


elim(L, R) :- eliminate(L, L, R).
