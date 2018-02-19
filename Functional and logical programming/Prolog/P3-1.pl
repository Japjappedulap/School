comb(_, 0, []).
comb([H | T], K, [H | TR]) :-
    K > 0,
    K1 is K - 1,
    comb(T, K1, TR).
comb([_ | T], K, TR) :-
    K > 0,
    comb(T, K, TR).

getComb(L, K, R) :-
    findall(RL, comb(L, K, RL), R).
