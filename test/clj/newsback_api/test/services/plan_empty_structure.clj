(ns newsback-api.test.services.plan-empty-structure)

(defn plan-test-data []
  {:country-id "australia"
   :country-name "Australia"
   :plan-month 5
   :plan-year 2017
   :plan [{:month 5
           :year 2017
           :opening-headcount 200.0
           :ending-headcount 200.0
           :new-hires {:total 0.0
                       :twu {:total 0.0}
                       :laterals {:total 0.0}}
           :loa {:total 0.0
                 :inbounds {:total 0.0}
                 :outbounds {:total 0.0 }}
           :attrition {:total 0.0}
           :global-movements {:total 0.0
                              :inbounds {:total 0.0}
                              :outbounds {:total 0.0}}}
          {:month 6
           :year 2017
           :opening-headcount 200.0
           :ending-headcount 200.0
           :new-hires {:total 0.0
                       :twu {:total 0.0}
                       :laterals {:total 0.0}}
           :loa {:total 0.0
                 :inbounds {:total 0.0 }
                 :outbounds {:total 0.0 }}
           :attrition {:total 0.0}
           :global-movements {:total 0.0
                              :inbounds {:total 0.0}
                              :outbounds {:total 0.0}}}
          {:month 7
           :year 2017
           :opening-headcount 200.0
           :ending-headcount 200.0
           :new-hires {:total 0.0
                       :twu {:total 0.0 }
                       :laterals {:total 0.0}}
           :loa {:total 0.0
                 :inbounds {:total 0.0}
                 :outbounds {:total 0.0 }}
           :attrition {:total 0.0}
           :global-movements {:total 0.0
                              :inbounds {:total 0.0}
                              :outbounds {:total 0.0}}}
          {:month 8
           :year 2017
           :opening-headcount 200.0
           :ending-headcount 200.0
           :new-hires {:total 0.0
                       :twu {:total 0.0 }
                       :laterals {:total 0.0 }}
           :loa {:total 0.0
                 :inbounds {:total 0.0 }
                 :outbounds {:total 0.0 }}
           :attrition {:total 0.0 }
           :global-movements {:total 0.0
                              :inbounds {:total 0.0}
                              :outbounds {:total 0.0}}}
          {:month 9
           :year 2017
           :opening-headcount 200.0
           :ending-headcount 200.0
           :new-hires {:total 0.0
                       :twu {:total 0.0}
                       :laterals {:total 0.0}}
           :loa {:total 0.0
                 :inbounds {:total 0.0}
                 :outbounds {:total 0.0}}
           :attrition {:total 0.0}
           :global-movements {:total 0.0
                              :inbounds {:total 0.0}
                              :outbounds {:total 0.0}}}
          {:month 10
           :year 2017
           :opening-headcount 200.0
           :ending-headcount 200.0
           :new-hires {:total 0.0
                       :twu {:total 0.0}
                       :laterals {:total 0.0}}
           :loa {:total 0.0
                 :inbounds {:total 0.0}
                 :outbounds {:total 0.0 }}
           :attrition {:total 0.0}
           :global-movements {:total 0.0
                              :inbounds {:total 0.0}
                              :outbounds {:total 0.0}}}]})
