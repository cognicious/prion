(defproject cognicious/prion "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.javassist/javassist "3.22.0-GA"]

                 [javax.servlet/javax.servlet-api "3.1.0"]
                 [bidi "2.0.16" :exclusions [commons-codec]]
                 [ring-middleware-format "0.7.2"]
                 [aleph "0.4.4"]]

  :source-paths      ["src/clj"]
  :java-source-paths ["src/java"]
  
  :profiles {:instrumentation
           {:main cognicious.prion.core
            :aot [cognicious.prion.state
                  cognicious.prion.core]
            :manifest {"Premain-Class" "cognicious.prion.core"                       
                       "Can-Redefine-Classes" "false"
                       "Can-Retransform-Classes" "true"
                       "Boot-Class-Path" "/Users/dan/Developer/cognicious/prion/target/prion-0.1.0-SNAPSHOT-standalone.jar"
                       :uberjar-name "prion.jar"}}})
