(ns newsback-api.test.rest.error.test-handler
  (:require [compojure.core :refer [routes]]
            [newsback-api.middleware :as middleware]
            [newsback-api.env :refer [defaults]]
            [newsback-api.test.rest.error.test-api :refer [service-routes]]))

(def app-routes
  (routes #'service-routes))

(defn app [] (middleware/wrap-base #'app-routes))
