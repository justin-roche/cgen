(ns app.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as server]
            [reitit.pedestal :as pedestal]
            [reitit.http :as http]
            [app.router :as r]
            [reitit.ring :as ring]))

(defrecord Server [service-map
                   router
                   service]

  component/Lifecycle
  (start [this]
    (println "got routes...")
    (clojure.pprint/pprint (:routes router))
    (println "got sm...")
    (clojure.pprint/pprint service-map)
    (if service
      this
      (-> service-map
          (server/default-interceptors)
          (pedestal/replace-last-interceptor
           (pedestal/routing-interceptor
            (http/router (:routes router ) r/route-data)))
          (server/dev-interceptors)
          (server/create-server)
          (server/start))))
  ;; (assoc this :service nil)

  (stop [this]
    ;; (/stop service)
    (assoc this :service nil)))

(defn new-server
  []
  (map->Server {}))

;; (let [routes  ]
  ;; (component/start router)
  ;; (component/stop router)
    ;; (clojure.pprint/pprint routes)
  ;; )
