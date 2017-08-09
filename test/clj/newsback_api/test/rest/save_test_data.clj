(ns newsback-api.test.rest.save-test-data)

(defn data []
  [{:month 5
    :year 2017
    :new-hires {:twu {:total 7.0} :laterals {:total 4.0}}
    :loa {:inbounds {:total 7.35} :outbounds {:total 4.25}}
    :attrition {:total 4.0}
    :global-movements {:inbounds {:total 20.0} :outbounds {:total 10.0}}}
   {:month 6
    :year 2017
    :new-hires {:twu {:total 3.0} :laterals {:total 4.0}}
    :loa {:inbounds {:total 6.45} :outbounds {:total 3.2}}
    :attrition {:total 2.0}
    :global-movements {:inbounds {:total 5.45} :outbounds {:total 3.45}}}
   {:month 7
    :year 2017
    :new-hires {:twu {:total 5.5} :laterals {:total 4.5}}
    :loa {:inbounds {:total 16.0} :outbounds {:total 15.0}}
    :attrition {:total 5.0}
    :global-movements {:inbounds {:total 20.0} :outbounds {:total 10.0}}}
   {:month 8
    :year 2017
    :new-hires {:twu {:total 2.0} :laterals {:total 2.5}}
    :loa {:inbounds {:total 5.0} :outbounds {:total 3.0}}
    :attrition {:total 6.0}
    :global-movements {:inbounds {:total 15.0} :outbounds {:total 10.0}}}
   {:month 9
    :year 2017
    :new-hires {:twu {:total 6.0} :laterals {:total 4.0}}
    :loa {:inbounds {:total 8.0} :outbounds {:total 3.0}}
    :attrition {:total 0.0}
    :global-movements {:inbounds {:total 8.0} :outbounds {:total 3.0}}}
   {:month 10
    :year 2017
    :new-hires {:twu {:total 3.0} :laterals {:total 7.0}}
    :loa {:inbounds {:total 5.0} :outbounds {:total 2.0}}
    :attrition {:total 3.0}
    :global-movements {:inbounds {:total 20.0} :outbounds {:total 10.0}}}])
