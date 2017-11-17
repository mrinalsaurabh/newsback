(ns newsback-api.data.news
  (:require [suricatta.dsl :as dsl]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clojure.walk :as walk]
            [cheshire.core :refer [generate-string]]
            [newsback-api.data.query-executor :as q]
            [utils.json :as json-utils]))

(defn news [user-id]
  (log/info (format "op=get-plan, status=OK, desc=Fetching news for %s." user-id))          
  (let [final-query (str "select distinct(news) from newsdetails "
                    "inner join newstags on newsdetails.id = newstags.newsid "
                    "inner join newsupvoterates on newsupvoterates.newsid = newsdetails.id "
                    "where tagid in (select id from (select count(tags.id),tags.id from usertags " 
                    "inner join tags on usertags.tagid = tags.id where userid=" user-id 
                    " group by tags.id) as toptags order by count desc limit 10);")
        result (q/fetch final-query)]
    (if-not (empty? result)
      (do
        (log/debug (format "op=get-news, status=OK, desc=Fetched news successfully."))
        result)
      (do
        (log/info (format "op=get-news, status=OK, 
          desc=There was no news corresponding to the month requested.")
        )))))

(defn save-news [id news-html user-id]
  (let [news-id (if id id "default")
        query (if-not id (format "BEGIN;
                                  INSERT INTO newsdetails VALUES (default, '%s');
                                  INSERT INTO changelog SELECT nextval('changelog_id_seq'), '%s',
                                  currval('newsdetails_id_seq'), true;
                                  INSERT INTO changeevents SELECT nextval('changeevents_id_seq'),
                                  currval('changelog_id_seq'),1,%s,current_timestamp;
                                  COMMIT;"
                              news-html news-html user-id)
                          (format "BEGIN;
                                   UPDATE newsdetails SET news='%s' WHERE id=%s;
                                   INSERT INTO changelog SELECT nextval('changelog_id_seq'), 
                                   '%s',%s, true;
                                   INSERT INTO changeevents SELECT nextval('changeevents_id_seq'),
                                   currval('changelog_id_seq'),2,%s,current_timestamp;
                                   COMMIT;"
                              news-html id news-html id user-id))]
    (prn "query: " query)
    (log/debug (format "op=save-news, status=OK, desc=Inserting news with id '%s' for user-id %s ." id user-id))
    (q/execute query)
    (log/debug (format "op=save-news, status=OK, desc=Inserted news with id '%s' for user-id %s ." id user-id))))