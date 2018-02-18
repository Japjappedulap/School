isMemberOfList([], _) :- false.
isMemberOfList([E | _], E) :- true.
isMemberOfList([_ | T], E) :-
    isMemberOfList(T, E).

deleteOccurrences([], _, []).
deleteOccurrences([E | T], E, TR) :-
    deleteOccurrences(T, E, TR).
deleteOccurrences([H | T], E, [H | TR]) :-
    deleteOccurrences(T, E, TR).

% a)

% checkSetsEquality(Set1, Set2).
% both MUST be sets
checkSetsEquality([], []) :- true.
checkSetsEquality([H | T], Set2) :-
    isMemberOfList(Set2, H),
    deleteOccurrences(Set2, H, NewSet2),
    checkSetsEquality(T, NewSet2).



% b)

getElement([], _, _).
getElement([H | _], 0, H).
getElement([_ | T], Pos, R):-
    NewPos is Pos - 1,
    getElement(T, NewPos, R).
