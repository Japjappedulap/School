% a) Write a predicate to compute the union of two sets.

isMemberOfList([], _) :- false.
isMemberOfList([E | _], E) :- true.
isMemberOfList([_ | T], E) :-
    isMemberOfList(T, E).

unionOfSets([], Set2, Set2).
unionOfSets([H | T], Set2, TR) :-
    isMemberOfList(Set2, H),
    unionOfSets(T, Set2, TR).
unionOfSets([H | T], Set2, [H | TR]) :-
    unionOfSets(T, Set2, TR).

% b) Write a predicate to determine the set of all the pairs of elements in a list.

% pairComb(L, R).

pairUp(_, [], []).
pairUp(A, [B | T], Aux) :-
    pairUp(A, T, R),
    append([[[A, B]], R], Aux).

pairComb([], []).
pairComb([H | T], Aux) :-
    pairUp(H, T, R),
    pairComb(T, TR),
    append([R, TR], Aux).
