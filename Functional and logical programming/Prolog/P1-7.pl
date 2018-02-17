isMemberOfList([], _) :- false.
isMemberOfList([E | _], E) :- true.
isMemberOfList([_ | T], E) :-
    isMemberOfList(T, E).

% a) Write a predicate to compute the intersection of two sets.

intersectionOfSets([], _, []).
intersectionOfSets([H | T], Set2, [H | TR]) :-
    isMemberOfList(Set2, H),
    intersectionOfSets(T, Set2, TR).
intersectionOfSets([_ | T], Set2, TR) :-
    intersectionOfSets(T, Set2, TR).

% b) Write a predicate to create a list (m, ..., n) of all integer
% numbers from the interval [m, n]

% makeList(A, B, R).

makeList(B, B, [B]).
makeList(A, B, []) :-
    A > B.
makeList(A, B, [A | R]) :-
    NewA is A + 1,
    makeList(NewA, B, R).
