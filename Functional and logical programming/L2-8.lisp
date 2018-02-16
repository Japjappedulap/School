(defun Inorder (Tree)
  ; (print Tree)
  (cond
    ((null (cdr Tree)) ; leaf
      ; (print (car Tree))
      (list (car Tree))
    )
    (T
      (progn
        (append
          (Inorder (car (cdr Tree)))

          (list (car Tree))

          (Inorder (car (cdr (cdr Tree))))
        )
      )
    )
  )
)


(print (Inorder '(8 (1 (2 (4) (5)) (3 (6) (7))) (9 (10 (11) (12)) (13 (14) (15)))) ))
