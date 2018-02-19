listProduct([], 1).
listProduct([H | T], R) :-
    listProduct(T, TR),
    R is TR * H.

listSum([], 0).
listSum([H | T], R) :-
    listSum(T, TR),
    R is TR + H.

addToList([], E, [E]).
addToList([H | T], E, [E, H | T]).
addToList([H | T], E, [H | TR]) :-
    addToList(T, E, TR).


perm([], []).
perm([H | T], R) :-
    perm(T, RT),
    addToList(RT, H, R).

comb(_, 0, []).
comb([H | T], K, [H | TR]) :-
    K > 0,
    K1 is K - 1,
    comb(T, K1, TR).
comb([_ | T], K, R) :-
    K > 0,
    comb(T, K, R).

arr(L, K, R) :-
    comb(L, K, R1),
    perm(R1, R).

oneSolution(L, K, P, S, R) :-
    arr(L, K, R),
    listSum(R, S),
    listProduct(R, P).

getPerm(L, R) :-
    findall(RL, perm(L, RL), R).

getComb(L, K, R) :-
    findall(RL, comb(L, K, RL), R).

getArr(L, K, R) :-
    findall(RL, arr(L, K, RL), R).

allSolutions(L, K, P, S, R) :-
    findall(RL, oneSolution(L, K, P, S, RL), R).
