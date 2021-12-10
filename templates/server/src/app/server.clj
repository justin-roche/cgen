(ns app.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defrecord Server [service-map service]

  component/Lifecycle
  (start [this]
    (if service
      this
      (cond-> service-map
        true                      http/create-server
        true                      ((partial assoc this :service)))))

  (stop [this]
    (http/stop service)
    (assoc this :service nil)))

(defn new-server
  []
  (map->Server {}))


