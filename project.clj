(def version (if-let [version (System/getenv "GO_PIPELINE_COUNTER")]
               (str version "." (System/getenv "GO_STAGE_COUNTER"))
               "0.1.0-SNAPSHOT"))

(defproject newsback-api-service version

  :description "API for newsback"
  :url "http://example.com/FIXME"

  :dependencies [[buddy "1.3.0"]
                 [cheshire "5.7.1"]
                 [clj-time "0.14.0"]
                 [compojure "1.6.0"]
                 [conman "0.6.7"]
                 [cprop "0.1.11"]
                 [funcool/struct "1.0.0"]
                 [io.clj/logging "0.8.1" :exclusions [org.clojure/tools.logging]]
                 [luminus-immutant "0.2.3"]
                 [luminus-migrations "0.3.9"]
                 [luminus-nrepl "0.1.4"]
                 [clj-http "3.1.0"]
                 [luminus/ring-ttl-session "0.3.2"]
                 [markdown-clj "0.9.99"]
                 [metosin/compojure-api "1.1.11"]
                 [metosin/muuntaja "0.3.2"]
                 [metosin/ring-http-response "0.9.0"]
                 [mount "0.1.11"]
                 [org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/tools.logging "0.4.0"]
                 [org.clojure/data.json "0.2.6"]
                 [org.postgresql/postgresql "42.1.4"]
                 [ring/ring-core "1.6.2"]
                 [ring/ring-defaults "0.3.1"]
                 [selmer "1.11.0"]
                 [slingshot "0.12.2"]
                 [funcool/suricatta "1.3.1"]]

  :min-lein-version "2.0.0"

  :jvm-opts ["-server" "-Dconf=.lein-env"]
  :source-paths ["src/clj"]
  :test-paths ["test/clj"]
  :resource-paths ["resources"]
  :target-path "target/%s/"
  :main ^:skip-aot newsback-api.core
  :migratus {:store :database :db ~(get (System/getenv) "DATABASE_URL")}

  :plugins [[lein-ancient "0.6.10"]
            [lein-shell "0.4.0"]
            [lein-cprop "1.0.3"]
            [migratus-lein "0.4.9"]
            [lein-immutant "2.1.0"]
            [lein-midje "3.2.1"]]

  :profiles
  {:uberjar {:omit-source true
             :aot :all
             :uberjar-name "newsback-api-service.jar"
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}

   :dev           [:project/dev :profiles/dev]
   :test          [:project/dev :project/test :profiles/test]
   :midje         [:project/test :profiles/test]
   :project/dev  {:dependencies [[prone "1.1.4"]
                                 [ring/ring-mock "0.3.1"]
                                 [ring/ring-devel "1.6.2"]
                                 [pjstadig/humane-test-output "0.8.2"]
                                 [midje "1.8.3" :exclusions [org.clojure/clojure]]]
                  :plugins      [[com.jakemccrary/lein-test-refresh "0.19.0"]]

                  :source-paths ["env/dev/clj"]
                  :resource-paths ["env/dev/resources"]
                  :repl-options {:init-ns user}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}
   :project/test {:dependencies [[midje "1.8.3" :exclusions [org.clojure/clojure]]]
                  :resource-paths ["env/test/resources"]}
   :profiles/dev {}
   :profiles/test {}}

  :aliases {"wrap-package" ["shell" "src/scripts/wrap-package" ~version]
            "package" ["do" ["compile"] ["uberjar"] ["wrap-package"]]
            "clean-package" ["do" ["clean"] ["package"]]})
