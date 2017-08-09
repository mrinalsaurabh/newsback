(ns newsback-api.test.rest.plan-resource
  (:use midje.sweet)
  (:require [ring.mock.request :as r]
            [cheshire.core :refer [parse-string]]
            [slingshot.slingshot :refer [throw+]]
            [newsback-api.services.plan :as plan-service]
            [newsback-api.services.countries :as country-service]
            [newsback-api.handler :refer [app]]
            [newsback-api.test.rest.json-util :as u]
            [newsback-api.test.plan-test-data :as test-data]
            [newsback-api.test.rest.save-test-data :as save-test-data]))

(facts "Plan Resource::Get endpoint"
       (fact "should return the plan as response"
             (with-redefs [country-service/country (fn [country-id] {:id 1 :country-id "australia" :name "Australia"})
                           plan-service/plan (fn [country user] (test-data/plan))]
               (let [expected-response (u/parse-body (clojure.java.io/resource "plan_api_response.json"))
                     response ((app) (r/request :get "/api/countries/australia/plan"))
                     actual-response (u/parse-body (:body response))]
                 (:status response) => 200
                 actual-response => expected-response)))

       (fact "Should return error if invalid country id is given when getting the plan"
             (with-redefs [country-service/country (fn [country-id] (throw+ {:type :entity-not-found
                                                                             :error {:key :country-not-found
                                                                                     :message (format "There is no country with id '%s'" country-id)}}))]
               (let [response ((app) (-> (r/request :get "/api/countries/non-existing-country-id/plan")))
                     expected-data {:key "COUNTRY_NOT_FOUND"
                                    :message "There is no country with id 'non-existing-country-id'"}
                     actual-response (u/parse-body (:body response))]
                 (:status response) => 404
                 actual-response => expected-data))))

(facts "Plan Resource::Save end points"
       (fact "Should save the given plan data for given country"
             (with-redefs [country-service/country (fn [country-id] {:id 1 :country-id "australia" :name "Australia"})]
               (let [country {:id 1 :country-id "australia" :name "Australia"}
                     user {:firstname "Shobana" :email "shobana@thoughtworks.com" :user-id "shobana_id@thoughtworks.com"}
                     save-data (save-test-data/data)
                     request (-> (r/request :put "/api/countries/australia/plan")
                                         (r/content-type "application/json")
                                         (r/header "auth-first-name" "Shobana")
                                         (r/header "auth-email" "shobana@thoughtworks.com")
                                         (r/header "auth-user-id" "shobana_id@thoughtworks.com")
                                         (r/body (slurp (clojure.java.io/resource "plan_api_request.json"))))]
                 (:status ((app) request)) => 204
                 (provided (plan-service/save country user save-data) => true :times 1))))

       (fact "Should save with default user (nobody) when user information is not supplied in the header"
             (with-redefs [country-service/country (fn [country-id] {:id 1 :country-id "australia" :name "Australia"})]
               (let [country {:id 1 :country-id "australia" :name "Australia"}
                     user {:firstname "Nobody" :email "nobody@tw.net" :user-id "nobody@tw.net"}
                     save-data (save-test-data/data)
                     request (-> (r/request :put "/api/countries/australia/plan")
                                         (r/content-type "application/json")
                                         (r/body (slurp (clojure.java.io/resource "plan_api_request.json"))))]
                 (:status ((app) request)) => 204
                 (provided (plan-service/save country user save-data) => true :times 1))))

       (fact "Should return error if invalid country id is given when updating plan"
             (with-redefs [country-service/country (fn [country-id] (throw+ {:type :entity-not-found
                                                                             :error {:key :country-not-found
                                                                                     :message (format "There is no country with id '%s'" country-id)}}))]
               (let [response ((app) (-> (r/request :put "/api/countries/non-existing-country-id/plan")
                                                (r/content-type "application/json")
                                                (r/body (slurp (clojure.java.io/resource "plan_api_request.json")))))
                     expected-data {:key "COUNTRY_NOT_FOUND"
                                    :message "There is no country with id 'non-existing-country-id'"}
                     actual-response (u/parse-body (:body response))]
                 (:status response) => 404
                 actual-response => expected-data)))

       (fact "Should return insufficient plan error if plan is not submitted for entire plan period"
             (let [response ((app) (-> (r/request :put "/api/countries/china/plan")
                                       (r/content-type "application/json")
                                       (r/body (slurp (clojure.java.io/resource "plan_for_one_month.json")))))
                   expected-data {:key "INSUFFICIENT_PLAN"
                                  :message "Supply plan is expected to be done for 6 months, but received plan only for 1 months"}
                   actual-response (u/parse-body (:body response))]
               (:status response) => 400
               actual-response => expected-data))

       (fact "Should return invalid input error if plan has alphabet values"
             (let [response ((app) (-> (r/request :put "/api/countries/china/plan")
                                       (r/content-type "application/json")
                                       (r/body (slurp (clojure.java.io/resource "plan_api_request_with_alphabet_input.json")))))
                   expected-data {:key "INVALID_INPUT"
                                  :message "Invalid input for the key ':total', given input 'abc' is not a valid number."}
                   actual-response (u/parse-body (:body response))]
               (:status response) => 400
               actual-response => expected-data))


       (fact "Should return invalid input error if plan has alphanumeric values"
             (let [response ((app) (-> (r/request :put "/api/countries/china/plan")
                                       (r/content-type "application/json")
                                       (r/body (slurp (clojure.java.io/resource "plan_api_request_with_alphanumeric_input.json")))))
                   expected-data {:key "INVALID_INPUT"
                                  :message "Invalid input for the key ':total', given input '7.ab' is not a valid number or not readable."}
                   actual-response (u/parse-body (:body response))]
               (:status response) => 400
               actual-response => expected-data)))
