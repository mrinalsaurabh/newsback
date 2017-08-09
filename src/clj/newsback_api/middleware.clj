(ns newsback-api.middleware
  (:require [newsback-api.env :refer [defaults]]
            [newsback-api.config :refer [env]]
            [ring.middleware.flash :refer [wrap-flash]]
            [immutant.web.middleware :refer [wrap-session]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [buddy.auth.middleware :refer [wrap-authentication wrap-authorization]]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth :refer [authenticated?]]
            [io.clj.logging :refer [with-logging-context]]
            [clojure.tools.logging :as log]
            [ring.util.http-predicates :as pred]
            [buddy.auth.backends.session :refer [session-backend]]))

(defn on-error [request response]
  {:status 403
   :headers {}
   :body (str "Access to " (:uri request) " is not authorized")})

(defn uuid [] (str (java.util.UUID/randomUUID)))

(def op-name "wrap-log")

(defn wrap-log [handler]
  (fn [req]
    (with-logging-context {:correlationId (uuid)}
      (log/info (format "op=%s, status=OK, desc=Request received at url %s." op-name (:uri req)))
      (let [response (handler req)]
        (if (or (pred/client-error? response) (pred/server-error? response))
          (log/info (format "op=%s, status=KO, desc=Request failed with HTTP status %s." op-name (:status response)))
          (log/info (format "op=%s, status=OK, desc=Request completed with HTTP status %s." op-name (:status response))))
        response))))

(defn wrap-restricted [handler]
  (restrict handler {:handler authenticated?
                     :on-error on-error}))

(defn wrap-auth [handler]
  (let [backend (session-backend)]
    (-> handler
        (wrap-authentication backend)
        (wrap-authorization backend))))

(defn wrap-base [handler]
  (-> ((:middleware defaults) handler)
      wrap-log
      wrap-auth
      wrap-flash
      (wrap-session {:cookie-attrs {:http-only true}})
      (wrap-defaults
        (-> site-defaults
            (assoc-in [:security :anti-forgery] false)
            (dissoc :session)))))
