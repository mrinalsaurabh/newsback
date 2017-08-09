(ns newsback-api.data.core
  (:require
   [conman.core :as conman]
   [mount.core :refer [defstate]]
   [newsback-api.config :refer [env]]))

(defstate ^:dynamic *db*
  :start (conman/connect! {:jdbc-url (env :database-url)})
  :stop (conman/disconnect! *db*))
