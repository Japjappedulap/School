subsets([], []).
subsets([H | T], [H | TR]) :-
    subsets(T, TR).
subsets([_ | T], TR) :-
    subsets(T, TR).


getSubsets(L, R) :-
    findall(RL, subsets(L, RL), R).

% incomplete
