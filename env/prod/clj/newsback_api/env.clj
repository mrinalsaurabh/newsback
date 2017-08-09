(ns newsback-api.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[newsback-api started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[newsback-api has shut down successfully]=-"))
   :middleware identity})
