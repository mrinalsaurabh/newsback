(ns newsback-api.services.countries
  (:require [newsback-api.data.countries :as countries]))

(defn all-countries []
  (countries/all-countries))

(defn country [country-id]
  (countries/country country-id))
