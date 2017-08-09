(ns newsback-api.data.plans
  (:require [suricatta.dsl :as dsl]
            [clojure.data.json :as json]
            [clojure.tools.logging :as log]
            [clojure.walk :as walk]
            [cheshire.core :refer [generate-string]]
            [newsback-api.data.query-executor :as q]
            [utils.json :as json-utils]))

(defn- from-db [plan]
  (walk/postwalk-replace {:new_hires :new-hires :global_movements :global-movements} plan))

(defn- process-plan [result]
  {:plan-month (:plan_month result)
   :plan-year (:plan_year result)
   :plan (from-db (json-utils/json->clj (.getValue (:plan_data result))))})

(defn plan [country month year]
  (log/info (format "op=get-plan, status=OK, desc=Fetching supply plans(s) for month:%s and year:%s of country '%s'." month year (:name country)))
  (let [country-id (:id country)
        result (q/fetch (-> (dsl/select :country_id :plan_month :plan_year :plan_data)
                            (dsl/from :supply_plans)
                            (dsl/where ["country_id = ? and plan_month = ? and plan_year =?" country-id month year])))]

    (if-not (empty? result)
      (do
        (log/debug (format "op=get-plan, status=OK, desc=Fetched supply plans successfully."))
        (process-plan (first result)))
      (do
        (log/info (format "op=get-plan, status=OK, desc=There was no supply plan corresponding to the month requested."))))))

(defn- upsert-to-supply-plan-table-query [country-id plan-month plan-year plan-data]
  (format "INSERT INTO supply_plans (country_id, plan_month, plan_year, plan_data, created_at)
            VALUES ('%s', %s, '%s', '%s', current_timestamp)
            ON CONFLICT (country_id, plan_month, plan_year)
            DO UPDATE SET plan_data = '%s', updated_at = current_timestamp"
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
