(ns app.server
  (:require
   [mount.core :as mount :refer [defstate start stop]]
   [io.pedestal.http :as ps]
   [reitit.pedestal :as pedestal]
   [app.config :refer [config]]
   ;; [reitit.http :as http]
   [io.pedestal.http :as http]
   [app.router :as r]))

(defn start-server [service-map]
  (print "starting server")
  (-> (merge service-map {::http/routes r/routes})
      (#(http/start (http/create-server %))))
  ;; (-> service-map
  ;;     (ps/default-interceptors)
  ;;     (pedestal/replace-last-interceptor
  ;;      (pedestal/routing-interceptor
  ;;       (http/router r/routes r/route-data)))
  ;;     (ps/dev-interceptors)
  ;;     (ps/create-server)
  ;;     ;; (server/start)
  ;;     )
  )

(defn stop-server [server]
  (print "disconnect server"))

(defstate s :start (start-server
                    (:server config))
  :stop (do (println "\nstopping server") (http/stop s)))

;; (mount/defstate server :start (start-server config)
;;   :stop (ps/stop {}))

;; (start)
;; (stop)
