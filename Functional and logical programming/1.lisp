; 1
; .
; a)
; Write a function to return the n
; -
; th element of a list, or NIL if such an element does not exist.
; b)
; Write a function to check whether an atom E is a
; member of a list which is not necessarily linear.
; c)
; Write a function to determine the
; list
; of all sublists of a given list, on any level.
; A sublist is either the list itself, or any element that is a list, at any level. Example:
; (1 2 (3 (4 5)
; (6 7)) 8 (9 10)) => 5
; sublists
; :
; (  (1 2 (3 (4 5) (6 7)) 8 (9 10))    (3 (4 5) (6 7))     (4 5)   (6 7)   (9 10) )
; d)
; Write a function to transform a linear list into a set


; a)
(defun getNthElem(L Nth) (
  cond
        (
          (null L)
          NIL
        )

        (
          (eq Nth 1)
          (car L)
        )

        (
          T
          (getNthElem (cdr L) (- Nth 1))
        )
  )
)

; (write (getNthElem '(1 2 3 4 5 6) -1111) )
; (write (getNthElem '(1 2 3 4 5 6) 1) )
; (write (getNthElem '(1 2 3 4 5 6) 2) )
; (write (getNthElem '(1 2 3 4 5 6) 3) )
; (write (getNthElem '(1 2 3 4 5 6) 4) )
; (write (getNthElem '(1 2 3 4 5 6) 5) )
; (write (getNthElem '(1 2 3 4 5 6) 6) )
; (write (getNthElem '(1 2 3 4 5 6) 7) )


; b)
(defun checkContains(L El)
  ( cond
    ( (null L)
      NIL
    )

    ( (listp (car L) )
      (or ( checkContains (car L) El ) (checkContains (cdr L) El ) )
    )

    ( (eq (car L) El)
      T
    )

    ( T
      (checkContains (cdr L) El)
    )
  )
)


; (write (checkContains '(1) 3))


; c)

(defun countSublists(L)
  (cond
    ( (null L)
      1
    )

    ( (listp (car L))
      (+ (countSublists (car L)) (countSublists (cdr L)))
    )

    ( t
      (countSublists (cdr L))
    )
  )
)

(defun printSublists (L)
  (cond
    ( (null L)
      NIL
    )

    ( (listp (car L))
      (progn
        (print (car L))
        (printSublists (car L))
        (printSublists (cdr L))
      )
    )

    ( (listp (cdr L))
      (printSublists (cdr L))
    )
  )
)


; (write (countSublists'(1 2 (3 (4 5) (6 7)) 8 (9 10))))
; (write (printSublists' (1 2) ))
; (printSublists'(1 2 (3 (4 5) (6 7)) 8 (9 10)))

; d)



(defun toSet (L)

  ; (print L)
  (cond
    ( (null L)
      NIL
    )

    ( T
      (progn
        (setq current_list (toSet (cdr L)))
        (cond
          ( (member (car L) current_list)
            current_list
          )

          ( T
            (cons (car L) current_list)
          )
        )
      )
    )
  )
)

(write (toSet '(1 1 1 2 3 3 4 4 3 2 3 5 6 3 3 5 3 4 5 6 3 4 5 6)))
; (write (toSet '(1 1 1)))
