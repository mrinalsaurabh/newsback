(ns newsback-api.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [newsback-api.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[newsback-api started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[newsback-api has shut down successfully]=-"))
   :middleware wrap-dev})
