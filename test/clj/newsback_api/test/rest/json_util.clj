(ns newsback-api.test.rest.json-util
  (:require [cheshire.core :refer [parse-string]]))

(defn parse-body [json-string]
  (parse-string (slurp json-string) true))
