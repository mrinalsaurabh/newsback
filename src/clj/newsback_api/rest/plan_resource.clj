(ns newsback-api.rest.plan-resource
  (:require [cheshire.core :refer [generate-string parse-string]]
            [compojure.api.sweet :refer [defroutes GET PUT]]
            [clojure.tools.logging :as log]
            [clojure.tools.reader.edn :as edn]
            [slingshot.slingshot :refer [try+ throw+]]
            [ring.util.http-response :refer [ok no-content]]
            [clojure.walk :as walk]
            [schema.core :as s]
            [utils.json :as json-util]
            [newsback-api.services.plan :as plan-service]
            [newsback-api.services.countries :as country-service]))

(def ^:private plan-period 6)

(defn- logged-in-user [request]
  {:firstname (get-in request [:headers "auth-first-name"] "Nobody")
   :email (get-in request [:headers "auth-email"] "nobody@tw.net")
   :user-id (get-in request [:headers "auth-user-id"] "nobody@tw.net")})

(defn- to-api-format [[k v]]
  [(keyword (clojure.string/replace (name k) "-" "_"))
   (cond
     (float? v) (format "%.2f" v)
     (integer? v) (str v)
     :else v)])

(defn to-api [m]
  (walk/postwalk (fn [x] (if (map? x) (into {} (map to-api-format x)) x)) m))

(s/defschema plan-schema
  {:plan [{:month s/Str
           :year s/Str
           :new_hires {:twu {:total s/Str}
                       :laterals {:total s/Str}}
           :loa {:inbounds {:total s/Str}
                 :outbounds {:total s/Str}}
           :attrition {:total s/Str}
           :global_movements {:inbounds {:total s/Str}
                              :outbounds {:total s/Str}}}]})

(defn- parse-number [key input-string]
  (try+
    (let [parsed-value (edn/read-string input-string)]
      (if (number? parsed-value)
        parsed-value
        (do
          (log/info (format "op=validate_input_data, status=KO, desc=Invalid input for the key '%s', given input '%s' is not a valid number." key input-string))
          (throw+ {:type :validation-error
                   :error {:key :invalid-input
                           :message (format "Invalid input for the key '%s', given input '%s' is not a valid number." key input-string)}}))))
    (catch [:type :reader-exception] {:keys []}
      (log/info (format "op=validate_input_data, status=KO, desc=Invalid input for the key '%s', given input '%s' is not a valid number or not readable." key input-string))
      (throw+ {:type :validation-error
               :error {:key :invalid-input
                       :message (format "Invalid input for the key '%s', given input '%s' is not a valid number or not readable." key input-string)}}))))

(defn- process [[k v]]
  [(keyword (clojure.string/replace (name k) "_" "-"))
   (cond
     (string? v) (parse-number k v)
     :else v)])

(defn- process-all-map-entries [plans]
  (walk/postwalk (fn [x] (if (map? x) (into {} (map process x)) x)) plans))

(defn- validate-and-process-plans [plans]
  (if (< (count plans) plan-period)
    (do
      (log/info (format "op=validate_input_data, status=KO, desc=Supply plan is expected to be done for %s months, but received plan only for %s months" plan-period (count plans)))
      (throw+ {:type :validation-error
               :error {:key :insufficient-plan
                       :message (format "Supply plan is expected to be done for %s months, but received plan only for %s months" plan-period (count plans))}}))
    (process-all-map-entries plans)))

(defroutes routes
  (GET "/countries/:country-id/plan" [:as request]
       :path-params [country-id :- String]
       :summary "Get supply plan for a country"
       (let [country (country-service/country country-id)
             current-user (logged-in-user request)
             plan (plan-service/plan country current-user)]
         (ok (to-api plan))))

  (PUT "/countries/:country-id/plan" [:as request]
       :path-params [country-id :- String]
       :body [plan plan-schema]
       :summary "Save supply plan for a country"
       (let [country (country-service/country country-id)
             monthly-plans (:plan plan)
             processed-plan (validate-and-process-plans monthly-plans)
             current-user (logged-in-user request)]
         (plan-service/save country current-user processed-plan)
         (no-content))))
