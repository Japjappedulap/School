(defun DotProduct(a b)
  (cond
    ( (null a)
      0
    )
    ( T
      (+ (* (car a) (car b)) (DotProduct (cdr a) (cdr b)))
    )
  )
)

; (write (DotProduct '(1 2 3) '(1 2 3)))


(defun getDepth (l depth)   ; non mapcar way
  (cond
    ( (null l)
      depth
    )
    ( (atom (car l))
      (getDepth (cdr l) depth)
    )
    (T
      (max (getDepth (car l) (+ depth 1)) (getDepth (cdr l) depth))
    )
  )
)


; (write (getDepth '(1 ((((2)) (3))) 3) 1))

(defun Insert (element l)
  (cond
    ((null L)
      (list element)
    )
    ((< element (car l))
      (cons element l)
    )
    (T
      (cons (car l) (Insert element (cdr l)))
    )

  )
)

(defun MySort (l)
  (cond
    ((null l)
      NIL
    )
    (T
      (Insert (car l) (MySort (cdr l)))
    )
  )
)

; (write (MySort '(2 4 5 3 5 3 4 1 2 1)))

(defun Intersect (Set1 Set2)
  (cond
    ((null Set1)
      NIL
    )
    ((member (car Set1) Set2)
      (cons (car Set1) (Intersect (cdr Set1) Set2))
    )
    (T
      (Intersect (cdr Set1) Set2)
    )
  )
)

; (write (Intersect '(1 2 3 4) '(2 3 4 5)))
