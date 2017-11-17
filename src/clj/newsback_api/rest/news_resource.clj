(ns newsback-api.rest.news-resource
  (:require [compojure.api.sweet :refer [defroutes GET PUT]]
            [ring.util.http-response :refer [ok]]
            [ring.util.http-response :refer [ok no-content]]
            [schema.core :as s]
            [newsback-api.services.news :as news-service]))

(s/defschema NewsHtml
  {:news-html s/Str})

(defroutes routes
    (GET "/newsback/all-news/:user-id" [:as request]
       :path-params [user-id :- String]
       :summary "Get news for a user"
       (let [all-news (news-service/news user-id)]
         (ok all-news)))
    (PUT "/newsback/add-update-news/:user-id/:news-id" [:as request]
        :path-params [news-id :- String
                     user-id :- String]
        :body [news-html NewsHtml]
        :summary "Save and Update news"
        (news-service/save-news news-id (:news-html news-html) user-id)
        (no-content)))
