% a) Write a predicate to determine if a list has even numbers of elements without counting the elements from the list

isEven_([], true) :- true.
isEven_([], false) :- false.
isEven_([_ | T], true) :-
    isEven_(T, false).
isEven_([_ | T], false) :-
    isEven_(T, true).

isEven(L) :-
    isEven_(L, true).


% b) Write a predicate to delete first occurrence of the minimum number from a list.


getMin([], 9999999).
getMin([H | T], TR) :-
    getMin(T, R),
    TR is min(H, R).


deleteFirstOccurrence([], _, []).
deleteFirstOccurrence([E | T], E, T).
deleteFirstOccurrence([H | T], E, [H | TR]) :-
    deleteFirstOccurrence(T, E, TR).

deleteFirstOccurrenceOfMin(L, R) :-
    getMin(L, Min),
    deleteFirstOccurrence(L, Min, R).
