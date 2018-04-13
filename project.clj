(defproject cognicious/prion "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]

  :profiles {:instrumentation
           {:main cognicious.prion.core
            :aot [cognicious.prion.core]
            :manifest {"Premain-Class" "cognicious.prion.core"
                       "Can-Redefine-Classes" "false"
                       "Can-Retransform-Classes" "true"
                       :uberjar-name "prion.jar"}}})
