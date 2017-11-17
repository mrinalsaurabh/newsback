(ns newsback-api.services.news
  (:require [newsback-api.data.news :as news-data]
            [utils.math :as math]))


(defn news [user-id]
  (news-data/news user-id))

(defn save-news [news-id news-html user-id]
  (let [id (if (math/numeric? news-id) news-id)]
    (news-data/save-news id news-html user-id)))
