(ns newsback-api.test.services.copied-plan)

(defn plan-test-data []
  {:plan-month 6
   :plan-year 2017
   :country-id "australia"
   :country-name "Australia"
   :plan [{:month 6
           :year 2017
           :opening-headcount 200
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
                              :outbounds {:total 10.0}}}
          {:month 11
           :year 2017
           :opening-headcount 0.0
           :ending-headcount 0.0
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

(defn plan-without-headcount []
  {:plan-month 6
   :plan-year 2017
   :plan [{:month 6
           :year 2017
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
           :new-hires {:total 10.0
                       :twu {:total 3.0}
                       :laterals {:total 7.0}}
           :loa {:total 3.0
                 :inbounds {:total 5.0}
                 :outbounds {:total 2.0 }}
           :attrition {:total 3.0}
           :global-movements {:total 10.0
                              :inbounds {:total 20.0}
                              :outbounds {:total 10.0}}}
          {:month 11
           :year 2017
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

(defn plan-with-headcount-and-subtotals []
  {:plan-month 6
   :plan-year 2017
   :plan [{:month 6
           :year 2017
           :opening-headcount 200
           :ending-headcount 210.25
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
           :opening-headcount 210.25
           :ending-headcount 226.25
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
           :opening-headcount 226.25
           :ending-headcount 231.75
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
           :opening-headcount 231.75
           :ending-headcount 251.75
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
           :opening-headcount 251.75
           :ending-headcount 271.75
           :new-hires {:total 10.0
                       :twu {:total 3.0}
                       :laterals {:total 7.0}}
           :loa {:total 3.0
                 :inbounds {:total 5.0}
                 :outbounds {:total 2.0 }}
           :attrition {:total 3.0}
           :global-movements {:total 10.0
                              :inbounds {:total 20.0}
                              :outbounds {:total 10.0}}}
          {:month 11
           :year 2017
           :opening-headcount 271.75
           :ending-headcount 271.75
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

(defn plan-without-subtotals-and-headcount []
  {:plan-month 6
   :plan-year 2017
   :plan [{:month 6
           :year 2017
           :new-hires {:twu {:total 3.0}
                       :laterals {:total 4.0}}
           :loa {:inbounds {:total 6.45 }
                 :outbounds {:total 3.2 }}
           :attrition {:total 2.0}
           :global-movements {:inbounds {:total 5.45}
                              :outbounds {:total 3.45}}}
          {:month 7
           :year 2017
           :new-hires {:twu {:total 5.5 }
                       :laterals {:total 4.5}}
           :loa {:inbounds {:total 16.0}
                 :outbounds {:total 15.0 }}
           :attrition {:total 5.0}
           :global-movements {:inbounds {:total 20.0}
                              :outbounds {:total 10.0}}}
          {:month 8
           :year 2017
           :new-hires {:twu {:total 2.0 }
                       :laterals {:total 2.5 }}
           :loa {:inbounds {:total 5.0 }
                 :outbounds {:total 3.0 }}
           :attrition {:total 6.0 }
           :global-movements {:inbounds {:total 15.0}
                              :outbounds {:total 10.0}}}
          {:month 9
           :year 2017
           :new-hires {:twu {:total 6.0}
                       :laterals {:total 4.0}}
           :loa {:inbounds {:total 8.0}
                 :outbounds {:total 3.0}}
           :attrition {:total 0.0}
           :global-movements {:inbounds {:total 8.0}
                              :outbounds {:total 3.0}}}
          {:month 10
           :year 2017
           :new-hires {:twu {:total 3.0}
                       :laterals {:total 7.0}}
           :loa {:inbounds {:total 5.0}
                 :outbounds {:total 2.0 }}
           :attrition {:total 3.0}
           :global-movements {:inbounds {:total 20.0}
                              :outbounds {:total 10.0}}}
          {:month 11
           :year 2017
           :new-hires {:twu {:total 0.0}
                       :laterals {:total 0.0}}
           :loa {:inbounds {:total 0.0}
                 :outbounds {:total 0.0 }}
           :attrition {:total 0.0}
           :global-movements {:inbounds {:total 0.0}
                              :outbounds {:total 0.0}}}]})

(defn empty-plan-only-with-headcount []
  {:plan-month 6
   :plan-year 2017
   :country-id "australia"
   :country-name "Australia"
   :plan [{:month 6
           :year 2017
           :opening-headcount 200
           :ending-headcount 200
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
          {:month 7
           :year 2017
           :opening-headcount 200
           :ending-headcount 200
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
          {:month 8
           :year 2017
           :opening-headcount 200
           :ending-headcount 200
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
          {:month 9
           :year 2017
           :opening-headcount 200
           :ending-headcount 200
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
           :opening-headcount 200
           :ending-headcount 200
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
          {:month 11
           :year 2017
           :opening-headcount 200
           :ending-headcount 200
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
          ]})
