(defun OverallSum (L)
  (cond
    ((numberp L)
      L
    )

    ((atom L)
      0
    )

    (T
      (apply '+ (mapcar 'OverallSum L))
    )
  )
)

(write (OverallSum '(1(2(3(4(5(6)))))7(((8((((9)))))))((((((((((10)))))))))))))
