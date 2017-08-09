(ns user
  (:require [mount.core :as mount]
            newsback-api.core))

(defn start []
  (mount/start-without #'newsback-api.core/repl-server))

(defn stop []
  (mount/stop-except #'newsback-api.core/repl-server))

(defn restart []
  (stop)
  (start))


