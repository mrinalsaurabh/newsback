(ns newsback-api.test.integration.rest.plan-resource
  (:use midje.sweet)
  (:require [clj-time.core :as time]
            [ring.mock.request :as r]
            [newsback-api.handler :refer [app]]
            [newsback-api.test.rest.json-util :as u]))

;; (facts :api "Save and Get plans"
;;        (fact "Save and Get plans"
;;              (with-redefs [time/now (fn [] (time/date-time 2017 5 6))]
;;                (let [save-api-response ((app) (-> (r/request :put "/api/countries/australia/plan")
;;                                                   (r/content-type "application/json")
;;                                                   (r/body (slurp (clojure.java.io/resource "plan_api_request.json")))))]
;;                  (:status save-api-response) => 200)
;;                (let [expected-response (u/parse-body (clojure.java.io/resource "plan_api_response.json"))
;;                      get-api-response ((app) (r/request :get "/api/countries/australia/plan"))
;;                      actual-response (u/parse-body (:body get-api-response))]
;;                  (:status get-api-response) => 200
;;                  actual-response => expected-response)
;;              )))
