(ns newsback-api.data.headcount
  (:require [clj-http.client :as client]
            [clojure.tools.logging :as log]
            [newsback-api.config :refer [env]]))

(defn opening-headcount [latest-closing-headcount-month latest-closing-headcount-year country]
  (log/info (format "op=get-opening-headcount, status=OK, desc=Fetching opening headcount for month:%s and year:%s of country '%s'." latest-closing-headcount-month latest-closing-headcount-year country))
  (let [period 1
        response-type "closing-headcount"
        gofigure-url (env :gofigure-url)
        gofigure-api-username (env :gofigure-api-username)
        gofigure-api-password (env :gofigure-api-password)
        result (:body (client/get (str gofigure-url "/api/internal/headcount/monthly-headcount?month=" latest-closing-headcount-month
                                       "&year=" latest-closing-headcount-year
                                       "&period=" period
                                       "&response-type=" response-type
                                       "&country=" country)
                                  {:digest-auth [gofigure-api-username gofigure-api-password]
                                   :as :json}))]
    (if-not (empty? result)
      (do
        (log/debug (format "op=get-opening-headcount, status=OK, desc=Fetched headcount successfully."))
        result)
      (do
        (log/error (format "op=get-opening-headcount, status=KO, desc=There was no headcount obtained for  the month requested."))))
    ))
