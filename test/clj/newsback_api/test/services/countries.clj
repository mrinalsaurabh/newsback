(ns newsback-api.test.services.countries
  (:use midje.sweet)
  (:require [newsback-api.services.countries :as countries-service]
            [newsback-api.data.countries :as countries-data]))

(facts "Country service"
       (fact "should get all countries"
             (with-redefs [countries-data/all-countries (fn [] [{:id 1 :country-id "india" :name "India"}
                                                          {:id 2 :country-id "uk" :name "UK"}])]
               (let [all-countries (countries-service/all-countries)
                     expected-data [{:id 1 :country-id "india" :name "India"}
                                    {:id 2 :country-id "uk" :name "UK"}]]
                 all-countries => expected-data)))

       (fact "should return a country"
             (with-redefs [countries-data/country (fn [country-id] {:id 1 :country-id "australia" :name "Australia"})]
               (let [country (countries-service/country "australia")
                     expected-data {:id 1 :country-id "australia" :name "Australia"}]

                 country => expected-data))))
