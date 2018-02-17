(defun Depth (L)
  (cond
    ((atom L)
      0
    )
    (T
      (+ 1 (apply 'max (mapcar 'Depth L)))
    )
  )
)

(write (Depth   '(1 (2) (((3)) (((4)))))  ))
