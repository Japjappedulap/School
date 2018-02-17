occurrences([], _, 0).

occurrences([X | T], X, Res) :-
    occurrences(T, X, ResRec),
    Res is ResRec + 1.

occurrences([_ | T], X, Res) :-
    occurrences(T, X, Res).



occurrencesCol([], _, C, C).

occurrencesCol([X | T], X, C, Res) :-
    C1 is C + 1,
    occurrencesCol(T, X, C1, Res).

occurrencesCol([_ | T], X, C, Res) :-
    occurrencesCol(T, X, C, Res).


inverse(0, Col, Col).

inverse(N, Col, Rez) :-
    LastDigit is N mod 10,
    NewCol is Col * 10 + LastDigit,
    Ndiv is N div 10,
    inverse(Ndiv, NewCol, Rez).
