(ns utils.math)

(defn numeric? [s]
  (if-let [s (seq s)]
    (let [s (if (= (first s) \-) (next s) s)
          s (drop-while #(Character/isDigit %) s)
          s (if (= (first s) \.) (next s) s)
          s (drop-while #(Character/isDigit %) s)]
      (empty? s))))
      
(defn round-to-decimal-places [number precision]
  (let [precision-format (Math/pow 10 precision)]
    (/ (Math/round (* number precision-format)) precision-format)))
