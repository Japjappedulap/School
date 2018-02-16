(defun GetNodesAtLevel (Tree K)
  (print Tree)
  (write K)
  (cond
    ((and (listp Tree) (equal K 0))        ; at requested level
      1
    )
    ((atom Tree)
      0
    )

    ((null (cdr Tree))  ; leaf
      0
    )
    (T
      (apply '+ (mapcar #'(lambda(A) (GetNodesAtLevel A (- K 1))) Tree))
    )

  )
)

(print (GetNodesAtLevel '(7 (2 (3)) (4) (5 (6))) 2) )
;(a (b (c)) (d) (e (f)))
