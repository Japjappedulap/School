(defun Linearize (L)
  (cond
    ((null L)
      NIL
    )

    ((atom L)
      (list L)
    )

    (T
      (apply 'append (mapcar 'Linearize L))
    )
  )
)

(print (Linearize '(1 2(3)((4)5))  ))
