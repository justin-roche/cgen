(ns app.errors
  (:require
   [malli.provider :as mp]
   [io.pedestal.log :as pl]
   [malli.core :as m]
   [malli.error :as me]
   [clojure.data.json :as json]
   [io.pedestal.log :as pl]
   [io.pedestal.interceptor.error :as error]
   [io.pedestal.interceptor :as interceptor]
   [io.pedestal.test :refer :all]
   [io.pedestal.http :as service]
   [io.pedestal.http.route.definition :refer [defroutes]]
   [ring.util.response :as ring-resp]
   [reitit.interceptor :as ri]
   [clojure.test :refer :all]
   [io.pedestal.interceptor.error :as error-int]
   [com.stuartsierra.component :as component]))

(def error-messages {401 "Unauthorized"})

(defn update-res [ctx v]
  (update-in ctx [:response] #(merge % v)))

(defn handle-unknown-error [e]
  (pl/error "unknown error" e))

(defn error-interceptor []
  {:error (fn [ctx e]
            ;; (pl/info :error-interceptor (get-in (ex-data e) :interceptor))
            (println "error handler" (:cause (ex-data e)) (:interceptor (ex-data e)))
            (let [origin (:interceptor (ex-data e))
                  cause (:cause (ex-data e))
                  m (case origin
                      :app.auth/verify-token {:status 401}
                      (handle-unknown-error e))]
              ctx))})

(defn v [schema input]
  (let [r (m/validate schema input)]
    (if (not r)
      (println (str "\n***Validation Error***\n" (me/humanize (m/explain schema input))));   (do
      nil)))

(defn throw [status]
  (throw (new Exception)))

;; (update-res {:response {:status 401}} {:status 2})
