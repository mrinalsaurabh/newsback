(ns utils.json
  (:require [clojure.data.json :as json]))

(defn json->clj [data]
  (json/read-str data :key-fn keyword))
