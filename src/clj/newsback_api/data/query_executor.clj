(ns newsback-api.data.query-executor
  (:require [suricatta.core :as sc]
            [suricatta.dsl :as dsl]
            [mount.core :as mount]
            [slingshot.slingshot :refer [try+ throw+]]
            [clojure.tools.logging :as log]
            [newsback-api.data.core :refer [*db*] :as db])
  (:import
   [org.jooq.exception DataAccessException]))

(mount/start
 #'newsback-api.config/env
 #'newsback-api.data.core/*db*)

(defn fetch [query]
  (try+ (with-open [ctx (sc/context *db*)]
          (sc/fetch ctx query))
        (catch DataAccessException e
          (log/error e "op=execute, status=KO, desc=Error occured while executing query.")
          (throw+ {:type :ex/default :error {:key :ex/default}}))
        (catch Exception e
          (log/error e "op=fetch, status=KO, desc=Error occured while executing query.")
          (throw+ {:type :gateway-error :error {:key :gateway-error}}))))

(defn- execute-queries-in-a-single-transaction [ctx queries current-index]
  (let [size-of-queries (count queries)
        is-current-index-in-query-range (< current-index size-of-queries)]
    (if is-current-index-in-query-range
    (sc/atomic ctx
               (sc/execute ctx (nth queries current-index))
               (execute-queries-in-a-single-transaction ctx queries (+ current-index 1))))))

(defn execute [& queries]
  (let [start-index 0]
    (with-open [ctx (sc/context *db*)]
      (try
        (execute-queries-in-a-single-transaction ctx queries start-index)
        (catch DataAccessException e
          (log/error e "op=execute, status=KO, desc=Error occured while executing query.")
          (throw+ {:type :ex/default :error {:key :ex/default}}))
        (catch Exception e
          (log/error e "op=fetch, status=KO, desc=Error occured while executing query.")
          (throw+ {:type :gateway-error :error {:key :gateway-error}}))))))
