(ns app.server
  (:require
   [mount.core :refer [defstate start]]
   [io.pedestal.http :as server]
   [reitit.pedestal :as pedestal]
   [app.config :refer [config]]
   [reitit.http :as http]
   [app.router :as r]))

(defn start-server [service-map]
  (print service-map)
  (-> service-map
      (server/default-interceptors)
      (pedestal/replace-last-interceptor
       (pedestal/routing-interceptor
        (http/router (:routes []) r/route-data)))
      (server/dev-interceptors)
      (server/create-server)
      (server/start)))

(defn stop-server [server]
  (print "disconnect server")
  (server/stop server))


(defstate server :start (start-server config)
  :stop (stop-server server))

(start)
