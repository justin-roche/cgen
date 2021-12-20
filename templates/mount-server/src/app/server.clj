(ns app.server
  (:require
   [mount.core :as mount :refer [defstate start stop]]
   [io.pedestal.http :as ps]
   [reitit.pedestal :as pedestal]
   [app.config :refer [config]]
   [reitit.http :as rt]
   [io.pedestal.http :as http]
   [taoensso.truss :as truss :refer [have]]
   [app.router :as r]))

(defn start-server [service-map]
  (print "starting server")
  (have keyword? (::http/type service-map))
  (-> service-map
      (ps/default-interceptors)
      (pedestal/replace-last-interceptor
       (pedestal/routing-interceptor
        (rt/router r/routes r/route-data)))
      (ps/dev-interceptors)
      (ps/create-server)
      (ps/start)))
(defn stop-server [server]
  (print "disconnect server"))

(defstate s :start (start-server
                    (:server config))
  :stop (do (println "\nstopping server") (http/stop s)))

;; (start)
;; (stop)
