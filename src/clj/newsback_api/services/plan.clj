(ns newsback-api.services.plan
  (:require [clj-time.core :as time]
            [clj-time.periodic :as periodic]
            [newsback-api.data.plans :as plan-data]
            [newsback-api.services.headcount :as headcount]))

(defn current-date []
  (time/now))

(defn planning-time []
  (let [date (current-date)
        end-date (time/plus date (time/months 5))]
    {:plan-month (time/month date)
     :plan-year (time/year date)
     :plan-end-month (time/month end-date)
     :plan-end-year (time/year end-date)}))

(defn previous-planning-time []
  (let [previous-plan-time (time/minus (current-date) (time/months 1))]
    {:plan-month (time/month previous-plan-time)
     :plan-year (time/year previous-plan-time)}))

(def ^:private plan-period 6)

(defn- empty-plan-for [month year]
  {:month month
   :year year
   :opening-headcount 0.0
   :ending-headcount 0.0
   :new-hires {:total 0.0
               :twu {:total 0.0}
               :laterals {:total 0.0}}
   :loa {:total 0.0
         :inbounds {:total 0.0}
         :outbounds {:total 0.0}}
   :attrition {:total 0.0}
   :global-movements {:total 0.0
                      :inbounds {:total 0.0}
                      :outbounds {:total 0.0}}})

(defn- empty-plan-structure [plan-month plan-year period]
  (let [time-seq (take period (periodic/periodic-seq (time/date-time plan-year plan-month) (time/months 1)))]
    (map
     (fn [time-val]
       (let [month (time/month time-val)
             year (time/year time-val)]
         (empty-plan-for month year)))
     time-seq)))

(defn- plan-meta [plan country-id country-name plan-month plan-year]
  (assoc {:country-id country-id
          :country-name country-name
          :plan-month plan-month
          :plan-year plan-year} :plan plan))

(defn update-all-recurring-months-with-headcounts [plan ending-headcount-for-previous-month]
  (letfn [(updated-plan [plan-data plan-to-be-updated opening-headcount ending-headcount]
            (conj plan-data (assoc plan-to-be-updated :opening-headcount opening-headcount :ending-headcount ending-headcount)))]
    (let [updated-plan (reduce (fn [val1 val2]
                                 (if (empty? val1)
                                   (updated-plan val1 val2 ending-headcount-for-previous-month (headcount/ending-headcount ending-headcount-for-previous-month val2))
                                   (updated-plan val1 val2 (:ending-headcount (last val1)) (headcount/ending-headcount (:ending-headcount (last val1)) val2))))
                               [] plan)]
      updated-plan)))

(defn- default-plan [country plan-time user]
  (let [country-id (:country-id country)
        country-name (:name country)
        plan-month (:plan-month plan-time)
        plan-year (:plan-year plan-time)
        previous-plan-time (previous-planning-time)
        previous-month (:plan-month previous-plan-time)
        previous-year (:plan-year previous-plan-time)
        previous-plan (plan-data/plan country previous-month previous-year)
        last-month-data [(empty-plan-for (:plan-end-month plan-time) (:plan-end-year plan-time))]
        plan-data (if previous-plan
                    (into (vec (rest (:plan previous-plan))) last-month-data)
                    (vec (empty-plan-structure plan-month plan-year plan-period)))
        closing-headcount-for-previous-month (headcount/opening-headcount previous-month previous-year country-name)
        plan-with-headcount (update-all-recurring-months-with-headcounts plan-data closing-headcount-for-previous-month)]

    (plan-data/save country plan-month plan-year plan-with-headcount user)
    (plan-meta plan-with-headcount country-id country-name plan-month plan-year)))

(defn plan [country user]
  (let [country-id (:country-id country)
        country-name (:name country)
        plan-time (planning-time)
        plan-month (:plan-month plan-time)
        plan-year (:plan-year plan-time)
        plan (plan-data/plan country plan-month plan-year)]
    (if plan
      (assoc plan :country-id country-id :country-name country-name)
      (default-plan country plan-time user))))

(defn calculate-subtotals-for-plan [plan]
  (let [new-hires (:new-hires plan)
        new-hires-total (+ (:total (:twu new-hires)) (:total (:laterals new-hires)))
        loa (:loa plan)
        loa-total (- (:total (:inbounds loa)) (:total (:outbounds loa)))
        global-movements (:global-movements plan)
        global-movements-total (- (:total (:inbounds global-movements)) (:total (:outbounds global-movements)))]

    (-> plan
        (assoc-in [:new-hires :total] new-hires-total)
        (assoc-in [:loa :total] loa-total)
        (assoc-in [:global-movements :total] global-movements-total))))

(defn calculate-subtotals-for [plans]
  (map calculate-subtotals-for-plan plans))

(defn- get-opening-headcount-from-gofigure [country]
  (let [previous-plan-time (previous-planning-time)
        previous-planning-month (:plan-month previous-plan-time)
        previous-planning-year (:plan-year previous-plan-time)
        country-name (:name country)]
    (headcount/opening-headcount previous-planning-month previous-planning-year country-name)))

(defn- get-opening-headcount-from-already-existing-plan-in-db [country planning-month planning-year]
  (let [already-existing-plan-for-planning-month (:plan (plan-data/plan country planning-month planning-year))]
    (if (some? already-existing-plan-for-planning-month)
      (some #(if (and (= (:month %) planning-month)
                      (= (:year %) planning-year)
                      (some? (:opening-headcount %)))
               (:opening-headcount %))
            already-existing-plan-for-planning-month))))

(defn- get-opening-headcount-for-planning-month [country planning-month planning-year]
  (or (get-opening-headcount-from-already-existing-plan-in-db country planning-month planning-year)
      (get-opening-headcount-from-gofigure country)))

(defn update-plan-with-headcount [country planning-month planning-year plan-without-headcount]
  (let [opening-headcount-for-planning-month (get-opening-headcount-for-planning-month country planning-month planning-year)
        plan-with-headcount (update-all-recurring-months-with-headcounts plan-without-headcount opening-headcount-for-planning-month)]
    plan-with-headcount))

(defn save [country user monthly-plans]
  (let [planning-time (planning-time)
        planning-month (:plan-month planning-time)
        planning-year (:plan-year planning-time)
        plan-with-subtotals (calculate-subtotals-for monthly-plans)
        plan-with-headcount (update-plan-with-headcount country planning-month planning-year plan-with-subtotals)]
    (plan-data/save country planning-month planning-year plan-with-headcount user)))
