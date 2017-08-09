(ns newsback-api.test.integration.data.plans
  (:use midje.sweet)
  (:require [suricatta.core :as sc]
            [suricatta.dsl :as dsl]
            [utils.json :as json-utils]
            [clojure.walk :as walk]
            [newsback-api.data.query-executor :as q]
            [newsback-api.data.core :refer [*db*] :as db]
            [newsback-api.test.utils.exception :refer [is-type?]]
            [newsback-api.data.plans :as plan-data]
            [newsback-api.test.plan-test-data :as test-data]))

(defn clean []
  (with-open [ctx (sc/context *db*)]
    (sc/execute ctx "DELETE from supply_plans")
    (sc/execute ctx "DELETE from changelog")))

(defn- from-db [plan]
  (walk/postwalk-replace {:new_hires :new-hires :global_movements :global-movements} plan))

(defn- process-plan [result]
  {:plan-month (:plan_month result)
   :plan-year (:plan_year result)
   :plan (from-db (json-utils/json->clj (.getValue (:change result))))})

(defn fetch-latest-plan-changelog []
  (with-open [ctx (sc/context *db*)]
    (q/fetch (-> (dsl/select)
                 (dsl/from :changelog)
                 (dsl/order-by [:created_at :desc])
                 (dsl/limit 1)))))

(facts "Supply Plan Data"
       (background [(after :facts (clean))])

       (fact "should save and return supply plan"
             (let [plan-month 1
                   plan-year 2017
                   country {:id 1 :country-id "australia" :name "Australia"}
                   plan-data (:plan (test-data/plan))
                   user {:user-id "test-user@tw.net"}]

               (plan-data/save country plan-month plan-year plan-data user)

               (let [plans (plan-data/plan country 1 2017)
                     expected-data {:plan-year 2017
                                    :plan-month 1
                                    :plan plan-data}
                     changelog-data (process-plan (first (fetch-latest-plan-changelog)))]

                 plans => expected-data
                 changelog-data => expected-data)))

        (fact "should return nil when plan does not exist in DB"
              (let [country {:id 1 :name "Australia"}]
                (plan-data/plan country 2 2018) => nil)))
