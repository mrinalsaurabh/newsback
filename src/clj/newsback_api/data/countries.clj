(ns newsback-api.data.countries
  (:require [suricatta.dsl :as dsl]
            [clojure.tools.logging :as log]
            [slingshot.slingshot :refer [throw+]]
            [newsback-api.data.query-executor :as q]))

(defn- map-country [country]
  {:id (:id country)
   :country-id (:country_id country)
   :name (:name country)})

(defn- map-countries [countries]
  (map map-country countries))

(defn all-countries []
  (log/info "op=get_countries, status=OK, desc=Fetching all countries")
  (let [all-countries (q/fetch (-> (dsl/select)
                                   (dsl/from :countries)))]
    (log/debug (format "op=get_countries, status=OK, desc=Fetched all countries."))
    (map-countries all-countries)))

(defn country [country-id]
  (log/info (format "op=get_country, status=OK, desc=Fetching the country with country id '%s'." country-id))
  (let [result (q/fetch (-> (dsl/select)
                            (dsl/from :countries)
                            (dsl/where ["country_id = ?" country-id])))]
    (if-not (empty? result)
      (do
        (log/debug "op=get_country, status=OK, desc=Successfully fetched the country.")
        (map-country (first result)))
      (do
        (log/info (format "op=get_country, status=KO, desc=There is no country with id '%s'." country-id))
        (throw+ {:type :entity-not-found
                 :error {:key :country-not-found
                         :message (format "There is no country with id '%s'" country-id)}})))))
