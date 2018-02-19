% member belongs inside list
isMemberOfList([], _) :- false.
isMemberOfList([E | _], E) :- true.
isMemberOfList([_ | T], E) :-
    isMemberOfList(T, E).


% occurrence ocurence count number of list
occurrences([], _, 0).
occurrences([X | T], X, Res) :-
    occurrences(T, X, ResRec),
    Res is ResRec + 1.
occurrences([_ | T], X, Res) :-
    occurrences(T, X, Res).

% delete remove rid erase
deleteNth([], _, []).
deleteNth([_ | T], 0, T).
deleteNth([H | T], N, [H | R]) :-
    NewN is N - 1,
    deleteNth(T, NewN, R).

deleteOccurrences([], _, []).
deleteOccurrences([E | T], E, TR) :-
    deleteOccurrences(T, E, TR).
deleteOccurrences([H | T], E, [H | TR]) :-
    deleteOccurrences(T, E, TR).

deleteFirstOccurrence([], _, []).
deleteFirstOccurrence([E | T], E, T).
deleteFirstOccurrence([H | T], E, [H | TR]) :-
    deleteFirstOccurrence(T, E, TR).



% minimum list
getMin([], 9999999).
getMin([H | T], TR) :-
    getMin(T, R),
    TR is min(H, R).

% maximum list
getMax([], -9999999).
getMax([H | T], TR) :-
    getMax(T, R),
    TR is max(H, R).

gcd(X, 0, X).
gcd(X, Y, R):-
    Y > 0,
    X1 is X mod Y,
    gcd(Y, X1, R).

% list set
listToSet([], []).
listToSet([H | T], [H | TR]) :-             % DEPENDENCIES!
    deleteOccurrences(T, H, NewH),
    listToSet(NewH, TR).


getElement([], _, _).
getElement([H | _], 0, H).
getElement([_ | T], Pos, R):-
    NewPos is Pos - 1,
    getElement(T, NewPos, R).

setElement([], _, _, []).
setElement([_ | T], 0, E, [E | T]).
setElement([H | T], Pos, E, [H | R]) :-
    NewPos is Pos - 1,
    setElement(T, NewPos, E, R).

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

insert([], 0, E, [E]).
insert([], _, _, []).
insert([H | T], 0, E, R) :-
    append([[E], [H], T], R).
insert([H | T], P, E, [H | R]) :-
    NewP is P - 1,
    insert(T, NewP, E, R).

addToList([], E, [E]).
addToList([H | T], E, [E, H | T]).
addToList([H | T], E, [H | TR]) :-
    addToList(T, E, TR).

getSize([], 0).
getSize([_ | T], TR) :-
    getSize(T, R), TR is R + 1.



% backtrack

subsets([], []).
subsets([H | T], [H | TR]) :-
    subsets(T, TR).
subsets([_ | T], TR) :-
    subsets(T, TR).

perm([], []).
perm([H | T], R) :-
    perm(T, RT),
    addToList(RT, H, R).        % DEPENDENCIES

comb(_, 0, []).
comb([H | T], K, [H | TR]) :-
    K > 0,
    K1 is K - 1,
    comb(T, K1, TR).
comb([_ | T], K, R) :-
    K > 0,
    comb(T, K, R).


arr(L, K, R) :-
    comb(L, K, R1),     % DEPENDENCIES
    perm(R1, R).        % DEPENDENCIES
