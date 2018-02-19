% a)

insertSortUnique([], E, [E]).
insertSortUnique([E | T], E, [E | T]).
insertSortUnique([H | T], E, [E, H | T]) :- E < H.
insertSortUnique([H | T], E, [H | R]) :- E > H,
    insertSortUnique(T, E, R).

sortUnique([], []).
sortUnique([H | T], R) :-
    sortUnique(T, TR),
    insertSortUnique(TR, H, R).

% b)

sortHeterogeneousUnique([], []).
sortHeterogeneousUnique([H | T], [H | TR]) :-
    number(H),
    sortHeterogeneousUnique(T, TR).
sortHeterogeneousUnique([H | T], [R1 | TR]) :-
    is_list(H),
    sortUnique(H, R1),
    sortHeterogeneousUnique(T, TR).
