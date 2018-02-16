(defun ReverseAll (L)
  (cond
    ((listp L)
      (reverse (mapcar 'ReverseAll L))
    )
    (T
      L)
  )
)

(print (ReverseAll '(1 2 3( 6 7 (A 4 5)))))
