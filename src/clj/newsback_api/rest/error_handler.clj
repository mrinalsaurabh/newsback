(ns newsback-api.rest.error-handler
  (:require [compojure.api.exception :as ex]
            [clojure.tools.logging :as log]
            [ring.util.http-response :as response]
            [newsback-api.rest.error-keys :as api-keys]))

(def generic-error-message "Oops, we are embarrassed. Unfortunately, we are experiencing some technical difficulties.")

(defn- client-error-handler [f]
  (fn [^Exception e data request]
    (f {:key (api-keys/api-error-key (get-in data [:error :key])), :message (get-in data [:error :message])})))

(defn- gateway-error-handler [f]
  (fn [^Exception e data request]
    (f {:key (api-keys/api-error-key (get-in data [:error :key])), :message generic-error-message})))

(defn- validation-error-handler [f]
  (fn [^Exception e data request]
    (f {:key (api-keys/api-error-key (get-in data [:error :key])), :message (get-in data [:error :message])})))

(defn- general-exception-handler [f]
  (fn [^Exception e data request]
    (log/error e (format "op=handle_error, status=KO, desc=An exception occured while serving the request at url %s." (:uri request)))
    (f {:key "ERR_GENERIC_ERROR", :message generic-error-message})))

(defn handlers []
  {:entity-not-found (client-error-handler response/not-found)
   :validation-error (validation-error-handler response/bad-request)
   :gateway-error (gateway-error-handler response/internal-server-error)
   ::ex/default (general-exception-handler response/internal-server-error)})
