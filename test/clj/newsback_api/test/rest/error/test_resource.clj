(ns newsback-api.test.rest.error.test-resource
  (:require [ring.util.http-response :refer [ok]]
            [compojure.api.sweet :refer [defroutes GET]]))

(defn response-data []
  {:data "Data"})

(defroutes test-routes
           (GET "/test-resource-collection/test-resource-uri" []
                (ok (response-data))))
