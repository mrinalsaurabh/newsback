(ns newsback-api.rest.routes
  (:require [ring.util.http-response :refer :all]
            [compojure.api.sweet :refer :all]
            [schema.core :as s]
            [compojure.api.meta :refer [restructure-param]]
            [buddy.auth.accessrules :refer [restrict]]
            [buddy.auth :refer [authenticated?]]
            [newsback-api.rest.error-handler :as e]
            [newsback-api.rest.news-resource :as news]))

(defn access-error [_ _]
  (unauthorized {:error "unauthorized"}))

(defn wrap-restricted [handler rule]
  (restrict handler {:handler  rule
                     :on-error access-error}))

(defmethod restructure-param :auth-rules
  [_ rule acc]
  (update-in acc [:middleware] conj [wrap-restricted rule]))

(defmethod restructure-param :current-user
  [_ binding acc]
  (update-in acc [:letks] into [binding `(:identity ~'+compojure-api-request+)]))

(defapi api-routes
  {:swagger {:ui "/doc"
             :spec "/swagger.json"
             :data {:info {:version "1.0.0"
                           :title "Supply Planning API"
                           :description "API with Supply planning capabilities."}}}

  :exceptions {:handlers (e/handlers)}}

  (GET "/authenticated" []
       :auth-rules authenticated?
       :current-user user
       (ok {:user user}))

  (context "/api" []
           :tags ["Supply Plan"]
           news/routes))
