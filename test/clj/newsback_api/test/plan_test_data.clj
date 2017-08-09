(ns newsback-api.test.plan-test-data)

(defn plan []
  {:country-id "australia"
   :country-name "Australia"
   :plan-month 5
   :plan-year 2017
   :plan [{:month 5
           :year 2017
           :opening-headcount 300.0
           :ending-headcount 320.1
           :new-hires {:total 11.0
                       :twu {:total 7.0}
                       :laterals {:total 4.0}}
           :loa {:total 3.1
                 :inbounds {:total 7.35}
                 :outbounds {:total 4.25 }}
           :attrition {:total 4.0}
           :global-movements {:total 10.0
                              :inbounds {:total 20.0}
                              :outbounds {:total 10.0}}}
          {:month 6
           :year 2017
           :opening-headcount 320.1
           :ending-headcount 330.35
           :new-hires {:total 7.0
                       :twu {:total 3.0}
                       :laterals {:total 4.0}}
           :loa {:total 3.25
                 :inbounds {:total 6.45 }
                 :outbounds {:total 3.2 }}
           :attrition {:total 2.0}
           :global-movements {:total 2.0
                              :inbounds {:total 5.45}
                              :outbounds {:total 3.45}}}
          {:month 7
           :year 2017
           :opening-headcount 330.35
           :ending-headcount 346.35
           :new-hires {:total 10.0
                       :twu {:total 5.5 }
                       :laterals {:total 4.5}}
           :loa {:total 1.0
                 :inbounds {:total 16.0}
                 :outbounds {:total 15.0 }}
           :attrition {:total 5.0}
           :global-movements {:total 10.0
                              :inbounds {:total 20.0}
                              :outbounds {:total 10.0}}}
          {:month 8
           :year 2017
           :opening-headcount 346.35
           :ending-headcount 351.85
           :new-hires {:total 4.5
                       :twu {:total 2.0 }
                       :laterals {:total 2.5 }}
           :loa {:total 2.0
                 :inbounds {:total 5.0 }
                 :outbounds {:total 3.0 }}
           :attrition {:total 6.0 }
           :global-movements {:total 5.0
                              :inbounds {:total 15.0}
                              :outbounds {:total 10.0}}}
          {:month 9
           :year 2017
           :opening-headcount 351.85
           :ending-headcount 371.85
           :new-hires {:total 10.0
                       :twu {:total 6.0}
                       :laterals {:total 4.0}}
           :loa {:total 5.0
                 :inbounds {:total 8.0}
                 :outbounds {:total 3.0}}
           :attrition {:total 0.0}
           :global-movements {:total 5.0
                              :inbounds {:total 8.0}
                              :outbounds {:total 3.0}}}
          {:month 10
           :year 2017
           :opening-headcount 371.85
           :ending-headcount 391.85
           :new-hires {:total 10.0
                       :twu {:total 3.0}
                       :laterals {:total 7.0}}
           :loa {:total 3.0
                 :inbounds {:total 5.0}
                 :outbounds {:total 2.0 }}
           :attrition {:total 3.0}
           :global-movements {:total 10.0
                              :inbounds {:total 20.0}
                              :outbounds {:total 10.0}}}]})

(defn plan-without-country []
  (dissoc (plan) :country-id :country-name))
