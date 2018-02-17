(defun nnulllist (L)  ;returns non null list L (linear) (nil nil nil 6 nil nil) -> (6)
  (cond               ;(nil nil nil) -> nil (NOT LIST)
    ((null L)
      NIL
    )
    ((null (car L))
      (nnulllist (cdr L))
    )
    (T
      (cons (car L) (nnulllist (cdr L)))
    )
  )
)

(defun DFS (Tree X)
  (progn
    (cond
      ((equal X (car Tree)); if X
        (list X)
      )
      ((null (cdr Tree))   ; if leaf
        NIL
      )
      (T                   ; if inside node
        (setq result (nnulllist (mapcar #' (lambda (next) (DFS next X)) (cdr Tree))))
        (if (not (null result))
          (cons (car Tree) (car result))
          NIL
        )

        ; (cons (car Tree) (nnulllist (mapcar ' DFS (cdr Tree))))
      )
    )
  )
)

; (write (DFS '(A (B) (C (D) (E))) 'B))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) 'A))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) 'B))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) '1))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) '2))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) 'C))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) 'D))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) 'E))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) 'F))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) 'G))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) 'H))
(print (DFS '(A (B (1 (2))) (C (D) (E (F) (G (H) (I))))) 'I))
; (DFS '(A (B) (C (D) (E))) B)

; (write (nnulllist '(nil nil nil nil nil)))
