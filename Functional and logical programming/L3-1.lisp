(defun IsMember (L E)
  (cond
    ((equal E L)
      T
    )

    ((atom L)
      NIL
    )

    (T
      (some #'(lambda (A) (if (equal T A) T NIL)) (mapcar #'(lambda (B) (IsMember B E)) L))
    )
  )
)

(print (IsMember '(1 2 3 (((4) 5) 6)) 0))
(print (IsMember '(1 2 3 (((4) 5) 6)) 1))
(print (IsMember '(1 2 3 (((4) 5) 6)) 2))
(print (IsMember '(1 2 3 (((4) 5) 6)) 3))
(print (IsMember '(1 2 3 (((4) 5) 6)) 4))
(print (IsMember '(1 2 3 (((4) 5) 6)) 5))
(print (IsMember '(1 2 3 (((4) 5) 6)) 6))
(print (IsMember '(1 2 3 (((4) 5) 6)) 7))
