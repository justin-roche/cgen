(ns app.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http.route :as route]
            [io.pedestal.http :as http]))





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
      (-> (merge service-map {::http/routes (:routes router)})
          (#(http/start (http/create-server %))))))
  ;; (assoc this :service nil)

  (stop [this]
    (http/stop service)
    (assoc this :service nil)))

(defn new-server
  []
  (map->Server {}))

;; (let [routes  ]
  ;; (component/start router)
  ;; (component/stop router)
    ;; (clojure.pprint/pprint routes)
  ;; )
