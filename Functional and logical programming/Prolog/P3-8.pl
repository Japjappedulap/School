
paranthesis(0, 0, "").
paranthesis(N, O, R) :-
    N > 0, O >= 0,
    NewN is N - 1, NewO is O + 1,
    paranthesis(NewN, NewO, TR),
    string_concat("(", TR, R).
paranthesis(N, O, R) :-
    N > 0, O > 0,
    NewN is N - 1, NewO is O - 1,
    paranthesis(NewN, NewO, TR),
    string_concat(")", TR, R).

getParanthesis(N, R) :-
    findall(RL, paranthesis(N, 0, RL), R).
