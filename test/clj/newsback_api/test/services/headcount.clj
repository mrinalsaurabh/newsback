(ns newsback-api.test.services.headcount
  (:use midje.sweet)
  (:require [newsback-api.services.headcount :as headcount-service]
            [newsback-api.data.headcount :as headcount-data]))

(facts "Headcount service"
       (fact "should get all countries"
             (let [previous-month 6
                   previous-year 2017
                   country "India"
                   expected-data 200.15]

               (headcount-service/opening-headcount previous-month previous-year country) => 200.15
               (provided (headcount-data/opening-headcount 6 2017 "India") => [{:department "PS" :last-day-total-active-fte 200.151112 :monthend "2017-06-30"}
                                                                               {:department "OPS" :last-day-total-active-fte 200.151112 :monthend "2017-06-30"}] :times 1))))
