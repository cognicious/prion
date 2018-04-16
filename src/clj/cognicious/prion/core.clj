(ns cognicious.prion.core
  (:import [cognicious.prion PrionTransformer] 
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
    (println "Loading premain")
    (-> instrumentation
        (.addTransformer (PrionTransformer. class-pool)))))
