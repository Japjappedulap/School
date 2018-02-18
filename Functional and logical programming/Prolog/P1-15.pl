deleteOccurrences([], _, []).
deleteOccurrences([E | T], E, TR) :-
    deleteOccurrences(T, E, TR).
deleteOccurrences([H | T], E, [H | TR]) :-
    deleteOccurrences(T, E, TR).

% a)

listToSet([], []).
listToSet([H | T], [H | TR]) :-
    deleteOccurrences(T, H, NewH),
    listToSet(NewH, TR).

% b)

getSize([], 0).
getSize([_ | T], TR) :-
    getSize(T, R), TR is R + 1.

getAllEven([], []).
getAllEven([H | T], [H | R]) :-
    H mod 2 =:= 0,
    getAllEven(T, R).
getAllEven([_ | T], R) :-
    getAllEven(T, R).

getAllOdd([], []).
getAllOdd([H | T], [H | R]) :-
    H mod 2 =:= 1,
    getAllOdd(T, R).
getAllOdd([_ | T], R) :-
    getAllOdd(T, R).

doExercise(List, ReturnList, EvenSize, OddSize) :-
    getAllEven(List, EvenList),
    getSize(EvenList, EvenSize),
    getAllOdd(List, OddList),
    getSize(OddList, OddSize),
    append([EvenList, OddList], ReturnList).
