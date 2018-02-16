(defun SubstituteNode (L E Sub)
  (cond
    ((equal L E)
      Sub
    )

    ((atom L)
      L
    )

    (T
      (apply 'list (mapcar #'(lambda (A) (SubstituteNode A E Sub)) L))
    )
  )
)

(write (SubstituteNode '(a (b (c)) (d) (e (f))) 'b 'g))
