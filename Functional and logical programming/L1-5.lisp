(defun TwiceN (L N)
  (cond
    ((null L)
      NIL
    )

    ((= N 1)
      (cons (car L) (cons (car L) (TwiceN (cdr L) (- N 1))))
    )

    (T
      (cons (car L) (TwiceN (cdr L) (- N 1)))
    )
  )
)


; (write (TwiceN '(10 20 30 40 50) 6))

(defun Associate (L1 L2)
  (cond
    ((null L1)
      NIL
    )

    (T
      (cons (list (car L1) (car L2)) (Associate (cdr L1) (cdr L2)))
    )
  )
)

; (write (Associate '(A B C) '(X Y Z)))


(defun countSublists(L)
  (cond
    ( (null L)
      1
    )

    ( (listp (car L))
      (+ (countSublists (car L)) (countSublists (cdr L)))
    )

    ( t
      (countSublists (cdr L))
    )
  )
)

; (write (countSublists '(A (A (A) A) ((A)A) ()((A)))    ))



(defun GetNumbersSuperficial (L)
  (cond
    ((null L)
      0
    )

    ((numberp (car L))
      (+ 1 (GetNumbersSuperficial (cdr L)))
    )

    (T
      (GetNumbersSuperficial (cdr L))
    )
  )
)

; (write (GetNumbersSuperficial '(1 2 A (1 2 A) C3 B)))
