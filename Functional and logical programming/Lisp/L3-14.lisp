(defun countAtoms (L)
  (cond
    ((atom L)
      1
    )

    (
      (apply '+ (mapcar 'countAtoms L))
    )
  )
)

(write (countAtoms '(1 2 3 (3 (3))(3 2((((2)))))) ))
