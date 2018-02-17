deleteFirstOccurrence([], _, []).
deleteFirstOccurrence([E | T], E, T).
deleteFirstOccurrence([H | T], E, [H | TR]) :-
    deleteFirstOccurrence(T, E, TR).
    
isMemberOfList([], _) :- false.
isMemberOfList([E | _], E) :- true.
isMemberOfList([_ | T], E) :-
    isMemberOfList(T, E).

% a. Write a predicate to test if a list is a set.

isSet([]) :- true.
isSet([H | T]) :-
    not(isMemberOfList(T, H)),
    isSet(T).

% b. Write a predicate to remove the first three occurrences of an element
% in a list. If the element occurs less than three times, all
% occurrences will be removed

deleteFirst3Occurrences(L, E, RF) :-
    deleteFirstOccurrence(L, E, R2),
    deleteFirstOccurrence(R2, E, R1),
    deleteFirstOccurrence(R1, E, RF).
