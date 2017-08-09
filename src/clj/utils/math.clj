(ns utils.math)

(defn round-to-decimal-places [number precision]
  (let [precision-format (Math/pow 10 precision)]
    (/ (Math/round (* number precision-format)) precision-format)))
