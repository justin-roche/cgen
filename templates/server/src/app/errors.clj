(ns app.errors
  (:require
   [malli.provider :as mp]
   [malli.core :as m]
   [malli.error :as me]
   [clojure.data.json :as json]
   [com.stuartsierra.component :as component]))

(def error-messages {401 "Unauthorized"})

(defn error-interceptor
  []
  {:error (fn [ctx e]
            (println "error handler")
            (let [;;
                  status  (or (:status (ex-data e)) 501)
                  ;; body (json/write-str {:errors [{:message message}]}
                  ;; )
                  ]
              (update-in ctx [:response] assoc :status status :message (get error-messages status))))})

(defn v [schema input]
  (let [r (m/validate schema input)]
    (if (not r)
      (println (str "\n***Validation Error***\n" (me/humanize (m/explain schema input))));   (do
      nil)))

(defn throw [status]
  (throw (ex-info "problem" {:status status})))
