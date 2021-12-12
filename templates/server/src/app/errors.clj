(ns app.errors
  (:require

   [clojure.data.json :as json]
   [com.stuartsierra.component :as component]))

(def error-messages {401 "Unauthorized"})

(defn error-interceptor
  []
  {:error (fn [ctx e]
            (let [status (:status (ex-data e))
                  message (get error-messages status)
                  body (json/write-str {:errors [{:message message}]})]
              (update-in ctx [:response] assoc :status status  :body body)))})

