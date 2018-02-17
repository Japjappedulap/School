(defun countAtoms (L)
  (apply '+ (mapcar #'(lambda(A) (if (atom A) 1 0)) L))
)

(defun getMaxAtomsSublist (L)
  (cond
    ((atom L)
      1
    )
    (T
      (max (apply 'max (mapcar 'getMaxAtomsSublist L)) (countAtoms L))
    )
  )
)

(write (getMaxAtomsSublist '(((a a ))((a a a) a a) (a a ) )))
