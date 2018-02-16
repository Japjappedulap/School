(defun DepthTree (L)
  (cond
    ((atom L)
      0
    )
    (T
      (+ 1 (apply 'max (mapcar 'DepthTree L)))
    )
  )
)

(write (DepthTree   '(a (b (c)) (d) (e (f)))  ))
