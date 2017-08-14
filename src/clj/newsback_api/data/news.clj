(ns newsback-api.data.news
  (:require [suricatta.dsl :as dsl]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clojure.walk :as walk]
            [cheshire.core :refer [generate-string]]
            [newsback-api.data.query-executor :as q]
            [utils.json :as json-utils]))

(defn- from-db [plan]
  (walk/postwalk-replace {:new_hires :new-hires :global_movements :global-movements} plan))

(defn news [user-id]
  (log/info (format "op=get-plan, status=OK, desc=Fetching supply plans(s) for month:%s and year:%s of country '%s'." month year (:name country)))
  (let [country-id (:id country)
        query "select id from 
              (select count(tags.id),tags.id from usertags inner join tags 
              on usertags.tagid = tags.id where userid=1 group by tags.id) 
              as toptags order by count desc limit 10;"
        query2 "select * from newsdetails inner join newstags on newsdetails.id = newstags.newsid 
               inner join newsupvoterates on newsupvoterates.newsid = newsdetails.id;"
        final-query "select distinct(news) from newsdetails inner join newstags on newsdetails.id = newstags.newsid 
                    inner join newsupvoterates on newsupvoterates.newsid = newsdetails.id
                    where tagid in (select id from 
                    (select count(tags.id),tags.id from usertags inner join tags 
                    on usertags.tagid = tags.id where userid=1 group by tags.id) 
                    as toptags order by count desc limit 10);"
        result (q/fetch (-> (dsl/select :id :news)
                            (dsl/from :newsdetails)))]

    (if-not (empty? result)
      (do
        (log/debug (format "op=get-plan, status=OK, desc=Fetched news successfully."))
        result)
      (do
        (log/info (format "op=get-plan, status=OK, desc=There was no news corresponding to the month requested."))))))

(defn- upsert-to-news-table-query [news-id news]
  (format "INSERT INTO newsdetails (news)
            VALUES ('%s')
            ON CONFLICT (id)
            DO UPDATE SET news = '%s'"
          country-id, plan-month, plan-year, plan-data, plan-data))

(defn- insert-into-changelog-table-query [country-id plan-month plan-year plan-data user]
  (format "INSERT INTO changelog (country_id, plan_month, plan_year, change, changed_by, created_at)
            VALUES ('%s', %s, '%s', '%s', '%s', current_timestamp)"
          country-id, plan-month, plan-year, plan-data (:user-id user)))

(defn- to-db [plan]
  (walk/postwalk-replace {:new-hires :new_hires :global-movements :global_movements} plan))

(defn save [country plan-month plan-year plan user]
  (let [country-id (:id country)
        plan-json (generate-string (to-db plan))]
    (log/debug (format "op=save-plan, status=OK, desc=Inserting supply plan with country-id '%s' for month '%s' and year '%s' ." country-id plan-month plan-year))
    (q/execute (upsert-to-supply-plan-table-query country-id plan-month plan-year plan-json)
               (insert-into-changelog-table-query country-id plan-month plan-year plan-json user))
     (log/debug (format "op=save-plan, status=OK, desc=Inserted/Updated supply plan with country-id '%s' for month '%s' and year '%s' ." country-id plan-month plan-year))))
