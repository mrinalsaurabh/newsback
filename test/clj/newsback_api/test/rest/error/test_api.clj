(ns newsback-api.test.rest.error.test-api
  (:require [compojure.api.sweet :refer [context defapi]]
            [newsback-api.test.rest.error.test-resource :as tr]
            [newsback-api.rest.error-handler :as e]))

(defapi service-routes
        {:exceptions {:handlers (e/handlers)}}
        (context "/api" []
                 :tags ["API for Global Demand Forecast"]
                 tr/test-routes))
