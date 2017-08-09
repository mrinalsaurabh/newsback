(ns newsback-api.rest.countries-resource
  (:require [compojure.api.sweet :refer [defroutes GET]]
            [ring.util.http-response :refer [ok]]
            [newsback-api.services.countries :as countries]))

(defn- to-api [countries]
  (map (fn [country] {:country_id (:country-id country)
                      :name (:name country)}) countries))

(defroutes routes
  (GET "/countries" []
       :summary "Get all countries."
       (let [countries (countries/all-countries)]
         (ok (to-api countries)))))
