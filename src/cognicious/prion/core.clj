(ns cognicious.prion.core
  (:import [java.lang.instrument ClassFileTransformer Instrumentation])
  (:gen-class
   :methods [^:static [premain [String java.lang.instrument.Instrumentation] void]]))

(defn -premain [^String args ^Instrumentation instrumentation]
  (println "Loading premain")
  (-> instrumentation
      (.addTransformer (proxy [ClassFileTransformer] []
                         (transform [loader className classBeingRedefined protectionDomain classfileBuffer]
                           (println {:class-name className})
                           nil)))))

