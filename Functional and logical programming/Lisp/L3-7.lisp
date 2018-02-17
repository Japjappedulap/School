(defun Prostitute (L E Sub)
  (cond
    ((equal L E)
      Sub
    )

    ((atom L)
      L
    )

    (T
      (apply 'list (mapcar #'(lambda (A) (Prostitute A E Sub)) L))
    )
  )
)

(write (Prostitute '(A (B (C ((A))) (B (C A) B C A)) B C) 'A 'mata))
