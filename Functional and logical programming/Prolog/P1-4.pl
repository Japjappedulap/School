% a. Write a predicate to determine the difference of two sets.

isMemberOfList([], _) :- false.
isMemberOfList([E | _], E) :- true.
isMemberOfList([_ | T], E) :-
    isMemberOfList(T, E).


differenceOfSets([], _, []).
differenceOfSets([H | T], Set2, TR) :-
    isMemberOfList(Set2, H),
    differenceOfSets(T, Set2, TR).
differenceOfSets([H | T], Set2, [H | TR]) :-
    differenceOfSets(T, Set2, TR).


% b. Write a predicate to add value 1 after every even element from a list


addAfterEven([], []).
addAfterEven([H | T], [H, 1 | TR]) :-
    H mod 2 =:= 0,
    addAfterEven(T, TR).
addAfterEven([H | T], [H | TR]) :-
    addAfterEven(T, TR).
