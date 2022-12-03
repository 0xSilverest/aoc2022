(def readFile
  (map
    #(clojure.string/split %1 #" ")
    (clojure.string/split-lines (slurp "./input/day2.txt"))
  )
)

(def rps
  {:A 1 :B 2 :C 3 :X 1 :Y 2 :Z 3}
)

(defn keyify [x]
  (read-string (str ":" x))
)

(defn convert [x]
  [(get rps (keyify (first x))) (get rps (keyify (last x)))]
)

(defn countScore [x]
  (cond
    (== (first x) (last x)) (+ 3 (last x))
    (and (= (first x) 1) (= (last x) 3)) (last x)
    (and (= (last x) 1) (= (first x) 3)) (+ 6 (last x))
    (< (first x) (last x)) (+ 6 (last x))
    (> (first x) (last x)) (last x)
  )
)

(defn check3 [x]
  (case x
    0 3
    x)
)

(defn countScore2 [x]
  (cond
    (= (last x) "X") (check3 (- (get rps (keyify (first x))) 1))
    (= (last x) "Y") (+ (get rps (keyify (first x))) 3)
    (= (last x) "Z") (+ (check3 (mod (+ 1 (get rps (keyify (first x)))) 3)) 6)
  )
)

(println
  (reduce +
    (map
      #(countScore2 %1) readFile))
)
