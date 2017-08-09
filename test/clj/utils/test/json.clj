(ns utils.test.json
  (:use midje.sweet)
  (:require [utils.json :as json-utils])
  (:import org.postgresql.util.PGobject))

(facts "test conversion of json to clj"
       (fact "should convert json to clj object"
             (let [json "{\"total\":20}"
                   expected-data {:total 20}
                   actual-data (json-utils/json->clj json)]
               expected-data => actual-data)))
