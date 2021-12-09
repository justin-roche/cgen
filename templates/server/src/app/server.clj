(ns app.server
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]))

(defn respond-hello [request]
  {:status 200 :body "Hello, world!"}) 

(def routes
  (route/expand-routes
   #{["/greet" :get respond-hello :route-name :greet]})) 

(defrecord Server [server port]

  component/Lifecycle
  (start [this]
    (assoc this :server (->
                         ;; (lp/service-map {})
                         (http/create-server
                          {::http/routes routes
                           ::http/type   :jetty
                           ::http/port   8890})
                         http/start)))

  (stop [this]
    (http/stop server)
    (assoc this :server nil)))

(defn new-server
  []
  {:server (component/using (map->Server {:port 8888})
                            [])})


