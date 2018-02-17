(defun IsMember (Tree E)
  (cond
    ((equal E Tree)
      T
    )

    ((atom Tree)
      NIL
    )

    (T
      (some #'(lambda (A) (if (equal T A) T NIL)) (mapcar #'(lambda (B) (IsMember B E)) Tree))
    )
  )
)


(write (IsMember '(a (b (c)) (d) (E (f))) 'b))
