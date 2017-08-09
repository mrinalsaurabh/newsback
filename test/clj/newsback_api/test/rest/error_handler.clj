(ns newsback-api.test.rest.error-handler
  (:use midje.sweet)
  (:require [ring.mock.request :refer [request]]
            [slingshot.slingshot :refer [throw+]]
            [newsback-api.test.rest.error.test-handler :refer [app]]
            [newsback-api.test.rest.error.test-resource :as tr]
            [newsback-api.test.rest.json-util :as u]))

(facts "API error handler"
       (fact "should return internal-server-error for any exception"
             (with-redefs [tr/response-data (fn [] (throw (Exception.)))]
               (let [response ((app) (request :get "/api/test-resource-collection/test-resource-uri"))
                     json-response (u/parse-body (:body response))]

                 (:status response) => 500
                 (:key json-response) => "ERR_GENERIC_ERROR"
                 (:message json-response) => "Oops, we are embarrassed. Unfortunately, we are experiencing some technical difficulties.")))

       (fact "should return internal-server-error for any gateway-error"
             (with-redefs [tr/response-data (fn [] (throw+ {:type :gateway-error :error {:key :gateway-error}}))]
               (let [response ((app) (request :get "/api/test-resource-collection/test-resource-uri"))
                     json-response (u/parse-body (:body response))]

                 (:status response) => 500
                 (:key json-response) => "ERR_GATEWAY_ERROR"
                 (:message json-response) => "Oops, we are embarrassed. Unfortunately, we are experiencing some technical difficulties."))))
