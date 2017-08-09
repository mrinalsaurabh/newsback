(ns newsback-api.services.headcount
  (:require [newsback-api.data.headcount :as headcount]
            [utils.math :refer [round-to-decimal-places]]))

(defn opening-headcount [previous-month previous-year country]
  (let [previous-headcount (headcount/opening-headcount previous-month previous-year country)
        previous-headcount-for-PS (first (filter #(= "PS" (:department %)) previous-headcount))]
    (round-to-decimal-places (:last-day-total-active-fte previous-headcount-for-PS) 2)))

(defn ending-headcount [closing-headcount-for-previous-month monthly-plan]
  (letfn [(attribute-total [attribute] (:total (attribute monthly-plan)))]
    (- (+ closing-headcount-for-previous-month
          (attribute-total :loa)
          (attribute-total :new-hires)
          (attribute-total :global-movements))
       (attribute-total :attrition))))
