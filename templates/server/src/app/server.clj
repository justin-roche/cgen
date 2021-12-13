(ns app.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as server]
            [reitit.pedestal :as pedestal]
            [malli.provider :as mp]
            [malli.core :as m]
            [reitit.http :as http]
            [app.router :as r]))


(defrecord Server [service-map
                   router
                   service]

  component/Lifecycle
  (start [this]
    (-> service-map
        (server/default-interceptors)
        (pedestal/replace-last-interceptor
         (pedestal/routing-interceptor
          (http/router (:routes router) r/route-data)))
        (server/dev-interceptors)
        (server/create-server)
        (server/start)
        ((partial assoc this :service)))
    )

  (stop [this]
    (print "disconnect server")
    (do
      (server/stop (:service this))
      (assoc this :service nil))))

(defn new-server
  []
  (map->Server {}))


