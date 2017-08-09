(ns newsback-api.test.utils.exception)

(defn is-type? [type error-key error-message]
  (fn [exception]
    (and (= type (get-in (.getData exception) [:type]))
         (= error-key (get-in (.getData exception) [:error :key]))
         (= error-message (get-in (.getData exception) [:error :message])))))
