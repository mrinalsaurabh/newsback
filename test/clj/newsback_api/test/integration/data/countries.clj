(ns newsback-api.test.integration.data.countries
  (:use midje.sweet)
  (:require [newsback-api.data.countries :as countries]
            [newsback-api.test.utils.exception :refer [is-type?]]))

(facts "Country repository"
       (fact "should fetch all the countries"
             (let [all-countries (countries/all-countries)
                   expected-data [{:country-id "australia", :id 1, :name "Australia"}
                                  {:country-id "brazil", :id 2, :name "Brazil"}
                                  {:country-id "canada", :id 3, :name "Canada"}
                                  {:country-id "chile", :id 4, :name "Chile"}
                                  {:country-id "china", :id 5, :name "China"}
                                  {:country-id "ecuador", :id 6, :name "Ecuador"}
                                  {:country-id "germany", :id 7, :name "Germany"}
                                  {:country-id "india", :id 8, :name "India"}
                                  {:country-id "italy", :id 9, :name "Italy"}
                                  {:country-id "singapore", :id 10, :name "Singapore"}
                                  {:country-id "south-africa", :id 11, :name "South Africa"}
                                  {:country-id "spain", :id 12, :name "Spain"}
                                  {:country-id "turkey", :id 13, :name "Turkey"}
                                  {:country-id "uk", :id 14, :name "UK"}
                                  {:country-id "us", :id 15, :name "US"}]]
               all-countries => expected-data))

       (fact "should fetch a country"
             (countries/country "australia") => {:id 1 :country-id "australia", :name "Australia"})

       (fact "should throw country not found error if a countryid doesn't exist"
             (countries/country "random-country-id") =>
             (throws clojure.lang.ExceptionInfo (is-type? :entity-not-found
                                                          :country-not-found
                                                          "There is no country with id 'random-country-id'"))))
