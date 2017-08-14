(ns newsback-api.rest.news-resource
  (:require [compojure.api.sweet :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [newsback-api.services.news :as news-service]))
(defroutes routes
    (GET "/newsback/all-news/:user-id" [:as request]
       :path-params [user-id :- String]
       :summary "Get news for a user"
       (let [all-news (news-service/news user-id)]
         (ok all-news))))
