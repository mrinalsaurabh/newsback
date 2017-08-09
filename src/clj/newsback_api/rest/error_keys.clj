(ns newsback-api.rest.error-keys)

(def error-key-map {:gateway-error "ERR_GATEWAY_ERROR"
                    :country-not-found "COUNTRY_NOT_FOUND"
                    :insufficient-plan "INSUFFICIENT_PLAN"
                    :invalid-input "INVALID_INPUT"})

(defn api-error-key [error-key]
  (error-key error-key-map))
