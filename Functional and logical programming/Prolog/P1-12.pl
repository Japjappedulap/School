
% a)

% substituteWithList(List, Value, Substitutor, R).
substituteWithList([], _, _, []).
substituteWithList([Value | T], Value, Substitutor, TR) :-
    substituteWithList(T, Value, Substitutor, R),
    append([Substitutor, R], TR).
substituteWithList([H | T], Value, Substitutor, [H | R]) :-
    substituteWithList(T, Value, Substitutor, R).



% b)

deleteNth([], _, []).
deleteNth([_ | T], 0, T).
deleteNth([H | T], N, [H | R]) :-
    NewN is N - 1,
    deleteNth(T, NewN, R).
