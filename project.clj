(defproject tryouts "0.0.1"
  :description "Tryouts"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2173"]
                 [ring/ring "1.2.1"]
                 [org.clojure/core.async "0.1.278.0-76b25b-alpha"]
                 [om "0.5.3"]
                 [compojure "1.1.6"]
                 [fogus/ring-edn "0.2.0"]]

  :plugins [[lein-cljsbuild "1.0.2"]
            [lein-ring "0.8.10"]]

  :source-paths ["src/clj" "src/cljs"]
  :resource-paths ["resources"]

  :ring {:handler tryouts.service.core/app}

  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src/clj" "src/cljs"]
                        :compiler     {:output-to     "resources/public/js/cljs/main.js"
                                       :output-dir    "resources/public/js/cljs/out"
                                       :optimizations :none
                                       :source-map    true}}]})