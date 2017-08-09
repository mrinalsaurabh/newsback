(ns newsback-api.test.integration.rest.countries-resource
  (:use midje.sweet)
  (:require [ring.mock.request :as r]
            [newsback-api.handler :refer [app]]
            [newsback-api.test.rest.json-util :as u]))

(facts :api "Get countries"
       (fact "Get all countries"
             (let [response ((app) (r/request :get "/api/countries"))
                   expected-count 15
                   actual-response (u/parse-body (:body response))]
               (:status response) => 200
               (count actual-response) => expected-count)))
