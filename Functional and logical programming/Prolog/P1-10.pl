% a. Define a predicate to test if a list of an integer elements has a "valley" aspect (a set has a "valley" aspect if
% elements decreases up to a certain point, and then increases.
% eg: 10 8 6 9 11 13 – has a “valley” aspect

isValleyUp([_]).

isValleyUp([H1, H2 | T]) :-
    H1 < H2,
    isValleyUp([H2 | T]).

isValleyDown([H1, H2 | T]) :-
    H1 > H2,
    isValleyDown([H2 | T]).

isValleyDown([H1, H2 | T]) :-
    H1 < H2,
    isValleyUp([H1, H2 | T]).

isValley([H1, H2 | T]) :-
    H1 > H2,
    isValleyDown([H1, H2 | T]).

% b. Calculate the alternate sum of list’s elements (l1 - l2 + l3 ...).

alternateSumFlag([], 0, _).
alternateSumFlag([H | T], NR, true) :-
    alternateSumFlag(T, R, false),
    NR is R + H.
alternateSumFlag([H | T], NR, false) :-
    alternateSumFlag(T, R, true),
    NR is R - H.

alternateSum(L, R) :-
    alternateSumFlag(L, R, true).
