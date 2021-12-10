(ns app.system
  (:require [com.stuartsierra.component :as component]
            ;; [com.stuartsierra.component.repl
            ;;  :refer [reset set-init start stop system]]
            [io.pedestal.http :as http]
            [app.server :as server]
            [app.routes :as routes]
            ))

(def env 'dev')

(defn new-system
  []
  (component/system-map
   :service-map
   {
    :env          env
    ::http/routes routes/routes
    ::http/type   :jetty
    ::http/port   8890
    ::http/join?  false}
   :pedestal
   (component/using ( server/new-server ) [:service-map])))

