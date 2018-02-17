(defun FaTreaba (L)
  (cond
    ((numberp L)
      (if (= 0 (rem L 2)) L (- 0 L))
    )
    ((atom L)
      0
    )
    (T
      (apply '+ (mapcar 'FaTreaba L))
    )
  )
)

(write (FaTreaba '(1 2 (3 4 (5 6)) (((7))) 8 ((((9)))))))
