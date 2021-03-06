(defun VectorSum(A B)
  (cond
    ((null A)
      NIL
    )

    (T
      (cons (+ (car A) (car B)) (VectorSum (cdr A) (cdr B)))
    )
  )
)

; (write (VectorSum '(1 2 3) '(3 2 1)))

(defun Linearize (L)
  (cond
    ((null L)
      NIL
    )

    ((atom (car L))
      (append (list (car L)) (Linearize (cdr L)))
    )

    ((listp (car L))
      (append (Linearize (car L)) (Linearize (cdr L)))
    )
  )
)

; (write (Linearize '(A (1 ((B) 3)) 4 5) ))

;(a b c (d (e f) g h i)) ==>
;(c b a (d (f e) i h g))

(defun GetMaxSuperficial (L)
  (cond
    ((null L)
      -99999
    )

    ((numberp (car L))
      (max (car L) (GetMaxSuperficial (cdr L)))
    )

    (T
      (GetMaxSuperficial (cdr L))
    )
  )
)


(write (GetMaxSuperficial '(1 2 3 4 (6))))
