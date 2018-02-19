subsets([], []).
subsets([H | T], [H | TR]) :-
    subsets(T, TR).
subsets([_ | T], TR) :-
    subsets(T, TR).


strictlyIncreasing([_]).
strictlyIncreasing([H1, H2 | T]) :-
    H1 < H2,
    strictlyIncreasing([H2 | T]).

oneSolution(L, R) :-
    subsets(L, R),
    strictlyIncreasing(R).

allSolutions(L, R) :-
    findall(RL, oneSolution(L, RL), R).
