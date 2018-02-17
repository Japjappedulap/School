(defun RemoveOccurence (L E)
  (cond
    ((equal L E)
      NIL
    )

    ((atom L)
      L
    )

    (T
      (remove NIL (apply 'list (mapcar #'(lambda (A) (RemoveOccurence A E)) L)))
    )
  )
)

(write (RemoveOccurence '(1 2 3 (2) 1 (2 4 5) 2 4 ((2))) 2))
