
(defun Insert(L E bit)
  (cond
    ((null L)
      NIL
    )

    ((= bit 1)
      (cons (car L) (cons E (Insert (cdr L) E 0)))
    )

    (T
      (cons (car L) (Insert (cdr L) E 1))
    )
  )
)


(defun InsertAtEven(L E)
  (Insert L E 0)
)

; (write (InsertAtEven '(1 1 1 1 1 1 1 1) 2))

(defun LiniarizeReverse (L)
  (cond
    ((null L)
      NIL
    )

    ((atom (car L))
      (append (LiniarizeReverse (cdr L)) (list (car L)))
    )

    ((listp (car L))
      (append (LiniarizeReverse (cdr L)) (LiniarizeReverse (car L)))
    )
  )
)

; (write (LiniarizeReverse '(A (1 ((B) 3)) 4 5) ))

(defun GCDNonLinearList (L)
  (cond
    ((null L)
      0
    )

    ((numberp (car L))
      (gcd (car L) (GCDNonLinearList (cdr L)))
    )

    ((listp (car L))
      (gcd (GCDNonLinearList (car L)) (GCDNonLinearList (cdr L)))
    )
  )
)

; (write (GCDNonLinearList '(50 (10 (150) 20 (((30)))) (100000))))

(defun CountOccurences (L At)
  (cond
    ((null L)
      0
    )

    ((and (atom (car L)) (= At (car L)))
      (+ 1 (CountOccurences (cdr L) At))
    )

    ((listp (car L))
      (+ (CountOccurences (car L) At) (CountOccurences (cdr L) At))
    )

    (T
      (CountOccurences (cdr L) At)
    )
  )
)

; (write (CountOccurences '(1 2 (1 (2) ((2))) ((1 2)) (1) 1 ((1)) (2)) 2) )
