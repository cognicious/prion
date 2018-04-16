(ns cognicious.prion.core
  (:require [aleph.http :as aleph-http]
            [cognicious.prion.state :refer [app]])
  (:import  [cognicious.prion PrionTransformer] 
            [javassist ClassPool CtClass CtMethod] 
            [java.io ByteArrayInputStream]
            [java.lang.instrument ClassFileTransformer Instrumentation])
  (:gen-class
   :methods [^:static [premain [String java.lang.instrument.Instrumentation] void]]))

(let [class-pool (doto (ClassPool/getDefault)
                   ;(.insertClassPath "/Users/dan/Developer/cognicious/prion/prion-0.1.0-SNAPSHOT.jar")
                   ;(.importPackage "cognicious.prion")
                   )]
  (defn -premain [^String args ^Instrumentation instrumentation]
    (println (str "                                                 \n"
                  "                          _/                     \n"
                  "     _/_/_/    _/  _/_/        _/_/    _/_/_/    \n"
                  "    _/    _/  _/_/      _/  _/    _/  _/    _/   \n"
                  "   _/    _/  _/        _/  _/    _/  _/    _/    \n"
                  "  _/_/_/    _/        _/    _/_/    _/    _/     \n"
                  " _/                                              \n"
                  "_/                                               \n"))

    (aleph-http/start-server app {:port 8080})
    (def ins11n instrumentation)

    (-> instrumentation
        (.addTransformer (PrionTransformer. class-pool)))))
