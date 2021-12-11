(ns app.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.interceptor :as i]
            [io.pedestal.http :as http]))


(defn get-db-interceptor [db]
  (i/interceptor {:name :database-interceptor
    :leave nil
    :enter
    (fn [context]
      (update context :request assoc :database db))}))

(defrecord Server [service-map
                   db
                   service]

  component/Lifecycle
  (start [this]
    (if service
      this
      (-> service-map
          (http/default-interceptors)
          (update ::http/interceptors conj (get-db-interceptor db))
          (#(http/start (http/create-server %)))
          ;; ((partial assoc this :service))
          )))

  (stop [this]
    (http/stop service)
    (assoc this :service nil)))

(defn new-server
  []
  (map->Server {}))


