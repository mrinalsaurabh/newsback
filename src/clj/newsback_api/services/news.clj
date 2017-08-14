(ns newsback-api.services.news
  (:require [newsback-api.data.news :as news]))

(defn news [user-id]
  (news/news user-id))
