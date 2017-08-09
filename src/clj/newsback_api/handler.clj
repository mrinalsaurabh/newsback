(ns newsback-api.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [newsback-api.rest.routes :refer [api-routes]]
            [compojure.route :as route]
            [newsback-api.env :refer [defaults]]
            [mount.core :as mount]
            [newsback-api.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    #'api-routes
    (route/not-found
      "page not found")))


(defn app [] (middleware/wrap-base #'app-routes))
