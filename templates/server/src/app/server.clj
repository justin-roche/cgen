(ns app.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defrecord Server [service-map server]

  component/Lifecycle
  (start [this]
    (assoc this :server (-> (http/create-server)
                            http/start)))

  (stop [this]
    (http/stop server)
    (assoc this :server nil)))

(defn new-server
  []
  (map->Server {}))


