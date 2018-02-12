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

(write (DotProduct '(1 2 3) '(1 2 3)))
