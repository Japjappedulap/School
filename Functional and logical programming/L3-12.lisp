(defun Sub (L E Sub)
  (cond
    ((equal L E)
      Sub
    )

    ((atom L)
      L
    )

    (T
      (apply 'list (mapcar #'(lambda (A) (Sub A E Sub)) L))
    )
  )
)

(write (Sub '(A (B (C ((A))) (B (C A) B C A)) B C) 'A 'G))
