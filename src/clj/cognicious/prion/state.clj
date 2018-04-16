(ns cognicious.prion.state
  (:require [bidi.ring :refer [make-handler]]
            [clojure.string :refer [split]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [response status]]
            [ring.middleware.format :refer [wrap-restful-format]]))

(def telemetry (atom {}))

(defn maintenance
  [{:keys [ts delta max min last times] 
    :or {delta 0
         max   0
         min   0
         times 0}
    :as old}]
  (if ts
      (let [last (- (System/currentTimeMillis) ts)]
        {:delta (if (= delta 0) last (double (/ (+ delta last) 2)))
         :max   (if (> last max) last max)
         :min   (if (< last min) last (if (= min 0) last min))
         :last  last
         :times (inc times)})
      (merge old {:ts (System/currentTimeMillis)})))

(defn register
  [class-name method-name]
  (let [path (conj (split class-name #"/|\$") method-name)]
    (swap! telemetry update-in path maintenance)))

(defn clean
  []
  (reset! telemetry {}))

(defn index-handler [req]
  (response @telemetry))

(defn fn-handler [fn-to-invoke]
  (fn [req]
    (fn-to-invoke)))

(def handler
  (make-handler ["/" {"" index-handler
                      ["clear"] (fn-handler clean)}]))

(defn wrap-cors [handler]
  (fn [request]
    (let [response (handler request)]
      (assoc-in response [:headers "Access-Control-Allow-Origin"] "*"))))

(def app
  (-> handler
      wrap-keyword-params
      wrap-params
      wrap-cors
      (wrap-restful-format :formats [:json-kw :edn])))
