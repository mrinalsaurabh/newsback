(ns newsback-api.test.rest.countries-resource
  (:use midje.sweet)
  (:require [newsback-api.rest.countries-resource :as countries-resource]
            [newsback-api.services.countries :as countries-service]
            [newsback-api.handler :refer [app]]
            [ring.mock.request :as r]
            [newsback-api.test.rest.json-util :as u]))

(facts "Countries resource"
       (fact "should get all countries"
             (with-redefs [countries-service/all-countries (fn [] [{:id 1 :country-id "india" :name "India"}
                                                                   {:id 2 :country-id "uk" :name "UK"}])]
               (let [expected-data [{:country_id "india" :name "India"}
                                    {:country_id "uk" :name "UK"}]
                     response ((app) (r/request :get "/api/countries"))
                     actual-response (u/parse-body (:body response))]
                 actual-response => expected-data))))
