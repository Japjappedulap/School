setElement([], _, _, []).
setElement([_ | T], 0, E, [E | T]).
setElement([H | T], Pos, E, [H | R]) :-
    NewPos is Pos - 1,
    setElement(T, NewPos, E, R).

getElement([], _, _).
getElement([H | _], 0, H).
getElement([_ | T], Pos, R):-
    NewPos is Pos - 1,
    getElement(T, NewPos, R).


% a)

substituteElements(List, Pos1, Pos2, R) :-
    getElement(List, Pos1, E1),
    getElement(List, Pos2, E2),
    setElement(List, Pos1, E2, R1),
    setElement(R1, Pos2, E1, R).

% b)

% getSublist(List, Pos1, Pos2, R).

getSublist([], _, _, _) :- false.
getSublist([H | _], 0, 0, [H]).
getSublist([H | T], 0, Pos2, [H | R]) :-
    NewPos2 is Pos2 - 1,
    getSublist(T, 0, NewPos2, R).
getSublist([_ | T], Pos1, Pos2, R) :-
    Pos1 > 0,
    NewPos1 is Pos1 - 1,
    NewPos2 is Pos2 - 1,
    getSublist(T, NewPos1, NewPos2, R).
