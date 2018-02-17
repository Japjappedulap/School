(defun Postorder (Tree)
  ; (print Tree)
  (cond
    ((null (cdr Tree)) ; leaf
      ; (print (car Tree))
      (list (car Tree))
    )
    (T
      (progn
        (append
          (Postorder (car (cdr Tree)))

          (Postorder (car (cdr (cdr Tree))))

          (list (car Tree))
        )
      )
    )
  )
)


(print (Postorder '(8 (1 (2 (4) (5)) (3 (6) (7))) (9 (10 (11) (12)) (13 (14) (15)))) ))
