% a)

insertSort([], E, [E]).
insertSort([E | T], E, [E, E | T]).
insertSort([H | T], E, [E, H | T]) :- E < H.
insertSort([H | T], E, [H | R]) :- E > H,
    insertSort(T, E, R).


mySort([], []).
mySort([H | T], R) :-
    mySort(T, TR),
    insertSort(TR, H, R).

sortHeterogeneous([], []).
sortHeterogeneous([H | T], [H | TR]) :-
    number(H),
    sortHeterogeneous(T, TR).
sortHeterogeneous([H | T], [R1 | TR]) :-
    is_list(H),
    mySort(H, R1),
    sortHeterogeneous(T, TR).
