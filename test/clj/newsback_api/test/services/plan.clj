(ns newsback-api.test.services.plan
  (:use midje.sweet)
  (:require [clj-time.core :as time]
            [newsback-api.test.plan-test-data :as test-data]
            [newsback-api.test.services.plan-empty-structure :as empty-structure]
            [newsback-api.services.plan :as plan-service]
            [newsback-api.data.plans :as plan-data]
            [newsback-api.services.headcount :as headcount-service]
            [newsback-api.test.services.copied-plan :as test-data-copied]))

(facts "Plan service::Get plan"
       (fact "should return the previous month and year"
             (with-redefs [plan-service/current-date (fn [] (time/date-time 2017 1))]

             (let [expected-data {:plan-month 12 :plan-year 2016}]
               (plan-service/previous-planning-time) => expected-data)))

       (fact "should return default supply plan if there is no existing supply plan"
             (let [country {:id 1 :country-id "australia" :name "Australia"}
                   expected-data (empty-structure/plan-test-data)
                   user {:user-id "test-user@tw.net"}]

               (plan-service/plan country user) => expected-data

               (provided (headcount-service/opening-headcount 4 2017 "Australia") => 200.0 :times 1
                         (plan-data/plan country 5 2017) => nil :times 1
                         (plan-data/plan country 4 2017) => nil :times 1
                         (plan-service/current-date) => (time/date-time 2017 5 20) :times 2)))

       (fact "should copy plan from previous month if plan does not exist for current month, but exists for previous planning month"
             (let [country {:id 1 :country-id "australia" :name "Australia"}
                   expected-data (assoc (test-data-copied/plan-with-headcount-and-subtotals) :country-id "australia" :country-name "Australia")
                   user {:user-id "test-user@tw.net"}]

               (plan-service/plan country user) => expected-data

               (provided (headcount-service/opening-headcount 5 2017 "Australia") => 200 :times 1
                         (plan-data/plan country 6 2017) => nil :times 1
                         (plan-data/plan country 5 2017) => (test-data/plan-without-country) :times 1
                         (plan-service/current-date) => (time/date-time 2017 6 20) :times 2)))

       (fact "should get the supply plan if it exists for the given plan-month"
             (let [country {:id 1 :country-id "australia" :name "Australia"}
                   expected-data (assoc (test-data/plan-without-country) :country-id "australia" :country-name "Australia")
                   user {:user-id "test-user@tw.net"}]

               (plan-service/plan country user) => expected-data

               (provided (plan-data/plan country 5 2017) => (test-data/plan-without-country) :times 1
                         (plan-service/current-date) => (time/date-time 2017 5 20) :times 1))))

(facts "Plan service::Save plan"
       (fact "should save current month plan data for the given country with headcounts and subtotals"
             (with-redefs [plan-service/current-date (fn [] (time/date-time 2017 6))]
               (let [country {:id 1 :country-id "australia" :name "Australia"}
                     user {:user-id "test-user@tw.net"}
                     basic-plan (:plan (test-data-copied/plan-without-subtotals-and-headcount))
                     plan-with-subtotals-and-headcount (:plan (test-data-copied/plan-with-headcount-and-subtotals))]
                 (plan-service/save country user basic-plan) => nil

                 (provided (plan-data/save country 6 2017 plan-with-subtotals-and-headcount user) => nil :times 1
                           (plan-service/current-date) => (time/date-time 2017 6 20) :times 1
                           (plan-data/plan country 6 2017) => (test-data-copied/empty-plan-only-with-headcount))))))

(facts "Update plan with opening and ending headcounts"
       (fact "should update all months' plans with headcounts by taking first months's opening headcount from already existing plan in database"
             (let [planning-month 6
                   planning-year 2017
                   country {:id 1 :country-id "australia" :name "Australia"}
                   plan-without-headcount (:plan (test-data-copied/plan-without-headcount))
                   expected-plan-with-headcount (:plan (test-data-copied/plan-with-headcount-and-subtotals))]
               (plan-service/update-plan-with-headcount country planning-month planning-year plan-without-headcount) => expected-plan-with-headcount

               (provided (plan-data/plan country planning-month planning-year) => (test-data-copied/empty-plan-only-with-headcount))))

       (fact "should update all months' plan with headcounts by taking first month's opening headcount from gofigure api if there is no already existing plan in database"
             (let [planning-month 6
                   planning-year 2017
                   previous-planning-month 5
                   previous-planning-year 2017
                   country {:id 1 :country-id "australia" :name "Australia"}
                   plan-without-headcount (:plan (test-data-copied/plan-without-headcount))
                   expected-plan-with-headcount (:plan (test-data-copied/plan-with-headcount-and-subtotals))]
               (plan-service/update-plan-with-headcount country planning-month planning-year plan-without-headcount) => expected-plan-with-headcount

               (provided (plan-service/current-date) => (time/date-time 2017 6 20) :times 1
                         (plan-data/plan country planning-month planning-year) => nil
                         (headcount-service/opening-headcount previous-planning-month previous-planning-year "Australia") => 200)
               )))
